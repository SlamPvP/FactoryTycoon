package com.slampvp.factory.minion;

import com.slampvp.factory.FactoryServer;

public final class MinionManager {
    private static MinionManager instance;

    private MinionManager() {
    }

    public static synchronized MinionManager getInstance() {
        if (instance == null) {
            instance = new MinionManager();
        }
        return instance;
    }

    public void init() {
        FactoryServer.LOGGER.info("Initializing Minion Manager...");
        FactoryServer.LOGGER.info("Initializing Minion Listener...");
        new MinionListener();
        FactoryServer.LOGGER.info("Initializing Minion Listener ✔");
        FactoryServer.LOGGER.info("Initializing Minion Manager ✔");
    }

}
