package ru.magistu.siegemachines;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import ru.magistu.siegemachines.block.ModBlocks;
import ru.magistu.siegemachines.client.ClientProxyForge;
import ru.magistu.siegemachines.config.SpecsConfig;
import ru.magistu.siegemachines.datagen.ModDatagen;
import ru.magistu.siegemachines.gui.ModMenuTypes;
import ru.magistu.siegemachines.item.recipes.ModRecipeSerializers;
import ru.magistu.siegemachines.entity.ModEntityTypes;
import ru.magistu.siegemachines.item.ModItems;
import ru.magistu.siegemachines.network.PacketHandler;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SiegeMachines.ID)
public class SiegeMachinesForge {

    public SiegeMachinesForge(IEventBus bus, Dist dist, ModContainer modContainer) {
        bus.addListener(ModDatagen::gather);
        ModEntityTypes.register();
        SoundTypes.register();
        ModMenuTypes.register();
        ModBlocks.register();
        ModItems.register();
        ModRecipeSerializers.register();

        PacketHandler.init();

        modContainer.registerConfig(ModConfig.Type.SERVER,SpecsConfig.SPEC,"siege-machines-specs.toml");

        if (dist.isClient()) {
            ClientProxyForge.setup(bus);
        }

        SiegeMachines.init();

    }
}
