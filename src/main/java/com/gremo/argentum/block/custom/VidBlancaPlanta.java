package com.gremo.argentum.block.custom;

import com.gremo.argentum.block.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.TriState;

import javax.annotation.Nullable;

public class VidBlancaPlanta extends DoublePlantBlock implements BonemealableBlock {
    public static final MapCodec<VidBlancaPlanta> CODEC =
            simpleCodec(VidBlancaPlanta::new);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    public static final int MAX_AGE = 7;
    private static final int DOUBLE_PLANT_AGE_INTERSECTION = 4;
    private static final int BONEMEAL_INCREASE = 1;
    private static final VoxelShape FULL_UPPER_SHAPE;
    private static final VoxelShape FULL_LOWER_SHAPE;
    private static final VoxelShape COLLISION_SHAPE_BULB;
    private static final VoxelShape COLLISION_SHAPE_CROP;
    private static final VoxelShape[] UPPER_SHAPE_BY_AGE;
    private static final VoxelShape[] LOWER_SHAPE_BY_AGE;

    public MapCodec<VidBlancaPlanta> codec() {
        return CODEC;
    }

    public VidBlancaPlanta(Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(HALF, DoubleBlockHalf.LOWER)
                        .setValue(AGE, 0)
        );
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState();
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(HALF) == DoubleBlockHalf.UPPER ? UPPER_SHAPE_BY_AGE[Math.min(Math.abs(4 - ((Integer)state.getValue(AGE) + 1)), UPPER_SHAPE_BY_AGE.length - 1)] : LOWER_SHAPE_BY_AGE[(Integer)state.getValue(AGE)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (isDouble((Integer)state.getValue(AGE))) {
            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        } else {
            return state.canSurvive(level, currentPos) ? state : Blocks.AIR.defaultBlockState();
        }
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        TriState soilDecision = level.getBlockState(pos.below()).canSustainPlant(level, pos.below(), Direction.UP, state);
        return isLower(state) && !sufficientLight(level, pos) ? soilDecision.isTrue() : super.canSurvive(state, level, pos);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getBlock() instanceof FarmBlock;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AGE});
        super.createBlockStateDefinition(builder);
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof Ravager && level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            level.destroyBlock(pos, true, entity);
        }

        super.entityInside(state, level, pos, entity);
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return false;
    }

    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
    }

    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER && !this.isMaxAge(state);
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        boolean flag = random.nextInt(25) == 0;
        if (flag) {
            this.grow(level, state, pos, 1);
        }

    }

    private void grow(ServerLevel level, BlockState state, BlockPos pos, int ageIncrement) {
        BlockPos lowerPos = state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos : pos.below();
        BlockPos upperPos = lowerPos.above();

        int age = Math.min(level.getBlockState(lowerPos).getValue(AGE) + ageIncrement, MAX_AGE);

        BlockState lower = level.getBlockState(lowerPos)
                .setValue(AGE, age);

        BlockState upper = level.getBlockState(upperPos);

        if (!upper.is(this)) {
            upper = this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER);
        }

        upper = upper.setValue(AGE, age);

        level.setBlock(lowerPos, lower, 2);
        level.setBlock(upperPos, upper, 2);
    }

    private static boolean canGrowInto(LevelReader level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos);
        return blockstate.isAir() || blockstate.is(ModBlocks.VID_BLANCA);
    }

    private static boolean sufficientLight(LevelReader level, BlockPos pos) {
        return CropBlock.hasSufficientLight(level, pos);
    }

    private static boolean isLower(BlockState state) {
        return state.is(ModBlocks.VID_BLANCA) && state.getValue(HALF) == DoubleBlockHalf.LOWER;
    }

    private static boolean isDouble(int age) {
        return true;
    }

    private boolean canGrow(LevelReader reader, BlockPos pos, BlockState state, int age) {
        return !this.isMaxAge(state) && sufficientLight(reader, pos) && (!isDouble(age) || canGrowInto(reader, pos.above()));
    }

    private boolean isMaxAge(BlockState state) {
        return (Integer)state.getValue(AGE) >= 7;
    }

    @Nullable
    private PosAndState getLowerHalf(LevelReader level, BlockPos pos, BlockState state) {
        if (isLower(state)) {
            return new PosAndState(pos, state);
        } else {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            return isLower(blockstate) ? new PosAndState(blockpos, blockstate) : null;
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        PosAndState lower = this.getLowerHalf(level, pos, state);
        if (lower == null) {
            return false;
        }

        boolean can = this.canGrow(level, lower.pos, lower.state, lower.state.getValue(AGE) + 1);

        return can;
    }

    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        PosAndState pitchercropblock$posandstate = this.getLowerHalf(level, pos, state);
        if (pitchercropblock$posandstate != null) {
            this.grow(level, pitchercropblock$posandstate.state, pitchercropblock$posandstate.pos, 1);
        }
    }
    static {
        FULL_UPPER_SHAPE = Block.box((double)3.0F, (double)0.0F, (double)3.0F, (double)13.0F, (double)15.0F, (double)13.0F);
        FULL_LOWER_SHAPE = Block.box((double)3.0F, (double)-1.0F, (double)3.0F, (double)13.0F, (double)16.0F, (double)13.0F);
        COLLISION_SHAPE_BULB = Block.box((double)5.0F, (double)-1.0F, (double)5.0F, (double)11.0F, (double)3.0F, (double)11.0F);
        COLLISION_SHAPE_CROP = Block.box((double)3.0F, (double)-1.0F, (double)3.0F, (double)13.0F, (double)5.0F, (double)13.0F);
        UPPER_SHAPE_BY_AGE = new VoxelShape[]{
                Block.box(3, 0, 3, 13, 11, 13),
                FULL_UPPER_SHAPE,
                FULL_UPPER_SHAPE,
                FULL_UPPER_SHAPE,
                FULL_UPPER_SHAPE
        };
        LOWER_SHAPE_BY_AGE = new VoxelShape[]{
                COLLISION_SHAPE_BULB,                                           // 0
                Block.box(3, -1, 3, 13, 14, 13),                                // 1
                FULL_LOWER_SHAPE,                                               // 2
                FULL_LOWER_SHAPE,                                               // 3
                FULL_LOWER_SHAPE,                                               // 4
                FULL_LOWER_SHAPE,                                               // 5
                FULL_LOWER_SHAPE,                                               // 6
                FULL_LOWER_SHAPE                                                // 7
        };
    }

    static record PosAndState(BlockPos pos, BlockState state) {
    }
}
