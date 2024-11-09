package ru.magistu.siegemachines.client;

import net.minecraft.client.gui.screens.MenuScreens;

public class ClientProx {
    public static void setup(){
        MenuScreens.register(ModMenuTypes.MACHINE_CONTAINER.get(), MachineInventoryScreen::new);
        MenuScreens.register(ModMenuTypes.SIEGE_WORKBENCH_CONTAINER.get(), SiegeWorkbenchScreen::new);
    }
}
