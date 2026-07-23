package com.gremo.argentum.event;

import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = "argentum")
public class CampfireEvents {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {

        Level level = event.getLevel();

        if (level.isClientSide()) {
            return;
        }

        InteractionHand hand = event.getHand();
        ItemStack stack = event.getEntity().getItemInHand(hand);

        // ¿Tiene una olla?
        if (!stack.is(ModBlocks.OLLA.get().asItem())) {
            return;
        }

        BlockPos campfirePos = event.getPos();
        BlockState state = level.getBlockState(campfirePos);

        // ¿Es una fogata?
        if (!(state.getBlock() instanceof CampfireBlock)) {
            return;
        }

        // ¿Está encendida?
        if (!state.getValue(CampfireBlock.LIT)) {
            return;
        }

        // Reemplazar la fogata
        level.setBlock(
                campfirePos,
                ModBlocks.OLLA_FOGATA.get().defaultBlockState(),
                3
        );

        if (!event.getEntity().getAbilities().instabuild) {
            stack.shrink(1);
        }

        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }


}