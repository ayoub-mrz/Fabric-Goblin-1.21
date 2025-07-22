package net.ayoubmrz.goblinmod.entity;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.BoneProjectileEntity;
import net.ayoubmrz.goblinmod.entity.custom.GoblinEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<GoblinEntity> GOBLIN = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(GoblinMod.MOD_ID, "goblin"),
            EntityType.Builder.create(GoblinEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6f, 2f).build());

    public static void registerModEntities() {
        GoblinMod.LOGGER.info("Registering Mod Goblin for " + GoblinMod.MOD_ID);
    }
}