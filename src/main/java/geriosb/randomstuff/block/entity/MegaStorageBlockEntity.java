package geriosb.randomstuff.block.entity;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

import geriosb.randomstuff.caps.MegaStorageItemHandler;
import geriosb.randomstuff.integrations.IMegaStorageBlockEntity;
import geriosb.randomstuff.init.GeriorandomstuffModBlockEntities;
import net.minecraft.core.Direction;

public class MegaStorageBlockEntity extends net.minecraft.world.level.block.entity.BlockEntity implements IMegaStorageBlockEntity {
	private final MegaStorageItemHandler handler = new MegaStorageItemHandler(1024);
	private final LazyOptional<IItemHandler> handlerOptional = LazyOptional.of(() -> handler);

	public MegaStorageBlockEntity(BlockPos position, BlockState state) {
		super(GeriorandomstuffModBlockEntities.MEGA_STORAGE.get(), position, state);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
    if (tag.contains("BigStorage", Tag.TAG_LIST))
        handler.loadFromNBT(tag.getList("BigStorage", Tag.TAG_COMPOUND));
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
    	tag.put("BigStorage", handler.saveToNBT());
	}
	
	@Override
	public ListTag JadeProviderAdditions() {
		return handler.saveToNBT();
	}

@Override
public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) { // the true size is much larger than normal, it's AE2 storage interface interaction is unimplemented
    if (cap == ForgeCapabilities.ITEM_HANDLER)
        return handlerOptional.cast();
    return super.getCapability(cap, side);
}

@Override
public void setRemoved() {
    super.setRemoved();
    handlerOptional.invalidate();
}

	public AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return ChestMenu.threeRows(id, inventory);
	}

	public Component getDisplayName() {
		return Component.literal("MegaStorage");
	}
}
