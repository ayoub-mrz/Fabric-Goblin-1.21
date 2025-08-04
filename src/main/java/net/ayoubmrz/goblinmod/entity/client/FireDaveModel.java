package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.FireDaveEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class FireDaveModel<T extends FireDaveEntity> extends GeoModel<FireDaveEntity> {

    @Override
    public Identifier getModelResource(FireDaveEntity fireDaveEntity) {
        return Identifier.of(GoblinMod.MOD_ID, "geo/lil_dave.geo.json");
    }

    @Override
    public Identifier getTextureResource(FireDaveEntity fireDaveEntity) {
        return Identifier.of(GoblinMod.MOD_ID, "textures/entity/fire_dave.png");
    }

    @Override
    public Identifier getAnimationResource(FireDaveEntity fireDaveEntity) {
        return Identifier.of(GoblinMod.MOD_ID, "animations/lil_dave.animation.json");
    }

    @Override
    public void setCustomAnimations(FireDaveEntity animatable, long instanceId, AnimationState<FireDaveEntity> animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }



}
