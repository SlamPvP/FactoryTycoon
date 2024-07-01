package com.slampvp.factory.common.menu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

public record MenuItem(List<Integer> slots, ItemStack itemStack, ClickAction action) {
    public static Function<Integer, MenuItem> CLOSE = slot ->
            new MenuItem(slot,
                    ItemStack.of(Material.BARRIER)
                            .withCustomName(Component.text("Close").color(NamedTextColor.DARK_RED).decorate(TextDecoration.BOLD)),
                    event -> {
                        Player player = event.getPlayer();
                        player.closeInventory();
                        MenuManager.getInstance().clearOpenMenu(player);
                    });

    public MenuItem(IntStream stream, ItemStack itemStack) {
        this(stream.boxed().toList(), itemStack, new ClickAction() {
        });
    }

    public MenuItem(IntStream stream, ItemStack itemStack, ClickAction action) {
        this(stream.boxed().toList(), itemStack, action);
    }

    public MenuItem(IntStream stream, ItemStack itemStack, Consumer<InventoryPreClickEvent> consumer) {
        this(stream.boxed().toList(), itemStack, true, consumer);
    }

    public MenuItem(IntStream stream, ItemStack itemStack, boolean cancel, Consumer<InventoryPreClickEvent> consumer) {
        this(stream.boxed().toList(), itemStack, cancel, consumer);
    }

    public MenuItem(List<Integer> slots, ItemStack itemStack) {
        this(slots, itemStack, new ClickAction() {
        });
    }

    public MenuItem(List<Integer> slots, ItemStack itemStack, Consumer<InventoryPreClickEvent> consumer) {
        this(slots, itemStack, true, consumer);
    }

    public MenuItem(List<Integer> slots, ItemStack itemStack, boolean cancel, Consumer<InventoryPreClickEvent> consumer) {
        this(slots, itemStack, new ClickAction() {
            @Override
            public boolean cancel() {
                return cancel;
            }

            @Override
            public void onClick(InventoryPreClickEvent event) {
                consumer.accept(event);
            }
        });
    }

    public MenuItem(int slot, ItemStack itemStack) {
        this(List.of(slot), itemStack, new ClickAction() {
        });
    }

    public MenuItem(int slot, ItemStack itemStack, Consumer<InventoryPreClickEvent> consumer) {
        this(List.of(slot), itemStack, consumer);
    }

    public MenuItem(int slot, ItemStack itemStack, boolean cancel, Consumer<InventoryPreClickEvent> consumer) {
        this(List.of(slot), itemStack, cancel, consumer);
    }

    public interface ClickAction {
        default boolean cancel() {
            return true;
        }

        default void onClick(InventoryPreClickEvent event) {
        }
    }
}
