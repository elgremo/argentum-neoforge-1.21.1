package com.gremo.argentum.block.entity;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.entity.ModEntities;
import com.gremo.argentum.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Chicken;
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

public class NidoBlockEntity extends BlockEntity {


    // Inventario: máximo 4 huevos
    public final ItemStackHandler inventory = new ItemStackHandler(4) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1; // solo un huevo por slot
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    };

    // Tiempo de incubación por huevo (en ticks)
    private static final int INCUBATION_TIME = 600; // 30 segundos
    private int[] incubationProgress = new int[4];

    // Mapeo de items (huevos) a entidades que spawnear
    private static final Map<Item, EntityType<?>> HUEVO_TO_ENTITY = new HashMap<>();

    static {
        HUEVO_TO_ENTITY.put(ModItems.HUEVO_GALLINA.get(), EntityType.CHICKEN); // ✅ Usamos nuestro item
        HUEVO_TO_ENTITY.put(ModItems.HUEVO_TERO.get(), ModEntities.TERO.get());
        HUEVO_TO_ENTITY.put(ModItems.HUEVO_HORNERO.get(), ModEntities.HORNERO.get()); // cuando lo tengas
    }

    public NidoBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.NIDO_BE.get(), pos, state);
    }

    public static boolean isValidHuevo(Item item) {
        return HUEVO_TO_ENTITY.containsKey(item);
    }

    // --- MÉTODOS PARA EL RENDERER ---
    public ItemStack getHuevoInSlot(int slot) {
        return inventory.getStackInSlot(slot);
    }

    public int getHuevoCount() {
        int count = 0;
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) count++;
        }
        return count;
    }

    public boolean tryInsertHuevo(ItemStack stack) {
        if (stack.isEmpty()) return false;

        // ✅ Determinar qué item vamos a guardar
        Item itemToInsert = stack.getItem();
        if (itemToInsert == Items.EGG) {
            // Si es huevo vanilla, lo convertimos a nuestro huevo gallina
            itemToInsert = ModItems.HUEVO_GALLINA.get();
        }

        // ✅ Verificar que el item (convertido o no) sea válido
        if (!isValidHuevo(itemToInsert)) {
            return false;
        }

        for (int i = 0; i < inventory.getSlots(); i++) {
            if (inventory.getStackInSlot(i).isEmpty()) {
                // Insertamos el item convertido
                inventory.insertItem(i, new ItemStack(itemToInsert, 1), false);
                stack.shrink(1); // Consumimos el item original (sea vanilla o no)
                setChanged();
                return true;
            }
        }
        return false;
    }


    // --- TICK DE INCUBACIÓN ---
    public static void tick(Level level, BlockPos pos, BlockState state, NidoBlockEntity be) {
        if (level.isClientSide) return;

        boolean changed = false;
        for (int i = 0; i < be.inventory.getSlots(); i++) {
            ItemStack stack = be.inventory.getStackInSlot(i);
            if (stack.isEmpty()) {
                be.incubationProgress[i] = 0;
                continue;
            }

            Item item = stack.getItem();
            if (!HUEVO_TO_ENTITY.containsKey(item)) {
                be.incubationProgress[i] = 0;
                continue;
            }

            be.incubationProgress[i]++;
            if (be.incubationProgress[i] >= INCUBATION_TIME) {
                // Spawnear la entidad
                // Spawnear la entidad
                EntityType<?> entityType = HUEVO_TO_ENTITY.get(item);
                if (entityType != null && level instanceof ServerLevel serverLevel) {
                    double x = pos.getX() + 0.5;
                    double y = pos.getY() + 0.7;
                    double z = pos.getZ() + 0.5;

                    Entity entity = entityType.create(serverLevel);
                    if (entity != null) {
                        entity.setPos(x, y, z);
                        // Si es un Mob, podemos establecer su edad o atributos (opcional)
                        if (entity instanceof Mob mob) {
                            // Opcional: hacerlo bebé si es un animal
                            if (mob instanceof AgeableMob ageable) {
                                ageable.setBaby(true);
                                ageable.setAge(-24000); // cría
                            }
                            // También se puede inicializar con equipment, etc.
                        }
                        serverLevel.addFreshEntity(entity);
                    }
                }

                // Vaciar el slot
                be.inventory.setStackInSlot(i, ItemStack.EMPTY);
                be.incubationProgress[i] = 0;
                changed = true;
            }
        }

        if (changed) {
            be.setChanged();
            level.sendBlockUpdated(pos, state, state, 3);
            // Actualizar el estado del bloque con la cantidad de huevos
            int count = be.getHuevoCount();
            level.setBlock(pos, state.setValue(com.gremo.argentum.block.custom.NidoBlock.HUEVOS, count), 3);
        }
    }

    // --- DROPEAR CONTENIDO ---
    public void drops() {
        if (level == null) return;
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                net.minecraft.world.Containers.dropItemStack(level, worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5, stack.copy());
            }
        }
    }

    // --- NBT ---
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putIntArray("incubationProgress", incubationProgress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        int[] arr = tag.getIntArray("incubationProgress");
        if (arr != null) {
            for (int i = 0; i < Math.min(arr.length, incubationProgress.length); i++) {
                incubationProgress[i] = arr[i];
            }
        }
    }

    @Nullable
    @Override
    public Packet<net.minecraft.network.protocol.game.ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}