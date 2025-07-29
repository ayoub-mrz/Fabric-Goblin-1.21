//package net.ayoubmrz.goblinmod.entity.custom;
//
//import net.ayoubmrz.goblinmod.entity.ModEntities;
//import net.ayoubmrz.goblinmod.item.ModItems;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityType;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.projectile.PersistentProjectileEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.Items;
//import net.minecraft.particle.ParticleTypes;
//import net.minecraft.server.world.ServerWorld;
//import net.minecraft.util.hit.EntityHitResult;
//import net.minecraft.world.World;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class RedBallProjectileEntity extends PersistentProjectileEntity {
//    private final Set<Entity> hitEntities = new HashSet<>();
//    private boolean hasHitPlayer = false;
//
//    public RedBallProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
//        super(entityType, world);
//    }
//
//    public RedBallProjectileEntity(World world, PlayerEntity player) {
//        super(ModEntities.REDBALL, player, world, new ItemStack(ModItems.RED_BALL), null);
//    }
//
//    public RedBallProjectileEntity(World world, GoblinEntity mob) {
//        super(ModEntities.REDBALL, world);
//    }
//    public RedBallProjectileEntity(World world, FireDaveEntity mob) {
//        super(ModEntities.REDBALL, world);
//    }
//
//    @Override
//    protected ItemStack getDefaultItemStack() {
//        return new ItemStack(ModItems.ANCIENT_BONE);
//    }
//
//    public boolean isGrounded() {
//        return inGround;
//    }
//
//    @Override
//    protected void onEntityHit(EntityHitResult entityHitResult) {
//        Entity hitEntity = entityHitResult.getEntity();
//
//        if (hitEntities.contains(hitEntity)) {
//            return;
//        }
//
//        hitEntities.add(hitEntity);
//
//        if (hitEntity instanceof PlayerEntity player) {
//            player.damage(this.getDamageSources().thrown(this, this.getOwner()), 4.0f);
//            hasHitPlayer = true;
//
//            if (!this.getWorld().isClient) {
//                this.discard();
//            }
//
//        } else if (hitEntity instanceof LivingEntity livingEntity) {
//            if (!this.getWorld().isClient) {
//                ((ServerWorld) this.getWorld()).spawnParticles(
//                        ParticleTypes.CRIT,
//                        hitEntity.getX(), hitEntity.getY() + hitEntity.getHeight() / 2, hitEntity.getZ(),
//                        5, 0.1, 0.1, 0.1, 0.1
//                );
//            }
//
//        }
//    }
//
//    @Override
//    protected ItemStack asItemStack() {
//        return new ItemStack(Items.BONE);
//    }
//
//}
