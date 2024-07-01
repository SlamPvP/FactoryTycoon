package com.slampvp.factory.minion;

import com.slampvp.factory.FactoryServer;
import com.slampvp.factory.database.DatabaseFormatter;
import com.slampvp.factory.database.DatabaseManager;
import com.slampvp.factory.database.queries.MinionQueries;
import com.slampvp.factory.minion.models.ActiveMinion;
import com.slampvp.factory.minion.models.Minion;
import com.slampvp.factory.minion.models.MinionStats;
import com.slampvp.factory.minion.models.MinionUpgrades;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.tag.Tag;
import net.minestom.server.timer.TaskSchedule;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public final class MinionManager {
    private static MinionManager instance;

    private final HashSet<ActiveMinion> minions;

    private MinionManager() {
        this.minions = new HashSet<>();
    }

    public static synchronized MinionManager getInstance() {
        if (instance == null) {
            instance = new MinionManager();
        }
        return instance;
    }

    public void init() {
        FactoryServer.LOGGER.info("Initializing Minion Manager...");
        FactoryServer.LOGGER.info("Initializing Minion Listener...");
        new MinionListener();
        FactoryServer.LOGGER.info("Initializing Minion Listener ✔");
        FactoryServer.LOGGER.info("Initializing Minion Manager ✔");
    }

    public void loadMinions(Player player) {
        DatabaseManager.getInstance().executeQuery(
                MinionQueries.Select.BY_OWNER,
                preparedStatement -> {
                    preparedStatement.setString(1, player.getUuid().toString());
                },
                resultSet -> {
                    try {
                        while (resultSet.next()) {
                            Optional<Minion> optionalMinion = Minion.byId(resultSet.getString("minion_id"));

                            if (optionalMinion.isEmpty()) {
                                throw new IllegalArgumentException("Unknown Minion");
                            }

                            ActiveMinion minion = new ActiveMinion(
                                    resultSet.getLong("id"),
                                    DatabaseFormatter.stringToBlockVec(resultSet.getString("chest_position")),
                                    DatabaseFormatter.stringToVec(resultSet.getString("position")),
                                    new MinionUpgrades(),
                                    new MinionStats(),
                                    optionalMinion.get(),
                                    UUID.fromString(resultSet.getString("owner"))
                            );

                            spawnMinionEntity(minion, player.getInstance());
                            this.minions.add(minion);
                        }
                    } catch (SQLException e) {
                        FactoryServer.LOGGER.error(e.toString());
                        throw new RuntimeException("Error processing ResultSet", e);
                    } finally {
                        try {
                            resultSet.close();
                        } catch (SQLException e) {
                            FactoryServer.LOGGER.error(e.toString());
                        }
                    }
                });
    }

    public void addMinion(Player player, Vec vec, Minion minion) {
        ActiveMinion activeMinion = new ActiveMinion(minion, player.getUuid(), vec);

        DatabaseManager.getInstance().executeUpdate(MinionQueries.Insert.MINION, preparedStatement -> {
            preparedStatement.setObject(1, minion.id(), Types.OTHER);
            preparedStatement.setString(2, player.getUuid().toString());
            preparedStatement.setLong(3, 0);
            preparedStatement.setLong(4, 0);
            preparedStatement.setObject(5, DatabaseFormatter.pointToString(activeMinion.position()), Types.OTHER);
            preparedStatement.setObject(6, DatabaseFormatter.pointToString(activeMinion.chestPosition()), Types.OTHER);
        }).thenAccept(id -> {
            activeMinion.setDbId(id);
            spawnMinionEntity(activeMinion, player.getInstance());
        });
    }

    private void spawnMinionEntity(ActiveMinion minion, Instance instance) {
        Entity displayEntity = new Entity(EntityType.ITEM_DISPLAY);
        displayEntity.setNoGravity(true);
        displayEntity.setTag(Tag.Long("minion"), minion.dbId());

        ItemDisplayMeta meta = (ItemDisplayMeta) displayEntity.getEntityMeta();

        Pos originalPosition = minion.position().add(0, 5, 0).asPosition();
        displayEntity.setInstance(instance, originalPosition);

        meta.setItemStack(minion.minion().getItem());
        meta.setScale(new Vec(1.5f, 1.5f, 1.5f));
        meta.setTranslation(new Vec(-0.5f, -0.5f, -0.5f));
        meta.setPosRotInterpolationDuration(20);

        AtomicReference<Float> angle = new AtomicReference<>(0.0F);
        AtomicReference<Float> pulseAngle = new AtomicReference<>(0.0F);

        MinecraftServer.getSchedulerManager().scheduleTask(() -> {
            // Rotate around Z-axis
            float currentAngle = angle.getAndUpdate(aFloat -> (aFloat + 5F) % 360F);

            // Pulse effect: update y-position and scale
            float currentPulseAngle = pulseAngle.getAndUpdate(aFloat -> (aFloat + 1F) % 360F);
            double radians = Math.toRadians(currentPulseAngle);
            double yOffset = 1.5 * Math.sin(radians);  // y oscillates between -1.5 and +1.5

            float scaleFactor;
            if (Math.abs(yOffset) <= 1.5) {
                // When between y=0 and y=2 or y=-2, scale oscillates between 1.5 and 1.4
                scaleFactor = 1.5f - 0.1f * (float) Math.abs(yOffset) / 1.5f;
            } else {
                // Elsewhere, use a fixed smaller scale
                scaleFactor = 1.2f;
            }

            Pos newPosition = originalPosition.add(0, yOffset, 0).withView(currentAngle, 0);
            Vec newScale = new Vec(scaleFactor, scaleFactor, scaleFactor);

            displayEntity.refreshPosition(newPosition);
            meta.setScale(newScale);
        }, TaskSchedule.tick(1), TaskSchedule.tick(1));
    }
}
