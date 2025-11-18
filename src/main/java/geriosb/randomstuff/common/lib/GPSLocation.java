package geriosb.randomstuff.common.lib;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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

    static void saveGPSLocToNBT(CompoundTag tag, GPSLocation loc){
        if (loc == null) return; // no information means do not save
        CompoundTag data = new CompoundTag();
        CompoundTag positron = new CompoundTag();
        positron.putInt("x",loc.pos.getX());
        positron.putInt("y",loc.pos.getY());
        positron.putInt("z",loc.pos.getZ());
        data.put("pos",positron);
        data.putByte("dir", (byte) loc.side.get3DDataValue());
        data.putString("dimension", loc.dimension.location().toString());
        tag.put("gpsableblock",data);
    }
    static GPSLocation loadGPSLocFromNBT(CompoundTag tag){
        if (!tag.contains("gpsableblock")) return null;
        CompoundTag roni = tag.getCompound("gpsableblock");
        CompoundTag loca = roni.getCompound("pos");
        return new GPSLocation(new BlockPos(loca.getInt("x"), loca.getInt("y"), loca.getInt("z")),
                Direction.from3DDataValue(roni.getByte("dir")),
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(roni.getString("dimension"))));
    }
}