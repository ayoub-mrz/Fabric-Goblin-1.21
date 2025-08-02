package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.BallProjectileEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BallProjectileRenderer extends EntityRenderer<BallProjectileEntity> {
    protected BallProjectileModel model;
    private static final String DEFAULT_TEXTURE = "textures/entity/red_ball.png";

    public BallProjectileRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new BallProjectileModel(ctx.getPart(BallProjectileModel.BALL));
    }

    @Override
    public void render(BallProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.scale(0.3f, 0.3f, 0.3f);

        Identifier textureId = getTexture(entity);

        VertexConsumer vertexconsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers,
                this.model.getLayer(textureId), false, false);
        this.model.render(matrices, vertexconsumer, light, OverlayTexture.DEFAULT_UV);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(BallProjectileEntity entity) {
        String texturePath = entity.getTexturePath();
        if (texturePath == null || texturePath.isEmpty()) {
            texturePath = DEFAULT_TEXTURE;
        }
        return Identifier.of(GoblinMod.MOD_ID, texturePath);
    }


}
