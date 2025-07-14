package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.GoblinEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GoblinModel<T extends GoblinEntity> extends GeoModel<GoblinEntity> {


    @Override
    public Identifier getModelResource(GoblinEntity goblinEntity) {
        return Identifier.of(GoblinMod.MOD_ID, "geo/goblin.geo.json");
    }

    @Override
    public Identifier getTextureResource(GoblinEntity goblinEntity) {
        return Identifier.of(GoblinMod.MOD_ID, "textures/entity/goblin.png");
    }

    @Override
    public Identifier getAnimationResource(GoblinEntity goblinEntity) {
        return Identifier.of(GoblinMod.MOD_ID, "animations/goblin.animation.json");
    }

    @Override
    public void setCustomAnimations(GoblinEntity animatable, long instanceId, AnimationState<GoblinEntity> animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }

    

}
