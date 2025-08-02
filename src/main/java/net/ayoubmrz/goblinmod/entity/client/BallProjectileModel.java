package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.BallProjectileEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BallProjectileModel extends EntityModel<BallProjectileEntity> {
    public static final EntityModelLayer BALL = new EntityModelLayer(Identifier.of(GoblinMod.MOD_ID, "ball"), "main");
    private final ModelPart ball;

    public BallProjectileModel(ModelPart root) {
        this.ball = root.getChild("ball");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData ball = modelPartData.addChild("ball", ModelPartBuilder.create().uv(0, 0).cuboid(-2.8066F, -15.75F, -0.2463F, 5.6131F, 31.5F, 0.4926F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.3768F, -15.375F, -3.7364F, 4.7535F, 30.75F, 7.4727F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-5.6154F, -14.75F, -2.6278F, 11.2307F, 29.5F, 5.2555F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.5085F, -14.375F, -6.8618F, 3.0171F, 28.75F, 13.7235F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-5.7711F, -13.75F, -5.7993F, 11.5422F, 27.5F, 11.5986F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-8.9275F, -13.125F, -2.0084F, 17.8551F, 26.25F, 4.0168F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-4.0543F, -12.75F, -8.775F, 8.1085F, 25.5F, 17.55F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-8.781F, -12.125F, -5.6461F, 17.562F, 24.25F, 11.2922F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.8966F, -11.75F, -10.8228F, 1.7933F, 23.5F, 21.6456F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-11.1242F, -11.5F, -0.0543F, 22.2483F, 23.0F, 0.1085F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-6.952F, -11.125F, -9.1599F, 13.9041F, 22.25F, 18.3198F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-11.3198F, -10.5F, -4.1967F, 22.6396F, 21.0F, 8.3933F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-3.6501F, -10.125F, -11.839F, 7.3002F, 20.25F, 23.6779F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-9.8038F, -9.5F, -8.3448F, 19.6076F, 19.0F, 16.6896F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-13.1852F, -8.875F, -1.8396F, 26.3705F, 17.75F, 3.6792F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-6.6821F, -8.5F, -11.7941F, 13.3641F, 17.0F, 23.5881F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-12.297F, -7.875F, -6.5397F, 24.594F, 15.75F, 13.0794F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.3259F, -7.5F, -13.9406F, 4.6517F, 15.0F, 27.8812F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-9.6548F, -6.875F, -10.7479F, 19.3097F, 13.75F, 21.4959F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-14.1858F, -6.25F, -3.9623F, 28.3717F, 12.5F, 7.9245F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-5.544F, -5.875F, -13.8112F, 11.0879F, 11.75F, 27.6224F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-12.2789F, -5.25F, -8.8128F, 24.5578F, 10.5F, 17.6256F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.4811F, -4.875F, -15.2316F, 0.9621F, 9.75F, 30.4633F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-15.293F, -4.625F, -0.8568F, 30.586F, 9.25F, 1.7135F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-8.6859F, -4.25F, -12.7473F, 17.3718F, 8.5F, 25.4945F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-14.3164F, -3.625F, -6.1563F, 28.6328F, 7.25F, 12.3127F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-3.8452F, -3.25F, -15.1872F, 7.6905F, 6.5F, 30.3744F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-11.4808F, -2.625F, -10.8306F, 22.9615F, 5.25F, 21.6611F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-15.5897F, -2.0F, -2.9935F, 31.1794F, 4.0F, 5.987F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-7.1289F, -1.625F, -14.2316F, 14.2577F, 3.25F, 28.4632F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-13.6977F, -1.0F, -8.2082F, 27.3953F, 2.0F, 16.4163F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.8223F, -0.625F, -15.8836F, 3.6447F, 1.25F, 31.7672F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 13.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(BallProjectileEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        ball.render(matrices, vertexConsumer, light, overlay, color);
    }
}
