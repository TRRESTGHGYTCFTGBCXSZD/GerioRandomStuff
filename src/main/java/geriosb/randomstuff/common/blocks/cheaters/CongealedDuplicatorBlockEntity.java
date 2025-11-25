package geriosb.randomstuff.common.blocks.cheaters;

import geriosb.randomstuff.common.caps.InfiniteItemStorage;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import geriosb.randomstuff.common.world.inventory.DupeMenu;
import geriosb.randomstuff.init.GeriorandomstuffModBlockEntities;

import javax.annotation.Nullable;

import java.util.stream.IntStream;

import io.netty.buffer.Unpooled;

public class CongealedDuplicatorBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
	private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);
    private final LazyOptional<? extends IItemHandler>[] guihandler = SidedInvWrapper.create(this, Direction.values());
    private final InfiniteItemStorage handler = new InfiniteItemStorage(stacks);
    private final LazyOptional<IItemHandler> handlerOptional = LazyOptional.of(() -> handler);

	public CongealedDuplicatorBlockEntity(BlockPos position, BlockState state) {
		super(GeriorandomstuffModBlockEntities.CONGEALED_DUPLICATOR.get(), position, state);

	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		if (!this.tryLoadLootTable(compound))
			this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(compound, this.stacks);
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		if (!this.trySaveLootTable(compound)) {
			ContainerHelper.saveAllItems(compound, this.stacks);
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
    public void setItem(int p_59616_, ItemStack p_59617_) {
        super.setItem(p_59616_, p_59617_);
    }

    @Override
	public Component getDefaultName() {
		return Component.literal("congealed_duplicator");
	}

	@Override
	public int getMaxStackSize() {
		return Integer.MAX_VALUE;
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return new DupeMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(this.worldPosition));
	}

	@Override
	public Component getDisplayName() {
		return Component.literal("Congealed Duplicator");
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
/*
	@Override
	public ItemStack removeItem(int slot, int count) {
		if (slot == 1) {
			ItemStack dropitem = this.stacks[0].copy()
			dropitem.setCount(count)
			return dropitem
		} else {
			
		}
	}

*/
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

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.ITEM_HANDLER)
            return handlerOptional.cast();
		return super.getCapability(capability, facing);
	}

    public <T> LazyOptional<T> getguiCapability(Capability<T> capability) {
        return guihandler[0].cast();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handlerOptional.invalidate();
    }
}
