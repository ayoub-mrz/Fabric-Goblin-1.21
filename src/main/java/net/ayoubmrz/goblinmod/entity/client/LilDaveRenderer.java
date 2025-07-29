package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.LilDaveEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LilDaveRenderer extends GeoEntityRenderer<LilDaveEntity> {


    public LilDaveRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new LilDaveModel<>());
    }

    @Override
    public Identifier getTextureLocation(LilDaveEntity animatable) {
        return Identifier.of(GoblinMod.MOD_ID, "textures/entity/lil_dave.png");
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, MatrixStack poseStack,
                                    LilDaveEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick,
                                    int packedLight, int packedOverlay) {
        super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model, isReRender, partialTick,
                packedLight, packedOverlay);
        this.withScale(1.6f);
    }


}
