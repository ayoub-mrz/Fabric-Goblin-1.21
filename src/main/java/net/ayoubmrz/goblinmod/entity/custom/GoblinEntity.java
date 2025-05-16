package net.ayoubmrz.goblinmod.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;

public class GoblinEntity extends HostileEntity implements GeoEntity {

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private boolean isAttackWindingUp = false;
    private int windupTicks = 0;

    @Override
    public void tick() {
        super.tick();

        if (isAttackWindingUp) {
            windupTicks--;

            if (windupTicks <= 0) {
                performAttack();
                isAttackWindingUp = false;
            }
        }
    }

    public void startAttackWindup() {
        this.isAttackWindingUp = true;
        this.windupTicks = 10;
        this.triggerAnim("attack_controller", "animation.goblin.attack");
    }

    private void performAttack() {
        LivingEntity target = this.getTarget();
        if (target != null && this.canSee(target)) {
            this.tryAttack(target);
        }
        this.triggerAnim("attack_controller", "animation.goblin.attack");
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (!isAttackWindingUp) {
            startAttackWindup();
            return false;
        }
        return super.tryAttack(target);
    }

    public GoblinEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0D)
//                .add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0F)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0F);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));

        this.goalSelector.add(1, new MeleeAttackGoal(this, 0.4D, true));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.4f, 1));

        this.goalSelector.add(4, new LookAroundGoal(this));

        this.goalSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

      @Override
      public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
         controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
         controllers.add(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
      }

    private PlayState attackPredicate(AnimationState<GoblinEntity> event) {

        if (this.handSwinging) {
            event.getController().forceAnimationReset();
            event.getController().setAnimation(
                    RawAnimation.begin().then("animation.goblin.attack", Animation.LoopType.PLAY_ONCE)
            );
            this.handSwinging = false;
            return PlayState.CONTINUE;
        }

        return PlayState.CONTINUE;
    }

    private PlayState predicate(AnimationState<GoblinEntity> animationState) {
        var controller = animationState.getController();

        if (animationState.isMoving()) {
            controller.setAnimation(RawAnimation.begin().then("animation.goblin.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        controller.setAnimation(RawAnimation.begin().then("animation.goblin.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


}
