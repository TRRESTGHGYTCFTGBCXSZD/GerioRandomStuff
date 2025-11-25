
/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fluids.FluidType;

import geriosb.randomstuff.common.fluid.types.PlasmaFluidType;
import geriosb.randomstuff.common.fluid.types.DuplicatorFluidType;
import geriosb.randomstuff.GeriorandomstuffMod;

public class GeriorandomstuffModFluidTypes {
	public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, GeriorandomstuffMod.MODID);
	public static final RegistryObject<FluidType> DUPLICATOR_TYPE = REGISTRY.register("duplicator", () -> new DuplicatorFluidType());
	public static final RegistryObject<FluidType> PLASMA_TYPE = REGISTRY.register("plasma", () -> new PlasmaFluidType());
}
