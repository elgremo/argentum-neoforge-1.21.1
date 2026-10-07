package com.gremo.argentum.item.custom;

import com.gremo.argentum.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class CopaVinoLlenaItem extends Item {

    public CopaVinoLlenaItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32; // mismo tiempo que la botella
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide) {
            // Efectos iguales a la botella de vino
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 60 * 30, 0));
            entity.addEffect(new MobEffectInstance(MobEffects.SATURATION, 20 * 60, 0));
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 10, 0));
            if (level.random.nextFloat() < 0.05f) {
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 10, 0));
            }
        }

        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0F, 1.0F);

        // Si es un jugador y no es creativo, devolver copa vacía
        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            // Consumir la copa (se reduce en 1)
            stack.shrink(1);
            // Dar copa vacía
            if (!player.getInventory().add(new ItemStack(ModItems.COPA_VINO_VACIA.get()))) {
                player.drop(new ItemStack(ModItems.COPA_VINO_VACIA.get()), false);
            }
            return stack;
        }

        return stack;
    }
}