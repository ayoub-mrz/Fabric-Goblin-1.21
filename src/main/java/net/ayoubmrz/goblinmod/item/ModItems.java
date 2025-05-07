package net.ayoubmrz.goblinmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.ModEntities;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item GOBLIN_SPAWN_EGG = registerItem("goblin_spawn_egg",
            new SpawnEggItem(ModEntities.GOBLIN, 0x9dc783, 0xbfaf5f, new Item.Settings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(GoblinMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        GoblinMod.LOGGER.info("Registering Mod Items for " + GoblinMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(GOBLIN_SPAWN_EGG);
        });
    }
}