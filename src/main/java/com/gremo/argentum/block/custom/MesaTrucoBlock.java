package com.gremo.argentum.block.custom;

import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.block.entity.MesaTrucoBlockEntity;
import com.gremo.argentum.item.ModItems;
import com.gremo.argentum.util.ModTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.Nullable;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class MesaTrucoBlock extends BaseEntityBlock {

    public static final MapCodec<MesaTrucoBlock> CODEC = simpleCodec(MesaTrucoBlock::new);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 14, 16);

    public MesaTrucoBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (myIndex() != 0) return null;
        return new MesaTrucoBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState neighborState, Direction side) {
        return false;
    }

    // ============================================================
    // INTERACCIÓN
    // ============================================================
    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit) {

        // ===== Buscar el master =====
        BlockPos masterPos;
        BlockState masterState;

        if (myIndex() == 0) {
            masterPos = pos;
            masterState = state;
        } else {
            masterPos = getMasterPos(pos, state, family());
            masterState = level.getBlockState(masterPos);
        }

        BlockEntity be = level.getBlockEntity(masterPos);
        if (!(be instanceof MesaTrucoBlockEntity mesa)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        // ===== Calcular slot =====
        int slot = getSlotFromClick(masterPos, masterState, hit.getLocation());
        ItemStack current = mesa.getItem(slot);

        // ===== MAZO (slot CENTRO) =====
        if (slot == MesaTrucoBlockEntity.CENTRO) {

            // === Click con baraja_sellada: llenar con las 40 cartas ===
            if (!stack.isEmpty() && stack.is(ModItems.BARAJA_SELLADA.get())) {
                if (mesa.llenarConBaraja()) {
                    if (!player.getAbilities().instabuild) stack.shrink(1);
                    level.playSound(null, pos, SoundEvents.BOOK_PAGE_TURN,
                            SoundSource.BLOCKS, 1.0f, 0.9f);
                    player.displayClientMessage(
                            Component.literal("Mazo lleno con las 40 cartas"),
                            true
                    );
                } else {
                    player.displayClientMessage(
                            Component.literal("El mazo ya tiene cartas"),
                            true
                    );
                }
                return ItemInteractionResult.SUCCESS;
            }

            // === Click con carta del truco: meter 1 ===
            if (!stack.isEmpty() && stack.is(ModTags.Items.CARTAS_TRUCO)) {
                if (mesa.addToMazo(stack)) {
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                        level.playSound(null, pos, SoundEvents.BOOK_PAGE_TURN,
                                SoundSource.BLOCKS, 0.7f, 1.0f);
                    }
                }
                return ItemInteractionResult.SUCCESS;
            }

            // === Cualquier otro ítem: no hacer nada ===
            if (!stack.isEmpty()) {
                return ItemInteractionResult.SUCCESS;
            }

            // === Mano vacía: sacar 1 al azar ===
            ItemStack drawn = mesa.drawRandomFromMazo();
            if (!drawn.isEmpty()) {
                if (!player.addItem(drawn)) {
                    player.drop(drawn, false);
                    level.playSound(null, pos, SoundEvents.BOOK_PUT,
                            SoundSource.BLOCKS, 0.7f, 0.9f);
                }
            }
            return ItemInteractionResult.SUCCESS;
        }

        // ===== PONER ítem =====
        if (!stack.isEmpty()) {
            // Slot ocupado → no hacer nada
            if (!current.isEmpty()) {
                return ItemInteractionResult.SUCCESS;
            }

            // Verificar tag según tipo de slot
            if (!isValidForSlot(stack, slot)) {
                return ItemInteractionResult.SUCCESS;
            }

            mesa.setItem(slot, stack.copyWithCount(1));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
                // Sonido según tipo de slot
                if (slot == MesaTrucoBlockEntity.CENTRO_IZQ
                        || slot == MesaTrucoBlockEntity.CENTRO_DER) {
                    level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_PLACE,
                            SoundSource.BLOCKS, 0.6f, 1.3f);
                } else {
                    level.playSound(null, pos, SoundEvents.BOOK_PAGE_TURN,
                            SoundSource.BLOCKS, 0.7f, 1.0f);
                }
            }
            return ItemInteractionResult.SUCCESS;
        }

        // ===== SACAR ítem =====
        if (!current.isEmpty()) {

            boolean eraFicha = slot == MesaTrucoBlockEntity.CENTRO_IZQ
                    || slot == MesaTrucoBlockEntity.CENTRO_DER;

            mesa.setItem(slot, ItemStack.EMPTY);
            if (!player.addItem(current)) {
                player.drop(current, false);
            }

            // Sonido según tipo
            if (eraFicha) {
                level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_BREAK,
                        SoundSource.BLOCKS, 0.6f, 1.3f);
            } else {
                level.playSound(null, pos, SoundEvents.BOOK_PAGE_TURN,
                        SoundSource.BLOCKS, 0.7f, 0.9f);
            }

            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    /** Chequea si el ítem corresponde al tipo de slot. */
    private boolean isValidForSlot(ItemStack stack, int slot) {
        // Fichas: centro_izq (3) y centro_der (5)
        if (slot == MesaTrucoBlockEntity.CENTRO_IZQ
                || slot == MesaTrucoBlockEntity.CENTRO_DER) {
            return stack.is(ModTags.Items.FICHAS);
        }
        // Todo lo demás (cartas + mazo por ahora): solo cartas
        return stack.is(ModTags.Items.CARTAS_TRUCO);
    }

    /** Mapea la posición exacta del click a un slot lógico (0..8). */
    private int getSlotFromClick(BlockPos masterPos, BlockState masterState, Vec3 hitWorld) {
        Direction facing = masterState.getValue(FACING);
        Direction frontDir = facing.getOpposite();
        BlockPos rightVec = right(facing);

        // Centro geométrico de la mesa 2x2
        double cx = masterPos.getX() + 0.5 + 0.5 * (frontDir.getStepX() + rightVec.getX());
        double cz = masterPos.getZ() + 0.5 + 0.5 * (frontDir.getStepZ() + rightVec.getZ());

        double ddx = hitWorld.x - cx;
        double ddz = hitWorld.z - cz;

        // Proyección sobre los ejes locales
        double u = ddx * rightVec.getX() + ddz * rightVec.getZ();       // + = derecha del jugador
        double v = ddx * frontDir.getStepX() + ddz * frontDir.getStepZ(); // + = lejos del jugador

        // Columna: 0 = izq, 1 = centro, 2 = der
        int col;
        if (u < -1.0 / 3) col = 0;
        else if (u > 1.0 / 3) col = 2;
        else col = 1;

        // Fila: 0 = arriba (rival), 1 = centro, 2 = abajo (jugador)
        int row;
        if (v > 1.0 / 3) row = 0;
        else if (v < -1.0 / 3) row = 2;
        else row = 1;

        return row * 3 + col;
    }

    // ============================================================
    // FAMILIA
    // ============================================================
    private Block[] family() {
        for (var entry : ModBlocks.FAMILIAS_MESA.entrySet()) {
            DeferredBlock<MesaTrucoBlock>[] fam = entry.getValue();
            for (int i = 0; i < 4; i++) {
                if (fam[i].get() == this) {
                    return new Block[]{
                            fam[0].get(),
                            fam[1].get(),
                            fam[2].get(),
                            fam[3].get()
                    };
                }
            }
        }
        return null;
    }

    private int myIndex() {
        Block[] fam = family();
        if (fam == null) return -1;

        for (int i = 0; i < fam.length; i++) {
            if (fam[i] == this) return i;
        }
        return -1;
    }

    // ============================================================
    // COLOCAR
    // ============================================================
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state,
                            LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (level.isClientSide) return;
        if (myIndex() != 0) return;

        Block[] fam = family();
        if (fam == null) return;

        Direction facing = state.getValue(FACING);
        BlockPos f = front(facing);
        BlockPos r = right(facing);

        BlockPos posFront      = pos.offset(f);
        BlockPos posFrontRight = pos.offset(f).offset(r);
        BlockPos posRight      = pos.offset(r);

        if (!level.getBlockState(posFront).canBeReplaced()
                || !level.getBlockState(posFrontRight).canBeReplaced()
                || !level.getBlockState(posRight).canBeReplaced()) {

            popResource(level, pos, new ItemStack(fam[0]));
            level.destroyBlock(pos, false);
            return;
        }

        level.setBlockAndUpdate(posFront,
                fam[1].defaultBlockState().setValue(FACING, facing));
        level.setBlockAndUpdate(posFrontRight,
                fam[2].defaultBlockState().setValue(FACING, facing));
        level.setBlockAndUpdate(posRight,
                fam[3].defaultBlockState().setValue(FACING, facing));
    }

    // ============================================================
    // ROMPER
    // ============================================================
    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state,
                              @Nullable BlockEntity blockEntity, ItemStack tool) {

        if (!level.isClientSide) {
            Block[] fam = family();
            if (fam != null) {
                BlockPos master = getMasterPos(pos, state, fam);

                // Dropear los ítems guardados
                BlockEntity masterBe = level.getBlockEntity(master);
                if (masterBe instanceof MesaTrucoBlockEntity mesa) {
                    for (int i = 0; i < MesaTrucoBlockEntity.TOTAL_SLOTS; i++) {
                        ItemStack s = mesa.getItem(i);
                        if (!s.isEmpty()) {
                            popResource(level, master, s);
                        }
                    }
                }

                // Dropear las cartas del mazo
                if (masterBe instanceof MesaTrucoBlockEntity mesa) {
                    for (ItemStack s : mesa.getMazo()) {
                        if (!s.isEmpty()) {
                            popResource(level, master, s);
                        }
                    }
                }

                Direction facing = state.getValue(FACING);
                BlockPos f = front(facing);
                BlockPos r = right(facing);

                BlockPos posFront      = master.offset(f);
                BlockPos posFrontRight = master.offset(f).offset(r);
                BlockPos posRight      = master.offset(r);

                BlockPos[] all = { master, posFront, posFrontRight, posRight };

                for (BlockPos p : all) {
                    if (!p.equals(pos)) {
                        level.destroyBlock(p, false);
                    }
                }

                if (!pos.equals(master)) {
                    popResource(level, master, new ItemStack(fam[0]));
                }
            }
        }

        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    private BlockPos getMasterPos(BlockPos pos, BlockState state, Block[] fam) {
        int index = myIndex();
        if (index <= 0) return pos;

        Direction facing = state.getValue(FACING);
        BlockPos f = front(facing);
        BlockPos r = right(facing);

        int fCount = (index == 1 || index == 2) ? 1 : 0;
        int rCount = (index == 2 || index == 3) ? 1 : 0;

        return pos.offset(f.multiply(-fCount)).offset(r.multiply(-rCount));
    }

    // ============================================================
    // HELPERS
    // ============================================================
    private BlockPos front(Direction facing) {
        Direction f = facing.getOpposite();
        return new BlockPos(f.getStepX(), 0, f.getStepZ());
    }

    private BlockPos right(Direction facing) {
        return switch (facing) {
            case NORTH -> new BlockPos(-1, 0, 0);
            case SOUTH -> new BlockPos(1, 0, 0);
            case EAST  -> new BlockPos(0, 0, -1);
            case WEST  -> new BlockPos(0, 0, 1);
            default -> BlockPos.ZERO;
        };
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level,
                                        BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level,
                               BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}