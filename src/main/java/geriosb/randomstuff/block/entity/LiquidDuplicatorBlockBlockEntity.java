package geriosb.randomstuff.block.entity;

import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import geriosb.randomstuff.init.GeriorandomstuffModBlockEntities;
import geriosb.randomstuff.InfiniteLiquidTank;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

import java.util.stream.IntStream;

public class LiquidDuplicatorBlockBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
	private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
	private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

	public LiquidDuplicatorBlockBlockEntity(BlockPos position, BlockState state) {
		super(GeriorandomstuffModBlockEntities.LIQUID_DUPLICATOR_BLOCK.get(), position, state);
	}

@Override
protected void saveAdditional(CompoundTag tag) {
    super.saveAdditional(tag);
    FluidStack fs = fluidTank.getFluid();
    if (fs != null && !fs.isEmpty()) {
        CompoundTag fluidTag = new CompoundTag();
        fs.writeToNBT(fluidTag); // writes fluid type + any nbt
        tag.put("FilterFluid", fluidTag);
    }
}

@Override
public void load(CompoundTag tag) {
    super.load(tag);
    if (tag.contains("FilterFluid")) {
        CompoundTag fluidTag = tag.getCompound("FilterFluid");
        FluidStack fs = FluidStack.loadFluidStackFromNBT(fluidTag); // safe loader
        if (fs != null && !fs.isEmpty()) {
        	fluidTank.setFluid(fs);
        } else {
        	fluidTank.setFluid(FluidStack.EMPTY);
        }
    }
}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.saveWithFullMetadata();
	}

	@Override
	public int getContainerSize() {
		return stacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.stacks)
			if (!itemstack.isEmpty())
				return false;
		return true;
	}

	@Override
	public Component getDefaultName() {
		return Component.literal("liquid_duplicator_block");
	}

	@Override
	public int getMaxStackSize() {
		return 64;
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return ChestMenu.threeRows(id, inventory);
	}

	@Override
	public Component getDisplayName() {
		return Component.literal("Liquid Duplicator");
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.stacks;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> stacks) {
		this.stacks = stacks;
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return IntStream.range(0, this.getContainerSize()).toArray();
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
		return this.canPlaceItem(index, stack);
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		return true;
	}

	private final InfiniteLiquidTank fluidTank = new InfiniteLiquidTank(Integer.MAX_VALUE) {
		@Override
		protected void onContentsChanged() {
			super.onContentsChanged();
			setChanged();
			level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), 2);
		}
	};

	public void setFluid(FluidStack fluid) {
		fluidTank.setFluid(fluid);
		this.setChanged();
	}

	public FluidStack getFluid() {
		return fluidTank.getFluid();
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if (!this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER)
			return handlers[facing.ordinal()].cast();
		if (!this.remove && capability == ForgeCapabilities.FLUID_HANDLER)
			return LazyOptional.of(() -> fluidTank).cast();
		return super.getCapability(capability, facing);
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		for (LazyOptional<? extends IItemHandler> handler : handlers)
			handler.invalidate();
	}
}
