package com.bilibili.hollowmagic;

import com.bilibili.hollowmagic.event.AttackEntityHandler;
import net.fabricmc.api.ModInitializer;
import com.bilibili.hollowmagic.manager.PlayerEntityManager;
import com.bilibili.hollowmagic.network.ModMesseges;
import com.bilibili.hollowmagic.sound.ModSounds;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.PlaySoundIdS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class HollowMagicMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("hollow_magic");

	public static final String MODID = "hollow_magic";

	public static DamageSource spellDamage(Entity attacker){
		return new EntityDamageSource("spell",attacker);
	}

	public static List<PlayerEntityManager> managers;

	public static PlayerEntityManager helpGetManager(ServerPlayerEntity playerEntity){
		if (playerEntity.isRemoved()){
			throw new IllegalStateException("Player is removed");
		}
		for (int i = managers.size()-1;i>=0;i--) {
			if(managers.get(i).serverPlayerEntity.isRemoved()){
				managers.remove(i);
			} else if (managers.get(i).serverPlayerEntity.getUuid().equals(playerEntity.getUuid())){
				return managers.get(i);
			}
		}
		throw new NoSuchElementException("No such player: " + playerEntity);
	}

	public static void helpAddSoul(PlayerEntityManager manager, float value){
		float last = manager.soul;
		manager.soul += value;
		if(manager.soul < 0){
			manager.soul = 0;
		} else if (manager.soul > 20){
			manager.soul = 20;
		}

		PacketByteBuf packetByteBuf = PacketByteBufs.create();
		packetByteBuf.writeFloat(manager.soul);
		ServerPlayNetworking.send(manager.serverPlayerEntity,ModMesseges.UPDATE_SOUL_ID,packetByteBuf);

		if (manager.soul >= 6 && last < 6){
			manager.serverPlayerEntity.networkHandler.sendPacket(new PlaySoundIdS2CPacket(new Identifier(HollowMagicMod.MODID,"player_got_soul"), SoundCategory.PLAYERS,manager.serverPlayerEntity.getPos(),1f,1f,manager.serverPlayerEntity.getWorld().getRandom().nextLong()));
		}
		if (manager.soul >= 20 && last < 20){
			manager.serverPlayerEntity.networkHandler.sendPacket(new PlaySoundIdS2CPacket(new Identifier(HollowMagicMod.MODID,"player_got_full_soul"), SoundCategory.PLAYERS,manager.serverPlayerEntity.getPos(),1f,1f,manager.serverPlayerEntity.getWorld().getRandom().nextLong()));
		}
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModSounds.registerSoundEvents();

		ModMesseges.registerPackets();

		AttackEntityHandler.register();

		managers = new ArrayList<>();
		LOGGER.info("Initialized HollowMagicMod");
	}
}
