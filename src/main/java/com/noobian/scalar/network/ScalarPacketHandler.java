package com.noobian.scalar.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ScalarPacketHandler {
	private static final String PROTOCOL_VERSION = "1";
	private static int id = 0;
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("scalar");

	public static void initNetwork() {
		INSTANCE.registerMessage(ModelSwapMessage.ModelSwapHandler.class, ModelSwapMessage.class, id++, Side.SERVER);
		INSTANCE.registerMessage(ModelListMessage.ModelListHandler.class, ModelListMessage.class, id++, Side.CLIENT);
	}
}
