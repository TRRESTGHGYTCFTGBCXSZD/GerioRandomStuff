package geriosb.randomstuff.common.blocks.integrations;

import geriosb.randomstuff.common.caps.MoteHandler;
import geriosb.randomstuff.common.lib.GPSLocation;
import geriosb.randomstuff.common.lib.IGPSableBlock;
import geriosb.randomstuff.init.GeriorandomstuffModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
	private final MoteHandler handler = new MoteHandler();
	private final LazyOptional<IItemHandler> handlerOptional = LazyOptional.of(() -> handler);

	public ExposedMoteBlockEntity(BlockPos position, BlockState state) {
		super(GeriorandomstuffModBlockEntities.EXPOSED_MOTE.get(), position, state);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
        targetloc = loadGPSLocFromNBT(tag);
        if (targetloc != null && this.getLevel() != null && this.getLevel().getBlockEntity(targetloc.pos) instanceof BlockEntityMediafiedStorage watashino)
            handler.setPosition(watashino);
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
        saveGPSLocToNBT(tag,targetloc);
	}

@Override
public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) { // the true size is much larger than normal, it's AE2 storage interface interaction is unimplemented
    if (cap == ForgeCapabilities.ITEM_HANDLER && targetloc != null && this.getLevel() != null && this.getLevel().getBlockEntity(targetloc.pos) instanceof BlockEntityMediafiedStorage watashino) {
        handler.setPosition(watashino);
        return handlerOptional.cast();
    }
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
        targetloc = loc;
        //handlerOptional.invalidate();
        if (targetloc != null && this.getLevel() != null && this.getLevel().getBlockEntity(targetloc.pos) instanceof BlockEntityMediafiedStorage watashino)
            handler.setPosition(watashino);
        this.setChanged();
    }

    @Override
    public GPSLocation GetGPSLocation() {
        return targetloc;
    }
}
