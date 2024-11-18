package ru.magistu.siegemachines.client.renderer;

import org.jetbrains.annotations.Nullable;
import ru.magistu.siegemachines.SiegeMachines;
import ru.magistu.siegemachines.client.renderer.model.CatapultGeoModel;
import ru.magistu.siegemachines.entity.machine.Catapult;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CatapultGeoRenderer extends MachineGeoRenderer<Catapult>
{
	public CatapultGeoRenderer(EntityRendererProvider.Context renderManager)
	{
		super(renderManager, new CatapultGeoModel(SiegeMachines.id("catapult")));
	}

	@Override
	public @Nullable RenderType getRenderType(Catapult animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
