
package geriosb.randomstuff.block;

import mekanism.api.Action;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.chemical.infuse.IInfusionHandler;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.chemical.pigment.IPigmentHandler;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.api.chemical.slurry.ISlurryHandler;
import mekanism.common.capabilities.Capabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import java.util.List;
import java.util.Collections;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;

import net.minecraftforge.fml.ModList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InstantGasPipeBlock extends Block {
	public static final DirectionProperty FACING = DirectionalBlock.FACING;
	private static Logger soggy = LogManager.getLogger();

	public InstantGasPipeBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.ANVIL).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}
	
	public class GasPipeProcedure {
		private static PullAway REM;
		private static PullAway fallback;
		
		public static PullAway pullitem() {
			if (REM == null) {
				if (ModList.get().isLoaded("mekanism")) {
					soggy.info("Mekanism detected loaded");
					REM = new PullAway();
				} else {
					soggy.info("Mekanism not detected loaded");
					REM = new PullAway.Dummy();
				}
			}
			return REM;
		}
		
		protected static class PullAway {
			protected void dothething1(BlockEntity intake, BlockEntity outlet, Direction travelside, Direction pipeend) {
				var sourceCap = intake.getCapability(Capabilities.GAS_HANDLER, travelside);
				var destCap = outlet.getCapability(Capabilities.GAS_HANDLER, pipeend.getOpposite());
				if (!sourceCap.isPresent() || !destCap.isPresent())
					return;
				var maybeSourceHandler = sourceCap.resolve();
				var maybeDestinationHandler = destCap.resolve();
				if (maybeSourceHandler.isEmpty() || maybeDestinationHandler.isEmpty())
					return;
				var sourceHandler = maybeSourceHandler.get();
				var destinationHandler = maybeDestinationHandler.get();
				for (int Slot = 0; Slot < sourceHandler.getTanks(); Slot++) {
					{
						var sourceStack = sourceHandler.extractChemical(Slot,Integer.MAX_VALUE,Action.SIMULATE);
						if (sourceStack.isEmpty())
							continue;
						for (int Channel = 0; Channel < destinationHandler.getTanks(); Channel++) {
							{
								var transferable = sourceStack.getAmount();
								var toTransfer = sourceStack.copy();
								toTransfer.setAmount(transferable);
								var remainder = destinationHandler.insertChemical(Channel,toTransfer,Action.EXECUTE);
								var insertedAmount = transferable - remainder.getAmount();
								if (insertedAmount > 0) {
									sourceHandler.extractChemical(Slot,insertedAmount,Action.EXECUTE);
									break;
								}
							}
						}
					}
				}
			}
			protected void dothething2(BlockEntity intake, BlockEntity outlet, Direction travelside, Direction pipeend) {
				var sourceCap = intake.getCapability(Capabilities.INFUSION_HANDLER, travelside);
				var destCap = outlet.getCapability(Capabilities.INFUSION_HANDLER, pipeend.getOpposite());
				if (!sourceCap.isPresent() || !destCap.isPresent())
					return;
				var maybeSourceHandler = sourceCap.resolve();
				var maybeDestinationHandler = destCap.resolve();
				if (maybeSourceHandler.isEmpty() || maybeDestinationHandler.isEmpty())
					return;
				var sourceHandler = maybeSourceHandler.get();
				var destinationHandler = maybeDestinationHandler.get();
				for (int Slot = 0; Slot < sourceHandler.getTanks(); Slot++) {
					{
						var sourceStack = sourceHandler.extractChemical(Slot,Integer.MAX_VALUE,Action.SIMULATE);
						if (sourceStack.isEmpty())
							continue;
						for (int Channel = 0; Channel < destinationHandler.getTanks(); Channel++) {
							{
								var transferable = sourceStack.getAmount();
								var toTransfer = sourceStack.copy();
								toTransfer.setAmount(transferable);
								var remainder = destinationHandler.insertChemical(Channel,toTransfer,Action.EXECUTE);
								var insertedAmount = transferable - remainder.getAmount();
								if (insertedAmount > 0) {
									sourceHandler.extractChemical(Slot,insertedAmount,Action.EXECUTE);
									break;
								}
							}
						}
					}
				}
			}
			protected void dothething3(BlockEntity intake, BlockEntity outlet, Direction travelside, Direction pipeend) {
				var sourceCap = intake.getCapability(Capabilities.PIGMENT_HANDLER, travelside);
				var destCap = outlet.getCapability(Capabilities.PIGMENT_HANDLER, pipeend.getOpposite());
				if (!sourceCap.isPresent() || !destCap.isPresent())
					return;
				var maybeSourceHandler = sourceCap.resolve();
				var maybeDestinationHandler = destCap.resolve();
				if (maybeSourceHandler.isEmpty() || maybeDestinationHandler.isEmpty())
					return;
				var sourceHandler = maybeSourceHandler.get();
				var destinationHandler = maybeDestinationHandler.get();
				for (int Slot = 0; Slot < sourceHandler.getTanks(); Slot++) {
					{
						var sourceStack = sourceHandler.extractChemical(Slot,Integer.MAX_VALUE,Action.SIMULATE);
						if (sourceStack.isEmpty())
							continue;
						for (int Channel = 0; Channel < destinationHandler.getTanks(); Channel++) {
							{
								var transferable = sourceStack.getAmount();
								var toTransfer = sourceStack.copy();
								toTransfer.setAmount(transferable);
								var remainder = destinationHandler.insertChemical(Channel,toTransfer,Action.EXECUTE);
								var insertedAmount = transferable - remainder.getAmount();
								if (insertedAmount > 0) {
									sourceHandler.extractChemical(Slot,insertedAmount,Action.EXECUTE);
									break;
								}
							}
						}
					}
				}
			}
			protected void dothething4(BlockEntity intake, BlockEntity outlet, Direction travelside, Direction pipeend) {
				var sourceCap = intake.getCapability(Capabilities.SLURRY_HANDLER, travelside);
				var destCap = outlet.getCapability(Capabilities.SLURRY_HANDLER, pipeend.getOpposite());
				if (!sourceCap.isPresent() || !destCap.isPresent())
					return;
				var maybeSourceHandler = sourceCap.resolve();
				var maybeDestinationHandler = destCap.resolve();
				if (maybeSourceHandler.isEmpty() || maybeDestinationHandler.isEmpty())
					return;
				var sourceHandler = maybeSourceHandler.get();
				var destinationHandler = maybeDestinationHandler.get();
				for (int Slot = 0; Slot < sourceHandler.getTanks(); Slot++) {
					{
						var sourceStack = sourceHandler.extractChemical(Slot,Integer.MAX_VALUE,Action.SIMULATE);
						if (sourceStack.isEmpty())
							continue;
						for (int Channel = 0; Channel < destinationHandler.getTanks(); Channel++) {
							{
								var transferable = sourceStack.getAmount();
								var toTransfer = sourceStack.copy();
								toTransfer.setAmount(transferable);
								var remainder = destinationHandler.insertChemical(Channel,toTransfer,Action.EXECUTE);
								var insertedAmount = transferable - remainder.getAmount();
								if (insertedAmount > 0) {
									sourceHandler.extractChemical(Slot,insertedAmount,Action.EXECUTE);
									break;
								}
							}
						}
					}
				}
			}
			public void PullAway(BlockEntity intake, BlockEntity outlet, Direction travelside, Direction pipeend) {
				dothething1(intake, outlet, travelside, pipeend);
				dothething2(intake, outlet, travelside, pipeend);
				dothething3(intake, outlet, travelside, pipeend);
				dothething4(intake, outlet, travelside, pipeend);
			}
		
			public static class Dummy extends PullAway {
				@Override
				protected void dothething1(BlockEntity intake, BlockEntity outlet, Direction travelside, Direction pipeend) {
				}
				@Override
				protected void dothething2(BlockEntity intake, BlockEntity outlet, Direction travelside, Direction pipeend) {
				}
				@Override
				protected void dothething3(BlockEntity intake, BlockEntity outlet, Direction travelside, Direction pipeend) {
				}
				@Override
				protected void dothething4(BlockEntity intake, BlockEntity outlet, Direction travelside, Direction pipeend) {
				}
				@Override
				public void PullAway(BlockEntity intake, BlockEntity outlet, Direction travelside, Direction pipeend) {
				}
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack itemstack, BlockGetter world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		if (!ModList.get().isLoaded("mekanism")) {
			list.add(Component.literal("DEPENDENCY MISSING - Mekanism is not installed."));
		} else {
		list.add(Component.literal("Instantly moves all Chemicals (including gases)."));
		list.add(Component.literal("You can chain it with itself, and there's no limit of range."));
		list.add(Component.literal("There's no internal storage, so don't worry about losing gases."));
		}
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 0;
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

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
        var state = this.defaultBlockState();
		Direction playerlook = context.getNearestLookingDirection();
		if (context.getPlayer().isShiftKeyDown()) {
			state = state.setValue(FACING, playerlook);
		} else {
			state = state.setValue(FACING, playerlook.getOpposite());
		}
        return state;
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	private static class Chacker {
		BlockPos retblock;
		Direction orientation;
		public Chacker(BlockPos block,Direction dir){
			retblock = block;
			orientation = dir;
		}
		public BlockPos GetPos(){
			return retblock;
		}
		public Direction GetDirection(){
			return orientation;
		}
	}

	protected Chacker FindOutlet(ServerLevel world, BlockPos pos, Direction orientation) {
		BlockPos fast = pos.relative(orientation);
		BlockPos slow = pos;
		Direction outorientation = orientation;
		BlockState block;
		boolean stepodd = false;
		while (!fast.equals(slow)) { // stop if the location is found out to be looping to itself
			stepodd = !stepodd;
			block = world.getBlockState(fast);
			if (block.getBlock() instanceof InstantGasPipeBlock) {
				fast = fast.relative(block.getValue(FACING));
				outorientation = block.getValue(FACING);
			} else {
				return new Chacker(fast,outorientation);
			}
			if (stepodd) {
			block = world.getBlockState(slow);
			if (block.getBlock() instanceof InstantGasPipeBlock) {
				slow = slow.relative(block.getValue(FACING));
			} else {
				return new Chacker(slow,outorientation);
			}
			}
		}
		return new Chacker(pos.relative(orientation),orientation);
	}

	public void pullitem(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        if (world.isClientSide()) //make sure that this is doing in server
            return;
		//first, get a block from behind and infront
		Direction travelside = blockstate.getValue(FACING);
		BlockState block = world.getBlockState(pos.relative(travelside.getOpposite()));
        if ((block.getBlock() instanceof InstantGasPipeBlock) && (block.getValue(FACING).equals(blockstate.getValue(FACING)))) //stop if the back is itself and facing same
            return;
		BlockEntity intake = world.getBlockEntity(pos.relative(travelside.getOpposite()));
		Chacker checker = FindOutlet(world, pos, travelside);
		Direction pipeend = checker.GetDirection();
		BlockEntity outlet = world.getBlockEntity(checker.GetPos());
		//check if the block is valid
		if (intake == null || outlet == null)
			return;
		GasPipeProcedure.pullitem().PullAway(intake,outlet,travelside,pipeend);
	}


	@Override
	public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
		super.tick(blockstate, world, pos, random);
		pullitem(blockstate, world, pos, random);
		world.scheduleTick(pos, this, 1);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		List<ItemStack> dropsOriginal = super.getDrops(state, builder);
		if (!dropsOriginal.isEmpty())
			return dropsOriginal;
		return Collections.singletonList(new ItemStack(this, 1));
	}
}
