package com.gremo.argentum.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BushBlock;

public class BatataSilvestrePlanta extends BushBlock {

    public static final MapCodec<BatataSilvestrePlanta> CODEC =
            simpleCodec(BatataSilvestrePlanta::new);

    public BatataSilvestrePlanta(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }
}