
/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;

import geriosb.randomstuff.fluid.PlasmaFluid;
import geriosb.randomstuff.fluid.DuplicatorFluid;
import geriosb.randomstuff.fluid.types.PlasmaFluidType;
import geriosb.randomstuff.fluid.types.DuplicatorFluidType;

public class GerioFluids {
	public static final DeferredRegister<Fluid> REGISTERFLUID = DeferredRegister.create(ForgeRegistries.FLUIDS, GeriorandomstuffMod.MODID);
	public static final RegistryObject<FlowingFluid> DUPLICATOR = REGISTERFLUID.register("duplicator", () -> new DuplicatorFluid.Source());
	public static final RegistryObject<FlowingFluid> FLOWING_DUPLICATOR = REGISTERFLUID.register("flowing_duplicator", () -> new DuplicatorFluid.Flowing());
	public static final RegistryObject<FlowingFluid> PLASMA = REGISTERFLUID.register("plasma", () -> new PlasmaFluid.Source());
	public static final RegistryObject<FlowingFluid> FLOWING_PLASMA = REGISTERFLUID.register("flowing_plasma", () -> new PlasmaFluid.Flowing());
	
	public static final DeferredRegister<FluidType> REGISTERTYPE = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, GeriorandomstuffMod.MODID);
	public static final RegistryObject<FluidType> DUPLICATOR_TYPE = REGISTERTYPE.register("duplicator", () -> new DuplicatorFluidType());
	public static final RegistryObject<FluidType> PLASMA_TYPE = REGISTERTYPE.register("plasma", () -> new PlasmaFluidType());

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientSideHandler {
		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			ItemBlockRenderTypes.setRenderLayer(DUPLICATOR.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FLOWING_DUPLICATOR.get(), RenderType.translucent());
		}
	}
}
