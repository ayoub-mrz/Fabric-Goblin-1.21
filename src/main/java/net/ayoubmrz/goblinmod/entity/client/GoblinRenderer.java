package net.ayoubmrz.goblinmod.entity.client;

import net.ayoubmrz.goblinmod.GoblinMod;
import net.ayoubmrz.goblinmod.entity.custom.GoblinEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GoblinRenderer extends GeoEntityRenderer<GoblinEntity> {


    public GoblinRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new GoblinModel<>());
    }

    @Override
    public Identifier getTextureLocation(GoblinEntity animatable) {
        return Identifier.of(GoblinMod.MOD_ID, "textures/entity/goblin.png");
    }

}
