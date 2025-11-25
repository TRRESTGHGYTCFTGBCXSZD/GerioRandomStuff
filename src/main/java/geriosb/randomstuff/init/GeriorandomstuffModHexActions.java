
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff.init;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.HexRegistries;
import geriosb.randomstuff.common.hexactions.ColorIota;
import geriosb.randomstuff.common.hexactions.OpContinuationDecoder;
import geriosb.randomstuff.common.hexactions.OpHyperRead;
import geriosb.randomstuff.common.hexactions.OpNumericCreator;
import geriosb.randomstuff.common.hexactions.colors.OpColorConstructor;
import geriosb.randomstuff.common.hexactions.colors.OpColorDeconstructor;
import geriosb.randomstuff.common.hexactions.colors.OpColorTransparencyStripper;
import geriosb.randomstuff.common.hexactions.colors.OpColorConstructorNoTrans;
import geriosb.randomstuff.common.hexactions.colors.OpColorDeconstructorNoTrans;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import geriosb.randomstuff.GeriorandomstuffMod;

public class GeriorandomstuffModHexActions { //seriously? kotlin!?
    // WEST awqaw, SOUTH_EAST w, EAST edaq, EAST daed, NORTH_WEST waa, WEST aqed, WEST awqaq, NORTH_WEST dew, SOUTH_EAST qaqede, EAST awqaq, EAST aqawq, EAST
    // WEST awqaw (0)
    // SOUTH_EAST w (1)
    // EAST edaq (2)
    // EAST daed (3)
    // NORTH_WEST waa (4)
    // WEST aqed (5)
    // WEST awqaq (6)
    // NORTH_WEST dew (7)
    // SOUTH_EAST qaqede (8)
    // EAST awqaq (9)
    // EAST aqawq (E)
    // EAST (-.)
	public static final DeferredRegister<ActionRegistryEntry> ACTIONS = DeferredRegister.create(HexRegistries.ACTION, GeriorandomstuffMod.MODID);
        public static final RegistryObject<ActionRegistryEntry> NUMBER_FORMATTER = ACTIONS.register("number_formatter",
            () -> new ActionRegistryEntry(HexPattern.fromAngles("dqddeawawaawdwdadwwdwwd", HexDir.EAST), OpNumericCreator.INSTANCE));
    public static final RegistryObject<ActionRegistryEntry> COLOR_CONSTRUCTOR_NONOPAQUE = ACTIONS.register("color_constructor_nonopaque",
            () -> new ActionRegistryEntry(HexPattern.fromAngles("qqqwqwqqqwqwqqqwq", HexDir.WEST), OpColorConstructorNoTrans.INSTANCE));
    public static final RegistryObject<ActionRegistryEntry> COLOR_CONSTRUCTOR = ACTIONS.register("color_constructor",
            () -> new ActionRegistryEntry(HexPattern.fromAngles("qqqwadqqqqwadqqqqwad", HexDir.WEST), OpColorConstructor.INSTANCE));
    public static final RegistryObject<ActionRegistryEntry> COLOR_DECONSTRUCTOR_NONOPAQUE = ACTIONS.register("color_deconstructor_nonopaque",
            () -> new ActionRegistryEntry(HexPattern.fromAngles("eeeweweeeweweeewe", HexDir.WEST), OpColorDeconstructorNoTrans.INSTANCE));
    public static final RegistryObject<ActionRegistryEntry> COLOR_DECONSTRUCTOR = ACTIONS.register("color_deconstructor",
            () -> new ActionRegistryEntry(HexPattern.fromAngles("eeewdaeeeewdaeeeewda", HexDir.WEST), OpColorDeconstructor.INSTANCE));
    public static final RegistryObject<ActionRegistryEntry> COLOR_TRANSPARENCY_STRIPPER = ACTIONS.register("color_transparency_stripper",
            () -> new ActionRegistryEntry(HexPattern.fromAngles("dqaawadeee", HexDir.WEST), OpColorTransparencyStripper.INSTANCE));
    public static final RegistryObject<ActionRegistryEntry> HYPER_READ = ACTIONS.register("hyper_read",
            () -> new ActionRegistryEntry(HexPattern.fromAngles("aqqqqqeawqwqwqwqwqw", HexDir.EAST), OpHyperRead.INSTANCE));
    public static final RegistryObject<ActionRegistryEntry> CONTINUATION_DECODER = ACTIONS.register("continuation_decoder",
            () -> new ActionRegistryEntry(HexPattern.fromAngles("ewdeaq", HexDir.NORTH_EAST), OpContinuationDecoder.INSTANCE));
    public static final DeferredRegister<IotaType<?>> IOTAS = DeferredRegister.create(HexRegistries.IOTA_TYPE, GeriorandomstuffMod.MODID);
    public static final RegistryObject<IotaType<ColorIota>> COLOR_IOTA = IOTAS.register("color", () -> ColorIota.TYPE);
    // WHY FORGE NO WAY WORK
}
