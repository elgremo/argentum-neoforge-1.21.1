package com.gremo.argentum.event;

import com.gremo.argentum.item.ModItems;
import com.gremo.argentum.util.ModTags;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class CraftingEvents {

    public static void onCraft(PlayerEvent.ItemCraftedEvent event) {
        ItemStack crafted = event.getCrafting();

        // Verificar si el ítem crafteado está en el tag "mates"
        if (!crafted.is(ModTags.Items.MATES)) {
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