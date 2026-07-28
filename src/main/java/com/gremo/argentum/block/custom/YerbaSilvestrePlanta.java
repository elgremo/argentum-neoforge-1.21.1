package com.gremo.argentum.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BushBlock;

public class YerbaSilvestrePlanta extends BushBlock {

    public static final MapCodec<YerbaSilvestrePlanta> CODEC =
            simpleCodec(YerbaSilvestrePlanta::new);

    public YerbaSilvestrePlanta(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }
}