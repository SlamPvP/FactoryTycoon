package com.slampvp.factory.common.menu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryOpenEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
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
                new MenuItem() {
                    @Override
                    public List<Integer> slots() {
                        return IntStream.range(0,9).boxed().toList();
                    }

                    @Override
                    public ItemStack itemStack() {
                        return ItemStack.of(Material.ACACIA_BOAT);
                    }

                    @Override
                    public ClickAction action() {
                        return new ClickAction() {
                            @Override
                            public void onClick(InventoryPreClickEvent event) {
                                System.out.println("click");
                            }
                        };
                    }
                }
        );
    }
}
