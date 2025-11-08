package geriosb.randomstuff.block.entity;

import at.petrak.hexcasting.api.block.HexBlockEntity;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.HexBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SuperSlateBlockEntity extends HexBlockEntity {
    public static final String TAG_IOTA = "iota";

    @Nullable
    private CompoundTag iotaTag = null;

    public SuperSlateBlockEntity(BlockPos pos, BlockState state) {
        super(HexBlockEntities.SLATE_TILE, pos, state);
    }

    @Nullable
    public CompoundTag getIotaTag() {
        return iotaTag;
    }


    @Override
    protected void saveModData(CompoundTag compoundTag) {
        if (this.iotaTag != null) {
            compoundTag.put(TAG_IOTA, this.iotaTag);
        }
    }

    @Override
    protected void loadModData(CompoundTag tag) {
        if (tag.contains(TAG_IOTA)) {
            this.iotaTag = tag.getCompound(TAG_IOTA);
        } else {
            this.iotaTag = null;
        }
    }

}