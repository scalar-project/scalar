package com.noobian.scalar;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

import com.noobian.scalar.network.ScalarPacketHandler;

@Mod(modid = Scalar.MODID, name = Scalar.NAME, version = Scalar.VERSION, dependencies = "required-after:custommodel", acceptedMinecraftVersions = "1.12.2")
public class Scalar {
	public static final String MODID = "scalar";
	public static final String NAME = "Scalar Core";
	public static final String VERSION = "0.6";

	@SidedProxy(clientSide = "com.noobian.scalar.ClientProxy", serverSide = "com.noobian.scalar.ServerProxy")
	public static CommonProxy proxy;

	private static Logger logger;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		proxy.preInit(event);
		
		ScalarPacketHandler.initNetwork();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}
