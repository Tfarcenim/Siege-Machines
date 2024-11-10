package ru.magistu.siegemachines.item;


import com.mojang.math.Vector3d;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class GiantStone extends Missile
{
	public GiantStone(EntityType<GiantStone> entitytype, Level level)
    {
		super(entitytype, level);
		this.item = ModItems.GIANT_STONE.get();
	}

	public GiantStone(EntityType<GiantStone> entitytype, Level level, Vector3d pos, LivingEntity entity, Item item)
    {
		super(entitytype, level, pos, entity, MissileType.GIANT_STONE, item);
	}
}
