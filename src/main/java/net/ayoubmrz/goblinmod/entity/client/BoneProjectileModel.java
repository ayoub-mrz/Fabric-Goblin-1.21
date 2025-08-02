package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.BoneProjectileEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BoneProjectileModel extends EntityModel<BoneProjectileEntity> {
    public static final EntityModelLayer ANCIENTBONE = new EntityModelLayer(Identifier.of(GoblinMod.MOD_ID, "ancient_bone"), "main");
    private final ModelPart bone;

    public BoneProjectileModel(ModelPart root) {
        this.bone = root.getChild("bone");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create().uv(4, 2).cuboid(-1.0F, -1.75F, 4.4571F, 1.0F, 1.0F, 1.0F, new Dilation(0.04F))
                .uv(0, 0).cuboid(-1.0F, -10.75F, 4.4571F, 1.0F, 13.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, 12.0F, -5.0F));

        ModelPartData cube_r1 = bone.addChild("cube_r1", ModelPartBuilder.create().uv(4, 0).cuboid(-0.5F, -4.0355F, 2.1213F, 1.0F, 1.0F, 1.0F, new Dilation(0.001F)), ModelTransform.of(-0.5F, 6.6464F, 5.1036F, 0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r2 = bone.addChild("cube_r2", ModelPartBuilder.create().uv(4, 0).cuboid(-0.5F, -4.0355F, 2.1213F, 1.0F, 1.0F, 1.0F, new Dilation(0.001F)), ModelTransform.of(-0.5F, 6.6464F, 6.1036F, 0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r3 = bone.addChild("cube_r3", ModelPartBuilder.create().uv(4, 0).cuboid(-0.5F, -4.0355F, 2.1213F, 1.0F, 1.0F, 1.0F, new Dilation(0.001F)), ModelTransform.of(-0.5F, -6.3536F, 6.1036F, 0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r4 = bone.addChild("cube_r4", ModelPartBuilder.create().uv(4, 0).cuboid(-0.5F, -4.0355F, 2.1213F, 1.0F, 1.0F, 1.0F, new Dilation(0.001F)), ModelTransform.of(-0.5F, -6.3536F, 5.1036F, 0.7854F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void setAngles(BoneProjectileEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        bone.render(matrices, vertexConsumer, light, overlay, color);
    }
}
