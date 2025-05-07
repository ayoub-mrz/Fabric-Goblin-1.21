package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.GoblinEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GoblinRenderer extends MobEntityRenderer<GoblinEntity, GoblinModel<GoblinEntity>> {

    public GoblinRenderer(EntityRendererFactory.Context context) {
        super(context, new GoblinModel<>(context.getPart(GoblinModel.GOBLIN)), 0.5f);
    }

    @Override
    public Identifier getTexture(GoblinEntity entity) {
        return Identifier.of(GoblinMod.MOD_ID, "textures/entity/goblin/goblin.png");
    }

    @Override
    public void render(GoblinEntity livingEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
         matrixStack.scale(1f, 1f, 1f);
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

}
