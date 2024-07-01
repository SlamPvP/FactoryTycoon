package com.slampvp.factory.common.menu;

import net.kyori.adventure.text.TextComponent;
import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryOpenEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface Menu {
    @NotNull
    TextComponent name();

    @NotNull
    InventoryType type();

    @NotNull
    List<MenuItem> items();

    default Optional<MenuItem> itemBySlot(int slot) {
        return items().stream()
                .filter(menuItem -> menuItem.slots().contains(slot))
                .findFirst();
    }

    default void open(Player player) {
        MenuManager.getInstance().setOpenMenu(player, this);

        Inventory inventory = new Inventory(type(), name());

        items().forEach(menuItem -> menuItem.slots().forEach(slot -> inventory.setItemStack(slot, menuItem.itemStack())));

        player.openInventory(inventory);
    }

    default void onOpen(InventoryOpenEvent event) {
    }

    default void onClose(InventoryCloseEvent event) {
    }

}
