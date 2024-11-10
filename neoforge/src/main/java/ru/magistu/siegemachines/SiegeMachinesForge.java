package ru.magistu.siegemachines;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import ru.magistu.siegemachines.block.ModBlocks;
import ru.magistu.siegemachines.client.ClientProxyForge;
import ru.magistu.siegemachines.config.SpecsConfig;
import ru.magistu.siegemachines.datagen.ModDatagen;
import ru.magistu.siegemachines.item.recipes.ModRecipes;
import ru.magistu.siegemachines.entity.ModEntityTypes;
import ru.magistu.siegemachines.client.gui.ModMenuTypes;
import ru.magistu.siegemachines.item.ModItems;
import ru.magistu.siegemachines.network.PacketHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SiegeMachines.ID)
public class SiegeMachinesForge {

    public static final int RENDER_UPDATE_RANGE = 128;
    public static final int RENDER_UPDATE_TIME = 20;
    public static final int RENDER_UPDATE_RANGE_SQR = RENDER_UPDATE_RANGE * RENDER_UPDATE_RANGE;
    
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();



    public SiegeMachinesForge(IEventBus bus, Dist dist) {
        bus.addListener(ModDatagen::gather);
        ModEntityTypes.register();
        SoundTypes.register();
        ModMenuTypes.register();
        ModBlocks.register();
        ModItems.register();
        ModRecipes.register();
        SpecsConfig.register();

        PacketHandler.init();

        if (dist.isClient()) {
            ClientProxyForge.setup(bus);
        }

        SiegeMachines.init();

    }
}
