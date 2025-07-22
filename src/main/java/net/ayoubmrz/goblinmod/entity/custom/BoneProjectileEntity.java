package net.ayoubmrz.goblinmod.entity.custom;

import net.ayoubmrz.goblinmod.entity.ModEntities;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class BoneProjectileEntity extends AbstractFireballEntity {

    public BoneProjectileEntity(EntityType<? extends BoneProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public BoneProjectileEntity(World world, LivingEntity owner, Vec3d velocity) {
        super(EntityType.SMALL_FIREBALL, owner, velocity, world);
    }

    public BoneProjectileEntity(World world, double x, double y, double z, Vec3d velocity) {
        super(EntityType.SMALL_FIREBALL, x, y, z, velocity, world);
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        World entity = this.getWorld();
        if (entity instanceof ServerWorld serverWorld) {
            Entity var7 = entityHitResult.getEntity();
            Entity entity2 = this.getOwner();
            int i = var7.getFireTicks();
            var7.setOnFireFor(5.0F);//
            DamageSource damageSource = this.getDamageSources().fireball(this, entity2);
            if (!var7.damage(damageSource, 5.0F)) {
                var7.setFireTicks(i);
            } else {
                EnchantmentHelper.onTargetDamaged(serverWorld, var7, damageSource);
            }

        }
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            Entity entity = this.getOwner();
            if (!(entity instanceof MobEntity) || this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
                if (this.getWorld().isAir(blockPos)) {
                    this.getWorld().setBlockState(blockPos, AbstractFireBlock.getState(this.getWorld(), blockPos));
                }
            }

        }
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.discard();
        }

    }

    public boolean damage(DamageSource source, float amount) {
        return false;
    }
}

