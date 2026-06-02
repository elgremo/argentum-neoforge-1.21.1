package com.gremo.argentum.item.custom;

import com.gremo.argentum.item.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class MateListoItem extends Item {
    private final SoundEvent customDrinkSound;

    public MateListoItem(Properties properties) {
        this(properties, null);
    }

    public MateListoItem(Properties properties, SoundEvent customDrinkSound) {
        super(properties);
        this.customDrinkSound = customDrinkSound;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (!world.isClientSide && entity instanceof Player player) {
            player.getFoodData().eat(8, 0.8F);

            if (customDrinkSound != null) {
                world.playSound(null, player.blockPosition(), customDrinkSound, SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            boolean willBreak = stack.getDamageValue() + 1 >= stack.getMaxDamage();

            EquipmentSlot slot = player.getUsedItemHand() == InteractionHand.MAIN_HAND
                    ? EquipmentSlot.MAINHAND
                    : EquipmentSlot.OFFHAND;

            stack.hurtAndBreak(1, player, slot);

            if (willBreak) {
                ItemStack termo = new ItemStack(ModItems.TERMO.get());
                if (!player.getInventory().add(termo)) {
                    player.drop(termo, false);
                }
            }
        }

        return stack;
    }
}