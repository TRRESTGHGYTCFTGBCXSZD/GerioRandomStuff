package geriosb.randomstuff;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

import geriosb.randomstuff.block.YoncrusherBlock;
import geriosb.randomstuff.block.UnbreakableBlockBlock;
import geriosb.randomstuff.block.TelekinesisSetterBlock;
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
import geriosb.randomstuff.block.SuperSlateBlock;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;

import geriosb.randomstuff.block.entity.YoncrusherBlockEntity;
import geriosb.randomstuff.block.entity.TelekinesisSetterBlockEntity;
import geriosb.randomstuff.block.entity.SilkerBlockEntity;
import geriosb.randomstuff.block.entity.RemoteStorageBlockEntity;
import geriosb.randomstuff.block.entity.MegaStorageBlockEntity;
import geriosb.randomstuff.integrations.MegaStorageAE2BlockEntity;
import geriosb.randomstuff.block.entity.LiquidDuplicatorBlockBlockEntity;
import geriosb.randomstuff.block.entity.CongealedDuplicatorBlockEntity;
import geriosb.randomstuff.block.entity.SuperSlateBlockEntity;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.*;

import geriosb.randomstuff.*;

import net.minecraftforge.fml.ModList;

public class GerioBlocks {
	public static final DeferredRegister<Block> REGISTERBLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, GeriorandomstuffMod.MODID);
	public static final RegistryObject<Block> DEBUG_STEEL_BLOCK = REGISTERBLOCK.register("debug_steel_block", () -> new DebugSteelBlockBlock());
	public static final RegistryObject<Block> TELEKINESIS_SETTER = REGISTERBLOCK.register("telekinesis_setter", () -> new TelekinesisSetterBlock());
	public static final RegistryObject<Block> DUPLICATOR = REGISTERBLOCK.register("duplicator", () -> new DuplicatorBlock());
	public static final RegistryObject<Block> CONGEALED_DUPLICATOR = REGISTERBLOCK.register("congealed_duplicator", () -> new CongealedDuplicatorBlock());
	public static final RegistryObject<Block> SILKER = REGISTERBLOCK.register("silker", () -> new SilkerBlock());
	public static final RegistryObject<Block> YONCRUSHER = REGISTERBLOCK.register("yoncrusher", () -> new YoncrusherBlock());
	public static final RegistryObject<Block> GUSSUNHEAD = REGISTERBLOCK.register("gussunhead", () -> new GussunheadBlock());
	public static final RegistryObject<Block> UNBREAKABLE_BLOCK = REGISTERBLOCK.register("unbreakable_block", () -> new UnbreakableBlockBlock());
	public static final RegistryObject<Block> LIQUID_DUPLICATOR_BLOCK = REGISTERBLOCK.register("liquid_duplicator_block", () -> new LiquidDuplicatorBlockBlock());
	public static final RegistryObject<Block> INSTANT_ITEM_PIPE = REGISTERBLOCK.register("instant_item_pipe", () -> new InstantItemPipeBlock());
	public static final RegistryObject<Block> INSTANT_LIQUID_PIPE = REGISTERBLOCK.register("instant_liquid_pipe", () -> new InstantLiquidPipeBlock());
	public static final RegistryObject<Block> PLASMA = REGISTERBLOCK.register("plasma", () -> new PlasmaBlock());
	public static final RegistryObject<Block> REMOTE_STORAGE = REGISTERBLOCK.register("remote_storage", () -> new RemoteStorageBlock());
	public static final RegistryObject<Block> INSTANT_GAS_PIPE = REGISTERBLOCK.register("instant_gas_pipe", () -> new InstantGasPipeBlock());
	public static final RegistryObject<Block> MEGA_STORAGE = REGISTERBLOCK.register("mega_storage", () -> new MegaStorageBlock());
	public static final RegistryObject<Block> GERIO_BLOCK = REGISTERBLOCK.register("gerio_block", () -> new GerioBlockBlock());
	public static final RegistryObject<Block> SUPER_SLATE = REGISTERBLOCK.register("super_slate", () -> new SuperSlateBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_TILES).strength(4f, 4f).pushReaction(PushReaction.DESTROY)));

	public static final DeferredRegister<BlockEntityType<?>> REGISTERTILEENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, GeriorandomstuffMod.MODID);
	public static final RegistryObject<BlockEntityType<?>> TELEKINESIS_SETTER_ENTITY = register("telekinesis_setter", TELEKINESIS_SETTER, TelekinesisSetterBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> CONGEALED_DUPLICATOR_ENTITY = register("congealed_duplicator", CONGEALED_DUPLICATOR, CongealedDuplicatorBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> SILKER_ENTITY = register("silker", SILKER, SilkerBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> YONCRUSHER_ENTITY = register("yoncrusher", YONCRUSHER, YoncrusherBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> LIQUID_DUPLICATOR_BLOCK_ENTITY = register("liquid_duplicator_block", LIQUID_DUPLICATOR_BLOCK, LiquidDuplicatorBlockBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> REMOTE_STORAGE_ENTITY = register("remote_storage", REMOTE_STORAGE, RemoteStorageBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> MEGA_STORAGE_ENTITY = register("mega_storage", MEGA_STORAGE, ModList.get().isLoaded("ae2") ? MegaStorageAE2BlockEntity::new : MegaStorageBlockEntity::new );
	public static final RegistryObject<BlockEntityType<?>> SUPER_SLATE_ENTITY = register("super_slate", SUPER_SLATE, SuperSlateBlockEntity::new);

	private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTERTILEENTITY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
}
