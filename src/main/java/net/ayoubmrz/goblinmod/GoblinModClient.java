package net.ayoubmrz.goblinmod;

import net.ayoubmrz.goblinmod.entity.ModEntities;
import net.ayoubmrz.goblinmod.entity.client.GoblinModel;
import net.ayoubmrz.goblinmod.entity.client.GoblinRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class GoblinModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(GoblinModel.GOBLIN, GoblinModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.GOBLIN, GoblinRenderer::new);

    }
}
