package net.ayoubmrz.goblinmod;

import net.ayoubmrz.goblinmod.entity.ModEntities;
import net.ayoubmrz.goblinmod.entity.client.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class GoblinModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(ModEntities.GOBLIN, GoblinRenderer::new);
        EntityRendererRegistry.register(ModEntities.LILDAVE, LilDaveRenderer::new);
        EntityRendererRegistry.register(ModEntities.FIREDAVE, FireDaveRenderer::new);
        EntityRendererRegistry.register(ModEntities.WATERDAVE, WaterDaveRenderer::new);
        EntityRendererRegistry.register(ModEntities.LIGHTNINGDAVE, LightningDaveRenderer::new);
        EntityRendererRegistry.register(ModEntities.BIGWOLF, BigWolfRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(BoneProjectileModel.ANCIENTBONE, BoneProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.ANCIENTBONE, BoneProjectileRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(BallProjectileModel.BALL, BallProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.BALL, BallProjectileRenderer::new);


    }
}
