package com.noobian.scalar.network;

import java.util.ArrayList;
import java.util.Collection;

import com.noobian.scalar.ClientProxy;
import com.noobian.scalar.gui.ModelSelectGui;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ModelListMessage implements IMessage {

	public static final byte MODEL_LIST = 0;
	public static final byte SKIN_LIST = 1;

	public byte listType;
	public Collection<String> models;
	private int length = 0;
	private String msg = "";

	public ModelListMessage() {
	}

	public ModelListMessage(byte listType, Collection<String> models) {
		this.models = models;
		this.listType = listType;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.models = new ArrayList<String>();
		this.listType = buf.readByte();
		this.length = buf.readInt();
		byte[] msg = new byte[this.length];
		buf.readBytes(msg);
		this.msg = new String(msg);
		for (String s : this.msg.split(" ")) {
			models.add(s);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		models.forEach((s) -> {
			this.msg += s + " ";
		});
		buf.writeByte(listType);
		buf.writeInt(this.msg.length());
		buf.writeBytes(Unpooled.wrappedBuffer(this.msg.getBytes()));
	}

	public static class ModelListHandler implements IMessageHandler<ModelListMessage, IMessage> {

		@Override
		public IMessage onMessage(ModelListMessage message, MessageContext ctx) {
			Minecraft mc = Minecraft.getMinecraft();

			if (message.listType == MODEL_LIST) {
				ClientProxy.allowedModels = message.models;
//				if (mc.currentScreen == null)
//					mc.displayGuiScreen(new ModelSelectGui());

				mc.player.sendMessage(new TextComponentString("Available models:"));
				mc.player.sendMessage(new TextComponentString(message.msg));
			}

			else if (message.listType == SKIN_LIST) {
				if (message.models.size() <= 1)
					mc.player.sendMessage(new TextComponentString("There are no skin variants for this model."));
				else {
					mc.player.sendMessage(new TextComponentString("Model skin variants:"));
					mc.player.sendMessage(new TextComponentString(message.msg));
				}
			}

			return null;
		}
	}

}
