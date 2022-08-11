package com.bilibili.hollowmagic.network;

import com.bilibili.hollowmagic.HollowMagicMod;
import com.bilibili.hollowmagic.network.packet.c2s.SuperSpellC2SPacket;
import com.bilibili.hollowmagic.network.packet.s2c.UpdateSoulS2CPacket;
import com.bilibili.hollowmagic.network.packet.c2s.SpellC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMesseges {
    public static final Identifier SPELL_ID = new Identifier(HollowMagicMod.MODID,"spell");
    public static final Identifier SUPER_SPELL_ID = new Identifier(HollowMagicMod.MODID,"super_spell");
    public static final Identifier UPDATE_SOUL_ID = new Identifier(HollowMagicMod.MODID,"update_soul");
    public static void registerPackets(){
        ServerPlayNetworking.registerGlobalReceiver(SPELL_ID, SpellC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SUPER_SPELL_ID, SuperSpellC2SPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(UPDATE_SOUL_ID, UpdateSoulS2CPacket::receive);
    }
}
