package net.ayoubmrz.goblinmod.entity.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;

import java.util.EnumSet;

public class GoblinEntity extends HostileEntity implements GeoEntity {

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private boolean isAttackWindingUp = false;
    private int windupTicks = 0;
    public static boolean isShooting = false;

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
//        this.triggerAnim("attackController", "animation.goblin.attack");
    }

    private void performAttack() {
        LivingEntity target = this.getTarget();
        if (this.isAlive() && target != null && this.canSee(target)) {
            this.tryAttack(target);
        }
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
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0F)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0F);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));

        this.goalSelector.add(1, new CustomMeleeAttackGoal(this, 0.4D, true));
        
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.4f, 1));

        this.goalSelector.add(4, new LookAroundGoal(this));

        this.goalSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    static class CustomMeleeAttackGoal extends Goal{
        protected final GoblinEntity mob;
        private final double speed;
        private final boolean pauseWhenMobIdle;
        private Path path;
        private double targetX;
        private double targetY;
        private double targetZ;
        private int updateCountdownTicks;
        private int cooldown;
        private final int attackIntervalTicks = 20;
        private long lastUpdateTime;
        private static final long MAX_ATTACK_TIME = 20L;
        private int shootCooldown = 0;
        private int waitForAnimation = 0;
        private int targetNotVisibleTicks;
        private boolean animationTriggered = false;

        public CustomMeleeAttackGoal(GoblinEntity mob, double speed, boolean pauseWhenMobIdle) {
            this.mob = mob;
            this.speed = speed;
            this.pauseWhenMobIdle = pauseWhenMobIdle;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        public boolean canStart() {
            long l = this.mob.getWorld().getTime();
            if (l - this.lastUpdateTime < 20L) {
                return false;
            } else {
                this.lastUpdateTime = l;
                LivingEntity livingEntity = this.mob.getTarget();
                if (livingEntity == null) {
                    return false;
                } else if (!livingEntity.isAlive()) {
                    return false;
                } else {
                    this.path = this.mob.getNavigation().findPathTo(livingEntity, 0);
                    if (this.path != null) {
                        return true;
                    } else {
                        return this.mob.isInAttackRange(livingEntity);
                    }
                }
            }
        }

        public boolean shouldContinue() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else if (!this.pauseWhenMobIdle) {
                return !this.mob.getNavigation().isIdle();
            } else if (!this.mob.isInWalkTargetRange(livingEntity.getBlockPos())) {
                return false;
            } else {
                return !(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity)livingEntity).isCreative();
            }
        }

        public void start() {
            this.mob.getNavigation().startMovingAlong(this.path, this.speed);
            this.mob.setAttacking(true);
            this.updateCountdownTicks = 0;
            this.cooldown = 0;
        }

        public void stop() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
                this.mob.setTarget((LivingEntity)null);
            }

            this.mob.setAttacking(false);
            this.mob.getNavigation().stop();
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

//        public void tick() {
//            ++this.shootCooldown;
//            if (this.shootCooldown <= 100) {
//                LivingEntity livingEntity = this.mob.getTarget();
//                if (livingEntity != null) {
//                    this.mob.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
//                    this.updateCountdownTicks = Math.max(this.updateCountdownTicks - 1, 0);
//                    if ((this.pauseWhenMobIdle || this.mob.getVisibilityCache().canSee(livingEntity)) && this.updateCountdownTicks <= 0 && (this.targetX == (double)0.0F && this.targetY == (double)0.0F && this.targetZ == (double)0.0F || livingEntity.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) >= (double)1.0F || this.mob.getRandom().nextFloat() < 0.05F)) {
//                        this.targetX = livingEntity.getX();
//                        this.targetY = livingEntity.getY();
//                        this.targetZ = livingEntity.getZ();
//                        this.updateCountdownTicks = 4 + this.mob.getRandom().nextInt(7);
//                        double d = this.mob.squaredDistanceTo(livingEntity);
//                        if (d > (double)1024.0F) {
//                            this.updateCountdownTicks += 10;
//                        } else if (d > (double)256.0F) {
//                            this.updateCountdownTicks += 5;
//                        }
//
//                        if (!this.mob.getNavigation().startMovingTo(livingEntity, this.speed)) {
//                            this.updateCountdownTicks += 15;
//                        }
//
//                        this.updateCountdownTicks = this.getTickCount(this.updateCountdownTicks);
//                    }
//
//                    this.cooldown = Math.max(this.cooldown - 1, 0);
//                    this.attack(livingEntity);
//                }
//            } else {
//
//                if (this.waitForAnimation == 0) {
//                    // First tick of ranged attack - trigger animation
//                    this.mob.isShooting = true;
//                    this.mob.triggerAnim("shootController", "animation.goblin.attack");
//                    this.waitForAnimation++;
//                }
//                else if (this.waitForAnimation < 20) {
//                    // Waiting for animation to reach the attack point
//                    this.waitForAnimation++;
//                } else if (this.waitForAnimation >= 40) {
//                    this.shootCooldown = 0;
//                    this.waitForAnimation = 0;
//                    this.shootCount = 0;
//                    LivingEntity livingEntity = this.mob.getTarget();
//                    if (livingEntity != null) {
//                        boolean bl = this.mob.getVisibilityCache().canSee(livingEntity);
//                        if (bl) {
//                            this.targetNotVisibleTicks = 0;
//                        } else {
//                            ++this.targetNotVisibleTicks;
//                        }
//
//                        double d = this.mob.squaredDistanceTo(livingEntity);
//                        if (d < (double)4.0F) {
//                            if (!bl) {
//                                return;
//                            }
//
//                            this.mob.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), (double)1.0F);
//                        } else if (d < this.getFollowRange() * this.getFollowRange() && bl) {
//                            double e = livingEntity.getX() - this.mob.getX();
//                            double f = livingEntity.getBodyY((double)0.5F) - this.mob.getBodyY((double)0.5F);
//                            double g = livingEntity.getZ() - this.mob.getZ();
//
//                            if (this.shootCount < 1) {
//                                ++this.shootCount;
//                                this.animeCount = 0;
//                                double h = Math.sqrt(Math.sqrt(d)) * (double)0.5F;
//                                if (!this.mob.isSilent()) {
//                                    this.mob.getWorld().syncWorldEvent((PlayerEntity)null, 1018, this.mob.getBlockPos(), 0);
//                                }
//
//                                for(int i = 0; i < 1; ++i) {
//                                    Vec3d vec3d = new Vec3d(this.mob.getRandom().nextTriangular(e, 2.297 * h), f, this.mob.getRandom().nextTriangular(g, 2.297 * h));
//                                    SmallFireballEntity smallFireballEntity = new SmallFireballEntity(this.mob.getWorld(), this.mob, vec3d.normalize());
//                                    smallFireballEntity.setPosition(smallFireballEntity.getX(), this.mob.getBodyY((double)0.5F) + (double)0.5F, smallFireballEntity.getZ());
//                                    this.mob.getWorld().spawnEntity(smallFireballEntity);
//                                }
//
//                            }
//
//                        }
//
//                        this.mob.getLookControl().lookAt(livingEntity, 10.0F, 10.0F);
//                    }
//
//                    super.tick();
//                }
//            }
//        }

        public void tick() {
            ++this.shootCooldown;
            if (this.shootCooldown <= 100) {
                LivingEntity livingEntity = this.mob.getTarget();
                if (livingEntity != null) {
                    this.mob.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
                    this.updateCountdownTicks = Math.max(this.updateCountdownTicks - 1, 0);
                    if ((this.pauseWhenMobIdle || this.mob.getVisibilityCache().canSee(livingEntity)) && this.updateCountdownTicks <= 0 && (this.targetX == (double)0.0F && this.targetY == (double)0.0F && this.targetZ == (double)0.0F || livingEntity.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) >= (double)1.0F || this.mob.getRandom().nextFloat() < 0.05F)) {
                        this.targetX = livingEntity.getX();
                        this.targetY = livingEntity.getY();
                        this.targetZ = livingEntity.getZ();
                        this.updateCountdownTicks = 4 + this.mob.getRandom().nextInt(7);
                        double d = this.mob.squaredDistanceTo(livingEntity);
                        if (d > (double)1024.0F) {
                            this.updateCountdownTicks += 10;
                        } else if (d > (double)256.0F) {
                            this.updateCountdownTicks += 5;
                        }

                        if (!this.mob.getNavigation().startMovingTo(livingEntity, this.speed)) {
                            this.updateCountdownTicks += 15;
                        }

                        this.updateCountdownTicks = this.getTickCount(this.updateCountdownTicks);
                    }

                    this.cooldown = Math.max(this.cooldown - 1, 0);
                    this.attack(livingEntity);
                }
            }
            else {

                // trigger animation
                if (!animationTriggered) {
                    System.out.println("making it true");
                    isShooting = true;
                    this.waitForAnimation = 0;
                    this.animationTriggered = true;
                }

                    while(waitForAnimation < 20){
                        this.waitForAnimation++;
                    }

                    System.out.println(waitForAnimation);

                if (this.waitForAnimation == 20) {

                    System.out.println(waitForAnimation);
                    System.out.println("time for fireball");
                    this.shootFireBall();

                }
                    this.shootCooldown = 0;
                    this.waitForAnimation = 0;
                    this.animationTriggered = false;
            }
        }

        private double getFollowRange() {
            return this.mob.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
        }

        protected void shootFireBall() {
            LivingEntity target = this.mob.getTarget();
            if (target != null) {
                Vec3d vec3d = new Vec3d(
                        target.getX() - this.mob.getX(),
                        target.getBodyY(0.5) - this.mob.getBodyY(0.5),
                        target.getZ() - this.mob.getZ()
                ).normalize();

                SmallFireballEntity fireball = new SmallFireballEntity(
                        this.mob.getWorld(), this.mob, vec3d
                );
                fireball.setPosition(
                        fireball.getX(),
                        this.mob.getBodyY(0.5) + 0.5,
                        fireball.getZ()
                );
                this.mob.getWorld().spawnEntity(fireball);
            }
        }

        protected void attack(LivingEntity target) {
            if (this.canAttack(target)) {
                this.resetCooldown();
                this.mob.swingHand(Hand.MAIN_HAND);
                this.mob.tryAttack(target);
            }

        }

        protected void resetCooldown() {
            this.cooldown = this.getTickCount(20);
        }

        protected boolean isCooledDown() {
            return this.cooldown <= 0;
        }

        protected boolean canAttack(LivingEntity target) {
            return this.isCooledDown() && this.mob.isInAttackRange(target) && this.mob.getVisibilityCache().canSee(target);
        }

        protected int getCooldown() {
            return this.cooldown;
        }

        protected int getMaxCooldown() {
            return this.getTickCount(20);
        }

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllers.add(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
        controllers.add(new AnimationController<>(this, "shootController", 0, this::shootPredicate));
    }

    private PlayState shootPredicate(AnimationState<GoblinEntity> event) {

        if (isShooting) {
            System.out.println("shooting worked");
            event.getController().forceAnimationReset();
            event.getController().setAnimation(
                    RawAnimation.begin().then("animation.goblin.attack", Animation.LoopType.PLAY_ONCE)
            );
            this.isShooting = false;
            return PlayState.CONTINUE;
        }

        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationState<GoblinEntity> event) {

        if (this.handSwinging) {
            System.out.println("attacking worked");
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

        if (animationState.isMoving() && !isShooting) {
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
