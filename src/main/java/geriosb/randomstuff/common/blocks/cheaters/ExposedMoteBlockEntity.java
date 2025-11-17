package geriosb.randomstuff.common.blocks.cheaters;

import geriosb.randomstuff.common.caps.MegaStorageItemHandler;
import geriosb.randomstuff.common.lib.GPSLocation;
import geriosb.randomstuff.common.lib.IGPSableBlock;
import geriosb.randomstuff.init.GeriorandomstuffModBlockEntities;
import geriosb.randomstuff.integrations.IMegaStorageBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import ram.talia.hexal.common.blocks.entity.BlockEntityMediafiedStorage;

import javax.annotation.Nullable;

public class ExposedMoteBlockEntity extends net.minecraft.world.level.block.entity.BlockEntity implements IGPSableBlock {
    private GPSLocation targetloc = null;
	private final MegaStorageItemHandler handler = new MegaStorageItemHandler(1024);
	private final LazyOptional<IItemHandler> handlerOptional = LazyOptional.of(() -> handler);

	public ExposedMoteBlockEntity(BlockPos position, BlockState state) {
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
        if (!(targetloc != null && this.getLevel().getBlockEntity(targetloc.pos) instanceof BlockEntityMediafiedStorage)) return super.getCapability(cap, side);
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

    @Override
    public void SetGPSLocation(GPSLocation loc) {
        handlerOptional.invalidate();
    }

    @Override
    public GPSLocation GetGPSLocation() {
        return null;
    }
}
