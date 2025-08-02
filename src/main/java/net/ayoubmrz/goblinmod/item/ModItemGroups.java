package net.ayoubmrz.goblinmod.item;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup MITHRIL_EQUIPMENTS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(GoblinMod.MOD_ID, "muck_mobs"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.RED_BALL))
                    .displayName(Text.translatable("itemgroup.goblinmod.muck_mobs"))
                    .entries((displayContext, entries) -> {

                        entries.add(ModItems.GOBLIN_SPAWN_EGG);
                        entries.add(ModItems.LIL_DAVE_SPAWN_EGG);
                        entries.add(ModItems.BIG_WOLF_SPAWN_EGG);
                        entries.add(ModItems.FIRE_DAVE_SPAWN_EGG);
                        entries.add(ModItems.LIGHTNING_DAVE_SPAWN_EGG);
                        entries.add(ModItems.WATER_DAVE_SPAWN_EGG);
                        entries.add(ModItems.ROCK_GOLEM_SPAWN_EGG);
                        entries.add(ModItems.WYVERN_SPAWN_EGG);

                        entries.add(ModItems.RED_BALL);
                        entries.add(ModItems.BLUE_BALL);
                        entries.add(ModItems.YELLOW_BALL);
                        entries.add(ModItems.WYVERN_CLAWS);

                    }).build());

    public static void registerItemGroups() {
        GoblinMod.LOGGER.info("Registering Item Groups for " + GoblinMod.MOD_ID);
    }
}

