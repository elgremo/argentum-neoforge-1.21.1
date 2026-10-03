package com.gremo.argentum.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BushBlock;

public class VidBlancaSilvestrePlanta extends BushBlock {

    public static final MapCodec<VidBlancaSilvestrePlanta> CODEC =
            simpleCodec(VidBlancaSilvestrePlanta::new);

    public VidBlancaSilvestrePlanta(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }
}