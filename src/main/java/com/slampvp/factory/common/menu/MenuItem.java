package com.slampvp.factory.common.menu;

import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.item.ItemStack;

import java.util.List;

public interface MenuItem {

    List<Integer> slots();

    ItemStack itemStack();

    default ClickAction action() {
        return new ClickAction() {
        };
    }

    interface ClickAction {
        default boolean canEquip() {
            return false;
        }

        default void onClick(InventoryPreClickEvent event) {
        }
    }
}
