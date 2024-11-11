package ru.magistu.siegemachines.gui.machine.crosshair;

import net.minecraft.client.gui.GuiGraphics;
import ru.magistu.siegemachines.SiegeMachines;
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

    public final void render(GuiGraphics matrixstack, float partialticks) {}

    public abstract void render(GuiGraphics matrixstack, float ticks, Minecraft mc, Player player);
}