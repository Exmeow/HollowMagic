package com.bilibili.hollowmagic.network.packet.c2s;

import com.bilibili.hollowmagic.HollowMagicMod;
import com.bilibili.hollowmagic.entity.ShadeSoulEntity;
import com.bilibili.hollowmagic.entity.VengefulSpiritEntity;
import com.bilibili.hollowmagic.manager.PlayerEntityManager;
import com.bilibili.hollowmagic.sound.ModSounds;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;

public final class SpellAction {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler ignoredHandler, PacketByteBuf ignoredBuf, PacketSender ignoredSender, boolean isSuperSpell){
        if (!player.interactionManager.getGameMode().equals(GameMode.SPECTATOR)){
            PlayerEntityManager manager = HollowMagicMod.helpGetManager(player);
            if (player.getPitch() < 45 && player.getPitch() > -45 && manager.canCast()) {
                if (manager.soul >= 6 && !isSuperSpell) {
                    float pitch = 0f;
                    float yaw = player.getYaw();
                    float roll = 0f;
                    float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
                    float g = -MathHelper.sin((pitch + roll) * 0.017453292F);
                    float h = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
                    VengefulSpiritEntity vengefulSpiritEntity = VengefulSpiritEntity.build(player.getWorld(), player, f, g, h);
                    vengefulSpiritEntity.setPosition(player.getPos());
                    boolean bl = player.getWorld().spawnEntity(vengefulSpiritEntity);
                    if (bl) {
                        HollowMagicMod.helpAddSoul(manager, -6);
                        ServerWorld world = server.getWorld(player.getWorld().getRegistryKey());
                        if (world != null) {
                            world.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.VENGEFUL_SPIRIT_SPELL_SOUND_EVENT, SoundCategory.PLAYERS, 1f, 1f);
                        }
                    }
                } else if (manager.soul >= 20) {
                    float pitch = 0f;
                    float yaw = player.getYaw();
                    float roll = 0f;
                    float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
                    float g = -MathHelper.sin((pitch + roll) * 0.017453292F);
                    float h = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
                    ShadeSoulEntity shadeSoulEntity = ShadeSoulEntity.build(player.getWorld(), player, f, g, h);
                    shadeSoulEntity.setPosition(player.getPos());
                    boolean bl = player.getWorld().spawnEntity(shadeSoulEntity);
                    if (bl) {
                        HollowMagicMod.helpAddSoul(manager, -20);
                        ServerWorld world = server.getWorld(player.getWorld().getRegistryKey());
                        if (world != null) {
                            world.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.SHADE_SOUL_SPELL_SOUND_EVENT, SoundCategory.PLAYERS, 1f, 1f);
                        }
                    }
                }
            } else if (player.getPitch() >= 45 && !player.isOnGround() && manager.canCast() && player.getVelocity().y < 0) {
                if (manager.soul >= 6 && !isSuperSpell) {
                    manager.castingDesolateDive = true;
                    manager.isSuperSpell = false;
                    player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.DESOLATE_DIVE_SPELL_SOUND_EVENT, SoundCategory.PLAYERS, 1f, 1f);
                    HollowMagicMod.helpAddSoul(manager, -6);
                } else if (manager.soul >= 20){
                    manager.castingDesolateDive = true;
                    manager.isSuperSpell = true;
                    player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.DESCENDING_DARK_SPELL_SOUND_EVENT, SoundCategory.PLAYERS, 1f, 1f);
                    HollowMagicMod.helpAddSoul(manager, -20);
                }
            } else if (player.getPitch() <= -45 && manager.canCast()) {
                if (manager.soul >= 6 && !isSuperSpell){
                    manager.castingHowlingWraithsTick = 4;
                    manager.isSuperSpell = false;
                    player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.HOWLING_WRAITHS_SPELL_SOUND_EVENT, SoundCategory.PLAYERS, 1f, 1f);
                    HollowMagicMod.helpAddSoul(manager,-6);
                } else if (manager.soul >= 20) {
                    manager.castingHowlingWraithsTick = 3;
                    manager.isSuperSpell = true;
                    player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.ABYSS_SHRIEK_SPELL_SOUND_EVENT, SoundCategory.PLAYERS, 1f, 1f);
                    HollowMagicMod.helpAddSoul(manager,-20);
                }
            }
        }
    }
}