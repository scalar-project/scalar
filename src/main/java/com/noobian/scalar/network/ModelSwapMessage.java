package com.noobian.scalar.network;

import java.util.Collection;
import java.util.List;

import com.noobian.scalar.util.ModelMood;
import com.noobian.scalar.util.ModelName;
import com.noobian.scalar.util.ModelNameParser;
import com.noobian.scalar.util.ModelSwapper;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ModelSwapMessage implements IMessage {

	public static final int TOGGLE_ERECTION = 0;
	public static final int CYCLE_AROUSAL_BACKWARD = 1;
	public static final int CYCLE_AROUSAL_FORWARD = 2;
	public static final int REQUEST_MODEL_SCREEN = 3;
	public static final int SWAP_MODEL = 4;
	public static final int REQUEST_SKIN_SCREEN = 5;
	public static final int SWAP_SKIN = 6;

	public int value;
	public String str;

	public ModelSwapMessage() {
	}

	public ModelSwapMessage(int toSend, String str) {
		this.value = toSend;
		this.str = str;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(value);

		// Check for extra string data
		if (value == SWAP_MODEL || value == SWAP_SKIN) {
			buf.writeInt(str.length());
			buf.writeBytes(Unpooled.wrappedBuffer(this.str.getBytes()));
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		value = buf.readInt();

		// Check for extra string data
		if (value == SWAP_MODEL || value == SWAP_SKIN) {
			int length = buf.readInt();
			byte[] msg = new byte[length];
			buf.readBytes(msg);
			this.str = new String(msg);
		}
	}

	public static class ModelSwapHandler implements IMessageHandler<ModelSwapMessage, IMessage> {

		@Override
		public IMessage onMessage(ModelSwapMessage message, MessageContext ctx) {
			EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
			int val = message.value;

			if (val == TOGGLE_ERECTION) {
				ModelSwapper.toggleErect(serverPlayer);
			} else if (val == CYCLE_AROUSAL_FORWARD) {
				ModelSwapper.cycleArousalForward(serverPlayer);
			} else if (val == CYCLE_AROUSAL_BACKWARD) {
				ModelSwapper.cycleArousalBackward(serverPlayer);
			} else if (val == REQUEST_MODEL_SCREEN) {
				List<String> models = ModelSwapper.getAllowedModels(serverPlayer);
				return new ModelListMessage(ModelListMessage.MODEL_LIST, models);
			} else if (val == SWAP_MODEL) {
				ModelSwapper.trySwapModel(serverPlayer, message.str);
			} else if (val == REQUEST_SKIN_SCREEN) {
				List<String> skins = ModelSwapper
						.getModelSkins(ModelNameParser.getBasename(ModelSwapper.getModel(serverPlayer)));
				return new ModelListMessage(ModelListMessage.SKIN_LIST, skins);
			} else if (val == SWAP_SKIN) {
				ModelName mn = ModelNameParser.parseModelName(ModelSwapper.getModel(serverPlayer));
				if (message.str.equals("default"))
					mn.skin = null;
				else
					mn.skin = message.str;
				mn.mood = ModelMood.NORMAL;
				mn.stage = 0;
				ModelSwapper.trySwapSkin(serverPlayer, ModelNameParser.generateModelName(mn));
			}
			return null;
		}

	}

}
