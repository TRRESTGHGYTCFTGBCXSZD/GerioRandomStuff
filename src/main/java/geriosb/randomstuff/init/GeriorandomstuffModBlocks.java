
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff.init;

import geriosb.randomstuff.common.blocks.cheaters.*;
import geriosb.randomstuff.common.blocks.integrations.ExposedMoteBlock;
import geriosb.randomstuff.common.blocks.integrations.SuperSlateBlock;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

import geriosb.randomstuff.common.blocks.recipeblock.YoncrusherBlock;
import geriosb.randomstuff.common.blocks.PlasmaBlock;
import geriosb.randomstuff.common.blocks.pipes.InstantLiquidPipeBlock;
import geriosb.randomstuff.common.blocks.pipes.InstantItemPipeBlock;
import geriosb.randomstuff.common.blocks.pipes.InstantGasPipeBlock;
import geriosb.randomstuff.common.blocks.GussunheadBlock;
import geriosb.randomstuff.common.blocks.GerioBlockBlock;
import geriosb.randomstuff.common.blocks.DuplicatorBlock;
import geriosb.randomstuff.common.blocks.DebugSteelBlockBlock;
import geriosb.randomstuff.GeriorandomstuffMod;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.*;

public class GeriorandomstuffModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, GeriorandomstuffMod.MODID);
	public static final RegistryObject<Block> DEBUG_STEEL_BLOCK = REGISTRY.register("debug_steel_block", DebugSteelBlockBlock::new);
	public static final RegistryObject<Block> TELEKINESIS_SETTER = REGISTRY.register("telekinesis_setter", TelekinesisSetterBlock::new);
	public static final RegistryObject<Block> DUPLICATOR = REGISTRY.register("duplicator", DuplicatorBlock::new);
	public static final RegistryObject<Block> CONGEALED_DUPLICATOR = REGISTRY.register("congealed_duplicator", CongealedDuplicatorBlock::new);
	public static final RegistryObject<Block> SILKER = REGISTRY.register("silker", SilkerBlock::new);
	public static final RegistryObject<Block> YONCRUSHER = REGISTRY.register("yoncrusher", YoncrusherBlock::new);
	public static final RegistryObject<Block> GUSSUNHEAD = REGISTRY.register("gussunhead", GussunheadBlock::new);
	public static final RegistryObject<Block> UNBREAKABLE_BLOCK = REGISTRY.register("unbreakable_block", UnbreakableBlockBlock::new);
	public static final RegistryObject<Block> LIQUID_DUPLICATOR_BLOCK = REGISTRY.register("liquid_duplicator_block", LiquidDuplicatorBlockBlock::new);
	public static final RegistryObject<Block> INSTANT_ITEM_PIPE = REGISTRY.register("instant_item_pipe", InstantItemPipeBlock::new);
	public static final RegistryObject<Block> INSTANT_LIQUID_PIPE = REGISTRY.register("instant_liquid_pipe", InstantLiquidPipeBlock::new);
	public static final RegistryObject<Block> PLASMA = REGISTRY.register("plasma", PlasmaBlock::new);
	public static final RegistryObject<Block> REMOTE_STORAGE = REGISTRY.register("remote_storage", RemoteStorageBlock::new);
	public static final RegistryObject<Block> INSTANT_GAS_PIPE = REGISTRY.register("instant_gas_pipe", InstantGasPipeBlock::new);
	public static final RegistryObject<Block> MEGA_STORAGE = REGISTRY.register("mega_storage", MegaStorageBlock::new);
	public static final RegistryObject<Block> GERIO_BLOCK = REGISTRY.register("gerio_block", GerioBlockBlock::new);

    public static final DeferredRegister<Block> HEXREGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, GeriorandomstuffMod.MODID);
	public static final RegistryObject<Block> SUPER_SLATE = HEXREGISTRY.register("super_slate", () -> new SuperSlateBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_TILES).strength(4f, 4f).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> REMOTE_SLATE = HEXREGISTRY.register("remote_slate", RemoteStorageBlock::new);

    public static final DeferredRegister<Block> HEXALREGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, GeriorandomstuffMod.MODID);
    public static final RegistryObject<Block> EXPOSED_MOTE = HEXALREGISTRY.register("exposed_mote", ExposedMoteBlock::new);

}
