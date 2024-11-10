package ru.magistu.siegemachines.client;

import net.minecraft.client.gui.screens.MenuScreens;
import ru.magistu.siegemachines.gui.ModMenuTypes;
import ru.magistu.siegemachines.gui.workbench.SiegeWorkbenchScreen;


public class ClientProx {
    public static void setup(){
        MenuScreens.register(ModMenuTypes.SIEGE_WORKBENCH_CONTAINER.get(), SiegeWorkbenchScreen::new);
    }
}
