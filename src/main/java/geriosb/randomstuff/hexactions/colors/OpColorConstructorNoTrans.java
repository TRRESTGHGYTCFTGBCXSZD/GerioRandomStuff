package geriosb.randomstuff.hexactions.colors;

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.sideeffects.OperatorSideEffect;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota;
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs;
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughMedia;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import geriosb.randomstuff.hexactions.ColorIota;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class OpColorConstructorNoTrans implements ConstMediaAction { //why kotlin why
    public static final OpColorConstructorNoTrans INSTANCE;

    static {
        OpColorConstructorNoTrans var0 = new OpColorConstructorNoTrans();
        INSTANCE = var0;
    }

    @Override
    public int getArgc() {
        return 3;
    }

    @Override
    public long getMediaCost() {
        return 0;
    }

    @Override
    public @NotNull List<Iota> execute(@NotNull List<? extends Iota> list, @NotNull CastingEnvironment castingEnvironment) throws Mishap {
        Iota args2 = list.get(0);
        Iota args3 = list.get(1);
        Iota args4 = list.get(2);
            if (args2 instanceof DoubleIota Colorr) {
                if (args3 instanceof DoubleIota Colorg) {
                    if (args4 instanceof DoubleIota Colorb) {
                        List<Iota> FUCK = new ArrayList<>();
                        int alpha = 0xff;
                        int red = (int) Colorr.getDouble() & 0xff;
                        int green = (int) Colorg.getDouble() & 0xff;
                        int blue = (int) Colorb.getDouble() & 0xff;
                        FUCK.add(new ColorIota(alpha<<24|red<<16|green<<8|blue));
                        return FUCK;
                    } else {
                        throw new MishapInvalidIota(args4,3,Component.translatable("number"));
                    }
                } else {
                    throw new MishapInvalidIota(args3,2,Component.translatable("number"));
                }
            } else {
                throw new MishapInvalidIota(args2,1,Component.translatable("number"));
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
        return new OperationResult(image2, sideEffects, continuation, HexEvalSounds.NORMAL_EXECUTE);
    } // Java, what are you doing about kotlin's open interfaces that don't even need to be overridden!?

    @Override
    public CostMediaActionResult executeWithOpCount(List<? extends Iota> args, CastingEnvironment env) throws Mishap {
        List<Iota> stack = this.execute(args, env);
        return new CostMediaActionResult(stack,1L);
    }
}

