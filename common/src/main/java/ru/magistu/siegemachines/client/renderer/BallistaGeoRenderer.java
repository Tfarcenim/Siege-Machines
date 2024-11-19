package ru.magistu.siegemachines.client.renderer;

import org.jetbrains.annotations.Nullable;
import ru.magistu.siegemachines.SiegeMachines;
import ru.magistu.siegemachines.client.renderer.model.BallistaGeoModel;
import ru.magistu.siegemachines.entity.machine.Ballista;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BallistaGeoRenderer extends MachineGeoRenderer<Ballista> {
	public BallistaGeoRenderer(EntityRendererProvider.Context renderManager)
	{
		super(renderManager, new BallistaGeoModel(SiegeMachines.id("ballista")));
	}

	@Override
	public @Nullable RenderType getRenderType(Ballista animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
