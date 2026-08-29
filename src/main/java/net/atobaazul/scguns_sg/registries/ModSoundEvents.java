package net.atobaazul.scguns_sg.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.atobaazul.scguns_sg.ScrapGuns.MOD_ID;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);

    /*
    public static final RegistryObject<SoundEvent> HANGMAN_FIRE = register("item.hangman.fire");
    public static final RegistryObject<SoundEvent> HANGMAN_SILENCED_FIRE = register("item.hangman.silended_fire");
    */

    private static RegistryObject<SoundEvent> register(String key) {
        return REGISTER.register(key, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, key)));
    }
}
