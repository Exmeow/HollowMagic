package com.bilibili.hollowmagic.entity;

import com.bilibili.hollowmagic.HollowMagicMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class ModEntities {
    public static final EntityType<VengefulSpiritEntity> VENGEFUL_SPIRIT = Registry.register(Registry.ENTITY_TYPE,new Identifier(HollowMagicMod.MODID,"vengeful_spirit"), FabricEntityTypeBuilder.create(SpawnGroup.MISC,VengefulSpiritEntity::new).fireImmune().dimensions(EntityDimensions.fixed(0.7F, 2.4F)).trackRangeChunks(8).build());
    public static final EntityType<ShadeSoulEntity> SHADE_SOUL = Registry.register(Registry.ENTITY_TYPE,new Identifier(HollowMagicMod.MODID,"shade_soul"), FabricEntityTypeBuilder.create(SpawnGroup.MISC,ShadeSoulEntity::new).fireImmune().dimensions(EntityDimensions.fixed(0.7F, 2.4F)).trackRangeChunks(8).build());
}