package geriosb.randomstuff.hexactions;

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.sideeffects.OperatorSideEffect;
import at.petrak.hexcasting.api.casting.eval.vm.*;
import at.petrak.hexcasting.api.casting.iota.*;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.api.casting.mishaps.*;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class OpContinuationDecoder implements ConstMediaAction { //why kotlin why
    public static final OpContinuationDecoder INSTANCE;

    static {
        OpContinuationDecoder var0 = new OpContinuationDecoder();
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
        if (args1 instanceof ContinuationIota listter) {
            var continuation = listter.getContinuation();
            List<Iota> FUCK = new ArrayList<>();
            while (continuation instanceof SpellContinuation.NotDone notDone) {
                var da = notDone.component1();
                if (da instanceof FrameEvaluate re) {
                    FUCK.add(new ListIota(re.getList()));
                } else if (da instanceof FrameForEach re) {
                    List<Iota> stanky = new ArrayList<Iota>(3);
                    if (re.getBaseStack() != null) {
                        stanky.addAll(re.getBaseStack());
                    }
                    stanky.add(new ListIota(re.getCode()));
                    stanky.add(new ListIota(re.getData()));
                    FUCK.add(new ListIota(stanky));
                } else if (da instanceof FrameFinishEval re) {
                    // safety, to prevent accidental mishaps
                } else {
                    throw new MishapUnsupportedAction(Component.translatable("gui.geriorandomstuff.hex.unsupportedcontinuation"));
                }
                continuation = notDone.component2();
            }
            return FUCK;
        } else {
            throw new MishapInvalidIota(args1,0,ContinuationIota.DISPLAY);
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

