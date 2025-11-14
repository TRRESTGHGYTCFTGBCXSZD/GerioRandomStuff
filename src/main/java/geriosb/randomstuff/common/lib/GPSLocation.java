package geriosb.randomstuff.common.lib;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class GPSLocation {
	public ResourceKey<Level> dimension;
	public BlockPos pos;
	public Direction side;
	public GPSLocation() { //identity
		pos = new BlockPos(0, 0, 0);
		side = Direction.SOUTH;
	}
	public GPSLocation(BlockPos epos, Direction eside) { //identity
		pos = epos;
		side = eside;
	}
	public GPSLocation(BlockPos epos, Direction eside, Level edim) { //identity
		pos = epos;
		side = eside;
		dimension = edim.dimension();
	}
	public GPSLocation(BlockPos epos, Direction eside, ResourceKey<Level> edim) { //identity
		pos = epos;
		side = eside;
		dimension = edim;
	}
}