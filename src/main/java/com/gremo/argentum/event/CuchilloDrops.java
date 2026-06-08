package com.gremo.argentum.event;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Cow;
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
        if (!(event.getEntity() instanceof Cow cow)) return;

        Entity attacker = event.getSource().getEntity();
        if (!(attacker instanceof Player player)) return;

        ItemStack weapon = player.getMainHandItem();
        if (!weapon.is(ModItems.CUCHILLO.get())) return;

        // --- Obtener nivel de Saqueo (Looting) leyendo NBT de forma robusta ---
        int lootingLevel = 0;

        CompoundTag tag = getTagSafe(weapon);
        if (tag != null) {
            // En items encantados suele usarse la lista "Enchantments"
            if (tag.contains("Enchantments")) {
                ListTag enchList = tag.getList("Enchantments", 10); // 10 = TAG_Compound
                for (int i = 0; i < enchList.size(); i++) {
                    CompoundTag ench = enchList.getCompound(i);
                    String id = ench.getString("id"); // p.ej. "minecraft:looting"
                    if ("minecraft:looting".equals(id)) {
                        lootingLevel = ench.getInt("lvl");
                        break;
                    }
                }
            }

            // También puede estar en "StoredEnchantments" (libros)
            if (lootingLevel == 0 && tag.contains("StoredEnchantments")) {
                ListTag st = tag.getList("StoredEnchantments", 10);
                for (int i = 0; i < st.size(); i++) {
                    CompoundTag ench = st.getCompound(i);
                    String id = ench.getString("id");
                    if ("minecraft:looting".equals(id)) {
                        lootingLevel = ench.getInt("lvl");
                        break;
                    }
                }
            }
        }

        // --- Lógica de drops ---
        // 1) GRASA siempre (con posible bonus por Saqueo)
        int grasaAmount = 1;
        if (lootingLevel > 0) {
            grasaAmount += cow.getRandom().nextInt(lootingLevel + 1);
        }
        addDrop(event, cow, new ItemStack(ModItems.GRASA.get(), grasaAmount));

        // 2) Resto de cortes: base 50% chance, +0.1 por nivel de saqueo (cap 95%)
        float baseChance = 0.5f;
        float chance = Math.min(0.95f, baseChance + lootingLevel * 0.1f);

        checkAndAddDrop(event, cow, ModItems.MOLLEJA_CRUDA.get(), chance, lootingLevel);
        checkAndAddDrop(event, cow, ModItems.MATAMBRE_CRUDO.get(), chance, lootingLevel);
        checkAndAddDrop(event, cow, ModItems.LOMO_CRUDO.get(), chance, lootingLevel);
        checkAndAddDrop(event, cow, ModItems.ENTRANA_CRUDA.get(), chance, lootingLevel);
        checkAndAddDrop(event, cow, ModItems.COSTILLA_CRUDA.get(), chance, lootingLevel);
        checkAndAddDrop(event, cow, ModItems.CHINCHULIN_CRUDO.get(), chance, lootingLevel);
        checkAndAddDrop(event, cow, ModItems.BIFE_CRUDO.get(), chance, lootingLevel);
    }

    private static void checkAndAddDrop(LivingDropsEvent event, Cow cow, Item item, float chance, int lootingLevel) {
        if (cow.getRandom().nextFloat() > chance) return;

        int extraBound = 2 + Math.max(0, lootingLevel); // >=2
        int amount = 1 + cow.getRandom().nextInt(extraBound); // 1 .. extraBound
        addDrop(event, cow, new ItemStack(item, amount));
    }

    private static void addDrop(LivingDropsEvent event, Cow cow, ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(
                cow.level(),
                cow.getX(),
                cow.getY(),
                cow.getZ(),
                stack
        );
        event.getDrops().add(itemEntity);
    }

    // Helper robusto para obtener el CompoundTag del ItemStack intentando distintos nombres de método
    private static CompoundTag getTagSafe(ItemStack stack) {
        try {
            String[] candidateNames = new String[] {
                    "getTag", "tag", "getNbt", "getOrCreateTag", "getOrCreateNbt"
            };
            for (String name : candidateNames) {
                try {
                    Method m = ItemStack.class.getMethod(name);
                    Object res = m.invoke(stack);
                    if (res instanceof CompoundTag) return (CompoundTag) res;
                } catch (NoSuchMethodException ignored) {
                    // probar siguiente nombre
                } catch (Throwable ignored) {
                    // no queremos que un fallo aquí rompa todo; probamos siguientes opciones
                }
            }
        } catch (Throwable ignored) {
        }
        return null;
    }
}