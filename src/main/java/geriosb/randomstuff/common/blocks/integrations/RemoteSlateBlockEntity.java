package geriosb.randomstuff.common.blocks.integrations;

import geriosb.randomstuff.common.lib.GPSLocation;
import geriosb.randomstuff.common.lib.IGPSableBlock;
import geriosb.randomstuff.init.GeriorandomstuffModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

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
