package com.bilibili.hollowmagic.event;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;

public final class AttackEntityHandler {
    public static void register(){
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult)->{
            if (entity instanceof LivingEntity livingEntity){
                if (player.getPitch() >= 45 && livingEntity.canTakeDamage() && player.getStackInHand(hand).getItem() instanceof SwordItem && !player.isOnGround()){
                    player.setVelocity(player.getVelocity().x,0.6,player.getVelocity().z);
                }
            }
            return ActionResult.PASS;
        });
    }
}
