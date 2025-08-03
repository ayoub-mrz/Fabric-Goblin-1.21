package net.ayoubmrz.goblinmod.entity.custom;

import net.ayoubmrz.goblinmod.entity.ModEntities;
import net.ayoubmrz.goblinmod.item.ModItems;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class BoneProjectileEntity extends PersistentProjectileEntity {
    private float rotation;
    public Vector2f groundedOffset;
    private final Set<Entity> hitEntities = new HashSet<>();
    private boolean hasHitPlayer = false;

    public BoneProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public BoneProjectileEntity(World world, PlayerEntity player) {
        super(ModEntities.ANCIENTBONE, player, world, new ItemStack(ModItems.ANCIENT_BONE), null);
    }

    public BoneProjectileEntity(World world, GoblinEntity mob) {
        super(ModEntities.ANCIENTBONE, world);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(ModItems.ANCIENT_BONE);
    }

    public float getRenderingRotation() {
        rotation += 0.5f;
        if(rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    public boolean isGrounded() {
        return inGround;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity hitEntity = entityHitResult.getEntity();

        if (hitEntities.contains(hitEntity)) {
            return;
        }

        hitEntities.add(hitEntity);

        if (hitEntity instanceof PlayerEntity player) {
            player.damage(this.getDamageSources().thrown(this, this.getOwner()), 6.0f);
            hasHitPlayer = true;

            if (!this.getWorld().isClient) {
                this.discard();
            }

        } else if (hitEntity instanceof LivingEntity livingEntity) {
            if (!this.getWorld().isClient) {
                ((ServerWorld) this.getWorld()).spawnParticles(
                        ParticleTypes.CRIT,
                        hitEntity.getX(), hitEntity.getY() + hitEntity.getHeight() / 2, hitEntity.getZ(),
                        5, 0.1, 0.1, 0.1, 0.1
                );
            }

        }
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(Items.BONE);
    }

    @Override
    protected void onBlockHit(BlockHitResult result) {
        super.onBlockHit(result);

        if(result.getSide() == Direction.SOUTH) {
            groundedOffset = new Vector2f(215f,180f);
        }
        if(result.getSide() == Direction.NORTH) {
            groundedOffset = new Vector2f(215f, 0f);
        }
        if(result.getSide() == Direction.EAST) {
            groundedOffset = new Vector2f(215f,-90f);
        }
        if(result.getSide() == Direction.WEST) {
            groundedOffset = new Vector2f(215f,90f);
        }

        if(result.getSide() == Direction.DOWN) {
            groundedOffset = new Vector2f(115f,180f);
        }
        if(result.getSide() == Direction.UP) {
            groundedOffset = new Vector2f(285f,180f);
        }
    }

}
