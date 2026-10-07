package com.gremo.argentum.item.custom;

import com.gremo.argentum.block.ModBlocks;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CopaMundoItem extends BlockItem {
    public CopaMundoItem(Properties properties) {
        super(ModBlocks.COPA_MUNDO.get(), properties);
    }

    // ⭐ SIN @Override (pero igual es una sobrescritura válida)
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        // Solo en servidor
        if (level.isClientSide) return;
        if (!(entity instanceof Player player)) return;

        // Verificar si el item está en la mano principal o secundaria
        boolean inMainHand = player.getMainHandItem() == stack;
        boolean inOffHand = player.getOffhandItem() == stack;

        if (inMainHand || inOffHand) {
            // Aplicar absorción si no la tiene o si está por expirar
            boolean hasAbsorption = player.hasEffect(MobEffects.ABSORPTION);
            if (!hasAbsorption) {
                player.addEffect(new MobEffectInstance(
                        MobEffects.ABSORPTION,
                        20 * 60 * 5, // 5 minutos
                        1,           // Nivel 1 = 2 corazones extra
                        false, false, true
                ));
            }
        }
    }
}