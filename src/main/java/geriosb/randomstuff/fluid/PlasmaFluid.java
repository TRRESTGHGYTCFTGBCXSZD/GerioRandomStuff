
package geriosb.randomstuff.fluid;

import net.minecraftforge.fluids.ForgeFlowingFluid;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.LiquidBlock;

import geriosb.randomstuff.GerioFluids;
import geriosb.randomstuff.GerioBlocks;

public abstract class PlasmaFluid extends ForgeFlowingFluid {
	public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> GerioFluids.PLASMA_TYPE.get(), () -> GerioFluids.PLASMA.get(),
			() -> GerioFluids.FLOWING_PLASMA.get()).explosionResistance(100f).block(() -> (LiquidBlock) GerioBlocks.PLASMA.get());

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
