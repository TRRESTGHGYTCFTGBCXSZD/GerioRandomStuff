package geriosb.randomstuff.integrations;

import geriosb.randomstuff.common.blocks.cheaters.MegaStorageBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;

public class MegaStorageSafetyLoader {
    public static NoAE2 retrieve(){
        if (ModList.get().isLoaded("ae2")) {
            return new NoAE2.HasAE2();
        } else {
            return new NoAE2();
        }
    }
    public static class NoAE2 {
        public BlockEntity retrieve(BlockPos position, BlockState state){
            return new MegaStorageBlockEntity(position, state);
        }
        public static class HasAE2 extends NoAE2 {
            @Override
            public BlockEntity retrieve(BlockPos position, BlockState state){
                return new MegaStorageAE2BlockEntity(position, state);
            }
        }
    }
}
