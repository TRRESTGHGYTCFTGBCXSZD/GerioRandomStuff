
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff.init;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.HexRegistries;
import geriosb.randomstuff.hexactions.OpNumericCreator;
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
	public static final DeferredRegister<ActionRegistryEntry> REGISTRY = DeferredRegister.create(HexRegistries.ACTION, GeriorandomstuffMod.MODID);
        public static final RegistryObject<ActionRegistryEntry> NUMBER_FORMATTER = REGISTRY.register("number_formatter",
            () -> new ActionRegistryEntry(HexPattern.fromAngles("dqddeawawaawdwdadwwdwwd", HexDir.EAST), OpNumericCreator.INSTANCE));
    // WHY FORGE NO WAY WORK
}
