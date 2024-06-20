package com.slampvp.factory;

import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.common.Constants;
import com.slampvp.factory.plot.PlotGenerator;
import com.slampvp.factory.plot.PlotManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.anvil.AnvilLoader;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Stream;


public final class FactoryServer {
    public static final Logger LOGGER = LoggerFactory.getLogger(FactoryServer.class);

    public static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();

        MojangAuth.init();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        instanceContainer.setChunkSupplier(LightingChunk::new);
        instanceContainer.setGenerator(PlotGenerator.getGenerator());
        instanceContainer.setChunkLoader(new AnvilLoader("worlds/world"));

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            Player player = event.getPlayer();

            event.setSpawningInstance(instanceContainer);

            player.setRespawnPoint(new Pos(0.5, Constants.Plot.HEIGHT + 1, 0.5));
            player.setGameMode(GameMode.CREATIVE);
        });

//        MinecraftServer.getSchedulerManager().buildShutdownTask(() -> {
//            instanceContainer.saveChunksToStorage().thenAccept(t -> LOGGER.info("Saved world."));
//        });

        PlotManager.getInstance().init();

        streamPackage("com.slampvp.factory.command", FactoryCommand.class).forEach(c ->
                MinecraftServer.getCommandManager().register(c)
        );

        minecraftServer.start("0.0.0.0", 25565);
    }

    public static <T> Stream<T> streamPackage(String packageName, Class<T> clazz) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends T>> subTypes = reflections.getSubTypesOf(clazz);

        return subTypes
                .stream()
                .filter(klazz -> !klazz.getPackageName().contains("sub"))
                .map(klazz -> {
                    try {
                        T instance = clazz.cast(klazz.getDeclaredConstructor().newInstance());

                        if (instance instanceof FactoryCommand command) {
                            String subPackage = instance.getClass().getPackageName() + ".sub";
                            Reflections subReflections = new Reflections(subPackage);

                            subReflections.getSubTypesOf(clazz).forEach(subCommand -> {
                                try {
                                    command.addSubcommand((Command) subCommand.getDeclaredConstructor().newInstance());
                                } catch (InstantiationException | IllegalAccessException |
                                         InvocationTargetException | NoSuchMethodException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }

                        return instance;
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                             InvocationTargetException e) {
                        return null;
                    }
                })
                .filter(java.util.Objects::nonNull);
    }

}
