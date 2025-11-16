package geriosb.randomstuff.hexactions;

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.sideeffects.OperatorSideEffect;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.*;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.mishaps.*;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import at.petrak.hexcasting.api.casting.OperatorUtils;

import java.util.ArrayList;
import java.util.List;

import at.petrak.hexcasting.api.casting.math.HexPattern;

public final class OpColorTransparencyStripper implements ConstMediaAction { //why kotlin why
    public static final OpColorTransparencyStripper INSTANCE;

    static {
        OpColorTransparencyStripper var0 = new OpColorTransparencyStripper();
        INSTANCE = var0;
    }

    @Override
    public int getArgc() {
        return 1;
    }

    @Override
    public long getMediaCost() {
        return 0;
    }

    @Override
    public @NotNull List<Iota> execute(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment) throws Mishap {
        Iota args1 = list.get(0);
        if (args1 instanceof ColorIota Colore) {
            List<Iota> FUCK = new ArrayList<>();
            FUCK.add(new ColorIota(Colore.getColor()|0xff000000));
            return FUCK;
        } else {
            throw new MishapInvalidIota(args1,0,Component.translatable("color"));
        }
    }

    @Override
    public OperationResult operate(CastingEnvironment env, CastingImage image, SpellContinuation continuation) throws Mishap {
        List<Iota> stack = new ArrayList<>(image.getStack());

        if (this.getArgc() > stack.size()) {
            throw new MishapNotEnoughArgs(this.getArgc(), stack.size());
        }

        List<Iota> args = stack.subList(stack.size() - this.getArgc(), stack.size());
        List<Iota> argsCopy = new ArrayList<>(args);
        for (int i = 0; i < this.getArgc(); i++) {
            stack.remove(stack.size() - 1);
        }

        CostMediaActionResult result = this.executeWithOpCount(argsCopy, env);
        stack.addAll(result.getResultStack());

        if (env.extractMedia(this.getMediaCost(), true) > 0) {
            throw new MishapNotEnoughMedia(this.getMediaCost());
        }

        List<OperatorSideEffect> sideEffects = new ArrayList<>();
        sideEffects.add(new OperatorSideEffect.ConsumeMedia(this.getMediaCost()));

        CastingImage image2 = image.copy(stack, 0, new ArrayList<CastingImage.ParenthesizedIota>(), false, (int) (image.getOpsConsumed() + result.getOpCount()), new CompoundTag()); // JAVA, CAN YOU STOP SUPPLEMENTING OPTIONAL ARGUMENTS!?!?
        return new OperationResult(image2, sideEffects, continuation, HexEvalSounds.HERMES);
    } // Java, what are you doing about kotlin's open interfaces that don't even need to be overridden!?

    @Override
    public CostMediaActionResult executeWithOpCount(List<? extends Iota> args, CastingEnvironment env) throws Mishap {
        List<Iota> stack = this.execute(args, env);
        return new CostMediaActionResult(stack,1L);
    }
}

