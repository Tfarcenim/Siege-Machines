package ru.magistu.siegemachines.entity.machine;

import ru.magistu.siegemachines.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import ru.magistu.siegemachines.util.CartesianGeometry;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Trebuchet extends ShootingMachine implements GeoEntity
{
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    static RawAnimation SHOOTING_ANIM = RawAnimation.begin().then("Shooting", Animation.LoopType.LOOP);
    static RawAnimation RELOADING_ANIM = RawAnimation.begin().then("Reloading", Animation.LoopType.LOOP);
    static RawAnimation IDLE_RELOADED_ANIM = RawAnimation.begin().then("IdleReloaded", Animation.LoopType.LOOP);
    static RawAnimation IDLE_NOT_RELOADED_ANIM = RawAnimation.begin().then("IdleNotReloaded", Animation.LoopType.LOOP);

    private final MachinePartEntity[] subentities;
    private final MachinePartEntity backside;

    private final Vec3 backsidepos;

    public enum State
    {
        SHOOTING,
        RELOADING,
        IDLE_RELOADED,
        IDLE_NOT_RELOADED
    }
    public State state = State.RELOADING;

    public Trebuchet(EntityType<? extends Mob> entitytype, Level level)
    {
        super(entitytype, level, MachineType.TREBUCHET);
        this.backside = new MachinePartEntity(this, "backside", 5.0F, 2.0F);
        this.backsidepos = new Vec3(0.0, 0.0, -85.0).scale(1.0 / 16.0);
        this.subentities = new MachinePartEntity[] { this.backside };
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event)
    {
        switch (state) {
            case SHOOTING -> {
                event.getController().setAnimation(SHOOTING_ANIM);
                return PlayState.CONTINUE;
            }
            case IDLE_RELOADED -> {
                event.getController().setAnimation(IDLE_RELOADED_ANIM);
                return PlayState.CONTINUE;
            }
            case RELOADING -> {
                event.getController().setAnimation(RELOADING_ANIM);
                return PlayState.CONTINUE;
            }
            case IDLE_NOT_RELOADED -> {
                event.getController().setAnimation(IDLE_NOT_RELOADED_ANIM);
                return PlayState.CONTINUE;
            }
        }

        return PlayState.CONTINUE;
    }

    private void tickPart(MachinePartEntity subentity, double p_226526_2_, double p_226526_4_, double p_226526_6_)
    {
        subentity.setPos(this.getX() + p_226526_2_, this.getY() + p_226526_4_, this.getZ() + p_226526_6_);
    }

    @Override
    public void aiStep()
    {
        Vec3[] avector3d = new Vec3[this.subentities.length];

        Vec3 pos = this.position().add(CartesianGeometry.applyRotations(this.backsidepos, 0.0, this.getYaw()));
        this.tickPart(this.backside, pos.x, pos.y, pos.z);

        for(int i = 0; i < this.subentities.length; ++i)
        {
            avector3d[i] = new Vec3(this.subentities[i].getX(), this.subentities[i].getY(), this.subentities[i].getZ());
        }

        for(int i = 0; i < this.subentities.length; ++i)
        {
            this.subentities[i].xo = avector3d[i].x;
            this.subentities[i].yo = avector3d[i].y;
            this.subentities[i].zo = avector3d[i].z;
            this.subentities[i].xOld = avector3d[i].x;
            this.subentities[i].yOld = avector3d[i].y;
            this.subentities[i].zOld = avector3d[i].z;
        }

        super.aiStep();
    }

    public MachinePartEntity[] getSubEntities() {
        return this.subentities;
    }

    @Override
    public net.minecraftforge.entity.PartEntity<?>[] getParts()
    {
        return this.subentities;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data)
    {
        AnimationController<?> controller = new AnimationController<>(this, "controller", 1, (t) ->
        {
            if (this.state.equals(State.RELOADING))
            {
                return (double) (this.type.specs.delaytime.get() - this.delayticks) / this.type.specs.delaytime.get();
            }
            return t;
        }, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public AnimatableInstanceCache getFactory()
    {
        return this.factory;
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);

        if (super.mobInteract(player, hand) == InteractionResult.SUCCESS)
        {
            return InteractionResult.SUCCESS;
        }
        if (!this.level().isClientSide() && !this.isVehicle())
        {
            player.startRiding(this);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public void startShooting(Player player)
    {
        if (this.delayticks <= 0 && this.useticks <= 0 && this.shootingticks <= 0)
        {
            this.state = State.SHOOTING;
            this.useticks = this.type.usetime;
            this.shootingticks = this.type.userealisetime;

            Vec3 pos = this.position();
            this.level.playLocalSound(pos.x, pos.y, pos.z, SoundTypes.TREBUCHET_SHOOTING.get(), this.getSoundSource(), 1.0f, 1.0f, false);
        }
    }

    @Override
    public void shoot()
    {
        if (!level.isClientSide())
        {
            super.shoot();
        }
    }

    @Override
    public void travel(Vec3 pos)
    {
        if (this.isAlive())
        {
            if (this.isVehicle() && this.useticks <= 0 && this.delayticks <= 0)
            {
                LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();

                this.setTurretRotations(livingentity.getXRot(), this.getTurretYaw());
                this.updateTurretRotations();

                this.setYawDest(livingentity.getYRot());
                this.updateYaw();
            }
            super.travel(pos);
        }
    }

    @Override
    public void tick()
    {
        if (this.useticks != 0 && --this.useticks <= 0)
        {
            this.state = State.RELOADING;
            this.useticks = 0;
            this.delayticks = this.type.specs.delaytime.get();
        }

        if (this.shootingticks != 0 && --this.shootingticks <= 0)
        {
            this.useRealise();
            this.shootingticks = 0;
        }

        if (!level.isClientSide() && this.isOnGround())
        {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.0, 1.0, 0.0));
        }

        if (this.delayticks > 0 && this.isVehicle())
        {
            if (this.delayticks % 40 == 0)
            {
                Vec3 pos = this.position();
                this.level.playLocalSound(pos.x, pos.y, pos.z, SoundTypes.TREBUCHET_RELOADING.get(), this.getSoundSource(), 1.0f, 1.0f, false);
            }
            if (--this.delayticks <= 0)
            {
                this.state = State.IDLE_RELOADED;
            }
        }

        if (this.renderupdateticks-- <= 0)
        {
            this.updateMachineRender();
            this.renderupdateticks = SiegeMachinesForge.RENDER_UPDATE_TIME;
        }

        super.tick();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Crosshair createCrosshair()
    {
        return new ReloadingCrosshair();
    }

    @Override
    public Item getMachineItem()
    {
        return ModItems.TREBUCHET.get();
    }
}