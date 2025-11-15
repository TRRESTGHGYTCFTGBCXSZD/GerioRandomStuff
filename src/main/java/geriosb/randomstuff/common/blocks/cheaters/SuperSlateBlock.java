
// this is just a copy of slate block, to see which way works
package geriosb.randomstuff.common.blocks.cheaters;

import at.petrak.hexcasting.annotations.SoftImplement;
import at.petrak.hexcasting.api.block.circle.BlockCircleComponent;
import at.petrak.hexcasting.api.casting.eval.ExecutionClientView;
import at.petrak.hexcasting.api.casting.eval.env.CircleCastEnv;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.common.blocks.circles.BlockSlate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import at.petrak.hexcasting.api.utils.NBTHelper;

import geriosb.randomstuff.init.GeriorandomstuffModItems;

import javax.annotation.Nonnull;
import java.util.EnumSet;

// When on the floor or ceiling FACING is the direction the *bottom* of the pattern points
// (or which way is "down").
// When on the wall FACING is the direction of the *front* of the block
public class SuperSlateBlock extends BlockCircleComponent implements EntityBlock, SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<AttachFace> ATTACH_FACE = BlockStateProperties.ATTACH_FACE;

    public static final double THICKNESS = 1;
    public static final VoxelShape AABB_FLOOR = Block.box(0, 0, 0, 16, THICKNESS, 16);
    public static final VoxelShape AABB_CEILING = Block.box(0, 16 - THICKNESS, 0, 16, 16, 16);
    public static final VoxelShape AABB_EAST_WALL = Block.box(0, 0, 0, THICKNESS, 16, 16);
    public static final VoxelShape AABB_WEST_WALL = Block.box(16 - THICKNESS, 0, 0, 16, 16, 16);
    public static final VoxelShape AABB_SOUTH_WALL = Block.box(0, 0, 0, 16, 16, THICKNESS);
    public static final VoxelShape AABB_NORTH_WALL = Block.box(0, 0, 16 - THICKNESS, 16, 16, 16);

    public SuperSlateBlock(Properties p_53182_) {
        super(p_53182_);
        this.registerDefaultState(
            this.stateDefinition.any()
                .setValue(ENERGIZED, false)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, @Nonnull BlockGetter reader, @Nonnull BlockPos pos) {
        return !state.getValue(WATERLOGGED);
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public ControlFlow acceptControlFlow(CastingImage imageIn, CircleCastEnv env, Direction enterDir, BlockPos pos,
        BlockState bs, ServerLevel world) {
        Iota pattern = null;
        if (world.getBlockEntity(pos) instanceof SuperSlateBlockEntity tile) {
            if (!(tile.getIotaTag() == null)) pattern = IotaType.deserialize(tile.getIotaTag(), world);
        } else {
            return new ControlFlow.Stop();
        }

        var exitDirsSet = this.possibleExitDirections(pos, bs, world);
        exitDirsSet.remove(enterDir.getOpposite());

        var exitDirs = exitDirsSet.stream().map((dir) -> this.exitPositionFromDirection(pos, dir));

        if (pattern == null)
            return new ControlFlow.Continue(imageIn, exitDirs.toList());
        if (imageIn.getParenCount() > 0 || imageIn.getEscapeNext() || pattern instanceof PatternIota) {
            var vm = new CastingVM(imageIn, env);

            var result = vm.queueExecuteAndWrapIota(pattern, world);
            //if (result.getResolutionType().getSuccess()) { // bypass mishaps
                return new ControlFlow.Continue(vm.getImage(), exitDirs.toList());
            //} else {
                //return new ControlFlow.Stop();
            //}
        } else {
            CastingImage newimage = imageIn.copy(imageIn.getStack(),imageIn.getParenCount(),imageIn.getParenthesized(),imageIn.getEscapeNext(),imageIn.getOpsConsumed(),imageIn.getUserData());
            newimage.getStack().add(pattern);
            return new ControlFlow.Continue(newimage, exitDirs.toList());
        }
    }

    @Override
    public boolean canEnterFromDirection(Direction enterDir, BlockPos pos, BlockState bs, ServerLevel world) {
        var thisNormal = this.normalDir(pos, bs, world);
        return enterDir != thisNormal.getOpposite(); // && enterDir != thisNormal;
    }

    @Override
    public EnumSet<Direction> possibleExitDirections(BlockPos pos, BlockState bs, Level world) {
        var allDirs = EnumSet.allOf(Direction.class);
        var normal = this.normalDir(pos, bs, world);
        allDirs.remove(normal);
//        allDirs.remove(normal.getOpposite());
        return allDirs;
    }

    @SoftImplement("forge")
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos,
        Player player) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof SuperSlateBlockEntity slate) {
            ItemStack stack = new ItemStack(GeriorandomstuffModItems.SUPER_SLATE.get());
            if (slate.getIotaTag() != null) {
                var beTag = NBTHelper.getOrCreateCompound(stack, "BlockEntityTag");
                beTag.put(SuperSlateBlockEntity.TAG_IOTA, slate.getIotaTag());
            }
            return stack;
        }

        return new ItemStack(this);
    }

    @Override
    public Direction normalDir(BlockPos pos, BlockState bs, Level world, int recursionLeft) {
        return switch (bs.getValue(ATTACH_FACE)) {
            case FLOOR -> Direction.UP;
            case CEILING -> Direction.DOWN;
            case WALL -> bs.getValue(FACING);
        };
    }

    @Override
    public float particleHeight(BlockPos pos, BlockState bs, Level world) {
        return 0.5f - 15f / 16f;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SuperSlateBlockEntity(pPos, pState);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(ATTACH_FACE)) {
            case FLOOR -> AABB_FLOOR;
            case CEILING -> AABB_CEILING;
            case WALL -> switch (pState.getValue(FACING)) {
                case NORTH -> AABB_NORTH_WALL;
                case EAST -> AABB_EAST_WALL;
                case SOUTH -> AABB_SOUTH_WALL;
                // NORTH; up and down don't happen (but we need branches for them)
                default -> AABB_WEST_WALL;
            };
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, ATTACH_FACE, WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidState = pContext.getLevel().getFluidState(pContext.getClickedPos());

        for (Direction direction : pContext.getNearestLookingDirections()) {
            BlockState blockstate;
            if (direction.getAxis() == Direction.Axis.Y) {
                blockstate = this.defaultBlockState()
                    .setValue(ATTACH_FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR)
                    .setValue(FACING, pContext.getHorizontalDirection().getOpposite());
            } else {
                blockstate = this.defaultBlockState()
                    .setValue(ATTACH_FACE, AttachFace.WALL)
                    .setValue(FACING, direction.getOpposite());
            }
            blockstate = blockstate.setValue(WATERLOGGED,
                fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8);

            if (blockstate.canSurvive(pContext.getLevel(), pContext.getClickedPos())) {
                return blockstate;
            }
        }

        return null;
    }

    // i do as the FaceAttachedHorizontalDirectionBlock.java guides
    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return canAttach(pLevel, pPos, getConnectedDirection(pState).getOpposite());
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel,
        BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return getConnectedDirection(pState).getOpposite() == pFacing
            && !pState.canSurvive(pLevel, pCurrentPos) ?
            pState.getFluidState().createLegacyBlock()
            : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    public static boolean canAttach(LevelReader pReader, BlockPos pPos, Direction pDirection) {
        BlockPos blockpos = pPos.relative(pDirection);
        return pReader.getBlockState(blockpos).isFaceSturdy(pReader, blockpos, pDirection.getOpposite());
    }

    protected static Direction getConnectedDirection(BlockState pState) {
        return switch (pState.getValue(ATTACH_FACE)) {
            case CEILING -> Direction.DOWN;
            case FLOOR -> Direction.UP;
            default -> pState.getValue(FACING);
        };
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return (BlockState) state.setValue(FACING, rot.rotate((Direction) state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation((Direction) state.getValue(FACING)));
    }
}