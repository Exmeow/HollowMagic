package com.bilibili.hollowmagic.entity;

import com.bilibili.hollowmagic.HollowMagicMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;

public class VengefulSpiritEntity extends WitherSkullEntity {
    public VengefulSpiritEntity(EntityType<? extends WitherSkullEntity> entityType, World world) {
        super(entityType, world);
    }

    public static VengefulSpiritEntity build(World world, LivingEntity owner, double directionX, double directionY, double directionZ){
        VengefulSpiritEntity vengefulSpiritEntity = new VengefulSpiritEntity(ModEntities.VENGEFUL_SPIRIT,world);
        buildHelper(vengefulSpiritEntity,owner.getX(),owner.getY(),owner.getZ(),directionX,directionY,directionZ);
        vengefulSpiritEntity.setOwner(owner);
        vengefulSpiritEntity.setRotation(owner.getYaw(),owner.getPitch());
        return vengefulSpiritEntity;
    }

    private static void buildHelper(VengefulSpiritEntity vengefulSpiritEntity, double x, double y, double z, double directionX, double directionY, double directionZ){
        vengefulSpiritEntity.refreshPositionAndAngles(x, y, z, vengefulSpiritEntity.getYaw(),vengefulSpiritEntity.getPitch());
        vengefulSpiritEntity.refreshPosition();
        double d = Math.sqrt(directionX * directionX + directionY * directionY + directionZ * directionZ);
        if (d != 0.0) {
            vengefulSpiritEntity.powerX = directionX / d * 0.1;
            vengefulSpiritEntity.powerY = directionY / d * 0.1;
            vengefulSpiritEntity.powerZ = directionZ / d * 0.1;
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (!this.world.isClient) {
            Entity entity = entityHitResult.getEntity();
            Entity entity2 = this.getOwner();
            boolean bl;
            if (entity2 instanceof LivingEntity livingEntity) {
                if (entity != entity2){
                    bl = entity.damage(HollowMagicMod.spellDamage(entity2), 15.0F);
                }else{
                    bl = false;
                }
                if (bl) {
                    if (entity.isAlive()) {
                        this.applyDamageEffects(livingEntity, entity);
                    }
                }
            }
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.ENTITY) {
            this.onEntityHit((EntityHitResult)hitResult);
            this.world.emitGameEvent(GameEvent.PROJECTILE_LAND, hitResult.getPos(), GameEvent.Emitter.of(this, null));
        } else if (type == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult)hitResult;
            this.onBlockHit(blockHitResult);
            BlockPos blockPos = blockHitResult.getBlockPos();
            this.world.emitGameEvent(GameEvent.PROJECTILE_LAND, blockPos, GameEvent.Emitter.of(this, this.world.getBlockState(blockPos)));
        }
        if (!this.world.isClient) {
            this.world.createExplosion(this, HollowMagicMod.spellDamage(this.getOwner()), null, this.getX(), this.getY(), this.getZ(), 1.0F, false, Explosion.DestructionType.NONE);
            if (type == HitResult.Type.BLOCK) {
                this.discard();
            }
        }
    }
}
