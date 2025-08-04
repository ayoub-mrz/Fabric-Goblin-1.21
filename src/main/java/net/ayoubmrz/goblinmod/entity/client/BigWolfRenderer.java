package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.BigWolfEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BigWolfRenderer extends GeoEntityRenderer<BigWolfEntity> {

    public BigWolfRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new BigWolfModel<>());
    }

    @Override
    public Identifier getTextureLocation(BigWolfEntity animatable) {
        return Identifier.of(GoblinMod.MOD_ID, "textures/entity/big_wolf.png");
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, MatrixStack poseStack,
                                    BigWolfEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick,
                                    int packedLight, int packedOverlay) {
        super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model, isReRender, partialTick,
                packedLight, packedOverlay);
        this.withScale(1.6f);
    }

}
