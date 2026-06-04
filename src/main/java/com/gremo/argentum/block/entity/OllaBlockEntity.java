package com.gremo.argentum.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.minecraft.world.Containers;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * OllaBlockEntity: single-slot frying pot.
 * Registrar recetas en runtime con addRecipe(raw, fried, ticks).
 */
public class OllaBlockEntity extends BlockEntity {
    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 64;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private int cookProgress = 0;

    // Mapas raw -> fried y raw -> tiempo (ticks)
    private static final Map<Item, Item> COOK_RESULT = new HashMap<>();
    private static final Map<Item, Integer> COOK_TIME = new HashMap<>();

    static {
        // Opcional: recetas vanilla por defecto si querés (puedes dejar vacío)
        COOK_RESULT.put(Items.POTATO, Items.BAKED_POTATO);
        COOK_TIME.put(Items.POTATO, 200);
    }

    /** Añade receta runtime: raw -> fried en ticks. Llamar desde commonSetup. */
    public static void addRecipe(Item raw, Item fried, int ticks) {
        if (raw == null || fried == null) return;
        COOK_RESULT.put(raw, fried);
        COOK_TIME.put(raw, ticks);
    }

    /** Comprueba si un item es fryable (está en COOK_RESULT). */
    public static boolean isFryable(Item item) {
        return item != null && COOK_RESULT.containsKey(item);
    }

    /** Intenta insertar 1 del stack del jugador en el slot; devuelve true si pudo. */
    public boolean tryInsertOne(ItemStack playerStack) {
        if (playerStack.isEmpty()) return false;

        ItemStack slot = inventory.getStackInSlot(0);
        if (!slot.isEmpty()) return false; // ocupada (single slot)

        ItemStack toInsert = new ItemStack(playerStack.getItem(), 1);
        inventory.setStackInSlot(0, toInsert);
        playerStack.shrink(1);

        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
        return true;
    }

    /** Extrae el contenido del slot (server-side) y lo devuelve; deja slot vacío. */
    public ItemStack extractOne() {
        ItemStack s = inventory.getStackInSlot(0);
        if (s.isEmpty()) return ItemStack.EMPTY;
        ItemStack out = s.copy();
        inventory.setStackInSlot(0, ItemStack.EMPTY);
        cookProgress = 0;
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
        return out;
    }

    // rotación para renderer (si hacés renderer)
    public float getRenderingRotation() {
        if (level == null) return 0f;
        final float speed = 4.0f;
        long t = level.getGameTime();
        return (t * speed) % 360f;
    }

    public OllaBlockEntity(BlockPos pos, BlockState state) {
        super(com.gremo.argentum.block.entity.ModBlockEntities.OLLA_BE.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, OllaBlockEntity be) {
        // CLIENT: partículas si está ON
        if (level.isClientSide()) {
            if (state.getValue(com.gremo.argentum.block.custom.OllaBlock.ON)) {
                if (level.random.nextInt(8) == 0) {
                    double x = pos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 0.3;
                    double y = pos.getY() + 1.0;
                    double z = pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 0.3;
                    level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 0.0, 0.02, 0.0);
                }
            }
            return;
        }

        // SERVER: lógica de fritura (single slot)
        boolean on = state.getValue(com.gremo.argentum.block.custom.OllaBlock.ON);
        if (!on) return;

        ItemStack slot = be.inventory.getStackInSlot(0);
        if (slot.isEmpty()) {
            be.cookProgress = 0;
            return;
        }

        Item item = slot.getItem();
        if (!COOK_RESULT.containsKey(item)) {
            be.cookProgress = 0;
            return;
        }

        int needed = COOK_TIME.getOrDefault(item, 200);
        be.cookProgress++;

        if (be.cookProgress >= needed) {
            Item resultItem = COOK_RESULT.get(item);

            // reducimos input (1)
            slot.shrink(1);

            // creamos output
            ItemStack out = new ItemStack(resultItem, 1);
            double dx = pos.getX() + 0.5;
            double dy = pos.getY() + 1.0;
            double dz = pos.getZ() + 0.5;
            ItemEntity itemEntity = new ItemEntity(level, dx, dy, dz, out);
            double vx = (level.random.nextDouble() - 0.5) * 0.06;
            double vz = (level.random.nextDouble() - 0.5) * 0.06;
            double vy = 0.08 + level.random.nextDouble() * 0.04;
            itemEntity.setDeltaMovement(vx, vy, vz);
            level.addFreshEntity(itemEntity);

            // sonido y partículas
            level.playSound(null, pos, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.8F, 1.0F);

            // actualizar slot
            if (slot.isEmpty()) {
                be.inventory.setStackInSlot(0, ItemStack.EMPTY);
            } else {
                be.inventory.setStackInSlot(0, slot);
            }

            be.cookProgress = 0;
            be.setChanged();
            level.sendBlockUpdated(pos, state, state, 3);
        }

        // si hubo cambios, enviarlos
        if (be.cookProgress == 0) {
            be.setChanged();
            level.sendBlockUpdated(pos, state, state, 3);
        }
    }

    public void clearContents() {
        inventory.setStackInSlot(0, ItemStack.EMPTY);
        cookProgress = 0;
    }

    public void drops() {
        if (level == null) return;
        ItemStack stack = inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            Containers.dropItemStack(level, worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5, stack.copy());
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("cookProgress", cookProgress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        cookProgress = tag.getInt("cookProgress");
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