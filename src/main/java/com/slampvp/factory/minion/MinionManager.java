package com.slampvp.factory.minion;

import com.slampvp.factory.FactoryServer;
import com.slampvp.factory.minion.models.Minion;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;

public final class MinionManager {
    private static MinionManager instance;

    private MinionManager() {
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

    public void addMinion(Player player, BlockVec position, Minion minion) {
        Entity displayEntity = new Entity(EntityType.ITEM_DISPLAY);
        displayEntity.setNoGravity(true);

        ItemDisplayMeta meta = (ItemDisplayMeta) displayEntity.getEntityMeta();

        meta.setItemStack(minion.getItem());
        meta.setScale(new Vec(1.5f, 1.5f, 1.5f));

        meta.setLeftRotation(new float[]{0.0f, 0.0f, 0.0f, 1.0f});
        meta.setRightRotation(new float[]{0.0f, 1.0f, 0.0f, 0.0f});
        meta.setTranslation(new Vec(2.0f, 0.0f, -1.0f));

        displayEntity.setInstance(player.getInstance(), position.add(0.5, 3, 0.5));
        displayEntity.teleport(new Pos(position).add(0.5, 3, 0.5));
    }
}
