package net.ayoubmrz.goblinmod.datagen;

import net.ayoubmrz.goblinmod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModLootTableProvider extends SimpleFabricLootTableProvider {

    public ModLootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup, LootContextTypes.ENTITY);
    }

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, Builder> consumer) {
        RegistryKey<LootTable> goblinLootTableKey = RegistryKey.of(RegistryKeys.LOOT_TABLE,
                Identifier.of("goblinmod", "entities/goblin"));

        consumer.accept(
                goblinLootTableKey,
                LootTable.builder()
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(0.05f))
                                .with(ItemEntry.builder(ModItems.ANCIENT_BONE)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(1.0f, 1.0f)
                                        ))
                                )
                        )
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(0.25f))
                                .with(ItemEntry.builder(ModItems.COIN)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(6.0f, 12.0f)
                                        ))
                                )
                        )
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(0.05f))
                                .with(ItemEntry.builder(Items.GOLD_ORE)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(5.0f, 15.0f)
                                        ))
                                )
                        )
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(0.005f))
                                .with(ItemEntry.builder(ModItems.SWORD_HILT)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(1.0f, 1.0f)
                                        ))
                                )
                        )
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(0.005f))
                                .with(ItemEntry.builder(ModItems.BLADE)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(1.0f, 1.0f)
                                        ))
                                )
                        )
        );

        RegistryKey<LootTable> daveLootTableKey = RegistryKey.of(RegistryKeys.LOOT_TABLE,
                Identifier.of("goblinmod", "entities/lil_dave"));

        consumer.accept(
                daveLootTableKey,
                LootTable.builder()
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(0.25f))
                                .with(ItemEntry.builder(ModItems.COIN)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(4.0f, 8.0f)
                                        ))
                                )
                        )
        );

        RegistryKey<LootTable> FireDaveLootTableKey = RegistryKey.of(RegistryKeys.LOOT_TABLE,
                Identifier.of("goblinmod", "entities/fire_dave"));

        consumer.accept(
                FireDaveLootTableKey,
                LootTable.builder()
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(0.2f))
                                .with(ItemEntry.builder(ModItems.RED_BALL)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(4.0f, 8.0f)
                                        ))
                                )
                        )
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(1f))
                                .with(ItemEntry.builder(ModItems.COIN)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(10.0f, 20.0f)
                                        ))
                                )
                        )
        );

        RegistryKey<LootTable> WaterDaveLootTableKey = RegistryKey.of(RegistryKeys.LOOT_TABLE,
                Identifier.of("goblinmod", "entities/water_dave"));

        consumer.accept(
                WaterDaveLootTableKey,
                LootTable.builder()
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(0.2f))
                                .with(ItemEntry.builder(ModItems.BLUE_BALL)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(4.0f, 8.0f)
                                        ))
                                )
                        )
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(1f))
                                .with(ItemEntry.builder(ModItems.COIN)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(10.0f, 20.0f)
                                        ))
                                )
                        )
        );

        RegistryKey<LootTable> LightningDaveLootTableKey = RegistryKey.of(RegistryKeys.LOOT_TABLE,
                Identifier.of("goblinmod", "entities/lightning_dave"));

        consumer.accept(
                LightningDaveLootTableKey,
                LootTable.builder()
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(0.2f))
                                .with(ItemEntry.builder(ModItems.YELLOW_BALL)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(4.0f, 8.0f)
                                        ))
                                )
                        )
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(1f))
                                .with(ItemEntry.builder(ModItems.COIN)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(10.0f, 20.0f)
                                        ))
                                )
                        )
        );

        RegistryKey<LootTable> WolfLootTableKey = RegistryKey.of(RegistryKeys.LOOT_TABLE,
                Identifier.of("goblinmod", "entities/big_wolf"));

        consumer.accept(
                WolfLootTableKey,
                LootTable.builder()
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(1f))
                                .with(ItemEntry.builder(ModItems.COIN)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(5.0f, 15.0f)
                                        ))
                                )
                        )
        );

        RegistryKey<LootTable> RockGolemLootTableKey = RegistryKey.of(RegistryKeys.LOOT_TABLE,
                Identifier.of("goblinmod", "entities/rock_golem"));

        consumer.accept(
                RockGolemLootTableKey,
                LootTable.builder()
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(1f))
                                .with(ItemEntry.builder(ModItems.COIN)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(12.0f, 16.0f)
                                        ))
                                )
                        )
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(1f))
                                .with(ItemEntry.builder(Items.COBBLED_DEEPSLATE)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(5.0f, 15.0f)
                                        ))
                                )
                        )
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(0.5f))
                                .with(ItemEntry.builder(Items.FLINT)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(2.0f, 4.0f)
                                        ))
                                )
                        )
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(0.1f))
                                .with(ItemEntry.builder(Items.IRON_ORE)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(1.0f, 2.0f)
                                        ))
                                )
                        )
        );

        RegistryKey<LootTable> WyvernLootTableKey = RegistryKey.of(RegistryKeys.LOOT_TABLE,
                Identifier.of("goblinmod", "entities/wyvern"));

        consumer.accept(
                WyvernLootTableKey,
                LootTable.builder()
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(1f))
                                .with(ItemEntry.builder(ModItems.COIN)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(15.0f, 30.0f)
                                        ))
                                )
                        )
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(0.1f))
                                .with(ItemEntry.builder(Items.GOLD_INGOT)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(2.0f, 8.0f)
                                        ))
                                )
                        )
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(0.05f))
                                .with(ItemEntry.builder(ModItems.WYVERN_CLAWS)
                                        .apply(SetCountLootFunction.builder(
                                                UniformLootNumberProvider.create(1.0f, 1.0f)
                                        ))
                                )
                        )
        );

    }

}