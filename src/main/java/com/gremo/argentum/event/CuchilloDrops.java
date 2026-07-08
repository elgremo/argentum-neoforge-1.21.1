package com.gremo.argentum.event;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.lang.reflect.Method;

@EventBusSubscriber(modid = Argentum.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class CuchilloDrops {

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {

        Entity attacker = event.getSource().getEntity();
        if (!(attacker instanceof Player player)) return;

        ItemStack weapon = player.getMainHandItem();
        if (!weapon.is(ModItems.CUCHILLO.get())) return;

        int lootingLevel = getLootingLevel(weapon);

        // ---------------- VACA ----------------

        if (event.getEntity() instanceof Cow cow) {

            int grasaAmount = 1;
            if (lootingLevel > 0) {
                grasaAmount += cow.getRandom().nextInt(lootingLevel + 1);
            }

            addDrop(event, cow, new ItemStack(ModItems.GRASA.get(), grasaAmount));

            float chance = Math.min(0.95f, 0.5f + lootingLevel * 0.1f);

            checkAndAddDrop(event, cow, ModItems.MOLLEJA_CRUDA.get(), chance, lootingLevel);
            checkAndAddDrop(event, cow, ModItems.MATAMBRE_CRUDO.get(), chance, lootingLevel);
            checkAndAddDrop(event, cow, ModItems.LOMO_CRUDO.get(), chance, lootingLevel);
            checkAndAddDrop(event, cow, ModItems.ENTRANA_CRUDA.get(), chance, lootingLevel);
            checkAndAddDrop(event, cow, ModItems.COSTILLA_CRUDA.get(), chance, lootingLevel);
            checkAndAddDrop(event, cow, ModItems.CHINCHULIN_CRUDO.get(), chance, lootingLevel);
            checkAndAddDrop(event, cow, ModItems.BIFE_CRUDO.get(), chance, lootingLevel);

            return;
        }

        // ---------------- CERDO ----------------

        if (event.getEntity() instanceof Pig pig) {

            int grasa = 1;
            int tripin = 1;

            if (lootingLevel > 0) {
                grasa += pig.getRandom().nextInt(lootingLevel + 1);
                tripin += pig.getRandom().nextInt(lootingLevel + 1);
            }

            addDrop(event, pig, new ItemStack(ModItems.GRASA.get(), grasa));
            addDrop(event, pig, new ItemStack(ModItems.TRIPIN_CERDO.get(), tripin));
        }
    }

    private static void checkAndAddDrop(LivingDropsEvent event, Entity entity, Item item, float chance, int lootingLevel) {

        if (entity.getRandom().nextFloat() > chance) return;

        int amount = 1 + entity.getRandom().nextInt(2 + Math.max(0, lootingLevel));

        addDrop(event, entity, new ItemStack(item, amount));
    }

    private static void addDrop(LivingDropsEvent event, Entity entity, ItemStack stack) {

        ItemEntity itemEntity = new ItemEntity(
                entity.level(),
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                stack
        );

        event.getDrops().add(itemEntity);
    }

    private static int getLootingLevel(ItemStack stack) {

        int lootingLevel = 0;

        CompoundTag tag = getTagSafe(stack);

        if (tag != null) {

            if (tag.contains("Enchantments")) {

                ListTag enchList = tag.getList("Enchantments", 10);

                for (int i = 0; i < enchList.size(); i++) {

                    CompoundTag ench = enchList.getCompound(i);

                    if ("minecraft:looting".equals(ench.getString("id"))) {

                        lootingLevel = ench.getInt("lvl");
                        break;
                    }
                }
            }

            if (lootingLevel == 0 && tag.contains("StoredEnchantments")) {

                ListTag enchList = tag.getList("StoredEnchantments", 10);

                for (int i = 0; i < enchList.size(); i++) {

                    CompoundTag ench = enchList.getCompound(i);

                    if ("minecraft:looting".equals(ench.getString("id"))) {

                        lootingLevel = ench.getInt("lvl");
                        break;
                    }
                }
            }
        }

        return lootingLevel;
    }

    private static CompoundTag getTagSafe(ItemStack stack) {

        try {

            String[] methods = {
                    "getTag",
                    "tag",
                    "getNbt",
                    "getOrCreateTag",
                    "getOrCreateNbt"
            };

            for (String method : methods) {

                try {

                    Method m = ItemStack.class.getMethod(method);

                    Object obj = m.invoke(stack);

                    if (obj instanceof CompoundTag compound)
                        return compound;

                } catch (Throwable ignored) {
                }
            }

        } catch (Throwable ignored) {
        }

        return null;
    }
}