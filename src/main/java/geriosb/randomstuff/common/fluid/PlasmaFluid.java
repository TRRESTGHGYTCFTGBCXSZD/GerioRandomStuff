
package geriosb.randomstuff.common.fluid;

import net.minecraftforge.fluids.ForgeFlowingFluid;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.LiquidBlock;

import geriosb.randomstuff.init.GeriorandomstuffModFluids;
import geriosb.randomstuff.init.GeriorandomstuffModFluidTypes;
import geriosb.randomstuff.init.GeriorandomstuffModBlocks;

public abstract class PlasmaFluid extends ForgeFlowingFluid {
	public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> GeriorandomstuffModFluidTypes.PLASMA_TYPE.get(), () -> GeriorandomstuffModFluids.PLASMA.get(),
			() -> GeriorandomstuffModFluids.FLOWING_PLASMA.get()).explosionResistance(100f).block(() -> (LiquidBlock) GeriorandomstuffModBlocks.PLASMA.get());

	private PlasmaFluid() {
		super(PROPERTIES);
	}

	public static class Source extends PlasmaFluid {
		public int getAmount(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}

	public static class Flowing extends PlasmaFluid {
		protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}

		public int getAmount(FluidState state) {
			return state.getValue(LEVEL);
		}

		public boolean isSource(FluidState state) {
			return false;
		}
	}
}
