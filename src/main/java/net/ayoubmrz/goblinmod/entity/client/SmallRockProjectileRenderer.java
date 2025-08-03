package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.SmallRockProjectileEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SmallRockProjectileRenderer extends EntityRenderer<SmallRockProjectileEntity> {
    protected RockProjectileModel model;

    public SmallRockProjectileRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new RockProjectileModel(ctx.getPart(RockProjectileModel.ROCK));
    }

    @Override
    public void render(SmallRockProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.scale(0.2f, 0.2f, 0.2f);

        VertexConsumer vertexconsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers,
                this.model.getLayer(Identifier.of(GoblinMod.MOD_ID, "textures/entity/rock.png")), false, false);
        this.model.render(matrices, vertexconsumer, light, OverlayTexture.DEFAULT_UV);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(SmallRockProjectileEntity entity) {
        return Identifier.of(GoblinMod.MOD_ID, "textures/entity/rock.png");
    }
}
