package com.slampvp.factory.common.menu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryOpenEvent;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.IntStream;

public class TestMenu implements Menu {
    @Override
    public @NotNull TextComponent name() {
        return Component.text("test");
    }

    @Override
    public @NotNull InventoryType type() {
        return InventoryType.CHEST_4_ROW;
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        event.getPlayer().sendMessage("open");
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        event.getPlayer().sendMessage("close");
    }

    @Override
    public @NotNull List<MenuItem> items() {
        return List.of(
                new MenuItem(
                        IntStream.range(0, 9),
                        ItemStack.of(Material.ACACIA_BOAT)
                ),
                MenuItem.CLOSE.apply(31)
        );
    }
}
