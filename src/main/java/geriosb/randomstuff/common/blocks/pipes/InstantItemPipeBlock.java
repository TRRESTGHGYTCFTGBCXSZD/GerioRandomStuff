package geriosb.randomstuff.common.blocks.pipes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import java.util.List;

import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import com.mojang.datafixers.util.Pair;

public class InstantItemPipeBlock extends AbstractPipeBlock{
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final Capability<IItemHandler> ITEM_HANDLER = CapabilityManager.get(new CapabilityToken<>() {
    });

    public InstantItemPipeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.literal("Instantly moves all items."));
        list.add(Component.literal("You can chain it with itself, and there's no limit of range."));
        list.add(Component.literal("To branch, you need to connect with storage media,"));
        list.add(Component.literal("as the pipe only pulls from storage media."));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case UP -> box(6, 0, 6, 10, 16, 10);
            case DOWN -> box(6, 0, 6, 10, 16, 10);
            case EAST -> box(0, 6, 6, 16, 10, 10);
            case WEST -> box(0, 6, 6, 16, 10, 10);
            case SOUTH -> box(6, 6, 0, 10, 10, 16);
            case NORTH -> box(6, 6, 0, 10, 10, 16);
            default -> box(6, 6, 6, 10, 10, 10);
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    protected Pair<BlockPos,Direction> FindOutlet(ServerLevel world, BlockPos pos, Direction orientation) {
        BlockPos fast = pos.relative(orientation);
        BlockPos slow = pos;
        Direction outorientation = orientation;
        BlockState block;
        boolean stepodd = false;
        while (!fast.equals(slow)) { // stop if the location is found out to be looping to itself
            stepodd = !stepodd;
            block = world.getBlockState(fast);
            if (block.getBlock() instanceof InstantItemPipeBlock) {
                fast = fast.relative(block.getValue(FACING));
                outorientation = block.getValue(FACING);
            } else {
                return new Pair<>(fast,outorientation);
            }
            if (stepodd) {
                block = world.getBlockState(slow);
                if (block.getBlock() instanceof InstantItemPipeBlock) {
                    slow = slow.relative(block.getValue(FACING));
                } else {
                    return new Pair<>(slow,outorientation);
                }
            }
        }
        return new Pair<>(pos.relative(orientation),orientation);
    }

    public void pullitem(BlockState blockstate, ServerLevel world, BlockPos pos) {
        if (world.isClientSide()) //make sure that this is doing in server
            return;
        //first, get a block from behind and infront
        Direction travelside = blockstate.getValue(FACING);
        BlockState block = world.getBlockState(pos.relative(travelside.getOpposite()));
        if ((block.getBlock() instanceof InstantItemPipeBlock) && (block.getValue(FACING).equals(blockstate.getValue(FACING)))) //stop if the back is itself and facing same
            return;
        BlockEntity intake = world.getBlockEntity(pos.relative(travelside.getOpposite()));
        var checker = FindOutlet(world, pos, travelside);
        Direction pipeend = checker.getSecond();
        BlockEntity outlet = world.getBlockEntity(checker.getFirst());
        //check if the block is valid
        if (intake == null || outlet == null)
            return;
        var sourceCap = intake.getCapability(ITEM_HANDLER, travelside);
        var destCap = outlet.getCapability(ITEM_HANDLER, pipeend.getOpposite());
        if (!sourceCap.isPresent() || !destCap.isPresent())
            return;
        var maybeSourceHandler = sourceCap.resolve();
        var maybeDestinationHandler = destCap.resolve();
        if (maybeSourceHandler.isEmpty() || maybeDestinationHandler.isEmpty())
            return;
        var sourceHandler = maybeSourceHandler.get();
        var destinationHandler = maybeDestinationHandler.get();
        for (int Slot = 0; Slot < sourceHandler.getSlots(); Slot++) {
            {
                var sourceStack = sourceHandler.extractItem(Slot, Integer.MAX_VALUE, true); //instead of copying directly (causes the item to dupe)
                if (sourceStack.isEmpty())
                    continue;
                var transferable = sourceStack.getCount();
                var toTransfer = sourceStack.copy();
                toTransfer.setCount(transferable);
                var remainder = ItemHandlerHelper.insertItem(destinationHandler, toTransfer, false);
                var insertedAmount = transferable - remainder.getCount();
                if (insertedAmount > 0) {
                    sourceHandler.extractItem(Slot, insertedAmount, false);
                }
            }
        }
    }
}
