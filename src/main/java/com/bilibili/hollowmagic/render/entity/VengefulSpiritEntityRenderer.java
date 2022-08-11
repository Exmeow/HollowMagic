package com.bilibili.hollowmagic.render.entity;

import com.bilibili.hollowmagic.HollowMagicMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.WitherSkullEntityRenderer;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class VengefulSpiritEntityRenderer extends WitherSkullEntityRenderer {
    public VengefulSpiritEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
    private static final Identifier VENGEFUL_SPIRIT_TEXTURE = new Identifier(HollowMagicMod.MODID,"textures/entity/vengeful_spirit/vengeful_spirit.png");

    @Override
    public Identifier getTexture(WitherSkullEntity witherSkullEntity) {
        return VENGEFUL_SPIRIT_TEXTURE;
    }
}
