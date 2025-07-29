//package net.ayoubmrz.goblinmod.entity.custom;
//
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.ai.goal.Goal;
//import net.minecraft.entity.ai.pathing.Path;
//import net.minecraft.entity.attribute.EntityAttributes;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.predicate.entity.EntityPredicates;
//import net.minecraft.util.Hand;
//
//import java.util.EnumSet;
//import java.util.Random;
//
//public class DaveMeleeAttackGoal extends Goal {
//    protected final FireDaveEntity mob;
//    private final double speed;
//    private final boolean pauseWhenMobIdle;
//    private Path path;
//    private int updateCountdownTicks;
//    private int cooldown;
//    private long lastUpdateTime;
//    private int shootCooldown = 0;
//    private int waitForAnimation = 0;
//    public boolean animationTriggered = false;
//    Random random = new Random();
//    private int startShooting = random.nextInt(10, 21); // 10 - 20 sec
//
//    public DaveMeleeAttackGoal(FireDaveEntity mob, double speed, boolean pauseWhenMobIdle) {
//        this.mob = mob;
//        this.speed = speed;
//        this.pauseWhenMobIdle = pauseWhenMobIdle;
//        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
//    }
//
//    public boolean canStart() {
//        long l = this.mob.getWorld().getTime();
//        if (l - this.lastUpdateTime < 20L) {
//            return false;
//        } else {
//            this.lastUpdateTime = l;
//            LivingEntity livingEntity = this.mob.getTarget();
//            if (livingEntity == null) {
//                return false;
//            } else if (!livingEntity.isAlive()) {
//                return false;
//            } else {
//                this.path = this.mob.getNavigation().findPathTo(livingEntity, 0);
//                if (this.path != null) {
//                    return true;
//                } else {
//                    return this.mob.isInAttackRange(livingEntity);
//                }
//            }
//        }
//    }
//
//    public boolean shouldContinue() {
//        LivingEntity livingEntity = this.mob.getTarget();
//        if (livingEntity == null) {
//            return false;
//        } else if (!livingEntity.isAlive()) {
//            return false;
//        } else if (!this.pauseWhenMobIdle) {
//            return !this.mob.getNavigation().isIdle();
//        } else if (!this.mob.isInWalkTargetRange(livingEntity.getBlockPos())) {
//            return false;
//        } else {
//            return !(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity)livingEntity).isCreative();
//        }
//    }
//
//    public void start() {
//        this.mob.getNavigation().startMovingAlong(this.path, this.speed);
//        this.mob.setAttacking(true);
//        this.updateCountdownTicks = 0;
//        this.cooldown = 0;
//    }
//
//    public void stop() {
//        LivingEntity livingEntity = this.mob.getTarget();
//        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
//            this.mob.setTarget((LivingEntity)null);
//        }
//
//        this.mob.setAttacking(false);
//        this.mob.getNavigation().stop();
//    }
//
//    public boolean shouldRunEveryTick() {
//        return true;
//    }
//
//    public void tick() {
//        ++this.shootCooldown;
//        if (this.shootCooldown <= (startShooting * 10)) {
//
//            // trigger animation
//            if (!animationTriggered) {
////                this.mob.setShooting(true);
//                this.waitForAnimation = 0;
//                this.animationTriggered = true;
//            }
//
//            this.waitForAnimation++;
//
//            if (this.waitForAnimation == 27) {
//                this.shootBone();
//                this.shootCooldown = 0;
//                this.waitForAnimation = 0;
//                this.animationTriggered = false;
//            }
//        }
//    }
//
//    private double getFollowRange() {
//        return this.mob.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
//    }
//
//    protected void shootBone() {
//        LivingEntity target = this.mob.getTarget();
//        if (target != null) {
//
//            double offsetX = -Math.sin(Math.toRadians(this.mob.getYaw())) * 0.5;
//            double offsetZ = Math.cos(Math.toRadians(this.mob.getYaw())) * 0.5;
//
//            RedBallProjectileEntity bone = new RedBallProjectileEntity(this.mob.getWorld(), this.mob);
//            bone.setPosition(
//                    this.mob.getX() + offsetX,
//                    this.mob.getY() + this.mob.getHeight() * 0.5,
//                    this.mob.getZ() + offsetZ
//            );
//
//            double dX = target.getX() - bone.getX();
//            double dY = target.getBodyY(0.5) - bone.getY();
//            double dZ = target.getZ() - bone.getZ();
//
//            double horizontalDistance = Math.sqrt(dX * dX + dZ * dZ);
//
//            float upwardForce = 0.2f + (float)horizontalDistance * 0.1f;
//            dY += upwardForce;
//
//            double distance = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
//            dX /= distance;
//            dY /= distance;
//            dZ /= distance;
//
//            double speed = 1.2;
//            bone.setVelocity(
//                    dX * speed,
//                    dY * speed,
//                    dZ * speed,
//                    2.0f,
//                    1.0f
//            );
//
//            this.mob.getWorld().spawnEntity(bone);
//        }
//    }
//
//    protected void attack(LivingEntity target) {
//        if (this.canAttack(target)) {
//            this.resetCooldown();
//            this.mob.swingHand(Hand.MAIN_HAND);
//            this.mob.tryAttack(target);
//        }
//
//    }
//
//    protected void resetCooldown() {
//        this.cooldown = this.getTickCount(20);
//    }
//
//    protected boolean isCooledDown() {
//        return this.cooldown <= 0;
//    }
//
//    protected boolean canAttack(LivingEntity target) {
//        return this.isCooledDown() && this.mob.isInAttackRange(target) && this.mob.getVisibilityCache().canSee(target);
//    }
//
//    protected int getCooldown() {
//        return this.cooldown;
//    }
//
//    protected int getMaxCooldown() {
//        return this.getTickCount(20);
//    }
//
//}