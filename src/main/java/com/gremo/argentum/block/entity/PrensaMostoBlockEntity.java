package com.gremo.argentum.block.entity;

import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.block.custom.PrensaMostoBlock;
import com.gremo.argentum.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;


public class PrensaMostoBlockEntity extends BlockEntity {
    public final ItemStackHandler inventory = new ItemStackHandler(9) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private final int[] cookProgress = new int[9];


    /**
     * intenta insertar 1 unidad del stack del player en la primera ranura vacía.
     * Reduce la stack del jugador en 1 si succeed. Debe llamarse server-side.
     */
    public boolean tryInsertOne(ItemStack playerStack) {
        if (playerStack.isEmpty()) {
            return false;
        }

        if (playerStack.getItem() != ModItems.UVA.get()) {
            return false;
        }

        for (int i = 0; i < inventory.getSlots(); i++) {

            if (inventory.getStackInSlot(i).isEmpty()) {

                inventory.setStackInSlot(i, new ItemStack(ModItems.UVA.get()));

                playerStack.shrink(1);

                setChanged();

                return true;
            }
        }

        return false;
    }

    // rotación para renderer
    public float getRenderingRotation() {
        if (level == null) return 0f;
        final float speed = 4.0f; // grados por tick
        long t = level.getGameTime();
        return (t * speed) % 360f;
    }

    public PrensaMostoBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.MOSTO_BE.get(), pos, blockState);
    }

    /**
     * Ticker del BE:
     * - client: partículas y sonidos ambientales
     * - server: avanza cocción y genera ItemEntity con impulso cuando termina
     */
    public static void tick(Level level, BlockPos pos, BlockState state, PrensaMostoBlockEntity be) {
        if (level.isClientSide()) {
            return;
        }

        boolean llena = true;

        for (int i = 0; i < be.inventory.getSlots(); i++) {
            ItemStack stack = be.inventory.getStackInSlot(i);

            if (stack.isEmpty()) {
                llena = false;
                break;
            }

            // CAMBIAR UVA POR TU ITEM REAL
            if (stack.getItem() != ModItems.UVA.get()) {
                llena = false;
                break;
            }
        }

        if (llena) {

            be.clearContents();

            level.setBlock(
                    pos,
                    ModBlocks.PRENSA_MOSTO_LISTA.get().defaultBlockState(),
                    3
            );

            return;
        }
    }

    public void clearContents() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public void drops() {
        if (level == null) return;
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                Containers.dropItemStack(level, worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5, stack.copy());
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putIntArray("cookProgress", cookProgress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        int[] arr = tag.getIntArray("cookProgress");
        if (arr != null) {
            for (int i = 0; i < Math.min(arr.length, cookProgress.length); i++) {
                cookProgress[i] = arr[i];
            }
        }
    }

    @Nullable
    @Override
    public Packet<net.minecraft.network.protocol.game.ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(net.minecraft.core.HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }
}