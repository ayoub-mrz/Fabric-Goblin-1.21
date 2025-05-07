package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.GoblinEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class GoblinModel<T extends GoblinEntity> extends SinglePartEntityModel<T> {

    public static final EntityModelLayer GOBLIN = new EntityModelLayer(Identifier.of(GoblinMod.MOD_ID, "goblin"), "main");

    private final ModelPart root;
    private final ModelPart goblin;
    private final ModelPart head;
    private final ModelPart ears;
    public GoblinModel(ModelPart root) {
        this.root = root.getChild("root");
        this.goblin = this.root.getChild("goblin");
        this.head = this.goblin.getChild("head");
        this.ears = this.head.getChild("ears");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData goblin = root.addChild("goblin", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = goblin.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -24.0F, 0.0F));

        ModelPartData headwear = head.addChild("headwear", ModelPartBuilder.create().uv(32, 30).cuboid(-4.0F, -2.0F, -4.0F, 8.0F, 2.0F, 8.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData ears = head.addChild("ears", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData rightear = ears.addChild("rightear", ModelPartBuilder.create(), ModelTransform.of(3.9898F, -28.6508F, 0.5F, 0.0F, -0.1309F, 0.0F));

        ModelPartData cube_r1 = rightear.addChild("cube_r1", ModelPartBuilder.create().uv(40, 8).cuboid(-3.5F, -0.5F, 0.5611F, 7.0F, 4.0F, 0.0F, new Dilation(0.0F))
                .uv(32, 0).cuboid(-3.5F, -0.5F, 0.5F, 7.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.4931F, -2.0992F, -0.7611F, 0.0F, 0.0F, -0.3927F));

        ModelPartData cube_r2 = rightear.addChild("cube_r2", ModelPartBuilder.create().uv(32, 10).cuboid(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.2544F, -3.1875F, -0.2611F, 0.0F, 0.0F, -0.2618F));

        ModelPartData cube_r3 = rightear.addChild("cube_r3", ModelPartBuilder.create().uv(32, 8).cuboid(-2.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.4931F, -2.5992F, -0.2611F, 0.0F, 0.0F, -0.5236F));

        ModelPartData leftear = ears.addChild("leftear", ModelPartBuilder.create(), ModelTransform.of(-4.0102F, -28.6508F, 0.5F, 0.0F, -3.0107F, 0.0F));

        ModelPartData cube_r4 = leftear.addChild("cube_r4", ModelPartBuilder.create().uv(40, 8).cuboid(-3.5F, -0.5F, 0.4689F, 7.0F, 4.0F, 0.0F, new Dilation(0.0F))
                .uv(32, 4).cuboid(-3.5F, -0.5F, 0.5F, 7.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.4931F, -2.0992F, -0.2389F, 0.0F, 0.0F, -0.3927F));

        ModelPartData cube_r5 = leftear.addChild("cube_r5", ModelPartBuilder.create().uv(32, 10).cuboid(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.2544F, -3.1875F, 0.2611F, 0.0F, 0.0F, -0.2618F));

        ModelPartData cube_r6 = leftear.addChild("cube_r6", ModelPartBuilder.create().uv(32, 8).cuboid(-2.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.4931F, -2.5992F, 0.2611F, 0.0F, 0.0F, -0.5236F));

        ModelPartData eyebrow = head.addChild("eyebrow", ModelPartBuilder.create(), ModelTransform.pivot(-0.1201F, -4.3464F, -4.5F));

        ModelPartData cube_r7 = eyebrow.addChild("cube_r7", ModelPartBuilder.create().uv(16, 32).cuboid(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, 0.0F, 0.0F, -3.1416F, 0.0F, -2.9234F));

        ModelPartData cube_r8 = eyebrow.addChild("cube_r8", ModelPartBuilder.create().uv(16, 32).cuboid(-1.0F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.6201F, 0.5964F, 0.5F, 0.0F, 0.0F, -0.2182F));

        ModelPartData eyelid = head.addChild("eyelid", ModelPartBuilder.create().uv(17, 34).cuboid(-3.0F, -1.0F, -4.0F, 6.0F, 1.0F, 0.0F, new Dilation(0.001F)), ModelTransform.pivot(0.0F, -4.0F, 0.0F));

        ModelPartData body = goblin.addChild("body", ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, -18.0F, 0.0F));

        ModelPartData left_arm = body.addChild("left_arm", ModelPartBuilder.create().uv(24, 16).mirrored().cuboid(-1.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(5.0F, -6.0F, 0.0F));

        ModelPartData right_arm = body.addChild("right_arm", ModelPartBuilder.create().uv(24, 16).cuboid(-3.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -6.0F, 0.0F));

        ModelPartData left_leg = goblin.addChild("left_leg", ModelPartBuilder.create().uv(0, 32).mirrored().cuboid(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(1.9F, -12.0F, 0.0F));

        ModelPartData right_leg = goblin.addChild("right_leg", ModelPartBuilder.create().uv(0, 32).cuboid(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.9F, -12.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(GoblinEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);

        this.animateMovement(GoblinAnimations.ANIM_GOBLIN_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.updateAnimation(entity.idleAnimationState, GoblinAnimations.ANIM_GOBLIN_IDLE, ageInTicks, 1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        root.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return goblin;
    }
}
