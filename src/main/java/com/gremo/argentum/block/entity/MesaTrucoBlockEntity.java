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

public class MesaTrucoBlockEntity extends BlockEntity {

    // ============================================================
    // ÍNDICES DE SLOTS
    // ============================================================
    public static final int ARRIBA_IZQ    = 0;
    public static final int ARRIBA_CENTRO = 1;
    public static final int ARRIBA_DER    = 2;
    public static final int CENTRO_IZQ    = 3;
    public static final int CENTRO        = 4; // zona del mazo (no es slot visible)
    public static final int CENTRO_DER    = 5;
    public static final int ABAJO_IZQ     = 6;
    public static final int ABAJO_CENTRO  = 7;
    public static final int ABAJO_DER     = 8;

    public static final int TOTAL_SLOTS = 9;

    // ============================================================
    // MAZO
    // ============================================================
    public static final int MAZO_SIZE = 40;

    // ============================================================
    // ALMACENAMIENTO
    // ============================================================
    private final NonNullList<ItemStack> items =
            NonNullList.withSize(TOTAL_SLOTS, ItemStack.EMPTY);

    private final NonNullList<ItemStack> mazo =
            NonNullList.withSize(MAZO_SIZE, ItemStack.EMPTY);

    public MesaTrucoBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MESA_TRUCO_BE.get(), pos, state);
    }

    // ============================================================
    // GETTERS / SETTERS — SLOTS VISIBLES
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

    // ============================================================
    // GETTERS / SETTERS — MAZO
    // ============================================================

    /** Cantidad de cartas actualmente en el mazo. */
    public int getMazoCount() {
        int count = 0;
        for (ItemStack s : mazo) {
            if (!s.isEmpty()) count++;
        }
        return count;
    }

    /** Chequea si una carta específica ya está en el mazo (por ítem). */
    public boolean mazoContains(ItemStack stack) {
        for (ItemStack s : mazo) {
            if (!s.isEmpty() && s.is(stack.getItem())) {
                return true;
            }
        }
        return false;
    }

    /** Agrega una carta al mazo. Devuelve true si la agregó. */
    public boolean addToMazo(ItemStack stack) {
        // No aceptar repetidas
        if (mazoContains(stack)) return false;

        // Buscar primer hueco
        for (int i = 0; i < MAZO_SIZE; i++) {
            if (mazo.get(i).isEmpty()) {
                mazo.set(i, stack.copyWithCount(1));
                setChanged();
                if (level != null) {
                    level.sendBlockUpdated(
                            worldPosition,
                            getBlockState(),
                            getBlockState(),
                            Block.UPDATE_ALL
                    );
                }
                return true;
            }
        }
        return false; // lleno
    }

    /** Saca una carta aleatoria del mazo. Devuelve EMPTY si no hay. */
    public ItemStack drawRandomFromMazo() {
        // Juntar índices ocupados
        int count = getMazoCount();
        if (count == 0) return ItemStack.EMPTY;

        // Elegir uno al azar de los ocupados
        int[] ocupados = new int[count];
        int idx = 0;
        for (int i = 0; i < MAZO_SIZE; i++) {
            if (!mazo.get(i).isEmpty()) {
                ocupados[idx++] = i;
            }
        }

        RandomSource rng = level != null ? level.random : RandomSource.create();
        int chosen = ocupados[rng.nextInt(count)];

        ItemStack stack = mazo.get(chosen);
        mazo.set(chosen, ItemStack.EMPTY);
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

    /**
     * Llena el mazo con las 40 cartas del truco (1-7, 10-12 de cada palo).
     * Solo funciona si el mazo está VACÍO.
     * Devuelve true si llenó el mazo.
     */
    public boolean llenarConBaraja() {
        if (getMazoCount() > 0) return false;

        int idx = 0;

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

    /** Copia de las cartas actuales del mazo (para dropear al romper). */
    public NonNullList<ItemStack> getMazo() {
        return mazo;
    }

    // ============================================================
    // SYNC
    // ============================================================
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

        // Guardar el mazo en un sub-tag "Mazo" para no chocar con "Items"
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