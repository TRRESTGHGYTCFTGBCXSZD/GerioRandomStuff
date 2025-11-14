package geriosb.randomstuff.integrations;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

import geriosb.randomstuff.common.caps.MegaStorageItemHandler;
import geriosb.randomstuff.common.caps.MegaStorageMEHandler;
import geriosb.randomstuff.init.GeriorandomstuffModBlockEntities;
import net.minecraft.core.Direction;

import appeng.blockentity.grid.AENetworkInvBlockEntity;
import appeng.api.storage.IStorageProvider;
import appeng.api.storage.IStorageMounts;
import appeng.api.inventories.InternalInventory;
import appeng.api.networking.GridFlags;
import appeng.api.util.AECableType;
import appeng.api.networking.IGridNodeListener;

public class MegaStorageAE2BlockEntity extends AENetworkInvBlockEntity // they are literally redneck engineering
        implements IStorageProvider, IMegaStorageBlockEntity {
	private final MegaStorageItemHandler handler = new MegaStorageItemHandler(1024);
	private final LazyOptional<IItemHandler> handlerOptional = LazyOptional.of(() -> handler);
	private final MegaStorageMEHandler mehandler = new MegaStorageMEHandler(handler);
	private final InternalItemHandlerAdapter rehandler = new InternalItemHandlerAdapter(handler);

	public MegaStorageAE2BlockEntity(BlockPos position, BlockState state) {
		super(GeriorandomstuffModBlockEntities.MEGA_STORAGE.get(), position, state);
        this.getMainNode().setFlags(GridFlags.REQUIRE_CHANNEL);
        this.getMainNode().addService(IStorageProvider.class, this);
	}

	@Override
	public void loadTag(CompoundTag tag) {
		super.loadTag(tag);
    if (tag.contains("BigStorage", Tag.TAG_LIST))
        handler.loadFromNBT(tag.getList("BigStorage", Tag.TAG_COMPOUND));
	}

	@Override
	public void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
    	tag.put("BigStorage", handler.saveToNBT());
	}
	
	@Override
	public ListTag JadeProviderAdditions() {
		return handler.saveToNBT();
	}

@Override
public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
    if (cap == ForgeCapabilities.ITEM_HANDLER)
        return handlerOptional.cast();
    return super.getCapability(cap, side);
}

@Override
public void setRemoved() {
    super.setRemoved();
    handlerOptional.invalidate();
}

@Override
	public void mountInventories(IStorageMounts storageMounts) {
		storageMounts.mount(mehandler,0);
	}

    @Override
    public void onMainNodeStateChanged(IGridNodeListener.State reason) {
        if (reason != IGridNodeListener.State.GRID_BOOT) {
            this.markForUpdate();
        }
    }

@Override
public InternalInventory getInternalInventory() {
    return rehandler;
}

    @Override
    public AECableType getCableConnectionType(Direction dir) {
        return AECableType.SMART;
    }

@Override
	public void onChangeInventory(InternalInventory inv, int slot) {}; //do nothing against that (proprietary item system)


	public AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return ChestMenu.threeRows(id, inventory);
	}

	public Component getDisplayName() {
		return Component.literal("MegaStorage");
	}
}
