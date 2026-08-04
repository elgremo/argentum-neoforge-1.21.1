package com.gremo.argentum.worldgen.tree;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower JACARANDA = new TreeGrower(Argentum.MOD_ID + ":jacaranda",
            Optional.empty(), Optional.of(ModConfiguredFeatures.JACARANDA_KEY), Optional.empty());

}