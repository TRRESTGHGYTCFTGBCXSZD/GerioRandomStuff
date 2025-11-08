package geriosb.randomstuff.block.entity;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;

import geriosb.randomstuff.init.GeriorandomstuffModBlockEntities;
import geriosb.randomstuff.caps.IGPSableBlock;
import geriosb.randomstuff.caps.GPSLocation;

import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

public class RemoteStorageBlockEntity extends BlockEntity implements IGPSableBlock {
    private GPSLocation targetBlock;
    private volatile boolean InUse = false;
    private volatile long UsedTick = 0;

    public RemoteStorageBlockEntity(BlockPos pos, BlockState state) {
        super(GeriorandomstuffModBlockEntities.REMOTE_STORAGE.get(), pos, state);
    }

    // -------------------
    // NBT SAVE / LOAD
    // -------------------

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
		targetBlock = loadGPSLocFromNBT(tag);
		// legacy compat
        if (tag.contains("TargetX") && tag.contains("TargetDir")) {
            int x = tag.getInt("TargetX");
            int y = tag.getInt("TargetY");
            int z = tag.getInt("TargetZ");
            byte dirIndex = tag.getByte("TargetDir");
			targetBlock = new GPSLocation(new BlockPos(x, y, z), Direction.from3DDataValue(dirIndex), level);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
		saveGPSLocToNBT(tag, targetBlock);
    }

    // -------------------
    // SAFE ACCESS HELPERS
    // -------------------

	private @Nullable BlockEntity getTargetBlock() {
		if (targetBlock == null || level == null || level.isClientSide()) {
			return null; // Don't do this client-side
		}
	
		MinecraftServer server = level.getServer();
		if (server == null) {
			return null; // Shouldn't happen, but safe guard
		}
	
		ServerLevel targetLevel = server.getLevel(targetBlock.dimension);
		if (targetLevel == null) {
			return null; // Invalid dimension
		}
	
		BlockPos pos = targetBlock.pos;
		if (!targetLevel.isLoaded(pos)) {
			targetLevel.getChunk(targetBlock.pos.getX() >> 4, targetBlock.pos.getZ() >> 4);
		}
	
		return targetLevel.getBlockEntity(pos);
	}

    private <T> T tryCall(java.util.function.Function<BlockEntity, T> call, T fallback) {
        BlockEntity be = getTargetBlock();
        if (be == null) return fallback;
        try {
            return call.apply(be);
        } catch (Exception e) {
            return fallback;
        }
    }

    // -------------------
    // Capability Forwarding
    // -------------------

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (InUse && level != null && level.getGameTime() - UsedTick < 5) return LazyOptional.empty();
        InUse = true;
		UsedTick = level != null ? level.getGameTime() : 0;
        BlockEntity target = getTargetBlock();
        if (target == null) return LazyOptional.empty();
        try {
            return target.getCapability(cap, targetBlock.side);
        } catch (Exception e) {
        } finally {
        	InUse = false;
        }
        return LazyOptional.empty();
    }

    // -------------------
    // Inventory Forwarding (Optional)
    // -------------------

    public int getContainerSize() {
        return tryCall(be -> {
            if (be instanceof net.minecraft.world.Container c) return c.getContainerSize();
            return 0;
        }, 0);
    }

    public boolean isEmpty() {
        return tryCall(be -> {
            if (be instanceof net.minecraft.world.Container c) return c.isEmpty();
            return true;
        }, true);
    }

    public NonNullList<ItemStack> getItems() {
        return tryCall(be -> {
            if (be instanceof net.minecraft.world.Container c) {
                NonNullList<ItemStack> items = NonNullList.withSize(c.getContainerSize(), ItemStack.EMPTY);
                for (int i = 0; i < c.getContainerSize(); i++)
                    items.set(i, c.getItem(i));
                return items;
            }
            return NonNullList.create();
        }, NonNullList.create());
    }

    public void setItems(NonNullList<ItemStack> items) {
        BlockEntity be = getTargetBlock();
        if (be instanceof net.minecraft.world.Container c) {
            for (int i = 0; i < Math.min(items.size(), c.getContainerSize()); i++)
                c.setItem(i, items.get(i));
        }
    }

    // -------------------
    // UI
    // -------------------

    public Component getDisplayName() {
        return Component.literal("Remote Storage");
    }

	public void RefreshBlock() {
		this.setChanged();
	
		MinecraftServer server = level.getServer();
		if (server == null) {
			return; // Shouldn't happen, but safe guard
		}
	
		ServerLevel targetLevel = server.getLevel(targetBlock.dimension);
		if (targetLevel == null) {
			return; // Invalid dimension
		}
        if (!targetLevel.isClientSide()) //make sure that this is doing in server
            targetLevel.sendBlockUpdated(targetBlock.pos,targetLevel.getBlockState(targetBlock.pos),targetLevel.getBlockState(targetBlock.pos), 3);
	}

	@Override
	public void SetPositionAndDimension(GPSLocation targetblock) {
		targetBlock = targetblock;
		this.setChanged();
	
		MinecraftServer server = level.getServer();
		if (server == null) {
			return; // Shouldn't happen, but safe guard
		}
	
		ServerLevel targetLevel = server.getLevel(targetBlock.dimension);
		if (targetLevel == null) {
			return; // Invalid dimension
		}
        if (!targetLevel.isClientSide()) //make sure that this is doing in server
			targetLevel.sendBlockUpdated(targetBlock.pos,targetLevel.getBlockState(targetBlock.pos),targetLevel.getBlockState(targetBlock.pos), 3);
	}

}
