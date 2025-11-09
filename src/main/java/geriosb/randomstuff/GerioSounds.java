
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;

public class GerioSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, GeriorandomstuffMod.MODID);
	public static final RegistryObject<SoundEvent> BREAKHERE = REGISTRY.register("breakhere", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("geriorandomstuff", "breakhere")));
	public static final RegistryObject<SoundEvent> SAMBADEJANEIRO = REGISTRY.register("sambadejaneiro", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("geriorandomstuff", "sambadejaneiro")));
}
