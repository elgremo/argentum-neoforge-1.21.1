package com.gremo.argentum.block.entity;

import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.block.custom.PrensaMostoBlock;
import com.gremo.argentum.block.custom.PrensaMostoListaBlock;
import com.gremo.argentum.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class PrensaMostoBlockEntity extends BlockEntity {
    public final ItemStackHandler inventory = new ItemStackHandler(8) {
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

    private int uvasComunes = 0;
    private int uvasBlancas = 0;
    private boolean activado = false;
    private int tipoMosto = 0; // 0=sin tipo, 1=tinto, 2=blanco, 3=rosado, 4=turbio

    public PrensaMostoBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.MOSTO_BE.get(), pos, blockState);
    }

    public void setTipoMosto(int tipo) {
        this.tipoMosto = tipo;
    }

    public boolean tryInsertOne(ItemStack playerStack) {
        if (playerStack.isEmpty()) return false;
        if (activado) return false;

        boolean esComun = playerStack.getItem() == ModItems.UVA.get();
        boolean esBlanca = playerStack.getItem() == ModItems.UVA_BLANCA.get();
        if (!esComun && !esBlanca) return false;

        for (int i = 0; i < inventory.getSlots(); i++) {
            if (inventory.getStackInSlot(i).isEmpty()) {
                inventory.setStackInSlot(i, new ItemStack(playerStack.getItem(), 1));
                playerStack.shrink(1);

                if (esComun) uvasComunes++;
                else if (esBlanca) uvasBlancas++;

                setChanged();

                System.out.println("[Prensa] Insertada " + (esComun ? "común" : "blanca") +
                        ". Total: comunes=" + uvasComunes + ", blancas=" + uvasBlancas);

                if (uvasComunes + uvasBlancas == 8) {
                    System.out.println("[Prensa] ¡Llegó a 8! Activando...");
                    activarPrensa();
                }

                return true;
            }
        }
        System.out.println("[Prensa] No hay espacio (inventario lleno)");
        return false;
    }

    private int calcularTipo() {
        if (uvasComunes == 8 && uvasBlancas == 0) return 1; // tinto
        if (uvasComunes == 0 && uvasBlancas == 8) return 2; // blanco
        if (uvasComunes == 4 && uvasBlancas == 4) return 3; // rosado
        return 4; // turbio (cualquier otra combinación)
    }

    private Block getBloqueLista() {
        return switch (calcularTipo()) {
            case 1 -> ModBlocks.PRENSA_MOSTO_LISTA_TINTO.get();
            case 2 -> ModBlocks.PRENSA_MOSTO_LISTA_BLANCO.get();
            case 3 -> ModBlocks.PRENSA_MOSTO_LISTA_ROSADO.get();
            default -> ModBlocks.PRENSA_MOSTO_LISTA_TURBIO.get();
        };
    }

    public ItemStack getBalde() {
        System.out.println("[Prensa] getBalde() llamado. tipoMosto=" + tipoMosto);
        ItemStack result = switch (tipoMosto) {
            case 1 -> new ItemStack(ModItems.BALDE_MOSTO.get());
            case 2 -> new ItemStack(ModItems.BALDE_MOSTO_BLANCO.get());
            case 3 -> new ItemStack(ModItems.BALDE_MOSTO_ROSADO.get());
            case 4 -> new ItemStack(ModItems.BALDE_MOSTO_TURBIO.get());
            default -> ItemStack.EMPTY;
        };
        System.out.println("[Prensa] Balde devuelto: " + (result.isEmpty() ? "VACÍO" : result.getItem().getDescriptionId()));
        return result;
    }

    public void clearContents() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
        uvasComunes = 0;
        uvasBlancas = 0;
        activado = false;
        tipoMosto = 0;
        setChanged();
    }

    public void drops() {
        if (level == null) return;
        if (activado) return;

        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                Containers.dropItemStack(level,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 0.5,
                        worldPosition.getZ() + 0.5,
                        stack.copy());
            }
        }
        clearContents();
    }

    public float getRenderingRotation() {
        if (level == null) return 0f;
        final float speed = 4.0f;
        long t = level.getGameTime();
        return (t * speed) % 360f;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("UvasComunes", uvasComunes);
        tag.putInt("UvasBlancas", uvasBlancas);
        tag.putBoolean("Activado", activado);
        tag.putInt("TipoMosto", tipoMosto);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        uvasComunes = tag.getInt("UvasComunes");
        uvasBlancas = tag.getInt("UvasBlancas");
        activado = tag.getBoolean("Activado");
        tipoMosto = tag.getInt("TipoMosto");
    }

    @Nullable
    @Override
    public Packet<net.minecraft.network.protocol.game.ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    private void activarPrensa() {
        if (level == null || level.isClientSide()) return;
        if (activado) return;

        activado = true;

        int tipo = calcularTipo();
        System.out.println("[Prensa] Tipo calculado: " + tipo + " (1=tinto, 2=blanco, 3=rosado, 4=turbio)");
        System.out.println("[Prensa] Combinación: comunes=" + uvasComunes + ", blancas=" + uvasBlancas);

        tipoMosto = tipo;

        BlockState currentState = level.getBlockState(worldPosition);
        Direction facing = currentState.getValue(PrensaMostoBlock.FACING);

        Block bloqueLista = getBloqueLista();
        System.out.println("[Prensa] Bloque lista seleccionado: " + bloqueLista);

        // ⭐ Guardar el tipo antes de cambiar el bloque
        int tipoGuardado = tipoMosto;

        level.setBlock(worldPosition,
                bloqueLista.defaultBlockState()
                        .setValue(PrensaMostoListaBlock.FACING, facing),
                3);

        // ⭐ Después de cambiar el bloque, obtener el nuevo BE y asignarle el tipo
        if (level.getBlockEntity(worldPosition) instanceof PrensaMostoBlockEntity nuevoBE) {
            nuevoBE.tipoMosto = tipoGuardado;
            System.out.println("[Prensa] Tipo restaurado en el nuevo BE: " + nuevoBE.tipoMosto);
            nuevoBE.setChanged();
        } else {
            System.out.println("[Prensa] ERROR: No se pudo restaurar el tipo en el nuevo BE");
        }
    }
}