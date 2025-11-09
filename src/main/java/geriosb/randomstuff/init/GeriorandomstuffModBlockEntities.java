
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;

import geriosb.randomstuff.block.entity.YoncrusherBlockEntity;
import geriosb.randomstuff.block.entity.TelekinesisSetterBlockEntity;
import geriosb.randomstuff.block.entity.SuperSlateBlockEntity;
import geriosb.randomstuff.block.entity.SilkerBlockEntity;
import geriosb.randomstuff.block.entity.RemoteStorageBlockEntity;
import geriosb.randomstuff.block.entity.MegaStorageBlockEntity;
import geriosb.randomstuff.integrations.MegaStorageAE2BlockEntity;
import geriosb.randomstuff.block.entity.LiquidDuplicatorBlockBlockEntity;
import geriosb.randomstuff.block.entity.CongealedDuplicatorBlockEntity;
import geriosb.randomstuff.GeriorandomstuffMod;

import net.minecraftforge.fml.ModList;

public class GeriorandomstuffModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, GeriorandomstuffMod.MODID);
	public static final RegistryObject<BlockEntityType<?>> TELEKINESIS_SETTER = register("telekinesis_setter", GeriorandomstuffModBlocks.TELEKINESIS_SETTER, TelekinesisSetterBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> CONGEALED_DUPLICATOR = register("congealed_duplicator", GeriorandomstuffModBlocks.CONGEALED_DUPLICATOR, CongealedDuplicatorBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> SILKER = register("silker", GeriorandomstuffModBlocks.SILKER, SilkerBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> YONCRUSHER = register("yoncrusher", GeriorandomstuffModBlocks.YONCRUSHER, YoncrusherBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> LIQUID_DUPLICATOR_BLOCK = register("liquid_duplicator_block", GeriorandomstuffModBlocks.LIQUID_DUPLICATOR_BLOCK, LiquidDuplicatorBlockBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> REMOTE_STORAGE = register("remote_storage", GeriorandomstuffModBlocks.REMOTE_STORAGE, RemoteStorageBlockEntity::new);
	public static final RegistryObject<BlockEntityType<?>> MEGA_STORAGE = register("mega_storage", GeriorandomstuffModBlocks.MEGA_STORAGE, ModList.get().isLoaded("ae2") ? MegaStorageAE2BlockEntity::new : MegaStorageBlockEntity::new );
	public static final RegistryObject<BlockEntityType<?>> SUPER_SLATE = register("super_slate", GeriorandomstuffModBlocks.SUPER_SLATE, SuperSlateBlockEntity::new);

	private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
}
