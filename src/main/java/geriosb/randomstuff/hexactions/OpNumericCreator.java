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

public final class OpNumericCreator implements ConstMediaAction { //why kotlin why
    public static final OpNumericCreator INSTANCE;

    static {
        OpNumericCreator var0 = new OpNumericCreator();
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
        if (args1 instanceof ListIota listter) {
            if (listter.size() == 0) {
                throw new MishapInvalidIota(args1,0,Component.translatable("list of numpatterns"));
            }
            String Input = "a";
            boolean IsNumber = false;
            boolean Decimals = false;
            boolean Negative = false;
            boolean ExponentNegative = false;
            boolean WritingExponent = false;
            StringBuilder Mantissa = new StringBuilder();
            StringBuilder Exponent = new StringBuilder();
            int ExponentOffset = 0;
            for (Iota Symbol : listter.getList()) {
                if (Symbol instanceof PatternIota TrueSymbol) {
                    HexPattern TrueTrueSymbol = TrueSymbol.getPattern();
                    if (TrueTrueSymbol.getStartDir() == HexDir.WEST && TrueTrueSymbol.anglesSignature().equals("awqaw")) {
                        IsNumber = true;
                        Input = "0";
                    } else if (TrueTrueSymbol.getStartDir() == HexDir.SOUTH_EAST && TrueTrueSymbol.anglesSignature().equals("w")) {
                        IsNumber = true;
                        Input = "1";
                    } else if (TrueTrueSymbol.getStartDir() == HexDir.EAST && TrueTrueSymbol.anglesSignature().equals("edaq")) {
                        IsNumber = true;
                        Input = "2";
                    } else if (TrueTrueSymbol.getStartDir() == HexDir.EAST && TrueTrueSymbol.anglesSignature().equals("daed")) {
                        IsNumber = true;
                        Input = "3";
                    } else if (TrueTrueSymbol.getStartDir() == HexDir.NORTH_WEST && TrueTrueSymbol.anglesSignature().equals("waa")) {
                        IsNumber = true;
                        Input = "4";
                    } else if (TrueTrueSymbol.getStartDir() == HexDir.WEST && TrueTrueSymbol.anglesSignature().equals("aqed")) {
                        IsNumber = true;
                        Input = "5";
                    } else if (TrueTrueSymbol.getStartDir() == HexDir.WEST && TrueTrueSymbol.anglesSignature().equals("awqaq")) {
                        IsNumber = true;
                        Input = "6";
                    } else if (TrueTrueSymbol.getStartDir() == HexDir.NORTH_WEST && TrueTrueSymbol.anglesSignature().equals("dew")) {
                        IsNumber = true;
                        Input = "7";
                    } else if (TrueTrueSymbol.getStartDir() == HexDir.SOUTH_EAST && TrueTrueSymbol.anglesSignature().equals("qaqede")) {
                        IsNumber = true;
                        Input = "8";
                    } else if (TrueTrueSymbol.getStartDir() == HexDir.EAST && TrueTrueSymbol.anglesSignature().equals("awqaq")) {
                        IsNumber = true;
                        Input = "9";
                    } else if (TrueTrueSymbol.getStartDir() == HexDir.EAST && TrueTrueSymbol.anglesSignature().equals("aqawq")) {
                        IsNumber = false;
                        Input = "E";
                    } else if (TrueTrueSymbol.getStartDir() == HexDir.EAST && TrueTrueSymbol.anglesSignature().equals("")) {
                        IsNumber = false;
                        Input = "-";
                    } else {
                        throw new MishapInvalidIota(Symbol,0,Component.translatable("valid numpattern")); //no matching patterns (don't forget to start and end correctly) or incorrect orientation
                    }
                } else {
                    throw new MishapInvalidIota(Symbol,0,Component.translatable("valid numpattern"));
                }
                if (IsNumber) {
                    if (WritingExponent) {
                        Exponent.append(Input);
                    } else {
                        Mantissa.append(Input);
                    }
                    if (Decimals) {
                        ExponentOffset -= 1;
                    }
                } else if (Input.equals("-")) {
                    if (WritingExponent) {
                        if (Exponent.isEmpty()) {
                            if (ExponentNegative) {
                                throw new MishapDivideByZero(Component.translatable("gui.geriorandomstuff.hex.malform"), Component.translatable("gui.geriorandomstuff.hex.malform"), "malform"); // double minus signs
                            } else {
                                ExponentNegative = true;
                            }
                        } else {
                            throw new MishapDisallowedSpell("geriorandomstuff.decimalexponents"); // disallowed action (exponents can't have decimal points due to technical reasons)
                        }
                    } else {
                        if (Mantissa.isEmpty()) {
                            if (Negative) {
                                throw new MishapDivideByZero(Component.translatable("gui.geriorandomstuff.hex.malform"), Component.translatable("gui.geriorandomstuff.hex.malform"), "malform"); // double minus signs
                            } else {
                                Negative = true;
                            }
                        } else {
                            if (Decimals) {
                                throw new MishapDivideByZero(Component.translatable("gui.geriorandomstuff.hex.malform"), Component.translatable("gui.geriorandomstuff.hex.malform"), "malform"); // double decimal points
                            } else {
                                Decimals = true;
                            }
                        }
                    }
                } else if (Input.equals("E")) {
                    if (Mantissa.isEmpty()) {
                        throw new MishapDivideByZero(Component.translatable("gui.geriorandomstuff.hex.malform"), Component.translatable("gui.geriorandomstuff.hex.malform"), "malform"); // trying to write exponent before mantissa
                    }
                    if (WritingExponent) {
                        throw new MishapDivideByZero(Component.translatable("gui.geriorandomstuff.hex.malform"), Component.translatable("gui.geriorandomstuff.hex.malform"), "malform"); // 2 exponents
                    } else {
                        WritingExponent = true;
                        Decimals = false;
                    }
                }
            }
            double OutIota;
            if (Negative) {
                OutIota = Double.parseDouble("-" + new String(Mantissa));
            } else {
                OutIota = Double.parseDouble(new String(Mantissa));
            }
            double ExponentIncate = 0;
            if (!Exponent.isEmpty()) {
                if (ExponentNegative) {
                    ExponentIncate = Double.parseDouble("-" + new String(Exponent));
                } else {
                    ExponentIncate = Double.parseDouble(new String(Exponent));
                }
            }
            ExponentIncate = Math.pow(10, ExponentIncate+ExponentOffset);
            OutIota *= ExponentIncate;
            if (Math.abs(OutIota) >= Double.POSITIVE_INFINITY) {
                throw new MishapDivideByZero(Component.translatable("gui.geriorandomstuff.hex.toolarge"), Component.translatable("gui.geriorandomstuff.hex.toolarge"), "toolarge");
            } else { //WHY KOTLIN WHY
                List<Iota> FUCK = new ArrayList<>();
                FUCK.add(new DoubleIota(OutIota));
                return FUCK;
            }
        } else {
            throw new MishapInvalidIota(args1,0,Component.translatable("list of numpatterns"));
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

