package com.bilibili.hollowmagic;

import com.bilibili.hollowmagic.entity.ModEntities;
import com.bilibili.hollowmagic.render.entity.ShadeSoulEntityRenderer;
import com.bilibili.hollowmagic.render.entity.VengefulSpiritEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import com.bilibili.hollowmagic.event.KeyInputHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class HollowMagicModClient implements ClientModInitializer {
    public static float soul = 0;

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        EntityRendererRegistry.register(ModEntities.VENGEFUL_SPIRIT, VengefulSpiritEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.SHADE_SOUL, ShadeSoulEntityRenderer::new);
    }
}
