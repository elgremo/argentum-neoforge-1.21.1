package com.gremo.argentum.block.custom;

import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CeiboLeavesBlock extends LeavesBlock {

    private static final int DROP_INTERVAL = 100;

    public CeiboLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            int delay = serverLevel.getRandom().nextInt(40) + 20;
            serverLevel.scheduleTick(pos, this, delay);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        if (!level.isClientSide && level.getBlockState(pos).is(this)) {
            tryDropLeaf(level, pos);
            int nextDelay = DROP_INTERVAL + random.nextInt(40);
            level.scheduleTick(pos, this, nextDelay);
        }
    }

    private boolean tryDropLeaf(ServerLevel level, BlockPos pos) {
        BlockPos.MutableBlockPos checkPos = pos.mutable();
        BlockPos groundPos = null;

        // Buscar el primer bloque sólido debajo
        while (checkPos.getY() > level.getMinBuildHeight()) {
            checkPos.move(Direction.DOWN);
            if (level.getBlockState(checkPos).isSolid()) {
                groundPos = checkPos.immutable();
                break;
            }
        }

        if (groundPos == null) {
            return false;
        }

        BlockPos pilePos = groundPos.above();
        BlockState pileState = level.getBlockState(pilePos);

        // ⭐ VERIFICAR QUE NO SEA LÍQUIDO
        if (pileState.isAir() || (pileState.canBeReplaced() && pileState.getFluidState().isEmpty())) {
            int rotation = level.getRandom().nextInt(4);
            level.setBlock(pilePos, ModBlocks.CEIBO_PILA_HOJAS.get().defaultBlockState()
                    .setValue(CeiboLeavesPileBlock.AMOUNT, 1)
                    .setValue(CeiboLeavesPileBlock.ROTATION, rotation), 3);
            return true;
        } else if (pileState.getBlock() instanceof CeiboLeavesPileBlock) {
            int currentAmount = pileState.getValue(CeiboLeavesPileBlock.AMOUNT);
            if (currentAmount < 4) {
                level.setBlock(pilePos, pileState.setValue(CeiboLeavesPileBlock.AMOUNT, currentAmount + 1), 3);
                return true;
            }
            return false;
        }

        return false;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.isClientSide && random.nextInt(6) == 0) {
            BlockPos belowPos = pos.below();
            BlockState belowState = level.getBlockState(belowPos);
            if (!belowState.isSolid() && !belowState.isFaceSturdy(level, belowPos, Direction.UP)) {
                double x = pos.getX() + random.nextDouble();
                double y = pos.getY() - 0.05;
                double z = pos.getZ() + random.nextDouble();
                level.addParticle(ModParticles.CEIBO_PARTICLES.get(), x, y, z, 0.0, -0.01, 0.0);
            }
        }
    }
}