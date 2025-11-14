
package geriosb.randomstuff.common.blocks.cheaters;

import at.petrak.hexcasting.api.block.circle.BlockCircleComponent;
import at.petrak.hexcasting.api.casting.eval.env.CircleCastEnv;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;
import java.util.List;
import com.mojang.datafixers.util.Pair;
import javax.annotation.Nullable;

public class RemoteSlateBlock extends BlockCircleComponent implements EntityBlock {
    public RemoteSlateBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(ENERGIZED, false));
    }

    @Override
    public ControlFlow acceptControlFlow(CastingImage imageIn, CircleCastEnv env, Direction enterDir, BlockPos pos,
                                         BlockState bs, ServerLevel world) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof RemoteSlateBlockEntity tile) {
            if (tile.targetBlock.pos == null) return new ControlFlow.Stop();
            if (tile.targetBlock.side == null) return new ControlFlow.Stop();
            return new ControlFlow.Continue(imageIn, List.of(new Pair<BlockPos, Direction>(tile.targetBlock.pos, tile.targetBlock.side)));
        } else {
            return new ControlFlow.Stop();
        }
    }

    @Override
    public boolean canEnterFromDirection(Direction enterDir, BlockPos pos, BlockState bs, ServerLevel world) {
        return true;
    }

    @Override
    public EnumSet<Direction> possibleExitDirections(BlockPos pos, BlockState bs, Level world) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof RemoteSlateBlockEntity ber) {
            return EnumSet.of(ber.targetBlock.side);
        }
        return EnumSet.of(Direction.SOUTH);
    }

    @Override
    public Direction normalDir(BlockPos pos, BlockState bs, Level world, int recursionLeft) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof RemoteSlateBlockEntity ber) {
            return ber.targetBlock.side;
        }
        return Direction.SOUTH;
    }

    @Override
    public float particleHeight(BlockPos pos, BlockState bs, Level world) {
        return 0.5f;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new RemoteSlateBlockEntity(pPos, pState);
    }
}
