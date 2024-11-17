package ru.magistu.siegemachines.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.Nullable;
import ru.magistu.siegemachines.SiegeMachines;
import ru.magistu.siegemachines.entity.machine.Ballista;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

import java.util.Optional;

public class BallistaGeoRenderer extends MachineGeoRenderer<Ballista> {
	public BallistaGeoRenderer(EntityRendererProvider.Context renderManager)
	{
		super(renderManager, new DefaultedEntityGeoModel<>(SiegeMachines.id("ballista")));
	}

	@Override
	public @Nullable RenderType getRenderType(Ballista animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, Ballista animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
		//GeoModelProvider<Ballista> modelProvider = this.getGeoModelProvider();
		//GeoModel model = modelProvider.getModel(modelProvider.getModelResource(animatable));

		Optional<GeoBone> turret = model.getBone("Balliste");
		turret.ifPresent(bone -> bone.setRotX(-animatable.getTurretPitch() * (float) Math.PI / 180.0f));
		turret.ifPresent(bone -> bone.setRotY(-animatable.getTurretYaw() * (float) Math.PI / 180.0f));

		Optional<GeoBone> projectile = model.getBone("BallistaArrow");
		projectile.ifPresent(bone -> bone.setRotX(-animatable.getTurretPitch() * (float) Math.PI / 180.0f));
		projectile.ifPresent(bone -> bone.setRotY(-animatable.getTurretYaw() * (float) Math.PI / 180.0f));
		int useticks = animatable.getUseTicks();
		boolean shouldrender = ((useticks <= 0 && animatable.shootingticks <= 0) || (useticks > 0 && animatable.shootingticks > 0)) && animatable.getDelayTicks() <= 0;
		int projectilesize = shouldrender && animatable.hasAmmo() ? 1 : 0;
		projectile.ifPresent(bone -> bone.setScaleX(projectilesize));
		projectile.ifPresent(bone -> bone.setScaleY(projectilesize));
		projectile.ifPresent(bone -> bone.setScaleZ(projectilesize));

		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
	}
}
