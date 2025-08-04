package net.ayoubmrz.goblinmod.entity.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.Random;

public class WyvernAttackGoal extends Goal {
    Random random = new Random();
    protected final HostileEntity mob;
    private final double speed;
    private final boolean pauseWhenMobIdle;
    private int updateCountdownTicks;
    private int cooldown;
    private long lastUpdateTime;
    private int shootCooldown = 0;
    private int waitForAnimation = 0;
    public boolean animationTriggered = false;
    private int startShooting = random.nextInt(7, 13); // sec

    private Vec3d flyingTargetPosition = Vec3d.ZERO;
    private static final double FLYING_HEIGHT = 10.0;
    private static final double CIRCLE_RADIUS = 10.0;
    private static final double CIRCLE_SPEED = 0.04;
    private double circleAngle = 0.0;
    private Vec3d circleCenter = Vec3d.ZERO;

    public WyvernAttackGoal(HostileEntity mob, double speed, boolean pauseWhenMobIdle) {
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
                return true;
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
            return true;
        } else if (!this.mob.isInWalkTargetRange(livingEntity.getBlockPos())) {
            return false;
        } else {
            return !(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity)livingEntity).isCreative();
        }
    }

    public void start() {
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
    }

    public boolean shouldRunEveryTick() {
        return true;
    }

    public void tick() {
        ++this.shootCooldown;

        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity != null) {

            // Update circle center based on target's current location
            updateCircleCenter(livingEntity);

            // Handle circular flying movement
            updateCircularFlyingMovement();

            this.mob.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
        }

        // Handle shooting logic
        if (!this.mob.getWorld().isClient && this.shootCooldown >= (startShooting * 10)) {
            if (!animationTriggered) {
                if (this.mob instanceof IShootable) {
                    ((IShootable) this.mob).setShooting(true);
                }
                this.waitForAnimation = 0;
                this.animationTriggered = true;
            }

            this.waitForAnimation++;

            if (this.waitForAnimation == 10) {
                this.shootBall();
                this.shootCooldown = 0;
                this.waitForAnimation = 0;
                this.animationTriggered = false;
            }
        }
    }

    private void updateCircleCenter(LivingEntity target) {
        BlockPos targetPos = target.getBlockPos();
        circleCenter = new Vec3d(
                targetPos.getX() + 0.5,
                targetPos.getY() + FLYING_HEIGHT,
                targetPos.getZ() + 0.5
        );
    }

    private void updateCircularFlyingMovement() {
        Vec3d currentPos = this.mob.getPos();

        // Check if we're close enough to start circling
        double distanceToCenter = currentPos.distanceTo(circleCenter);

        if (distanceToCenter > CIRCLE_RADIUS + 3.0) {
            // Too far from circle, move towards the circle area first
            Vec3d directionToCenter = circleCenter.subtract(currentPos).normalize();
            double approachSpeed = this.speed * 1.8;

            this.mob.setVelocity(
                    directionToCenter.x * approachSpeed,
                    directionToCenter.y * approachSpeed * 0.7,
                    directionToCenter.z * approachSpeed
            );

            // Face the center while approaching
            float yaw = (float) (Math.atan2(directionToCenter.z, directionToCenter.x) * 180.0 / Math.PI) - 90.0f;
            this.mob.setYaw(yaw);

        } else {
            // Calculate current angle based on position relative to center
            Vec3d relativePos = currentPos.subtract(circleCenter);
            double currentAngleFromPos = Math.atan2(relativePos.z, relativePos.x);

            // Increment angle for smooth circular movement
            circleAngle += CIRCLE_SPEED;
            if (circleAngle >= 2 * Math.PI) {
                circleAngle -= 2 * Math.PI;
            }

            // Calculate target position on circle
            double targetX = circleCenter.x + Math.cos(circleAngle) * CIRCLE_RADIUS;
            double targetZ = circleCenter.z + Math.sin(circleAngle) * CIRCLE_RADIUS;
            double targetY = circleCenter.y + Math.sin(this.mob.age * 0.1) * 0.5;

            Vec3d targetPosition = new Vec3d(targetX, targetY, targetZ);

            // Calculate movement direction
            Vec3d moveDirection = targetPosition.subtract(currentPos);
            double moveDistance = moveDirection.length();

            if (moveDistance > 0.1) {
                moveDirection = moveDirection.normalize();
                double circleSpeed = this.speed * 1.2;

                this.mob.setVelocity(
                        moveDirection.x * circleSpeed,
                        moveDirection.y * circleSpeed * 0.5,
                        moveDirection.z * circleSpeed
                );
            }

            // Calculate tangent direction for facing (perpendicular to radius)
            Vec3d radiusDirection = new Vec3d(Math.cos(circleAngle), 0, Math.sin(circleAngle));
            Vec3d tangentDirection = new Vec3d(-radiusDirection.z, 0, radiusDirection.x);

            // Set yaw to face the tangent direction (direction of circular movement)
            float targetYaw = (float) (Math.atan2(tangentDirection.z, tangentDirection.x) * 180.0 / Math.PI) - 90.0f;

            // Smooth yaw transition to prevent spinning
            float currentYaw = this.mob.getYaw();
            float yawDiff = targetYaw - currentYaw;

            // Normalize yaw difference to [-180, 180]
            while (yawDiff > 180) yawDiff -= 360;
            while (yawDiff < -180) yawDiff += 360;

            // Apply gradual yaw change
            float maxYawChange = 5.0f;
            if (Math.abs(yawDiff) > maxYawChange) {
                yawDiff = Math.signum(yawDiff) * maxYawChange;
            }

            this.mob.setYaw(currentYaw + yawDiff);
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
        if (target != null && !this.mob.getWorld().isClient) {
            // Schedule projectile creation on the main server thread
            if (this.mob.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.getServer().execute(() -> {
                    createAndLaunchProjectile(target);
                });
            } else {
                // Fallback for non-server worlds
                createAndLaunchProjectile(target);
            }
        }
    }

    private void createAndLaunchProjectile(LivingEntity target) {

        if (target == null || !target.isAlive() || this.mob.getTarget() != target) {
            return;
        }

        try {
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

            if (!ball.hasNoGravity()) {
                ball.setNoGravity(true);
            }

            this.mob.getWorld().spawnEntity(ball);

        } catch (Exception e) {
            // Log error if spawning failed
            System.err.println("Error creating projectile: " + e.getMessage());
        }
    }
}