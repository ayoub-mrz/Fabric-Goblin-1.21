package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.SmallRockProjectileEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SmallRockProjectileModel extends EntityModel<SmallRockProjectileEntity> {
    public static final EntityModelLayer SMALLROCK = new EntityModelLayer(Identifier.of(GoblinMod.MOD_ID, "small_rock"), "main");
    private final ModelPart rock;

    public SmallRockProjectileModel(ModelPart root) {
        this.rock = root.getChild("rock");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData rock = modelPartData.addChild("rock", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 5.0F, 0.0F));
        return TexturedModelData.of(modelData, 48, 48);
    }

    @Override
    public void setAngles(SmallRockProjectileEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        rock.render(matrices, vertexConsumer, light, overlay, color);
    }
}
