package net.ayoubmrz.goblinmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.ayoubmrz.goblinmod.item.ModItems;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

import javax.annotation.processing.Generated;
import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

//        itemModelGenerator.register(ModItems.BONE_ITEM, Models.GENERATED);

        itemModelGenerator.register(ModItems.GOBLIN_SPAWN_EGG,
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));
    }
}