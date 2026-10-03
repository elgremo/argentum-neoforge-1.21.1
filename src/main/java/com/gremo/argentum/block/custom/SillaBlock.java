package com.gremo.argentum.block.custom;

import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.entity.ModEntities;
import com.gremo.argentum.entity.custom.ChairEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SillaBlock extends HorizontalDirectionalBlock {

    public static final MapCodec<SillaBlock> CODEC = simpleCodec(SillaBlock::new);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShapeFor(state);
    }

    /**
     * Shape de medio bloque de ancho, según FACING y si soy master o pieza 2.
     * La mitad "útil" mira hacia donde está la otra pieza.
     */
    private VoxelShape getShapeFor(BlockState state) {
        Direction facing = state.getValue(FACING);
        BlockPos r = right(facing);
        boolean master = (myIndex() == 0);

        // Si la pieza 2 está al oeste del master:
        //   master dibuja mitad oeste (0-8), pieza 2 dibuja mitad este (8-16)
        if (r.getX() == -1) {
            return master
                    ? Block.box(0, 0, 0, 8, 14, 16)
                    : Block.box(8, 0, 0, 16, 14, 16);
        }
        // Si la pieza 2 está al este del master:
        //   master dibuja mitad este (8-16), pieza 2 dibuja mitad oeste (0-8)
        if (r.getX() == 1) {
            return master
                    ? Block.box(8, 0, 0, 16, 14, 16)
                    : Block.box(0, 0, 0, 8, 14, 16);
        }
        // Si la pieza 2 está al norte del master:
        if (r.getZ() == -1) {
            return master
                    ? Block.box(0, 0, 0, 16, 14, 8)
                    : Block.box(0, 0, 8, 16, 14, 16);
        }
        // Si la pieza 2 está al sur del master:
        if (r.getZ() == 1) {
            return master
                    ? Block.box(0, 0, 8, 16, 14, 16)
                    : Block.box(0, 0, 0, 16, 14, 8);
        }
        // Fallback (no debería pasar)
        return Block.box(0, 0, 0, 16, 14, 16);
    }

    public SillaBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    // ============================================================
    // FAMILIA: 2 piezas (master + _2)
    // ============================================================
    private Block[] family() {
        for (var entry : ModBlocks.FAMILIAS_SILLA.entrySet()) {
            DeferredBlock<SillaBlock>[] fam = entry.getValue();
            for (int i = 0; i < 2; i++) {
                if (fam[i].get() == this) {
                    return new Block[]{ fam[0].get(), fam[1].get() };
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

    /** Derecha del jugador (misma convención que MesaTrucoBlock). */
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
        BlockPos pos2 = pos.offset(right(facing));

        if (!level.getBlockState(pos2).canBeReplaced()) {
            popResource(level, pos, new ItemStack(fam[0]));
            level.destroyBlock(pos, false);
            return;
        }

        level.setBlockAndUpdate(pos2, fam[1].defaultBlockState().setValue(FACING, facing));
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
                Direction facing = state.getValue(FACING);
                BlockPos r = right(facing);

                BlockPos master;
                BlockPos other;

                if (myIndex() == 0) {
                    master = pos;
                    other = pos.offset(r);
                } else {
                    master = pos.offset(r.multiply(-1));
                    other = master;
                }

                if (!other.equals(pos)) {
                    level.destroyBlock(other, false);
                }

                if (!pos.equals(master)) {
                    popResource(level, master, new ItemStack(fam[0]));
                }
            }
        }

        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    // ============================================================
    // INTERACCIÓN (sentarse)
    // ============================================================
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hitResult) {

        if (!level.isClientSide) {
            Block[] fam = family();
            if (fam == null) return InteractionResult.SUCCESS;

            Direction facing = state.getValue(FACING);
            BlockPos r = right(facing);

            // Posición del master
            BlockPos master;
            if (myIndex() == 0) {
                master = pos;
            } else {
                master = pos.offset(r.multiply(-1));
            }

            // Centro entre los 2 bloques
            double spawnX = master.getX() + 0.5 + r.getX() * 0.5;
            double spawnY = master.getY();
            double spawnZ = master.getZ() + 0.5 + r.getZ() * 0.5;

            // Buscar si ya hay una entidad cerca
            AABB area = new AABB(
                    spawnX - 1, spawnY - 1, spawnZ - 1,
                    spawnX + 1, spawnY + 1, spawnZ + 1
            );
            List<ChairEntity> existing = level.getEntitiesOfClass(ChairEntity.class, area);

            ChairEntity chair;
            if (existing.isEmpty()) {
                chair = ModEntities.CHAIR_ENTITY.get().create(level);
                if (chair == null) return InteractionResult.SUCCESS;

                chair.moveTo(spawnX, spawnY, spawnZ, facing.toYRot(), 0f);
                level.addFreshEntity(chair);
            } else {
                chair = existing.get(0);
            }

            player.startRiding(chair);
        }

        return InteractionResult.SUCCESS;
    }
}