package com.bilibili.hollowmagic.event;

import com.bilibili.hollowmagic.network.ModMesseges;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class KeyInputHandler {
    public static final String KEY_CATEGORY_SPELLS = "key.category.spells";
    public static final String KEY_SPELL = "key.hollow_magic.spell";
    public static final String KEY_SUPER_SPELL = "key.hollow_magic.super_spell";

    public static KeyBinding spellKey;
    public static KeyBinding superSpellKey;

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client->{
            if(spellKey.wasPressed()){
                ClientPlayNetworking.send(ModMesseges.SPELL_ID, PacketByteBufs.create());
            } else if (superSpellKey.wasPressed()) {
                ClientPlayNetworking.send(ModMesseges.SUPER_SPELL_ID, PacketByteBufs.create());
            }
        });
    }

    public static void register(){
        spellKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SPELL,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                KEY_CATEGORY_SPELLS
        ));
        superSpellKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SUPER_SPELL,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                KEY_CATEGORY_SPELLS
        ));

        registerKeyInputs();
    }
}