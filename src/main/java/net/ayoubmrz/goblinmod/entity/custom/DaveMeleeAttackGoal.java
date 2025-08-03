package net.ayoubmrz.goblinmod.entity.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;

import java.util.EnumSet;
import java.util.Random;

public class DaveMeleeAttackGoal extends Goal {
    Random random = new Random();
    protected final HostileEntity mob;
    private final double speed;
    private final boolean pauseWhenMobIdle;
    private Path path;
    private double targetX;
    private double targetY;
    private double targetZ;
    private int updateCountdownTicks;
    private int cooldown;
    private long lastUpdateTime;
    private int shootCooldown = 0;
    private int waitForAnimation = 0;
    public boolean animationTriggered = false;
    private int startShooting = random.nextInt(7, 13); // sec

    public DaveMeleeAttackGoal(HostileEntity mob, double speed, boolean pauseWhenMobIdle) {
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

    public void tick() {
        ++this.shootCooldown;
        if (this.shootCooldown >= (startShooting * 10)) {
            if (!animationTriggered) {
                if (this.mob instanceof IShootable) {
                    ((IShootable) this.mob).setShooting(true);
                }
                this.waitForAnimation = 0;
                this.animationTriggered = true;
            }

            this.waitForAnimation++;

            if (this.waitForAnimation == 10) {
                LivingEntity livingEntity = this.mob.getTarget();
                this.shootBall();
                this.shootCooldown = 0;
                this.waitForAnimation = 0;
                this.animationTriggered = false;
            }
        } else {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity != null) {
                this.mob.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
                this.updateCountdownTicks = Math.max(this.updateCountdownTicks - 1, 0);
                if (    this.mob.squaredDistanceTo(livingEntity) > 10.0D * 10.0D
                        && this.mob.squaredDistanceTo(livingEntity) >= 10.0F
                        && (this.pauseWhenMobIdle || this.mob.getVisibilityCache().canSee(livingEntity))
                        && this.updateCountdownTicks <= 0
                        && (this.targetX == (double) 0.0F && this.targetY == (double) 0.0F && this.targetZ == (double) 0.0F || livingEntity.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) >= (double) 1.0F || this.mob.getRandom().nextFloat() < 0.05F)) {
                    this.targetX = livingEntity.getX();
                    this.targetY = livingEntity.getY();
                    this.targetZ = livingEntity.getZ();
                    this.updateCountdownTicks = 4 + this.mob.getRandom().nextInt(7);
                    double d = this.mob.squaredDistanceTo(livingEntity);
                    if (d > (double) 1024.0F) {
                        this.updateCountdownTicks += 10;
                    } else if (d > (double) 256.0F) {
                        this.updateCountdownTicks += 5;
                    }

                    if (!this.mob.getNavigation().startMovingTo(livingEntity, this.speed)) {
                        this.updateCountdownTicks += 15;
                    }

                    this.updateCountdownTicks = this.getTickCount(this.updateCountdownTicks);
                }

                this.cooldown = Math.max(this.cooldown - 1, 0);
            }
        }
    }

    private String texturePath() {
        String path = "";
        if (this.mob instanceof IShootable) {
            path = ((IShootable) this.mob).getTexturePath();
        }
        return path;
    }

    protected void shootBall() {

        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            double offsetX = -Math.sin(Math.toRadians(this.mob.getYaw())) * 0.4;
            double offsetZ = Math.cos(Math.toRadians(this.mob.getYaw())) * 0.4;

            BallProjectileEntity ball = new BallProjectileEntity(this.mob.getWorld(), this.mob, texturePath());
            ball.setPosition(
                    this.mob.getX() + offsetX,
                    this.mob.getY() + this.mob.getHeight() * 0.5 + 0.5,
                    this.mob.getZ() + offsetZ
            );

            double dX = target.getX() - ball.getX();
            double dY = target.getBodyY(0.5) - ball.getY();
            double dZ = target.getZ() - ball.getZ();

            double distance = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
            if (distance > 0) {
                dX /= distance;
                dY /= distance;
                dZ /= distance;
            }

            double projectileSpeed = 1.6f;

            ball.setVelocity(
                    dX * projectileSpeed,
                    dY * projectileSpeed,
                    dZ * projectileSpeed,
                    1.4f,
                    1.0f
            );

            ball.setOnFireFor(100);

            if (!ball.hasNoGravity()) {
                ball.setNoGravity(true);
            }

            this.mob.getWorld().spawnEntity(ball);
        }
    }



}