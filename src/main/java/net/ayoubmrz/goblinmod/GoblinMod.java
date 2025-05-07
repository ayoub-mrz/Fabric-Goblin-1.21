package net.ayoubmrz.goblinmod;

import net.ayoubmrz.goblinmod.entity.ModEntities;
import net.ayoubmrz.goblinmod.entity.custom.GoblinEntity;
import net.ayoubmrz.goblinmod.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoblinMod implements ModInitializer {
	public static final String MOD_ID = "goblinmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModEntities.registerModEntities();

		FabricDefaultAttributeRegistry.register(ModEntities.GOBLIN, GoblinEntity.setAttributes());

		ModItems.registerModItems();

	}
}