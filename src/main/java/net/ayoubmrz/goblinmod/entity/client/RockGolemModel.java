package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.RockGolemEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class RockGolemModel<T extends RockGolemEntity> extends GeoModel<RockGolemEntity> {


    @Override
    public Identifier getModelResource(RockGolemEntity rockGolemEntity) {
        return Identifier.of(GoblinMod.MOD_ID, "geo/rock_golem.geo.json");
    }

    @Override
    public Identifier getTextureResource(RockGolemEntity rockGolemEntity) {
        return Identifier.of(GoblinMod.MOD_ID, "textures/entity/rock_golem.png");
    }

    @Override
    public Identifier getAnimationResource(RockGolemEntity rockGolemEntity) {
        return Identifier.of(GoblinMod.MOD_ID, "animations/rock_golem.animation.json");
    }

    @Override
    public void setCustomAnimations(RockGolemEntity animatable, long instanceId, AnimationState<RockGolemEntity> animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }

    

}
