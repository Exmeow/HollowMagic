package com.bilibili.hollowmagic.explosion;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

public class WeakExplosionBehavior extends ExplosionBehavior {
    public WeakExplosionBehavior() {
        super();
    }

    @Override
    public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
        return false;
    }
}
