package com.gremo.argentum.item.custom;

import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class BarajaSelladaItem extends net.minecraft.world.item.Item {
    public BarajaSelladaItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide) {
            // Consumir la baraja si no está en creativo
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }

            // Dropear el bloque MAZO

            // Dropear 2 comodines
            player.drop(new ItemStack(ModItems.CARTA_COMODIN.get()), true);
            player.drop(new ItemStack(ModItems.CARTA_COMODIN.get()), true);

            // Arreglos con las cartas por palo (1..12)
            Item[] copas = {
                    ModItems.CARTA_COPA_1.get(), ModItems.CARTA_COPA_2.get(), ModItems.CARTA_COPA_3.get(),
                    ModItems.CARTA_COPA_4.get(), ModItems.CARTA_COPA_5.get(), ModItems.CARTA_COPA_6.get(),
                    ModItems.CARTA_COPA_7.get(), ModItems.CARTA_COPA_8.get(), ModItems.CARTA_COPA_9.get(),
                    ModItems.CARTA_COPA_10.get(), ModItems.CARTA_COPA_11.get(), ModItems.CARTA_COPA_12.get()
            };
            Item[] espadas = {
                    ModItems.CARTA_ESPADA_1.get(), ModItems.CARTA_ESPADA_2.get(), ModItems.CARTA_ESPADA_3.get(),
                    ModItems.CARTA_ESPADA_4.get(), ModItems.CARTA_ESPADA_5.get(), ModItems.CARTA_ESPADA_6.get(),
                    ModItems.CARTA_ESPADA_7.get(), ModItems.CARTA_ESPADA_8.get(), ModItems.CARTA_ESPADA_9.get(),
                    ModItems.CARTA_ESPADA_10.get(), ModItems.CARTA_ESPADA_11.get(), ModItems.CARTA_ESPADA_12.get()
            };
            Item[] oros = {
                    ModItems.CARTA_ORO_1.get(), ModItems.CARTA_ORO_2.get(), ModItems.CARTA_ORO_3.get(),
                    ModItems.CARTA_ORO_4.get(), ModItems.CARTA_ORO_5.get(), ModItems.CARTA_ORO_6.get(),
                    ModItems.CARTA_ORO_7.get(), ModItems.CARTA_ORO_8.get(), ModItems.CARTA_ORO_9.get(),
                    ModItems.CARTA_ORO_10.get(), ModItems.CARTA_ORO_11.get(), ModItems.CARTA_ORO_12.get()
            };
            Item[] palos = {
                    ModItems.CARTA_PALO_1.get(), ModItems.CARTA_PALO_2.get(), ModItems.CARTA_PALO_3.get(),
                    ModItems.CARTA_PALO_4.get(), ModItems.CARTA_PALO_5.get(), ModItems.CARTA_PALO_6.get(),
                    ModItems.CARTA_PALO_7.get(), ModItems.CARTA_PALO_8.get(), ModItems.CARTA_PALO_9.get(),
                    ModItems.CARTA_PALO_10.get(), ModItems.CARTA_PALO_11.get(), ModItems.CARTA_PALO_12.get()
            };

            // Dropear todas las cartas (cada una una unidad)
            dropAllFromArray(player, copas);
            dropAllFromArray(player, espadas);
            dropAllFromArray(player, oros);
            dropAllFromArray(player, palos);

            // Feedback sonoro y mensaje
            world.playSound(null, player.blockPosition(), SoundEvents.CHEST_OPEN, SoundSource.PLAYERS, 1.0f, 1.0f);
            player.displayClientMessage(Component.literal("¡La baraja se ha abierto!"), true);
        }

        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }

    private void dropAllFromArray(Player player, Item[] items) {
        for (Item it : items) {
            if (it != null) {
                player.drop(new ItemStack(it), true);
            }
        }
    }
}