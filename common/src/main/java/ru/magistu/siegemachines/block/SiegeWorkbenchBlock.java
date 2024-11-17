package ru.magistu.siegemachines.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class SiegeWorkbenchBlock extends CraftingTableBlock
{
    private static final Component CONTAINER_TITLE = Component.translatable("container.crafting");

    public SiegeWorkbenchBlock(Properties p_i48422_1_)
    {
        super(p_i48422_1_);
    }

    public @NotNull InteractionResult use(@NotNull BlockState blockstate, Level level, @NotNull BlockPos blockpos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result)
    {
        if (level.isClientSide)
        {
            return InteractionResult.SUCCESS;
        }
        else
        {
            player.openMenu(this.getMenuProvider(blockstate, level, blockpos));
            return InteractionResult.CONSUME;
        }
    }
}
