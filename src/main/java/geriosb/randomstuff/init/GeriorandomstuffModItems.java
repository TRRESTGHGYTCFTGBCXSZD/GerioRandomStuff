
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import geriosb.randomstuff.item.WorstPickaxeItem;
import geriosb.randomstuff.item.WorstPickaxeBrokenItem;
import geriosb.randomstuff.item.SuperheatedDuplicatorItem;
import geriosb.randomstuff.item.SuperToolRodItem;
import geriosb.randomstuff.item.SuperToolHeadItem;
import geriosb.randomstuff.item.SAMBADEJANEIROItem;
import geriosb.randomstuff.item.PasteItem;
import geriosb.randomstuff.item.InstantPipeBaseItem;
import geriosb.randomstuff.item.InstantPipeBaseBundleItem;
import geriosb.randomstuff.item.DuplicatorItem;
import geriosb.randomstuff.item.DebugSteelItem;
import geriosb.randomstuff.item.DebugSoloItem;
import geriosb.randomstuff.item.CongealedDuplicatorDiv9Item;
import geriosb.randomstuff.item.GpsDeviceItem;
import geriosb.randomstuff.item.SuperSlateItem;
import geriosb.randomstuff.GeriorandomstuffMod;
import geriosb.randomstuff.GerioPipeItem;
import geriosb.randomstuff.DaegariBlockItem;

public class GeriorandomstuffModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, GeriorandomstuffMod.MODID);
	public static final RegistryObject<Item> WORST_PICKAXE = REGISTRY.register("worst_pickaxe", () -> new WorstPickaxeItem());
	public static final RegistryObject<Item> WORST_PICKAXE_BROKEN = REGISTRY.register("worst_pickaxe_broken", () -> new WorstPickaxeBrokenItem());
	public static final RegistryObject<Item> DEBUG_STEEL = REGISTRY.register("debug_steel", () -> new DebugSteelItem());
	public static final RegistryObject<Item> SAMBADEJANEIRO = REGISTRY.register("sambadejaneiro", () -> new SAMBADEJANEIROItem());
	public static final RegistryObject<Item> DEBUG_STEEL_BLOCK = block(GeriorandomstuffModBlocks.DEBUG_STEEL_BLOCK);
	public static final RegistryObject<Item> SUPER_TOOL_HEAD = REGISTRY.register("super_tool_head", () -> new SuperToolHeadItem());
	public static final RegistryObject<Item> SUPER_TOOL_ROD = REGISTRY.register("super_tool_rod", () -> new SuperToolRodItem());
	public static final RegistryObject<Item> DEBUG_SOLO = REGISTRY.register("debug_solo", () -> new DebugSoloItem());
	public static final RegistryObject<Item> TELEKINESIS_SETTER = block(GeriorandomstuffModBlocks.TELEKINESIS_SETTER);
	public static final RegistryObject<Item> DUPLICATOR_BUCKET = REGISTRY.register("duplicator_bucket", () -> new DuplicatorItem());
	public static final RegistryObject<Item> CONGEALED_DUPLICATOR = block(GeriorandomstuffModBlocks.CONGEALED_DUPLICATOR);
	public static final RegistryObject<Item> CONGEALED_DUPLICATOR_DIV_9 = REGISTRY.register("congealed_duplicator_div_9", () -> new CongealedDuplicatorDiv9Item());
	public static final RegistryObject<Item> SILKER = block(GeriorandomstuffModBlocks.SILKER);
	public static final RegistryObject<Item> PASTE = REGISTRY.register("paste", () -> new PasteItem());
	public static final RegistryObject<Item> YONCRUSHER = block(GeriorandomstuffModBlocks.YONCRUSHER);
	public static final RegistryObject<Item> GUSSUNHEAD = block(GeriorandomstuffModBlocks.GUSSUNHEAD);
	public static final RegistryObject<Item> UNBREAKABLE_BLOCK = block(GeriorandomstuffModBlocks.UNBREAKABLE_BLOCK);
	public static final RegistryObject<Item> LIQUID_DUPLICATOR_BLOCK = block(GeriorandomstuffModBlocks.LIQUID_DUPLICATOR_BLOCK);
	public static final RegistryObject<Item> SUPERHEATED_DUPLICATOR = REGISTRY.register("superheated_duplicator", () -> new SuperheatedDuplicatorItem());
	public static final RegistryObject<Item> INSTANT_PIPE_BASE = REGISTRY.register("instant_pipe_base", () -> new InstantPipeBaseItem());
	public static final RegistryObject<Item> INSTANT_PIPE_BASE_BUNDLE = REGISTRY.register("instant_pipe_base_bundle", () -> new InstantPipeBaseBundleItem());
	public static final RegistryObject<Item> INSTANT_ITEM_PIPE = geriopipes(GeriorandomstuffModBlocks.INSTANT_ITEM_PIPE);
	public static final RegistryObject<Item> INSTANT_LIQUID_PIPE = geriopipes(GeriorandomstuffModBlocks.INSTANT_LIQUID_PIPE);
	public static final RegistryObject<Item> REMOTE_STORAGE = block(GeriorandomstuffModBlocks.REMOTE_STORAGE);
	public static final RegistryObject<Item> GPS_DEVICE = REGISTRY.register("gps_device", () -> new GpsDeviceItem());
	public static final RegistryObject<Item> INSTANT_GAS_PIPE = geriopipes(GeriorandomstuffModBlocks.INSTANT_GAS_PIPE);
	public static final RegistryObject<Item> MEGA_STORAGE = block(GeriorandomstuffModBlocks.MEGA_STORAGE);
	public static final RegistryObject<Item> GERIO_BLOCK = daegari(GeriorandomstuffModBlocks.GERIO_BLOCK);
	public static final RegistryObject<Item> SUPER_SLATE = REGISTRY.register(GeriorandomstuffModBlocks.SUPER_SLATE.getId().getPath(), () -> new SuperSlateItem(GeriorandomstuffModBlocks.SUPER_SLATE.get(), new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> REMOTE_SLATE = block(GeriorandomstuffModBlocks.REMOTE_SLATE);

	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}

	private static RegistryObject<Item> geriopipes(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new GerioPipeItem(block.get(), new Item.Properties()));
	}

	private static RegistryObject<Item> daegari(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new DaegariBlockItem(block.get(), new Item.Properties()));
	}
}
