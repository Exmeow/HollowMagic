package com.bilibili.hollowmagic.render.entity;

import com.bilibili.hollowmagic.HollowMagicMod;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.util.Identifier;

public class ShadeSoulEntityRenderer extends VengefulSpiritEntityRenderer{
    public ShadeSoulEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
    private static final Identifier SHADE_SOUL_TEXTURE = new Identifier(HollowMagicMod.MODID,"textures/entity/shade_soul/shade_soul.png");

    @Override
    public Identifier getTexture(WitherSkullEntity witherSkullEntity) {
        return SHADE_SOUL_TEXTURE;
    }
}
