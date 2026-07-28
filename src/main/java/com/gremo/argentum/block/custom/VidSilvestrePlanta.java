package com.gremo.argentum.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BushBlock;

public class VidSilvestrePlanta extends BushBlock {

    public static final MapCodec<VidSilvestrePlanta> CODEC =
            simpleCodec(VidSilvestrePlanta::new);

    public VidSilvestrePlanta(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }
}