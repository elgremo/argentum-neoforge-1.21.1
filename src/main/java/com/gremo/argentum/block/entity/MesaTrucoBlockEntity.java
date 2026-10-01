package com.gremo.argentum.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MesaTrucoBlockEntity extends BlockEntity {

    // ============================================================
    // ÍNDICES DE SLOTS
    // ============================================================
    public static final int ARRIBA_IZQ    = 0;
    public static final int ARRIBA_CENTRO = 1;
    public static final int ARRIBA_DER    = 2;
    public static final int CENTRO_IZQ    = 3;
    public static final int CENTRO        = 4; // mazo
    public static final int CENTRO_DER    = 5;
    public static final int ABAJO_IZQ     = 6;
    public static final int ABAJO_CENTRO  = 7;
    public static final int ABAJO_DER     = 8;

    public static final int TOTAL_SLOTS = 9;

    // ============================================================
    // ALMACENAMIENTO
    // ============================================================
    private final NonNullList<ItemStack> items =
            NonNullList.withSize(TOTAL_SLOTS, ItemStack.EMPTY);

    public MesaTrucoBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MESA_TRUCO_BE.get(), pos, state);
    }

    // ============================================================
    // GETTERS / SETTERS
    // ============================================================
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(
                    worldPosition,
                    getBlockState(),
                    getBlockState(),
                    Block.UPDATE_ALL
            );
        }
    }

    public ItemStack removeItem(int slot) {
        ItemStack stack = items.get(slot);
        items.set(slot, ItemStack.EMPTY);
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(
                    worldPosition,
                    getBlockState(),
                    getBlockState(),
                    Block.UPDATE_ALL
            );
        }
        return stack;
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    // ============================================================
    // NBT SAVE / LOAD
    // ============================================================
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, items, registries);
    }
}