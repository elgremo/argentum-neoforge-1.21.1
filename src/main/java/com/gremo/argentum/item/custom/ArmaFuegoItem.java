package com.gremo.argentum.item.custom;

import com.gremo.argentum.entity.custom.BalaEntity;
import com.gremo.argentum.item.ModItems;
import com.gremo.argentum.sound.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class ArmaFuegoItem extends Item {

    public ArmaFuegoItem(Properties properties,
                         float damage,
                         float bulletSpeed,
                         int cooldown) {

        super(properties);

        this.damage = damage;
        this.bulletSpeed = bulletSpeed;
        this.cooldown = cooldown;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level,
                                                  Player player,
                                                  InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);
        ItemStack ammo = findAmmo(player);

        if (!player.getAbilities().instabuild && ammo.isEmpty()) {
            level.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.DISPENSER_FAIL,
                    SoundSource.PLAYERS,
                    0.8F,
                    1.2F
            );
            return InteractionResultHolder.fail(stack);
        }
        if (!level.isClientSide()) {

            BalaEntity bala = new BalaEntity(level, player);

            bala.setDamage(damage);

            bala.shootFromRotation(
                    player,
                    player.getXRot(),
                    player.getYRot(),
                    0.0F,
                    bulletSpeed,
                    0.0F
            );

            level.addFreshEntity(bala);

            stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

            level.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    ModSounds.CHUNGO_SHOOT.get(),
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );

            player.getCooldowns().addCooldown(this, cooldown);
            if (!player.getAbilities().instabuild) {
                ammo.shrink(1);
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());

    }

    private ItemStack findAmmo(Player player) {

        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ModItems.BALA.get())) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }
    @Override
    public int getEnchantmentValue() {
        return 15;
    }
    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.is(Enchantments.UNBREAKING)
                || enchantment.is(Enchantments.MENDING);
    }

    private final float damage;
    private final float bulletSpeed;
    private final int cooldown;
}