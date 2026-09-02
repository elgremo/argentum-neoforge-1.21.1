package com.gremo.argentum.event;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.item.ModItems;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class CraftingEvents {

    public static void onCraft(PlayerEvent.ItemCraftedEvent event) {

        ItemStack crafted = event.getCrafting();

        // Solo cuando se fabrica un Mate
        if (!crafted.is(ModItems.MATE.get())) {
            return;
        }
        Container inv = event.getInventory();

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);

            if (stack.is(ModItems.PAQUETE_YERBA_MATE.get())) {

                int damage = stack.getDamageValue() + 1;

                if (damage >= stack.getMaxDamage()) {
                    stack.shrink(1);
                } else {
                    stack.setDamageValue(damage);
                }

                break;
            }
        }
    }

}