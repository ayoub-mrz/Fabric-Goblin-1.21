package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.WyvernEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WyvernRenderer extends GeoEntityRenderer<WyvernEntity> {


    public WyvernRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new WyvernModel<>());
    }

    @Override
    public Identifier getTextureLocation(WyvernEntity animatable) {
        return Identifier.of(GoblinMod.MOD_ID, "textures/entity/wyvern.png");
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, MatrixStack poseStack,
                                    WyvernEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick,
                                    int packedLight, int packedOverlay) {
        super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model, isReRender, partialTick,
                packedLight, packedOverlay);
        this.withScale(0.6f);
    }


}
