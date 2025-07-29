package net.ayoubmrz.goblinmod.datagen;

import net.ayoubmrz.goblinmod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
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
                                .conditionally(RandomChanceLootCondition.builder(0.01f))
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

    }
}