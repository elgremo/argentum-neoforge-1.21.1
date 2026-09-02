package com.gremo.argentum.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BushBlock;

public class TeSilvestrePlanta extends BushBlock {

    public static final MapCodec<TeSilvestrePlanta> CODEC =
            simpleCodec(TeSilvestrePlanta::new);

    public TeSilvestrePlanta(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }
}