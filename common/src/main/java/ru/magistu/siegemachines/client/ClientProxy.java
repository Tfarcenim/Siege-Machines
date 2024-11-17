package ru.magistu.siegemachines.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.entity.EntityType;
import ru.magistu.siegemachines.entity.ModEntityTypes;
import ru.magistu.siegemachines.gui.ModMenuTypes;
import ru.magistu.siegemachines.gui.machine.crosshair.Crosshair;
import ru.magistu.siegemachines.gui.machine.crosshair.ReloadingCrosshair;
import ru.magistu.siegemachines.gui.workbench.SiegeWorkbenchScreen;

import java.util.HashMap;
import java.util.Map;


public class ClientProxy {

    public static final Map<EntityType<?>, Crosshair> CROSSHAIR_FACTORIES = new HashMap<>();

    public static void setup(){
        MenuScreens.register(ModMenuTypes.SIEGE_WORKBENCH_CONTAINER.get(), SiegeWorkbenchScreen::new);

        CROSSHAIR_FACTORIES.put(ModEntityTypes.BALLISTA.get(),new ReloadingCrosshair());
        CROSSHAIR_FACTORIES.put(ModEntityTypes.CULVERIN.get(),new ReloadingCrosshair());
        CROSSHAIR_FACTORIES.put(ModEntityTypes.MORTAR.get(),new ReloadingCrosshair());
        CROSSHAIR_FACTORIES.put(ModEntityTypes.CATAPULT.get(),new ReloadingCrosshair());
        CROSSHAIR_FACTORIES.put(ModEntityTypes.TREBUCHET.get(),new ReloadingCrosshair());
    }
}
