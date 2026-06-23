package com.gremo.argentum.event;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.item.ModItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

@EventBusSubscriber(modid = Argentum.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class SunflowerExtractEvent {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (level.isClientSide()) return;

        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        // Solo si es un girasol
        if (!state.is(Blocks.SUNFLOWER)) return;

        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack held = player.getItemInHand(hand);

        // Si está usando una botella de vidrio
        if (held.is(Items.GLASS_BOTTLE)) {
            // En modo creativo no se consume
            if (!player.getAbilities().instabuild) {
                held.shrink(1);
            }

            // Darle 1 ACEITE al jugador
            ItemStack aceite = new ItemStack(ModItems.ACEITE.get(), 1);
            if (!player.getInventory().add(aceite)) {
                player.drop(aceite, false); // tirarlo si no entra
            }

            // Sonido de éxito
            level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);

            // Evitar que se siga procesando el clic
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }
}