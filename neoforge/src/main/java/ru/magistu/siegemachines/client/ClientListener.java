package ru.magistu.siegemachines.client;

import ru.magistu.siegemachines.SiegeMachines;
import ru.magistu.siegemachines.client.renderer.*;
import ru.magistu.siegemachines.entity.ModEntityTypes;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SiegeMachines.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientListener
{
	@SubscribeEvent
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
