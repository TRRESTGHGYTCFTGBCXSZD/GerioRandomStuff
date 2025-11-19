
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff.init;

import geriosb.randomstuff.common.blocks.cheaters.*;
import geriosb.randomstuff.integrations.MegaStorageSafetyLoader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;

import geriosb.randomstuff.common.blocks.recipeblock.YoncrusherBlockEntity;
import geriosb.randomstuff.GeriorandomstuffMod;

public class GeriorandomstuffModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, GeriorandomstuffMod.MODID);
	public static final RegistryObject<BlockEntityType<TelekinesisSetterBlockEntity>> TELEKINESIS_SETTER = register("telekinesis_setter", GeriorandomstuffModBlocks.TELEKINESIS_SETTER, TelekinesisSetterBlockEntity::new);
	public static final RegistryObject<BlockEntityType<CongealedDuplicatorBlockEntity>> CONGEALED_DUPLICATOR = register("congealed_duplicator", GeriorandomstuffModBlocks.CONGEALED_DUPLICATOR, CongealedDuplicatorBlockEntity::new);
	public static final RegistryObject<BlockEntityType<SilkerBlockEntity>> SILKER = register("silker", GeriorandomstuffModBlocks.SILKER, SilkerBlockEntity::new);
	public static final RegistryObject<BlockEntityType<YoncrusherBlockEntity>> YONCRUSHER = register("yoncrusher", GeriorandomstuffModBlocks.YONCRUSHER, YoncrusherBlockEntity::new);
	public static final RegistryObject<BlockEntityType<LiquidDuplicatorBlockBlockEntity>> LIQUID_DUPLICATOR_BLOCK = register("liquid_duplicator_block", GeriorandomstuffModBlocks.LIQUID_DUPLICATOR_BLOCK, LiquidDuplicatorBlockBlockEntity::new);
	public static final RegistryObject<BlockEntityType<RemoteStorageBlockEntity>> REMOTE_STORAGE = register("remote_storage", GeriorandomstuffModBlocks.REMOTE_STORAGE, RemoteStorageBlockEntity::new);
    public static final RegistryObject<BlockEntityType<? extends BlockEntity>> MEGA_STORAGE =
            REGISTRY.register("mega_storage",
                    () -> BlockEntityType.Builder.of(
                            (pos, state) -> MegaStorageSafetyLoader.retrieve().retrieve(pos, state),
                            GeriorandomstuffModBlocks.MEGA_STORAGE.get()
                    ).build(null));

    public static final DeferredRegister<BlockEntityType<?>> HEXREGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, GeriorandomstuffMod.MODID);
	public static final RegistryObject<BlockEntityType<SuperSlateBlockEntity>> SUPER_SLATE = hexregister("super_slate", GeriorandomstuffModBlocks.SUPER_SLATE, SuperSlateBlockEntity::new);
    public static final RegistryObject<BlockEntityType<RemoteSlateBlockEntity>> REMOTE_SLATE = hexregister("remote_slate", GeriorandomstuffModBlocks.REMOTE_SLATE, RemoteSlateBlockEntity::new);

    public static final DeferredRegister<BlockEntityType<?>> HEXALREGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, GeriorandomstuffMod.MODID);
    public static final RegistryObject<BlockEntityType<ExposedMoteBlockEntity>> EXPOSED_MOTE = hexalregister("exposed_mote", GeriorandomstuffModBlocks.EXPOSED_MOTE, ExposedMoteBlockEntity::new);

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(
            String registryname,
            RegistryObject<Block> block,
            BlockEntityType.BlockEntitySupplier<T> supplier
    ) {		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> hexregister(
            String registryname,
            RegistryObject<Block> block,
            BlockEntityType.BlockEntitySupplier<T> supplier
    ) {        return HEXREGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
    }
    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> hexalregister(
            String registryname,
            RegistryObject<Block> block,
            BlockEntityType.BlockEntitySupplier<T> supplier
    ) {        return HEXALREGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
    }
}
