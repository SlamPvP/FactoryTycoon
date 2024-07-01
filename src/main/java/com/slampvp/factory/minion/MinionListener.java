package com.slampvp.factory.minion;

import com.slampvp.factory.minion.models.Minion;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;

import java.util.Optional;

public class MinionListener {
    public MinionListener() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        MinionManager minionManager = MinionManager.getInstance();

        globalEventHandler.addListener(PlayerBlockPlaceEvent.class, event -> {
            Player player = event.getPlayer();
            ItemStack itemInHand = player.getItemInHand(event.getHand());

            String minionId = itemInHand.getTag(Tag.String("minion"));
            if (minionId == null) {
                return;
            }

            Optional<Minion> optionalMinion = Minion.byId(minionId);
            if (optionalMinion.isEmpty()) {
                return;
            }

            event.setCancelled(true);
            player.setItemInMainHand(itemInHand.withAmount(itemInHand.amount() - 1));
            minionManager.addMinion(player, event.getBlockPosition().asVec(), optionalMinion.get());
        });
    }
}
