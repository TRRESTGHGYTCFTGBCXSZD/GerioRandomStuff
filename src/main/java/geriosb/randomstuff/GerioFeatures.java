
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.levelgen.feature.Feature;

import geriosb.randomstuff.world.features.DuplicatorFeatureFeature;

@Mod.EventBusSubscriber
public class GerioFeatures {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, GeriorandomstuffMod.MODID);
	//public static final RegistryObject<Feature<?>> DUPLICATOR_FEATURE = REGISTRY.register("duplicator_feature", DuplicatorFeatureFeature::new);
}
