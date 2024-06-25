package com.slampvp.factory.common;

import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.HeadProfile;
import org.jetbrains.annotations.NotNull;

public class ItemStackUtil {
    public static @NotNull ItemStack.Builder texturedHead(String texture) {
        ItemStack.Builder skull = ItemStack.builder(Material.PLAYER_HEAD);
        HeadProfile profile = new HeadProfile(new PlayerSkin(texture, null));
        skull.set(ItemComponent.PROFILE, profile);
        return skull;
    }
}
