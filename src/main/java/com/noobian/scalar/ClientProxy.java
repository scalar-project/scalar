package com.noobian.scalar;

import java.util.Collection;

import org.lwjgl.input.Keyboard;

import com.noobian.scalar.handlers.ClientKeyPressHandler;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {


public static KeyBinding[] keyBindings;
public static Collection<String> allowedModels = null;


	@Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        
        keyBindings = new KeyBinding[3]; // TODO 4
        keyBindings[0] = new KeyBinding("key.toggleerect.desc", Keyboard.KEY_APOSTROPHE, "key.scalar.category");
        keyBindings[1] = new KeyBinding("key.cyclearousalforward.desc", Keyboard.KEY_RBRACKET, "key.scalar.category");
        keyBindings[2] = new KeyBinding("key.cyclearousalbackward.desc", Keyboard.KEY_LBRACKET, "key.scalar.category");
//        keyBindings[3] = new KeyBinding("key.selectmodel.desc", Keyboard.KEY_BACKSLASH, "key.scalar.category");
        
        for (int i = 0; i < keyBindings.length; ++i) 
        {
            ClientRegistry.registerKeyBinding(keyBindings[i]);
        }
        
        ClientCommandHandler.instance.registerCommand(new ModelSwapPlaceholderCommand());
        ClientCommandHandler.instance.registerCommand(new SkinSwapPlaceholderCommand());
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }

}
