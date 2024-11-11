package ru.magistu.siegemachines.client;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
import ru.magistu.siegemachines.client.renderer.*;
import ru.magistu.siegemachines.entity.ModEntityTypes;
import ru.magistu.siegemachines.event.ClientEvents;
import ru.magistu.siegemachines.item.MachineItem;
import ru.magistu.siegemachines.item.ModItems;

public class ClientProxyForge {
    public static void setup(IEventBus modEventBus)
    {
        modEventBus.addListener(ClientProxyForge::clientSetup);
        modEventBus.addListener(ClientProxyForge::registerRenderers);
        modEventBus.addListener(ClientProxyForge::extensions);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        ClientProx.setup();
        NeoForge.EVENT_BUS.addListener(ClientEvents::onKeyPressedEvent);
    }

    public static void extensions(RegisterClientExtensionsEvent event) {
        for (RegistrySupplier<Item> supplier : ModItems.ITEMS) {
            Item item = supplier.get();
            if (item instanceof MachineItem<?> machineItem) {
                event.registerItem(new IClientItemExtensions() {
                    @Override
                    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                        return machineItem.getRenderer();
                    }
                }, machineItem);
            }
        }
    }

    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(ModEntityTypes.MORTAR.get(), MortarGeoRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CULVERIN.get(), CulverinGeoRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.TREBUCHET.get(), TrebuchetGeoRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CATAPULT.get(), CatapultGeoRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.BALLISTA.get(), BallistaGeoRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.BATTERING_RAM.get(), BatteringRamGeoRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SIEGE_LADDER.get(), SiegeLadderGeoRenderer::new);

        event.registerEntityRenderer(ModEntityTypes.CANNONBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.GIANT_STONE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.STONE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.GIANT_ARROW.get(), GiantArrowRenderer::new);

        event.registerEntityRenderer(ModEntityTypes.SEAT.get(), SeatRenderer::new);
    }
}
