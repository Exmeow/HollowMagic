package com.bilibili.hollowmagic.mixin;

import com.bilibili.hollowmagic.HollowMagicMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "damage", at = @At("HEAD"),cancellable = true)
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.getName().equals("spell") && source.getAttacker() != null && source.getAttacker().getUuid().equals(((LivingEntity)(Object)this).getUuid())){
            cir.setReturnValue(false);
        }
    }
    @Inject(method = "applyDamage",at = @At("HEAD"))
    public void applyDamage(DamageSource source, float amount, CallbackInfo ci){
        if (source.getAttacker() instanceof ServerPlayerEntity PlayerEntity && !source.getName().equals("spell")){
            HollowMagicMod.helpAddSoul(HollowMagicMod.helpGetManager(PlayerEntity), amount / 3);
        }
    }
}