
/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;

import geriosb.randomstuff.fluid.PlasmaFluid;
import geriosb.randomstuff.fluid.DuplicatorFluid;
import geriosb.randomstuff.GeriorandomstuffMod;

public class GeriorandomstuffModFluids {
	public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(ForgeRegistries.FLUIDS, GeriorandomstuffMod.MODID);
	public static final RegistryObject<FlowingFluid> DUPLICATOR = REGISTRY.register("duplicator", () -> new DuplicatorFluid.Source());
	public static final RegistryObject<FlowingFluid> FLOWING_DUPLICATOR = REGISTRY.register("flowing_duplicator", () -> new DuplicatorFluid.Flowing());
	public static final RegistryObject<FlowingFluid> PLASMA = REGISTRY.register("plasma", () -> new PlasmaFluid.Source());
	public static final RegistryObject<FlowingFluid> FLOWING_PLASMA = REGISTRY.register("flowing_plasma", () -> new PlasmaFluid.Flowing());

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientSideHandler {
		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			ItemBlockRenderTypes.setRenderLayer(DUPLICATOR.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FLOWING_DUPLICATOR.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(PLASMA.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FLOWING_PLASMA.get(), RenderType.translucent());
		}
	}
}
