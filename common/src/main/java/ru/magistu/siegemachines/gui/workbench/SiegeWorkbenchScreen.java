package ru.magistu.siegemachines.gui.workbench;

import net.minecraft.client.gui.GuiGraphics;
import org.lwjgl.glfw.GLFW;
import ru.magistu.siegemachines.SiegeMachines;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class SiegeWorkbenchScreen extends AbstractContainerScreen<SiegeWorkbenchContainer>
{
	private static final ResourceLocation DISPLAY_CASE_GUI = SiegeMachines.id("textures/gui/siege_workbench.png");

	public SiegeWorkbenchScreen(SiegeWorkbenchContainer screenContainer, Inventory inv, Component titleIn)
    {
		super(screenContainer, inv, titleIn);
	}

	@Override
	public void render(GuiGraphics matrixStack, int mouseX, int mouseY, float partialTicks)
    {
		this.renderBackground(matrixStack,mouseX,mouseY,partialTicks);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void init()
	{
		super.init();
		this.titleLabelX = 29;
	}

	@Override
	protected void renderBg(@NotNull GuiGraphics matrixStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    	int i = this.leftPos;
    	int j = (this.height - this.imageHeight) / 2;
    	matrixStack.blit(DISPLAY_CASE_GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);
	}

	@Override
	public boolean keyPressed(int key, int b, int c)
	{
		assert this.minecraft != null;
		if (key == GLFW.GLFW_KEY_ESCAPE)
		{
			assert this.minecraft.player != null;
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}
}
