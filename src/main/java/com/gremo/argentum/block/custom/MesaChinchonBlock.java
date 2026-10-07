package com.gremo.argentum.block.custom;

import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.block.entity.MesaChinchonBlockEntity;
import com.gremo.argentum.item.ModItems;
import com.gremo.argentum.util.ModTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

public class MesaChinchonBlock extends BaseEntityBlock {

    public static final MapCodec<MesaChinchonBlock> CODEC = simpleCodec(MesaChinchonBlock::new);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 14, 16);

    public MesaChinchonBlock(Properties properties) {
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
        return new MesaChinchonBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState neighborState, Direction side) {
        return false;
    }

    // ============================================================
    // INTERACCIÓN
    // ============================================================
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level,
                                              BlockPos pos, Player player, InteractionHand hand,
                                              BlockHitResult hit) {

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
        if (!(be instanceof MesaChinchonBlockEntity mesa)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        int slot = getSlotFromClick(masterPos, masterState, hit.getLocation());
        if (slot == -1) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        // ===== MAZO (slot 14) =====
        if (slot == MesaChinchonBlockEntity.MAZO) {

            // Baraja → llena el mazo con las 50 cartas
            if (!stack.isEmpty() && stack.is(ModItems.BARAJA_SELLADA.get())) {
                if (mesa.llenarConBaraja()) {
                    if (!player.getAbilities().instabuild) stack.shrink(1);
                    level.playSound(null, masterPos, SoundEvents.BOOK_PAGE_TURN,
                            SoundSource.BLOCKS, 1.0f, 0.9f);
                    player.displayClientMessage(
                            Component.literal("Mazo lleno con las 50 cartas"),
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

            // Carta suelta del tag #cartas: meter 1
            if (!stack.isEmpty() && stack.is(ModTags.Items.CARTAS)) {
                if (mesa.addToMazo(stack)) {
                    if (!player.getAbilities().instabuild) stack.shrink(1);
                    level.playSound(null, masterPos, SoundEvents.BOOK_PAGE_TURN,
                            SoundSource.BLOCKS, 0.7f, 1.0f);
                }
                return ItemInteractionResult.SUCCESS;
            }

            if (!stack.isEmpty()) {
                return ItemInteractionResult.SUCCESS;
            }

            // Mano vacía → sacar 1 al azar
            ItemStack drawn = mesa.drawRandomFromMazo();

            // Si el mazo está vacío pero hay descarte, reciclar y reintentar
            if (drawn.isEmpty() && mesa.getDescarteCount() > 0) {
                mesa.reciclarAlMazo();
                player.displayClientMessage(
                        Component.literal("Se ha mezclado el descarte al mazo"),
                        true
                );
                drawn = mesa.drawRandomFromMazo();
            }

            if (!drawn.isEmpty()) {
                if (!player.addItem(drawn)) player.drop(drawn, false);
                level.playSound(null, masterPos, SoundEvents.BOOK_PAGE_TURN,
                        SoundSource.BLOCKS, 0.7f, 0.9f);
            }
            return ItemInteractionResult.SUCCESS;
        }
        // ===== DESCARTE (slot 15) =====
        if (slot == MesaChinchonBlockEntity.DESCARTE) {

            // Click con carta → tirar al descarte
            if (!stack.isEmpty() && stack.is(ModTags.Items.CARTAS)) {

                // Detectar quién tira según la posición del jugador
                Direction facing = state.getValue(FACING);
                Direction frontDir = facing.getOpposite();
                BlockPos rightVec = right(facing);

                double cx = masterPos.getX() + 0.5 + 0.5 * (frontDir.getStepX() + rightVec.getX());
                double cz = masterPos.getZ() + 0.5 + 0.5 * (frontDir.getStepZ() + rightVec.getZ());

                double dx = player.getX() - cx;
                double dz = player.getZ() - cz;

                double proj = dx * frontDir.getStepX() + dz * frontDir.getStepZ();
                int owner = proj > 0 ? 1 : 0;

                // Tirar la carta (devuelve true si recicló)
                boolean reciclo = mesa.addToDescarte(stack, owner);

                if (!player.getAbilities().instabuild) stack.shrink(1);
                level.playSound(null, masterPos, SoundEvents.BOOK_PAGE_TURN,
                        SoundSource.BLOCKS, 0.7f, 1.0f);

                // Mensaje si se recicló
                if (reciclo) {
                    player.displayClientMessage(
                            Component.literal("Se han mezclado las cartas del descarte al mazo"),
                            true
                    );
                }

                return ItemInteractionResult.SUCCESS;
            }

            // Click con cualquier otra cosa: no hacer nada
            if (!stack.isEmpty()) {
                return ItemInteractionResult.SUCCESS;
            }

            // Mano vacía → sacar la de arriba
            ItemStack top = mesa.takeFromDescarte();
            if (!top.isEmpty()) {
                if (!player.addItem(top)) player.drop(top, false);
                level.playSound(null, masterPos, SoundEvents.BOOK_PAGE_TURN,
                        SoundSource.BLOCKS, 0.7f, 0.9f);
            }
            return ItemInteractionResult.SUCCESS;
        }

        // ===== SLOTS VISIBLES (0-13) =====
        ItemStack current = mesa.getItem(slot);

        // PONER
        if (!stack.isEmpty()) {
            if (!current.isEmpty()) return ItemInteractionResult.SUCCESS;
            if (!stack.is(ModTags.Items.CARTAS)) return ItemInteractionResult.SUCCESS;

            mesa.setItem(slot, stack.copyWithCount(1));
            if (!player.getAbilities().instabuild) stack.shrink(1);
            level.playSound(null, masterPos, SoundEvents.BOOK_PAGE_TURN,
                    SoundSource.BLOCKS, 0.7f, 1.0f);
            return ItemInteractionResult.SUCCESS;
        }

        // SACAR
        if (!current.isEmpty()) {
            mesa.setItem(slot, ItemStack.EMPTY);
            if (!player.addItem(current)) player.drop(current, false);
            level.playSound(null, masterPos, SoundEvents.BOOK_PAGE_TURN,
                    SoundSource.BLOCKS, 0.7f, 0.9f);
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    /** Mapea la posición del click a un slot (0-14) o -1 si clickeó en el borde. */
    private int getSlotFromClick(BlockPos masterPos, BlockState masterState, Vec3 hitWorld) {
        Direction facing = masterState.getValue(FACING);
        Direction frontDir = facing.getOpposite();
        BlockPos rightVec = right(facing);

        double cx = masterPos.getX() + 0.5 + 0.5 * (frontDir.getStepX() + rightVec.getX());
        double cz = masterPos.getZ() + 0.5 + 0.5 * (frontDir.getStepZ() + rightVec.getZ());

        double ddx = hitWorld.x - cx;
        double ddz = hitWorld.z - cz;

        double u = ddx * rightVec.getX() + ddz * rightVec.getZ();
        double v = ddx * frontDir.getStepX() + ddz * frontDir.getStepZ();

        // 7 slots arriba (rival) en v = 0.62, u de -0.9 a 0.9
        // 7 slots abajo (jugador) en v = -0.62, u de -0.9 a 0.9
        // 1 mazo en u = 0, v = 0

        double[][] positions = new double[16][2];

        // Rival (0-6)
        double startU = -0.9;
        double stepU = 0.3;
        for (int i = 0; i < 7; i++) {
            positions[i][0] = startU + i * stepU;
            positions[i][1] = 0.62;
        }

        // Jugador (7-13)
        for (int i = 0; i < 7; i++) {
            positions[7 + i][0] = startU + i * stepU;
            positions[7 + i][1] = -0.62;
        }

        // Mazo (14) - centro, movido 1px a la izq
        positions[14][0] = -0.0625;
        positions[14][1] = 0;

        // Descarte (15) - más separado del mazo
        positions[15][0] = 0.7;
        positions[15][1] = 0;

        int closest = -1;
        double minDist = Double.MAX_VALUE;

        for (int i = 0; i < positions.length; i++) {
            double du = u - positions[i][0];
            double dv = v - positions[i][1];
            double dist = du * du + dv * dv;

            // Si es el MAZO, aplica un radio máximo (zona más chica)
            if (i == 14) {
                double radioMazo = 0.25; // 4px de radio
                if (dist > radioMazo * radioMazo) continue;
            }

            if (dist < minDist) {
                minDist = dist;
                closest = i;
            }
        }

        return closest;
    }

    // ============================================================
    // FAMILIA
    // ============================================================
    private Block[] family() {
        for (var entry : ModBlocks.FAMILIAS_CHINCHON.entrySet()) {
            DeferredBlock<MesaChinchonBlock>[] fam = entry.getValue();
            for (int i = 0; i < 4; i++) {
                if (fam[i].get() == this) {
                    return new Block[]{
                            fam[0].get(), fam[1].get(), fam[2].get(), fam[3].get()
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

                BlockEntity masterBe = level.getBlockEntity(master);
                if (masterBe instanceof MesaChinchonBlockEntity mesa) {
                    for (int i = 0; i < MesaChinchonBlockEntity.TOTAL_SLOTS; i++) {
                        ItemStack s = mesa.getItem(i);
                        if (!s.isEmpty()) popResource(level, master, s);
                    }
                    for (ItemStack s : mesa.getMazo()) {
                        if (!s.isEmpty()) popResource(level, master, s);
                    }
                    for (ItemStack s : mesa.getDescarte()) {
                        if (!s.isEmpty()) popResource(level, master, s);
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
                    if (!p.equals(pos)) level.destroyBlock(p, false);
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

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}