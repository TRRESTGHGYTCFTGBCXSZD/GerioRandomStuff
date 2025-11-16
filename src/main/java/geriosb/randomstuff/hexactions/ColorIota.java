package geriosb.randomstuff.hexactions;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.utils.HexUtils;
import geriosb.randomstuff.init.GeriorandomstuffModHexActions;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataOutput;
import java.io.IOException;

public class ColorIota extends Iota {

    public ColorIota(int Color) {
        // We have to pass *something* here, but there's nothing that actually needs to go there,
        // so we just do this i guess
        super(GeriorandomstuffModHexActions.COLOR_IOTA.get(), Color);
    }

    public int getColor() {
        return (int) this.payload;
    }

    @Override
    public boolean isTruthy() {
        return false;
    }

    @Override
    public boolean toleratesOther(Iota that) {
        return typesMatch(this, that);
    }

    @Override
    public @NotNull Tag serialize() {
        return IntTag.valueOf(this.getColor());
    }

    public static IotaType<ColorIota> TYPE = new IotaType<>() {
        @Nullable
        @Override
        public ColorIota deserialize(Tag tag, ServerLevel world) throws IllegalArgumentException {
            return ColorIota.deserialize(tag);
        }

        @Override
        public Component display(Tag tag) {
            return ColorIota.display(ColorIota.deserialize(tag).getColor());
        }

        @Override
        public int color() {
            long taimu = System.currentTimeMillis();
            double angle = (taimu % 10000L) / 5000. * Math.PI;
            int red = (int) ((Math.sin(angle)+1)*127.5);
            angle = ((taimu % 10000L)+(100000./3.)) / 5000. * Math.PI;
            int green = (int) ((Math.sin(angle)+1)*127.5);
            angle = ((taimu % 10000L)+(200000./3.)) / 5000. * Math.PI;
            int blue = (int) ((Math.sin(angle)+1)*127.5);
            return 0xff000000|red<<16|green<<8|blue;
        }
    };

    public static ColorIota deserialize(Tag tag) throws IllegalArgumentException {
        var dtag = HexUtils.downcast(tag, IntTag.TYPE);
        return new ColorIota(dtag.getAsInt());
    }

    public static Component display(int d) {
        return Component.literal(String.format("#%08X", d)).withStyle(ChatFormatting.BLUE);
    }
}