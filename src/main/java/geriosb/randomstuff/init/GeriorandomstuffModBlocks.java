
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

import geriosb.randomstuff.block.YoncrusherBlock;
import geriosb.randomstuff.block.UnbreakableBlockBlock;
import geriosb.randomstuff.block.TelekinesisSetterBlock;
import geriosb.randomstuff.block.SuperSlateBlock;
import geriosb.randomstuff.block.SilkerBlock;
import geriosb.randomstuff.block.RemoteStorageBlock;
import geriosb.randomstuff.block.PlasmaBlock;
import geriosb.randomstuff.block.MegaStorageBlock;
import geriosb.randomstuff.block.LiquidDuplicatorBlockBlock;
import geriosb.randomstuff.block.InstantLiquidPipeBlock;
import geriosb.randomstuff.block.InstantItemPipeBlock;
import geriosb.randomstuff.block.InstantGasPipeBlock;
import geriosb.randomstuff.block.GussunheadBlock;
import geriosb.randomstuff.block.GerioBlockBlock;
import geriosb.randomstuff.block.DuplicatorBlock;
import geriosb.randomstuff.block.DebugSteelBlockBlock;
import geriosb.randomstuff.block.CongealedDuplicatorBlock;
import geriosb.randomstuff.GeriorandomstuffMod;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.*;

import geriosb.randomstuff.*;

public class GeriorandomstuffModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, GeriorandomstuffMod.MODID);
	public static final RegistryObject<Block> DEBUG_STEEL_BLOCK = REGISTRY.register("debug_steel_block", () -> new DebugSteelBlockBlock());
	public static final RegistryObject<Block> TELEKINESIS_SETTER = REGISTRY.register("telekinesis_setter", () -> new TelekinesisSetterBlock());
	public static final RegistryObject<Block> DUPLICATOR = REGISTRY.register("duplicator", () -> new DuplicatorBlock());
	public static final RegistryObject<Block> CONGEALED_DUPLICATOR = REGISTRY.register("congealed_duplicator", () -> new CongealedDuplicatorBlock());
	public static final RegistryObject<Block> SILKER = REGISTRY.register("silker", () -> new SilkerBlock());
	public static final RegistryObject<Block> YONCRUSHER = REGISTRY.register("yoncrusher", () -> new YoncrusherBlock());
	public static final RegistryObject<Block> GUSSUNHEAD = REGISTRY.register("gussunhead", () -> new GussunheadBlock());
	public static final RegistryObject<Block> UNBREAKABLE_BLOCK = REGISTRY.register("unbreakable_block", () -> new UnbreakableBlockBlock());
	public static final RegistryObject<Block> LIQUID_DUPLICATOR_BLOCK = REGISTRY.register("liquid_duplicator_block", () -> new LiquidDuplicatorBlockBlock());
	public static final RegistryObject<Block> INSTANT_ITEM_PIPE = REGISTRY.register("instant_item_pipe", () -> new InstantItemPipeBlock());
	public static final RegistryObject<Block> INSTANT_LIQUID_PIPE = REGISTRY.register("instant_liquid_pipe", () -> new InstantLiquidPipeBlock());
	public static final RegistryObject<Block> PLASMA = REGISTRY.register("plasma", () -> new PlasmaBlock());
	public static final RegistryObject<Block> REMOTE_STORAGE = REGISTRY.register("remote_storage", () -> new RemoteStorageBlock());
	public static final RegistryObject<Block> INSTANT_GAS_PIPE = REGISTRY.register("instant_gas_pipe", () -> new InstantGasPipeBlock());
	public static final RegistryObject<Block> MEGA_STORAGE = REGISTRY.register("mega_storage", () -> new MegaStorageBlock());
	public static final RegistryObject<Block> GERIO_BLOCK = REGISTRY.register("gerio_block", () -> new GerioBlockBlock());
	public static final RegistryObject<Block> SUPER_SLATE = REGISTRY.register("super_slate", () -> new SuperSlateBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_TILES).strength(4f, 4f).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> REMOTE_SLATE = REGISTRY.register("remote_slate", () -> new RemoteStorageBlock());

}
