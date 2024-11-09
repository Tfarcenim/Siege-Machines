package ru.magistu.siegemachines.client;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxyForge {
    public static void setup(IEventBus modEventBus)
    {
        modEventBus.addListener(ClientProxyForge::clientSetup);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        ClientProx.setup();
    }
}
