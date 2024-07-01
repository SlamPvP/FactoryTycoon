package com.slampvp.factory.common.menu;

import net.minestom.server.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class MenuManager {
    private static MenuManager instance;
    private final Map<UUID, Menu> openMenus;

    private MenuManager() {
        this.openMenus = new HashMap<>();
    }

    public static synchronized MenuManager getInstance() {
        if (instance == null) {
            instance = new MenuManager();
        }
        return instance;
    }

    public Optional<Menu> getOpenMenu(Player player) {
        return Optional.ofNullable(openMenus.get(player.getUuid()));
    }

    public void setOpenMenu(Player player, Menu menu) {
        openMenus.put(player.getUuid(), menu);
    }

    public void clearOpenMenu(Player player) {
        openMenus.remove(player.getUuid());
    }
}
