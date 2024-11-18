package ru.magistu.siegemachines.client.renderer;

import org.jetbrains.annotations.Nullable;
import ru.magistu.siegemachines.SiegeMachines;
import ru.magistu.siegemachines.client.renderer.model.MortarGeoModel;
import ru.magistu.siegemachines.entity.machine.Mortar;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MortarGeoRenderer extends MachineGeoRenderer<Mortar>
{
	public MortarGeoRenderer(EntityRendererProvider.Context renderManager)
	{
		super(renderManager, new MortarGeoModel(SiegeMachines.id("mortar")));
	}

	@Override
	public @Nullable RenderType getRenderType(Mortar animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
