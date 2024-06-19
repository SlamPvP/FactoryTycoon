package com.slampvp.factory.util;

import com.slampvp.factory.FactoryServer;

public abstract class Manager {
    public Manager() {
        FactoryServer.LOGGER.info("Loading {}", getClass().getSimpleName());
        load();
    }

    public abstract void load();
}
