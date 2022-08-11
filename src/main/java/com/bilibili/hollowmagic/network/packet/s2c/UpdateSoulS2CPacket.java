package com.bilibili.hollowmagic.network.packet.s2c;

import com.bilibili.hollowmagic.HollowMagicModClient;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class UpdateSoulS2CPacket {
    public static void receive(MinecraftClient ignoredClient, ClientPlayNetworkHandler ignoredHandler, PacketByteBuf buf, PacketSender ignoredResponseSender){
        HollowMagicModClient.soul = buf.getFloat(0);
    }
}
