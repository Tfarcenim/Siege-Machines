package ru.magistu.siegemachines.client.gui.machine.crosshair;

import ru.magistu.siegemachines.SiegeMachines;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;


public abstract class Crosshair extends HudElement
{
    protected static final ResourceLocation CROSSHAIR_TEXTURES = SiegeMachines.id("textures/gui/crosshairs.png");

    public Crosshair(int width, int height)
    {
        super(width, height);
    }

    public final void render(PoseStack matrixstack, float partialticks) {}

    public abstract void render(PoseStack matrixstack, float ticks, Minecraft mc, Player player);
}