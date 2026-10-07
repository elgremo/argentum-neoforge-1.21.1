package com.gremo.argentum.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BushBlock;

public class MembrilloSilvestrePlanta extends BushBlock {

    public static final MapCodec<MembrilloSilvestrePlanta> CODEC =
            simpleCodec(MembrilloSilvestrePlanta::new);

    public MembrilloSilvestrePlanta(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }
}