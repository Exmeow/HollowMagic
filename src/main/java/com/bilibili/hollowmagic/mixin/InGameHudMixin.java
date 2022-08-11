package com.bilibili.hollowmagic.mixin;

import com.bilibili.hollowmagic.HollowMagicMod;
import com.bilibili.hollowmagic.HollowMagicModClient;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public class InGameHudMixin{
    @Unique
    private static final Identifier texture = new Identifier(HollowMagicMod.MODID,"soul_orb.png");

    @Shadow
    private int scaledWidth;

    @Inject(method = "render",at = @At("TAIL"))
    public void render(MatrixStack matrices, float tickDelta, CallbackInfo ci){
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null){
            String text = Math.round(HollowMagicModClient.soul * 5d) +"%";
            int textLength = client.textRenderer.getWidth(text);
            int textHeight = client.textRenderer.fontHeight;
            int iconSize = 16;
            int iconGap = 2;
            int offsetForIcon = iconSize + iconGap;
            int iconOffset = textLength + iconGap;
            int iconY = 0;
            int myX = scaledWidth / 2 - (textLength + offsetForIcon) / 2;
            int myY = 0;
            RenderSystem.setShaderColor(1,1,1,1);
            RenderSystem.setShaderTexture(0,texture);
            DrawableHelper.drawTexture(matrices,myX + iconOffset,myY+iconY,0,0,16,16,16,16);
            client.textRenderer.draw(matrices,Text.literal(text),myX,myY,16777215);
        }
    }
}