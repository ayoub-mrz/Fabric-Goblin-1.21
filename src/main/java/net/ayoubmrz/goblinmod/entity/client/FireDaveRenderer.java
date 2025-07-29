package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.FireDaveEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireDaveRenderer extends GeoEntityRenderer<FireDaveEntity> {


    public FireDaveRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FireDaveModel<>());
    }

    @Override
    public Identifier getTextureLocation(FireDaveEntity animatable) {
        return Identifier.of(GoblinMod.MOD_ID, "textures/entity/fire_dave.png");
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, MatrixStack poseStack,
                                    FireDaveEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick,
                                    int packedLight, int packedOverlay) {
        super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model, isReRender, partialTick,
                packedLight, packedOverlay);
        this.withScale(2.2f);
    }


}
