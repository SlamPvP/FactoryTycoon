package com.slampvp.factory.common;

import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.HeadProfile;
import org.jetbrains.annotations.NotNull;

public class SkinnableHead {
    private final String texture;

    /**
     * @param texture The base64 texture of player head
     */
    public SkinnableHead(@NotNull String texture) {
        this.texture = texture;
    }

    public @NotNull ItemStack.Builder builder() {
        ItemStack.Builder skull = ItemStack.builder(Material.PLAYER_HEAD);
        HeadProfile profile = new HeadProfile(new PlayerSkin(this.texture, null));
        skull.set(ItemComponent.PROFILE, profile);
        return skull;
    }

    public @NotNull ItemStack build() {
        return builder().build();
    }
}
