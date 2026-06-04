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
 * ParrillaBlockEntity: manejador de inventario, recetas y tick de cocción.
 *
 * Para agregar recetas en runtime (despues de registries listos), llamá:
 * ParrillaBlockEntity.addRecipe(rawItem, cookedItem, ticks);
 *
 * Nota: no depende de tags - las recetas se registran explícitamente.
 */
public class ParrillaBlockEntity extends BlockEntity {
    public final ItemStackHandler inventory = new ItemStackHandler(9) {
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

    private final int[] cookProgress = new int[9];

    // Mapas raw -> cooked y raw -> tiempo (ticks)
    private static final Map<Item, Item> COOK_RESULT = new HashMap<>();
    private static final Map<Item, Integer> COOK_TIME = new HashMap<>();

    static {
        // Recetas vanilla por defecto (puedes extender llamando addRecipe desde commonSetup)
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
    }

    /**
     * Añade una receta runtime: raw -> cooked en N ticks.
     * Llamar desde commonSetup (o en tiempo de ejecución cuando las registries ya estén listas).
     */
    public static void addRecipe(Item raw, Item cooked, int ticks) {
        if (raw == null || cooked == null) return;
        COOK_RESULT.put(raw, cooked);
        COOK_TIME.put(raw, ticks);
    }

    /**
     * Comprueba si un Item es "grillable" (está en COOK_RESULT).
     */
    public static boolean isGrillable(Item item) {
        return item != null && COOK_RESULT.containsKey(item);
    }

    /**
     * intenta insertar 1 unidad del stack del player en la primera ranura vacía.
     * Reduce la stack del jugador en 1 si succeed. Debe llamarse server-side.
     */
    public boolean tryInsertOne(ItemStack playerStack) {
        if (playerStack.isEmpty()) return false;

        for (int i = 0; i < inventory.getSlots(); i++) {
            if (inventory.getStackInSlot(i).isEmpty()) {
                ItemStack toInsert = new ItemStack(playerStack.getItem(), 1);
                inventory.setStackInSlot(i, toInsert);
                playerStack.shrink(1);

                setChanged();
                if (level != null && !level.isClientSide()) {
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
                }
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

    public ParrillaBlockEntity(BlockPos pos, BlockState blockState) {
        super(com.gremo.argentum.block.entity.ModBlockEntities.PARRILLA_BE.get(), pos, blockState);
    }

    /**
     * Ticker del BE:
     * - client: partículas y sonidos ambientales
     * - server: avanza cocción y genera ItemEntity con impulso cuando termina
     */
    public static void tick(Level level, BlockPos pos, BlockState state, ParrillaBlockEntity be) {
        // CLIENT: partículas y sonido local
        if (level.isClientSide()) {
            if (state.getValue(com.gremo.argentum.block.custom.ParrillaBlock.ON)) {
                if (level.random.nextInt(6) == 0) {
                    double baseX = pos.getX() + 0.2 + level.random.nextDouble() * 0.6;
                    double baseY = pos.getY() + 1.0;
                    double baseZ = pos.getZ() + 0.2 + level.random.nextDouble() * 0.6;

                    level.addParticle(ParticleTypes.SMOKE, baseX, baseY, baseZ, 0.0, 0.02 + level.random.nextDouble() * 0.02, 0.0);

                    if (level.random.nextInt(3) == 0) {
                        level.addParticle(ParticleTypes.FLAME, baseX, baseY - 0.12, baseZ, 0.0, 0.01, 0.0);
                    }
                }
                if (level.random.nextInt(200) == 0) {
                    level.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.6F, 1.0F, false);
                }
            }
            return;
        }

        // SERVER: lógica de cocción
        boolean changed = false;
        boolean on = state.getValue(com.gremo.argentum.block.custom.ParrillaBlock.ON);
        if (!on) return;

        for (int i = 0; i < be.inventory.getSlots(); i++) {
            ItemStack stack = be.inventory.getStackInSlot(i);
            if (stack.isEmpty()) {
                be.cookProgress[i] = 0;
                continue;
            }

            Item item = stack.getItem();
            if (!COOK_RESULT.containsKey(item)) {
                be.cookProgress[i] = 0;
                continue;
            }

            int needed = COOK_TIME.getOrDefault(item, 200);
            be.cookProgress[i]++;

            if (be.cookProgress[i] >= needed) {
                Item resultItem = COOK_RESULT.get(item);

                // reducimos input
                stack.shrink(1);

                // creamos el output con impulso
                ItemStack out = new ItemStack(resultItem, 1);
                double dx = pos.getX() + 0.5;
                double dy = pos.getY() + 1.0;
                double dz = pos.getZ() + 0.5;

                ItemEntity itemEntity = new ItemEntity(level, dx, dy, dz, out);
                double vx = (level.random.nextDouble() - 0.5) * 0.08;
                double vz = (level.random.nextDouble() - 0.5) * 0.08;
                double vy = 0.12 + level.random.nextDouble() * 0.06;
                itemEntity.setDeltaMovement(vx, vy, vz);
                level.addFreshEntity(itemEntity);

                // Sonido principal y sonido adicional para marcar el cambio crudo->cocido
                level.playSound(null, pos, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.8F, 1.0F);

                // actualizamos slot
                if (stack.isEmpty()) {
                    be.inventory.setStackInSlot(i, ItemStack.EMPTY);
                } else {
                    be.inventory.setStackInSlot(i, stack);
                }

                be.cookProgress[i] = 0;
                changed = true;

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