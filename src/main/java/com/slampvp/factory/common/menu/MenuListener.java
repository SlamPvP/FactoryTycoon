package com.slampvp.factory.common.menu;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryOpenEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;

import java.util.Optional;

public class MenuListener {
    public MenuListener() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        globalEventHandler.addListener(InventoryPreClickEvent.class, event -> {
            Player player = event.getPlayer();
            Optional<Menu> optionalMenu = MenuManager.getInstance().getOpenMenu(player);

            if (optionalMenu.isEmpty()) {
                return;
            }

            Menu menu = optionalMenu.get();
            int slot = event.getSlot();

            Optional<MenuItem> optionalMenuItem = menu.itemBySlot(slot);

            if (optionalMenuItem.isEmpty()) {
                return;
            }

            MenuItem item = optionalMenuItem.get();

            if (!item.action().canEquip()) {
                event.setCancelled(true);
            }

            item.action().onClick(event);
        });

        globalEventHandler.addListener(InventoryOpenEvent.class, event -> {
            Player player = event.getPlayer();
            Optional<Menu> optionalMenu = MenuManager.getInstance().getOpenMenu(player);

            if (optionalMenu.isEmpty()) {
                return;
            }

            Menu menu = optionalMenu.get();
            menu.onOpen(event);
        });

        globalEventHandler.addListener(InventoryCloseEvent.class, event -> {
            Player player = event.getPlayer();
            Optional<Menu> optionalMenu = MenuManager.getInstance().getOpenMenu(player);

            if (optionalMenu.isEmpty()) {
                return;
            }

            Menu menu = optionalMenu.get();
            menu.onClose(event);
        });
    }
}
