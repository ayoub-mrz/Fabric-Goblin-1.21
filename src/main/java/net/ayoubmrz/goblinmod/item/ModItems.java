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
            new SpawnEggItem(ModEntities.GOBLIN, 0x5a925f, 0x412f1f, new Item.Settings()));

    public static final Item LIL_DAVE_SPAWN_EGG = registerItem("lil_dave_spawn_egg",
            new SpawnEggItem(ModEntities.LILDAVE, 0x9dc783, 0xbfaf5f, new Item.Settings()));
    public static final Item FIRE_DAVE_SPAWN_EGG = registerItem("fire_dave_spawn_egg",
            new SpawnEggItem(ModEntities.FIREDAVE, 0x9a474a, 0xc88e5c, new Item.Settings()));
    public static final Item WATER_DAVE_SPAWN_EGG = registerItem("water_dave_spawn_egg",
            new SpawnEggItem(ModEntities.WATERDAVE, 0x378ad1, 0x42c0bf, new Item.Settings()));
    public static final Item LIGHTNING_DAVE_SPAWN_EGG = registerItem("lightning_dave_spawn_egg",
            new SpawnEggItem(ModEntities.LIGHTNINGDAVE, 0xb0a14c, 0xd3bf55, new Item.Settings()));

    public static final Item BIG_WOLF_SPAWN_EGG = registerItem("big_wolf_spawn_egg",
            new SpawnEggItem(ModEntities.BIGWOLF, 0xf2f2f2, 0xc7c7c7, new Item.Settings()));

    public static final Item ROCK_GOLEM_SPAWN_EGG = registerItem("rock_golem_spawn_egg",
            new SpawnEggItem(ModEntities.ROCKGOLEM, 0x717577, 0x4b4f50, new Item.Settings()));

    public static final Item WYVERN_SPAWN_EGG = registerItem("wyvern_spawn_egg",
            new SpawnEggItem(ModEntities.WYVERN, 0x438672, 0xebe13e, new Item.Settings()));

    public static final Item ANCIENT_BONE = registerItem("ancient_bone", new Item( new Item.Settings()));
    public static final Item COIN = registerItem("coin", new Item( new Item.Settings()));
    public static final Item ROCKITEM = registerItem("rock_item", new Item( new Item.Settings()));
    public static final Item WYVERN_CLAWS = registerItem("wyvern_claws", new Item( new Item.Settings()));

    public static final Item RED_BALL = registerItem("red_ball", new Item( new Item.Settings()));
    public static final Item BLUE_BALL = registerItem("blue_ball", new Item( new Item.Settings()));
    public static final Item YELLOW_BALL = registerItem("yellow_ball", new Item( new Item.Settings()));

    public static final Item SWORD_HILT = registerItem("sword_hilt", new Item( new Item.Settings()));
    public static final Item BLADE = registerItem("blade", new Item( new Item.Settings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(GoblinMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        GoblinMod.LOGGER.info("Registering Mod Items for " + GoblinMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(ANCIENT_BONE);
            entries.add(RED_BALL);
            entries.add(BLUE_BALL);
            entries.add(YELLOW_BALL);
            entries.add(WYVERN_CLAWS);
            entries.add(SWORD_HILT);
            entries.add(BLADE);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(GOBLIN_SPAWN_EGG);
            entries.add(LIL_DAVE_SPAWN_EGG);
            entries.add(FIRE_DAVE_SPAWN_EGG);
            entries.add(WATER_DAVE_SPAWN_EGG);
            entries.add(LIGHTNING_DAVE_SPAWN_EGG);
            entries.add(BIG_WOLF_SPAWN_EGG);
            entries.add(ROCK_GOLEM_SPAWN_EGG);
            entries.add(WYVERN_SPAWN_EGG);
        });
    }
}