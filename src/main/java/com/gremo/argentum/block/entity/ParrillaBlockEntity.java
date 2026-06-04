package com.gremo.argentum.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
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

public class ParrillaBlockEntity extends BlockEntity {
    public final ItemStackHandler inventory = new ItemStackHandler(9) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 64; // dejamos 64, aunque la inserción por click solo pone 1
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    // Progreso por slot y tiempo restante (en ticks)
    private final int[] cookProgress = new int[9];

    /**
     * Intenta insertar exactamente 1 unidad del stack en la primera ranura vacía.
     * Devuelve true si se insertó y reduce el stack pasado en 1.
     * Este método debe llamarse server-side.
     */
    public boolean tryInsertOne(ItemStack playerStack) {
        if (playerStack.isEmpty()) return false;

        for (int i = 0; i < inventory.getSlots(); i++) {
            if (inventory.getStackInSlot(i).isEmpty()) {
                // Creamos un ItemStack de 1 del mismo item y lo colocamos directamente
                ItemStack toInsert = new ItemStack(playerStack.getItem(), 1);
                inventory.setStackInSlot(i, toInsert);

                // Removemos 1 del stack del jugador
                playerStack.shrink(1);

                // marcar y sincronizar
                setChanged();
                if (level != null && !level.isClientSide()) {
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
                }
                return true;
            }
        }
        return false; // no hay ranuras vacías
    }

    // rotación usada por el renderer (no muta por llamada; usa gameTime)
    public float getRenderingRotation() {
        if (level == null) return 0f;
        final float speed = 4.0f; // grados por tick (ajusta si querés)
        long t = level.getGameTime();
        return (t * speed) % 360f;
    }

    // Mapas configurables: qué item resulta y cuánto tarda (ticks)
    private static final Map<Item, Item> COOK_RESULT = new HashMap<>();
    private static final Map<Item, Integer> COOK_TIME = new HashMap<>();

    static {
        // Ejemplos con vanilla (ajustá o agregá tus items aquí).
        COOK_RESULT.put(Items.BEEF, Items.COOKED_BEEF);
        COOK_TIME.put(Items.BEEF, 200);

        COOK_RESULT.put(Items.PORKCHOP, Items.COOKED_PORKCHOP);
        COOK_TIME.put(Items.PORKCHOP, 200);

        COOK_RESULT.put(Items.CHICKEN, Items.COOKED_CHICKEN);
        COOK_TIME.put(Items.CHICKEN, 200);

        COOK_RESULT.put(Items.MUTTON, Items.COOKED_MUTTON);
        COOK_TIME.put(Items.MUTTON, 200);

        COOK_RESULT.put(Items.RABBIT, Items.COOKED_RABBIT);
        COOK_TIME.put(Items.RABBIT, 200);

        // Si querés agregar tus items después, pon aquí:
        // COOK_RESULT.put(ModItems.RAW_ARGENTUM_STEAK.get(), ModItems.COOKED_ARGENTUM_STEAK.get());
        // COOK_TIME.put(ModItems.RAW_ARGENTUM_STEAK.get(), 300);
    }

    public ParrillaBlockEntity(BlockPos pos, BlockState blockState) {
        super(com.gremo.argentum.block.entity.ModBlockEntities.PARRILLA_BE.get(), pos, blockState);
    }

    // Ticker que avanza la cocción cuando la parrilla está ON
    public static void tick(Level level, BlockPos pos, BlockState state, ParrillaBlockEntity be) {
        if (level.isClientSide()) return;

        boolean changed = false;
        boolean on = state.getValue(com.gremo.argentum.block.custom.ParrillaBlock.ON);

        if (!on) {
            // si está apagada, no avanzamos
            return;
        }

        for (int i = 0; i < be.inventory.getSlots(); i++) {
            ItemStack stack = be.inventory.getStackInSlot(i);
            if (stack.isEmpty()) {
                be.cookProgress[i] = 0;
                continue;
            }

            Item item = stack.getItem();

            if (!COOK_RESULT.containsKey(item)) {
                // item permitido por tag pero sin mapping de resultado: no cocinamos
                be.cookProgress[i] = 0;
                continue;
            }

            int needed = COOK_TIME.getOrDefault(item, 200);
            be.cookProgress[i]++;

            if (be.cookProgress[i] >= needed) {
                // cocción completada: reemplazamos UNA unidad por el resultado
                Item resultItem = COOK_RESULT.get(item);

                // reducimos una unidad del input
                stack.shrink(1);

                // si slot quedó vacío, ponemos el resultado; si no quedó vacío intentamos añadir,
                // si no es posible, soltamos el resultado en el mundo.
                if (stack.isEmpty()) {
                    be.inventory.setStackInSlot(i, new ItemStack(resultItem, 1));
                } else {
                    ItemStack current = be.inventory.getStackInSlot(i);
                    if (current.getItem() == resultItem && current.getCount() < current.getMaxStackSize()) {
                        current.grow(1);
                        be.inventory.setStackInSlot(i, current);
                    } else {
                        // En caso de conflicto, soltamos el resultado en el mundo
                        Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(resultItem, 1));
                    }
                }

                be.cookProgress[i] = 0;
                changed = true;

                // sincronizamos inmediatamente para evitar pérdida de datos en caso de break rápido
                be.setChanged();
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }

        if (changed) {
            be.setChanged();
            level.sendBlockUpdated(pos, state, state, 3);
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