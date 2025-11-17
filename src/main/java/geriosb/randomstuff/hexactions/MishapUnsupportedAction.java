package geriosb.randomstuff.hexactions;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MishapUnsupportedAction extends Mishap {
    Component nuke = Component.translatable("gui.geriorandomstuff.hex.generic");

    public MishapUnsupportedAction(Component mishapo){
        if (mishapo != null) nuke = mishapo;
    }

    @Override
    public @NotNull FrozenPigment accentColor(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context) {
        return dyeColor(DyeColor.WHITE);
    }

    @Override
    public @NotNull ResolvedPatternType resolutionType(@NotNull CastingEnvironment ctx) {
        return ResolvedPatternType.UNRESOLVED;
    }

    @Override
    public void execute(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context, @NotNull List<Iota> list) {

    }

    @Override
    protected @Nullable Component errorMessage(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context) {
        return nuke;
    }
}
