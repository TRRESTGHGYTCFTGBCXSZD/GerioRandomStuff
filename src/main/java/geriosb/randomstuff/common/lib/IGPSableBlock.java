package geriosb.randomstuff.common.lib;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public interface IGPSableBlock {
	
	void SetGPSLocation(GPSLocation loc);
    GPSLocation GetGPSLocation();

    default void saveGPSLocToNBT(CompoundTag tag, GPSLocation loc) {GPSLocation.saveGPSLocToNBT(tag,loc);}
    default GPSLocation loadGPSLocFromNBT(CompoundTag tag){return GPSLocation.loadGPSLocFromNBT(tag);}
}