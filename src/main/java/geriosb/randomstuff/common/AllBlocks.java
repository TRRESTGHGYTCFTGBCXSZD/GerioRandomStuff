
package geriosb.randomstuff.common;

import geriosb.randomstuff.GerioMod;
import geriosb.randomstuff.common.blocks.pipes.InstantItemPipeBlock;
import geriosb.randomstuff.common.items.GerioPipeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

//just to say, we are fucked up.

public class AllBlocks {
    public static final DeferredRegister<Block> REGISTERBLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, GerioMod.MODID);

    public static void registerBlocks(IEventBus eventBus) {
        REGISTERBLOCKS.register(eventBus);
    }

    // blocks
    public static final InstantItemPipeBlock INSTANT_ITEM_PIPE = blockPipeItem("instant_item_pipe", new InstantItemPipeBlock(BlockBehaviour.Properties.of().sound(SoundType.ANVIL).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));
//    public static final InstantItemPipeBlock INSTANT_ITEM_PIPE = blockItem("instant_item_pipe", new InstantItemPipeBlock(BlockBehaviour.Properties.of().sound(SoundType.ANVIL).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));

    private static <T extends Block> T blockNoItem(String name, T block) {
        REGISTERBLOCKS.register(name, () -> block);
        return block;
    }
    private static <T extends Block> T blockItem(String name, T block) {
        return blockItem(name, block, new Item.Properties());
    }
    private static <T extends Block> T blockItem(String name, T block, Item.Properties props) {
        blockNoItem(name, block);
        AllItems.REGISTERITEMS.register(name, () -> new BlockItem(block, props));
        return block;
    }
    private static <T extends Block> T blockPipeItem(String name, T block) {
        return blockPipeItem(name, block, new Item.Properties());
    }
    private static <T extends Block> T blockPipeItem(String name, T block, Item.Properties props) {
        blockNoItem(name, block);
        AllItems.REGISTERITEMS.register(name, () -> new GerioPipeItem(block, props));
        return block;
    }
}