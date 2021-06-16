package com.noobian.scalar.handlers;

import com.github.gamepiaynmo.custommodel.api.CustomModelApi;
import com.noobian.scalar.ClientProxy;
import com.noobian.scalar.gui.ModelSelectGui;
import com.noobian.scalar.network.ModelSwapMessage;
import com.noobian.scalar.network.ScalarPacketHandler;
import com.noobian.scalar.util.ModelMood;
import com.noobian.scalar.util.ModelName;
import com.noobian.scalar.util.ModelNameParser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

@EventBusSubscriber(modid = "scalar")
public class ClientKeyPressHandler {

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(ClientTickEvent event) {

		if (event.phase != TickEvent.Phase.END)
			return;

		KeyBinding[] keyBindings = ClientProxy.keyBindings;
		String currentModelStr;
		ModelName currentModel;

		// Toggle erect
		if (keyBindings[0].isPressed()) {
			ScalarPacketHandler.INSTANCE.sendToServer(new ModelSwapMessage(ModelSwapMessage.TOGGLE_ERECTION, null));
		}

		// Cycle arousal forward
		else if (keyBindings[1].isPressed()) {
			ScalarPacketHandler.INSTANCE.sendToServer(new ModelSwapMessage(ModelSwapMessage.CYCLE_AROUSAL_FORWARD, null));
		}

		// Cycle arousal backward
		else if (keyBindings[2].isPressed()) {
			ScalarPacketHandler.INSTANCE.sendToServer(new ModelSwapMessage(ModelSwapMessage.CYCLE_AROUSAL_BACKWARD, null));
		}

//		// Request Change Model Screen
//		else if (keyBindings[3].isPressed()) {
//			TgorPacketHandler.INSTANCE.sendToServer(new ModelSwapMessage(ModelSwapMessage.REQUEST_MODEL_SCREEN, null));
//		}
	}
}
