package com.bilibili.hollowmagic.mixin;

import com.bilibili.hollowmagic.HollowMagicMod;
import com.bilibili.hollowmagic.explosion.WeakExplosionBehavior;
import com.bilibili.hollowmagic.manager.PlayerEntityManager;
import com.bilibili.hollowmagic.sound.ModSounds;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends LivingEntityMixin {
    public PlayerEntityManager playerEntityManager;

    @Shadow
    private int joinInvulnerabilityTicks;

    private void createExplosion(ServerPlayerEntity serverPlayerEntity,boolean isSuperDesolateDive){
        PlayerEntityManager manager = HollowMagicMod.helpGetManager(serverPlayerEntity);
        if (!isSuperDesolateDive) {
            manager.castingDesolateDive = false;
            serverPlayerEntity.getWorld().createExplosion(serverPlayerEntity, HollowMagicMod.spellDamage(serverPlayerEntity),new WeakExplosionBehavior(), serverPlayerEntity.getX(), serverPlayerEntity.getY(), serverPlayerEntity.getZ(), 2, false, Explosion.DestructionType.BREAK);
            serverPlayerEntity.getWorld().playSound(null, serverPlayerEntity.getX(), serverPlayerEntity.getY(), serverPlayerEntity.getZ(), ModSounds.DESOLATE_DIVE_HIT_SOUND_EVENT, SoundCategory.PLAYERS, 1f, 1f);
            joinInvulnerabilityTicks = 20;
        } else {
            manager.castingDesolateDive = false;
            serverPlayerEntity.getWorld().createExplosion(serverPlayerEntity, HollowMagicMod.spellDamage(serverPlayerEntity), null, serverPlayerEntity.getX(), serverPlayerEntity.getY(), serverPlayerEntity.getZ(), 6, false, Explosion.DestructionType.DESTROY);
            serverPlayerEntity.getWorld().playSound(null, serverPlayerEntity.getX(), serverPlayerEntity.getY(), serverPlayerEntity.getZ(), ModSounds.DESCENDING_DARK_HIT_SOUND_EVENT, SoundCategory.PLAYERS, 1f, 1f);
            joinInvulnerabilityTicks = 20;
        }
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void Tick(CallbackInfo ci) {
        PlayerEntity self = (PlayerEntity)(Object) this;
        if (self instanceof ServerPlayerEntity serverPlayerEntity && !serverPlayerEntity.isRemoved()){
            PlayerEntityManager manager = HollowMagicMod.helpGetManager(serverPlayerEntity);
            if (manager.castingDesolateDive) {
                if (!HollowMagicMod.helpGetManager(serverPlayerEntity).isSuperSpell) {
                    serverPlayerEntity.fallDistance = 0;
                    serverPlayerEntity.getWorld().spawnParticles(ParticleTypes.POOF, serverPlayerEntity.getX(), serverPlayerEntity.getY(), serverPlayerEntity.getZ(), 1, 0, 1, 0, 1);
                    if (serverPlayerEntity.isOnGround()) {
                        createExplosion(serverPlayerEntity,false);
                    } else {
                        float f = serverPlayerEntity.getDimensions(serverPlayerEntity.getPose()).width * 0.8F;
                        Box box = Box.of(serverPlayerEntity.getEyePos(), f, 1.0E-6, f);
                        if (BlockPos.stream(box).map(serverPlayerEntity.getWorld()::getBlockState).filter(state -> state.getBlock() instanceof FluidBlock).toArray().length > 0) {
                            createExplosion(serverPlayerEntity,false);
                        }
                    }
                } else {
                    serverPlayerEntity.fallDistance = 0;
                    serverPlayerEntity.getWorld().spawnParticles(ParticleTypes.SMOKE, serverPlayerEntity.getX(), serverPlayerEntity.getY(), serverPlayerEntity.getZ(), 1, 0, 1, 0, 1);
                    if (serverPlayerEntity.isOnGround()) {
                        createExplosion(serverPlayerEntity,true);
                    } else {
                        float f = serverPlayerEntity.getDimensions(serverPlayerEntity.getPose()).width * 0.8F;
                        Box box = Box.of(serverPlayerEntity.getEyePos(), f, 1.0E-6, f);
                        if (BlockPos.stream(box).map(serverPlayerEntity.getWorld()::getBlockState).filter(state -> state.getBlock() instanceof FluidBlock).toArray().length > 0) {
                            createExplosion(serverPlayerEntity,true);
                        }
                    }
                }
            }
            if (manager.castingHowlingWraithsTick != 0) {
                manager.castingHowlingWraithsTick--;
                if (manager.castingHowlingWraithsTick == 0){
                    if (!manager.isSuperSpell) {
                        serverPlayerEntity.getWorld().createExplosion(serverPlayerEntity, HollowMagicMod.spellDamage(serverPlayerEntity), null, serverPlayerEntity.getX(), serverPlayerEntity.getY() + 5, serverPlayerEntity.getZ(), 2, false, Explosion.DestructionType.NONE);
                    } else {
                        serverPlayerEntity.getWorld().createExplosion(serverPlayerEntity, HollowMagicMod.spellDamage(serverPlayerEntity), null, serverPlayerEntity.getX(), serverPlayerEntity.getY() + 10, serverPlayerEntity.getZ(), 6, false, Explosion.DestructionType.DESTROY);
                    }
                }
            }
        }
    }

    @Inject(method = "<init>",at = @At("RETURN"))
    private void onInit(CallbackInfo ci){
        PlayerEntity self = (PlayerEntity) (Object)this;
        if (self instanceof ServerPlayerEntity serverPlayerEntity){
            playerEntityManager = new PlayerEntityManager(serverPlayerEntity);
            HollowMagicMod.managers.add(playerEntityManager);
        }
    }

    @Inject(method = "readCustomDataFromNbt",at = @At("HEAD"))
    private void readCustomDataToNbt(NbtCompound nbt, CallbackInfo ci){
        playerEntityManager.soul = nbt.getFloat("hollow_magic_soul");
        playerEntityManager.castingDesolateDive = nbt.getBoolean("hollow_magic_casting_desolate_dive");
        playerEntityManager.isSuperSpell = nbt.getBoolean("hollow_magic_is_super_desolate_dive");
        playerEntityManager.castingHowlingWraithsTick = nbt.getInt("hollow_magic_casting_howling_wraiths_tick");
    }

    @Inject(method = "writeCustomDataToNbt",at = @At("HEAD"))
    private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci){
        nbt.putFloat("hollow_magic_soul",playerEntityManager.soul);
        nbt.putBoolean("hollow_magic_casting_desolate_dive",playerEntityManager.castingDesolateDive);
        nbt.putBoolean("hollow_magic_is_super_desolate_dive",playerEntityManager.isSuperSpell);
        nbt.putInt("hollow_magic_casting_howling_wraiths_tick",playerEntityManager.castingHowlingWraithsTick);
    }
}