package ru.magistu.siegemachines.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import ru.magistu.siegemachines.SiegeMachines;
import ru.magistu.siegemachines.entity.machine.Trebuchet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

import java.util.Optional;

public class TrebuchetGeoRenderer extends MachineGeoRenderer<Trebuchet>
{
	public TrebuchetGeoRenderer(EntityRendererProvider.Context renderManager)
	{
		super(renderManager, new DefaultedEntityGeoModel<>(SiegeMachines.id("trebuchet")));
	}

	@Override
	public @Nullable RenderType getRenderType(Trebuchet animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, Trebuchet animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
		Optional<GeoBone> projectile = model.getBone("Cobblestone");
		int projectilesize = (animatable.state == Trebuchet.State.IDLE_RELOADED || animatable.shootingticks > 0) && animatable.hasAmmo() ? 1 : 0;
		projectile.ifPresent(bone -> bone.setScaleX(projectilesize));
		projectile.ifPresent(bone -> bone.setScaleY(projectilesize));
		projectile.ifPresent(bone -> bone.setScaleZ(projectilesize));

		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
	}

	@Override
	public boolean shouldRender(Trebuchet animatable, Frustum camera, double camx, double camy, double camz)
	{
		if (!animatable.shouldRender(camx, camy, camz)) {
			return false;
		} else if (animatable.noCulling) {
			return true;
		} else {
			AABB aabb = animatable.getBoundingBoxForCulling().inflate(10.0D);
			if (aabb.hasNaN() || aabb.getSize() == 0.0D) {
				aabb = new AABB(animatable.getX() - 2.0D, animatable.getY() - 2.0D, animatable.getZ() - 2.0D, animatable.getX() + 2.0D, animatable.getY() + 2.0D, animatable.getZ() + 2.0D);
			}

			return camera.isVisible(aabb);
		}
	}
}
