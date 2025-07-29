package net.ayoubmrz.goblinmod.entity;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<BoneProjectileEntity> ANCIENTBONE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(GoblinMod.MOD_ID, "ancient_bone"),
            EntityType.Builder.<BoneProjectileEntity>create(BoneProjectileEntity::new, SpawnGroup.MISC)
                    .dimensions(0.4f, 0.3f).build());

    public static final EntityType<GoblinEntity> GOBLIN = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(GoblinMod.MOD_ID, "goblin"),
            EntityType.Builder.create(GoblinEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6f, 2f).build());

    public static final EntityType<LilDaveEntity> LILDAVE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(GoblinMod.MOD_ID, "lil_dave"),
            EntityType.Builder.create(LilDaveEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.05f, 1.2f).build());

    public static final EntityType<FireDaveEntity> FIREDAVE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(GoblinMod.MOD_ID, "fire_dave"),
            EntityType.Builder.create(FireDaveEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.2f, 1.8f).build());

    public static final EntityType<WaterDaveEntity> WATERDAVE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(GoblinMod.MOD_ID, "water_dave"),
            EntityType.Builder.create(WaterDaveEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.2f, 1.8f).build());

    public static final EntityType<LightningDaveEntity> LIGHTNINGDAVE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(GoblinMod.MOD_ID, "lightning_dave"),
            EntityType.Builder.create(LightningDaveEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.2f, 1.8f).build());

//    public static final EntityType<RedBallProjectileEntity> REDBALL = Registry.register(Registries.ENTITY_TYPE,
//            Identifier.of(GoblinMod.MOD_ID, "red_ball"),
//            EntityType.Builder.<RedBallProjectileEntity>create(RedBallProjectileEntity::new, SpawnGroup.MONSTER)
//                    .dimensions(0.2f, 0.2f).build());

    public static void registerModEntities() {
        GoblinMod.LOGGER.info("Registering Mod Goblin for " + GoblinMod.MOD_ID);
    }
}