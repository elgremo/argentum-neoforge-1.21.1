package com.gremo.argentum.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MesaChinchonBlockEntity extends BlockEntity {

    // ============================================================
    // ÍNDICES DE SLOTS
    // ============================================================
    // Rival (arriba)
    public static final int RIVAL_1 = 0;
    public static final int RIVAL_2 = 1;
    public static final int RIVAL_3 = 2;
    public static final int RIVAL_4 = 3;
    public static final int RIVAL_5 = 4;
    public static final int RIVAL_6 = 5;
    public static final int RIVAL_7 = 6;

    // Jugador (abajo)
    public static final int JUGADOR_1 = 7;
    public static final int JUGADOR_2 = 8;
    public static final int JUGADOR_3 = 9;
    public static final int JUGADOR_4 = 10;
    public static final int JUGADOR_5 = 11;
    public static final int JUGADOR_6 = 12;
    public static final int JUGADOR_7 = 13;

    // Total de slots visibles
    public static final int TOTAL_SLOTS = 14;

    // Mazo (identificador lógico, NO es slot visible)
    public static final int MAZO = 14;


    // Descarte (identificador lógico, NO es slot visible)
    public static final int DESCARTE = 15;

    // Tamaño del descarte interno (historial)
    public static final int DESCARTE_SIZE = 36;

    private final NonNullList<ItemStack> descarte =
            NonNullList.withSize(DESCARTE_SIZE, ItemStack.EMPTY);

    // Owner de cada carta del descarte (0 = jugador 1, 1 = jugador 2, -1 = vacío)
    private final int[] descarteOwners = new int[DESCARTE_SIZE];

    // Tamaño del mazo interno
    public static final int MAZO_SIZE = 50;

    // ============================================================
    // ALMACENAMIENTO
    // ============================================================
    private final NonNullList<ItemStack> items =
            NonNullList.withSize(TOTAL_SLOTS, ItemStack.EMPTY);

    private final NonNullList<ItemStack> mazo =
            NonNullList.withSize(MAZO_SIZE, ItemStack.EMPTY);

    public MesaChinchonBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MESA_CHINCHON_BE.get(), pos, state);
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

    // ============================================================
    // DESCARTE
    // ============================================================

    /** Cantidad de cartas en el descarte. */
    public int getDescarteCount() {
        int count = 0;
        for (ItemStack s : descarte) if (!s.isEmpty()) count++;
        return count;
    }

    /**
     * Agrega una carta al tope del descarte.
     * Desplaza todas las demás un lugar hacia abajo.
     * Si el descarte llega a 36, pasa todas las cartas menos la de arriba al mazo.
     * Devuelve true si recicló algo al mazo.
     */
    public boolean addToDescarte(ItemStack stack, int owner) {
        // Desplazar cartas y owners un lugar hacia abajo
        for (int i = DESCARTE_SIZE - 1; i > 0; i--) {
            descarte.set(i, descarte.get(i - 1));
            descarteOwners[i] = descarteOwners[i - 1];
        }
        // Meter la nueva arriba
        descarte.set(0, stack.copyWithCount(1));
        descarteOwners[0] = owner;

        // Si el descarte está lleno (36), reciclar todo menos la de arriba
        boolean reciclo = false;
        if (getDescarteCount() >= DESCARTE_SIZE) {
            reciclarViejasAlMazo();
            reciclo = true;
        }

        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
        return reciclo;
    }

    /**
     * Devuelve el ítem en la posición indicada del descarte.
     * 0 = arriba (última descartada), 1 = abajo (anterior), 2+ = ocultas.
     */
    public ItemStack peekDescarte(int index) {
        if (index < 0 || index >= DESCARTE_SIZE) return ItemStack.EMPTY;
        return descarte.get(index);
    }

    /**
     * Devuelve el owner de la carta del descarte en la posición indicada.
     * 0 = jugador 1 (sur), 1 = jugador 2 (norte), -1 = vacío.
     */
    public int getDescarteOwner(int index) {
        if (index < 0 || index >= DESCARTE_SIZE) return -1;
        return descarteOwners[index];
    }

    /**
     * Saca la carta de arriba del descarte y la devuelve.
     * Desplaza todas las demás un lugar hacia arriba.
     * Devuelve EMPTY si no hay cartas.
     */
    public ItemStack takeFromDescarte() {
        if (descarte.get(0).isEmpty()) return ItemStack.EMPTY;

        ItemStack top = descarte.get(0);

        // Desplazar cartas y owners un lugar hacia arriba
        for (int i = 0; i < DESCARTE_SIZE - 1; i++) {
            descarte.set(i, descarte.get(i + 1));
            descarteOwners[i] = descarteOwners[i + 1];
        }
        descarte.set(DESCARTE_SIZE - 1, ItemStack.EMPTY);
        descarteOwners[DESCARTE_SIZE - 1] = -1;

        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
        return top;
    }

    /**
     * Pasa TODAS las cartas del descarte al mazo (se mezclan).
     * Solo lo hace si el mazo está vacío.
     * Devuelve true si recicló algo.
     */
    public boolean reciclarAlMazo() {
        if (getMazoCount() > 0) return false;
        if (getDescarteCount() == 0) return false;

        int idx = 0;
        for (int i = 0; i < DESCARTE_SIZE; i++) {
            ItemStack s = descarte.get(i);
            if (!s.isEmpty()) {
                mazo.set(idx++, s);
                descarte.set(i, ItemStack.EMPTY);
                descarteOwners[i] = -1;
            }
        }

        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
        return true;
    }

    /**
     * Pasa las cartas del descarte (índices 1+) al mazo.
     * Deja SOLO la carta de arriba (índice 0).
     * Se llama automáticamente cuando el descarte llega a 36.
     */
    private void reciclarViejasAlMazo() {
        for (int i = 1; i < DESCARTE_SIZE; i++) {
            ItemStack s = descarte.get(i);
            if (s.isEmpty()) continue;

            // Buscar hueco en el mazo
            boolean metida = false;
            for (int j = 0; j < MAZO_SIZE; j++) {
                if (mazo.get(j).isEmpty()) {
                    mazo.set(j, s);
                    metida = true;
                    break;
                }
            }

            // Si el mazo está lleno, se pierde la carta (raro pero posible)
            if (metida) {
                descarte.set(i, ItemStack.EMPTY);
                descarteOwners[i] = -1;
            }
        }
    }

    /** Copia del descarte (para dropear al romper). */
    public NonNullList<ItemStack> getDescarte() {
        return descarte;
    }

    /**
     * Llena el mazo con todas las cartas del tag #cartas (49 cartas).
     * Solo funciona si el mazo está VACÍO.
     */
    public boolean llenarConBaraja() {
        if (getMazoCount() > 0) return false;

        int idx = 0;

        // Iterar el tag #cartas desde el registro de items
        for (var holder : net.minecraft.core.registries.BuiltInRegistries.ITEM.getTagOrEmpty(com.gremo.argentum.util.ModTags.Items.CARTAS)) {
            if (idx >= MAZO_SIZE) break;
            mazo.set(idx++, new ItemStack(holder.value()));
        }

        if (idx == 0) return false; // El tag está vacío

        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
        return true;
    }

    public NonNullList<ItemStack> getMazo() {
        return mazo;
    }

    /** Vacía el mazo (útil cuando se necesite). */
    public void vaciarMazo() {
        for (int i = 0; i < MAZO_SIZE; i++) {
            mazo.set(i, ItemStack.EMPTY);
        }
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
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
        // Guardar el descarte
        CompoundTag descarteTag = new CompoundTag();
        ContainerHelper.saveAllItems(descarteTag, descarte, registries);
        tag.put("Descarte", descarteTag);
        tag.putIntArray("DescarteOwners", descarteOwners);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, items, registries);

        if (tag.contains("Mazo")) {
            CompoundTag mazoTag = tag.getCompound("Mazo");
            ContainerHelper.loadAllItems(mazoTag, mazo, registries);
        }
        if (tag.contains("Descarte")) {
            CompoundTag descarteTag = tag.getCompound("Descarte");
            ContainerHelper.loadAllItems(descarteTag, descarte, registries);
        }
        if (tag.contains("DescarteOwners")) {
            int[] owners = tag.getIntArray("DescarteOwners");
            for (int i = 0; i < Math.min(owners.length, DESCARTE_SIZE); i++) {
                descarteOwners[i] = owners[i];
            }
        }
    }
}