package ru.magistu.siegemachines.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.Nullable;
import ru.magistu.siegemachines.SiegeMachines;
import ru.magistu.siegemachines.entity.machine.Culverin;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

import java.util.Optional;

public class CulverinGeoRenderer extends MachineGeoRenderer<Culverin>
{
	public CulverinGeoRenderer(EntityRendererProvider.Context renderManager)
	{
		super(renderManager, new DefaultedEntityGeoModel<>(SiegeMachines.id("culverin")));
	}

	@Override
	public @Nullable RenderType getRenderType(Culverin animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, Culverin animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
	//	GeoModelProvider<Culverin> modelProvider = this.getGeoModelProvider();
	//	GeoModel model = modelProvider.getModel(modelProvider.getModelResource(animatable));

		Optional<GeoBone> bone0 = model.getBone("Barrel");
		bone0.ifPresent(bone -> bone.setRotX(-animatable.getTurretPitch() * (float) Math.PI / 180.0f));
		bone0.ifPresent(bone -> bone.setRotY(-animatable.getTurretYaw() * (float) Math.PI / 180.0f));
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
	}
}
