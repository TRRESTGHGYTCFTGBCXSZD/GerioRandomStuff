package geriosb.randomstuff.block.entity;

import geriosb.randomstuff.caps.GPSLocation;
import geriosb.randomstuff.caps.IGPSableBlock;
import geriosb.randomstuff.init.GeriorandomstuffModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class RemoteSlateBlockEntity extends BlockEntity implements IGPSableBlock {
    public GPSLocation targetBlock;

    public RemoteSlateBlockEntity(BlockPos pos, BlockState state) {
        super(GeriorandomstuffModBlockEntities.REMOTE_SLATE.get(), pos, state);
    }

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

	@Override
	public void SetGPSLocation(GPSLocation targetblock) {
		targetBlock = targetblock;
	}

    @Override
    public GPSLocation GetGPSLocation() {
        return targetBlock;
    }

}
