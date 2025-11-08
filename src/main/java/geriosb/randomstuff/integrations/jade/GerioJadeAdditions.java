package geriosb.randomstuff.integrations.jade;

import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

import geriosb.randomstuff.block.MegaStorageBlock;
import geriosb.randomstuff.integrations.jade.blocks.MegaStorageProvider;

import geriosb.randomstuff.block.entity.MegaStorageBlockEntity;
import geriosb.randomstuff.integrations.MegaStorageAE2BlockEntity;

import geriosb.randomstuff.GeriorandomstuffMod;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.fml.ModList;

@WailaPlugin
public class GerioJadeAdditions implements IWailaPlugin {
	public static final ResourceLocation MEGASTORAGE = GeriorandomstuffMod.rl("megastoragejade");

	@Override
	public void register(IWailaCommonRegistration registration) {
		registration.registerBlockDataProvider(MegaStorageProvider.INSTANCE, MegaStorageBlockEntity.class);
		if (ModList.get().isLoaded("ae2")) {
			registration.registerBlockDataProvider(MegaStorageProvider.INSTANCE, MegaStorageAE2BlockEntity.class);
		}
	}
	
	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerBlockComponent(MegaStorageProvider.INSTANCE, MegaStorageBlock.class);
	}
}