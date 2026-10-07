package com.gremo.argentum.block.entity;

import com.gremo.argentum.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredItem;

public class MesaTrucoCompaBlockEntity extends BlockEntity {

    // ============================================================
    // ÍNDICES DE SLOTS
    // ============================================================
    // Norte (arriba)
    public static final int NORTE_1 = 0;
    public static final int NORTE_2 = 1;
    public static final int NORTE_3 = 2;

    // Oeste (izquierda)
    public static final int OESTE_1 = 3;
    public static final int OESTE_2 = 4;
    public static final int OESTE_3 = 5;

    // Sur (abajo)
    public static final int SUR_1 = 6;
    public static final int SUR_2 = 7;
    public static final int SUR_3 = 8;

    // Este (derecha)
    public static final int ESTE_1 = 9;
    public static final int ESTE_2 = 10;
    public static final int ESTE_3 = 11;

    // Total de slots visibles
    public static final int TOTAL_SLOTS = 12;

    // Identificador del mazo (NO es un slot visible, es para la lógica de click)
    public static final int MAZO = 12;

    // Tamaño del mazo interno
    public static final int MAZO_SIZE = 40;

    // ============================================================
    // ALMACENAMIENTO
    // ============================================================
    private final NonNullList<ItemStack> items =
            NonNullList.withSize(TOTAL_SLOTS, ItemStack.EMPTY);

    private final NonNullList<ItemStack> mazo =
            NonNullList.withSize(MAZO_SIZE, ItemStack.EMPTY);

    public MesaTrucoCompaBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MESA_TRUCO_2V2_BE.get(), pos, state);
    }

    /**
     * Llena el mazo con las 40 cartas del truco (1-7, 10-12 de cada palo).
     * Solo funciona si el mazo está VACÍO.
     * Devuelve true si llenó el mazo.
     */
    public boolean llenarConBaraja() {
        // Si ya hay cartas, no hacer nada
        if (getMazoCount() > 0) return false;

        int idx = 0;

        // 4 palos: copa, espada, oro, palo
        DeferredItem<Item>[][] palos = new DeferredItem[][]{
                // COPA
                {
                        ModItems.CARTA_COPA_1, ModItems.CARTA_COPA_2, ModItems.CARTA_COPA_3,
                        ModItems.CARTA_COPA_4, ModItems.CARTA_COPA_5, ModItems.CARTA_COPA_6,
                        ModItems.CARTA_COPA_7, ModItems.CARTA_COPA_10, ModItems.CARTA_COPA_11,
                        ModItems.CARTA_COPA_12
                },
                // ESPADA
                {
                        ModItems.CARTA_ESPADA_1, ModItems.CARTA_ESPADA_2, ModItems.CARTA_ESPADA_3,
                        ModItems.CARTA_ESPADA_4, ModItems.CARTA_ESPADA_5, ModItems.CARTA_ESPADA_6,
                        ModItems.CARTA_ESPADA_7, ModItems.CARTA_ESPADA_10, ModItems.CARTA_ESPADA_11,
                        ModItems.CARTA_ESPADA_12
                },
                // ORO
                {
                        ModItems.CARTA_ORO_1, ModItems.CARTA_ORO_2, ModItems.CARTA_ORO_3,
                        ModItems.CARTA_ORO_4, ModItems.CARTA_ORO_5, ModItems.CARTA_ORO_6,
                        ModItems.CARTA_ORO_7, ModItems.CARTA_ORO_10, ModItems.CARTA_ORO_11,
                        ModItems.CARTA_ORO_12
                },
                // PALO (basto)
                {
                        ModItems.CARTA_PALO_1, ModItems.CARTA_PALO_2, ModItems.CARTA_PALO_3,
                        ModItems.CARTA_PALO_4, ModItems.CARTA_PALO_5, ModItems.CARTA_PALO_6,
                        ModItems.CARTA_PALO_7, ModItems.CARTA_PALO_10, ModItems.CARTA_PALO_11,
                        ModItems.CARTA_PALO_12
                }
        };

        for (DeferredItem<Item>[] palo : palos) {
            for (DeferredItem<Item> item : palo) {
                mazo.set(idx++, new ItemStack(item.get()));
            }
        }

        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
        return true;
    }

    // ============================================================
    // SLOTS VISIBLES
    // ============================================================
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    public ItemStack removeItem(int slot) {
        ItemStack stack = items.get(slot);
        items.set(slot, ItemStack.EMPTY);
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
        return stack;
    }

    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    // ============================================================
    // MAZO
    // ============================================================
    public int getMazoCount() {
        int count = 0;
        for (ItemStack s : mazo) if (!s.isEmpty()) count++;
        return count;
    }

    public boolean mazoContains(ItemStack stack) {
        for (ItemStack s : mazo) {
            if (!s.isEmpty() && s.is(stack.getItem())) return true;
        }
        return false;
    }

    public boolean addToMazo(ItemStack stack) {
        if (mazoContains(stack)) return false;

        for (int i = 0; i < MAZO_SIZE; i++) {
            if (mazo.get(i).isEmpty()) {
                mazo.set(i, stack.copyWithCount(1));
                setChanged();
                if (level != null) {
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                }
                return true;
            }
        }
        return false;
    }

    public ItemStack drawRandomFromMazo() {
        int count = getMazoCount();
        if (count == 0) return ItemStack.EMPTY;

        int[] ocupados = new int[count];
        int idx = 0;
        for (int i = 0; i < MAZO_SIZE; i++) {
            if (!mazo.get(i).isEmpty()) ocupados[idx++] = i;
        }

        RandomSource rng = level != null ? level.random : RandomSource.create();
        int chosen = ocupados[rng.nextInt(count)];

        ItemStack stack = mazo.get(chosen);
        mazo.set(chosen, ItemStack.EMPTY);
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
        return stack;
    }

    public NonNullList<ItemStack> getMazo() {
        return mazo;
    }

    // ============================================================
    // SYNC + NBT
    // ============================================================
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);

        CompoundTag mazoTag = new CompoundTag();
        ContainerHelper.saveAllItems(mazoTag, mazo, registries);
        tag.put("Mazo", mazoTag);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, items, registries);

        if (tag.contains("Mazo")) {
            CompoundTag mazoTag = tag.getCompound("Mazo");
            ContainerHelper.loadAllItems(mazoTag, mazo, registries);
        }
    }
}