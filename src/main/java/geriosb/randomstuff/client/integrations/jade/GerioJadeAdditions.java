package geriosb.randomstuff.client.integrations.jade;

import geriosb.randomstuff.client.integrations.jade.blocks.GPSableProvider;
import net.minecraft.world.level.block.Block;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

import geriosb.randomstuff.common.blocks.cheaters.MegaStorageBlock;
import geriosb.randomstuff.common.blocks.cheaters.MegaStorageBlockEntity;
import geriosb.randomstuff.common.blocks.integrations.MegaStorageAE2BlockEntity;
import geriosb.randomstuff.client.integrations.jade.blocks.MegaStorageProvider;

import geriosb.randomstuff.common.blocks.integrations.SuperSlateBlock;
import geriosb.randomstuff.common.blocks.integrations.SuperSlateBlockEntity;
import geriosb.randomstuff.client.integrations.jade.blocks.SuperSlateProvider;

import geriosb.randomstuff.GeriorandomstuffMod;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.fml.ModList;

@WailaPlugin
public class GerioJadeAdditions implements IWailaPlugin {
	public static final ResourceLocation MEGASTORAGE = GeriorandomstuffMod.rl("megastoragejade");
	public static final ResourceLocation SUPER_SLATE = GeriorandomstuffMod.rl("superslatejade");

	@Override
	public void register(IWailaCommonRegistration registration) {
		registration.registerBlockDataProvider(MegaStorageProvider.INSTANCE, MegaStorageBlockEntity.class);
		if (ModList.get().isLoaded("ae2")) {
			registration.registerBlockDataProvider(MegaStorageProvider.INSTANCE, MegaStorageAE2BlockEntity.class);
		}
		if (ModList.get().isLoaded("hexcasting")) {
			registration.registerBlockDataProvider(SuperSlateProvider.INSTANCE, SuperSlateBlockEntity.class);
		}
	}
	
	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerBlockComponent(MegaStorageProvider.INSTANCE, MegaStorageBlock.class);
		if (ModList.get().isLoaded("hexcasting")) {
			registration.registerBlockComponent(SuperSlateProvider.INSTANCE, SuperSlateBlock.class);
		}
        registration.registerBlockComponent(GPSableProvider.INSTANCE, Block.class);
	}
}