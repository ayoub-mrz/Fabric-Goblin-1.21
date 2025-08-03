package net.ayoubmrz.goblinmod.entity.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;


public class WyvernEntity extends HostileEntity implements GeoEntity, IShootable {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private static final TrackedData<Boolean> IS_SHOOTING = DataTracker.registerData(WyvernEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int shootingTicks = 0;
    private int wingFlapTicks = 0;
    private final String TEXTUREPATH = "textures/entity/white_ball.png";

    public WyvernEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();

        // Handle flying behavior
        if (!this.isOnGround()) {
            this.setVelocity(this.getVelocity().multiply(1.0, 0.6, 1.0));

            // Play wing flap sounds while flying
            wingFlapTicks++;
            if (wingFlapTicks >= 20) {
                this.playSound(SoundEvents.ENTITY_ENDER_DRAGON_FLAP, 0.2F, 1.4F);
                wingFlapTicks = 0;
            }
        } else {
            wingFlapTicks = 0;
        }

        if (isShooting()) {
            shootingTicks++;
            if (shootingTicks > 5) {
                setShooting(false);
                shootingTicks = 0;
            }
        }

    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(IS_SHOOTING, false);
    }

    public boolean isShooting() {
        return this.dataTracker.get(IS_SHOOTING);
    }

    public void setShooting(boolean shooting) {
        this.dataTracker.set(IS_SHOOTING, shooting);
    }

    public String getTexturePath() {return TEXTUREPATH;}

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0F)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4F)
                .add(EntityAttributes.GENERIC_FALL_DAMAGE_MULTIPLIER, 0.0F)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0F);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new WyvernAttackGoal(this, 0.4D, true));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllers.add(new AnimationController<>(this, "shootController", 0, this::shootPredicate));
    }

    private PlayState shootPredicate(AnimationState<WyvernEntity> event) {

        if (this.isShooting()) {
            System.out.println("is shooting");
            event.getController().forceAnimationReset();
            event.getController().setAnimation(
                    RawAnimation.begin().then("animation.wyvern.shoot", Animation.LoopType.PLAY_ONCE)
            );
            setShooting(false);
            return PlayState.CONTINUE;
        }

        return PlayState.CONTINUE;
    }

    private PlayState predicate(AnimationState<WyvernEntity> animationState) {
        var controller = animationState.getController();

        if(!this.isOnGround()) {
            controller.setAnimation(RawAnimation.begin().then("animation.wyvern.fly", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if(animationState.isMoving() && this.isOnGround()) {
            controller.setAnimation(RawAnimation.begin().then("animation.wyvern.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        controller.setAnimation(RawAnimation.begin().then("animation.wyvern.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}