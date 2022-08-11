package com.bilibili.hollowmagic.sound;

import com.bilibili.hollowmagic.HollowMagicMod;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {
    public static SoundEvent VENGEFUL_SPIRIT_SPELL_SOUND_EVENT;
    public static SoundEvent DESOLATE_DIVE_SPELL_SOUND_EVENT;
    public static SoundEvent DESOLATE_DIVE_HIT_SOUND_EVENT;
    public static SoundEvent DESCENDING_DARK_SPELL_SOUND_EVENT;
    public static SoundEvent DESCENDING_DARK_HIT_SOUND_EVENT;
    public static SoundEvent PLAYER_GOT_SOUL_SOUND_EVENT;
    public static SoundEvent PLAYER_GOT_FULL_SOUL_SOUND_EVENT;
    public static SoundEvent SHADE_SOUL_SPELL_SOUND_EVENT;
    public static SoundEvent HOWLING_WRAITHS_SPELL_SOUND_EVENT;
    public static SoundEvent ABYSS_SHRIEK_SPELL_SOUND_EVENT;

    private static SoundEvent registerSoundEvent(String name){
        Identifier id = new Identifier(HollowMagicMod.MODID,name);
        return Registry.register(Registry.SOUND_EVENT,id,new SoundEvent(id));
    }

    public static void registerSoundEvents(){
        VENGEFUL_SPIRIT_SPELL_SOUND_EVENT = registerSoundEvent("vengeful_spirit_spell");
        DESOLATE_DIVE_SPELL_SOUND_EVENT = registerSoundEvent("desolate_dive_spell");
        DESOLATE_DIVE_HIT_SOUND_EVENT = registerSoundEvent("desolate_dive_hit");
        DESCENDING_DARK_SPELL_SOUND_EVENT = registerSoundEvent("descending_dark_spell");
        DESCENDING_DARK_HIT_SOUND_EVENT = registerSoundEvent("descending_dark_hit");
        PLAYER_GOT_SOUL_SOUND_EVENT = registerSoundEvent("player_got_soul");
        PLAYER_GOT_FULL_SOUL_SOUND_EVENT = registerSoundEvent("player_got_full_soul");
        SHADE_SOUL_SPELL_SOUND_EVENT = registerSoundEvent("shade_soul_spell");
        HOWLING_WRAITHS_SPELL_SOUND_EVENT = registerSoundEvent("howling_wraiths_spell");
        ABYSS_SHRIEK_SPELL_SOUND_EVENT = registerSoundEvent("abyss_shriek_spell");
    }
}