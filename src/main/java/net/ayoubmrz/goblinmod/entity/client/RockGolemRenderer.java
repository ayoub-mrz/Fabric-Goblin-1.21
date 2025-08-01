package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.FireDaveEntity;
import net.ayoubmrz.goblinmod.entity.custom.RockGolemEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RockGolemRenderer extends GeoEntityRenderer<RockGolemEntity> {


    public RockGolemRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new RockGolemModel<>());
    }

    @Override
    public Identifier getTextureLocation(RockGolemEntity animatable) {
        return Identifier.of(GoblinMod.MOD_ID, "textures/entity/rock_golem.png");
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, MatrixStack poseStack,
                                    RockGolemEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick,
                                    int packedLight, int packedOverlay) {
        super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model, isReRender, partialTick,
                packedLight, packedOverlay);
        this.withScale(1.4f);
    }

}
