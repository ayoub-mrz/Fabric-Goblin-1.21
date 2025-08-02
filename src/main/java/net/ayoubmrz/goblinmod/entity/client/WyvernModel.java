package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.WyvernEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class WyvernModel<T extends WyvernEntity> extends GeoModel<WyvernEntity> {

    @Override
    public Identifier getModelResource(WyvernEntity wyvernEntity) {
        return Identifier.of(GoblinMod.MOD_ID, "geo/wyvern.geo.json");
    }

    @Override
    public Identifier getTextureResource(WyvernEntity wyvernEntity) {
        return Identifier.of(GoblinMod.MOD_ID, "textures/entity/wyvern.png");
    }

    @Override
    public Identifier getAnimationResource(WyvernEntity wyvernEntity) {
        return Identifier.of(GoblinMod.MOD_ID, "animations/wyvern.animation.json");
    }

    @Override
    public void setCustomAnimations(WyvernEntity animatable, long instanceId, AnimationState<WyvernEntity> animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }



}
