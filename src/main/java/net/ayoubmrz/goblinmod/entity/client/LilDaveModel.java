package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.LilDaveEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class LilDaveModel<T extends LilDaveEntity> extends GeoModel<LilDaveEntity> {

    @Override
    public Identifier getModelResource(LilDaveEntity daveEntity) {
        return Identifier.of(GoblinMod.MOD_ID, "geo/lil_dave.geo.json");
    }

    @Override
    public Identifier getTextureResource(LilDaveEntity daveEntity) {
        return Identifier.of(GoblinMod.MOD_ID, "textures/entity/lil_dave.png");
    }

    @Override
    public Identifier getAnimationResource(LilDaveEntity daveEntity) {
        return Identifier.of(GoblinMod.MOD_ID, "animations/lil_dave.animation.json");
    }

    @Override
    public void setCustomAnimations(LilDaveEntity animatable, long instanceId, AnimationState<LilDaveEntity> animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }



}
