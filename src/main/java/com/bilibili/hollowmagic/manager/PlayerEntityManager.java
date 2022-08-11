package com.bilibili.hollowmagic.manager;

import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerEntityManager {
    public final ServerPlayerEntity serverPlayerEntity;

    public float soul = 0;
    public boolean castingDesolateDive = false;
    public boolean isSuperSpell = false;
    public int castingHowlingWraithsTick = 0;

    public boolean canCast(){
        return castingHowlingWraithsTick == 0 && !castingDesolateDive;
    }

    public PlayerEntityManager(ServerPlayerEntity serverPlayerEntity) {
        this.serverPlayerEntity = serverPlayerEntity;
    }
}