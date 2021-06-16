package com.noobian.scalar;

import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.logging.Handler;

import org.apache.logging.log4j.Logger;

import com.noobian.scalar.handlers.ClientKeyPressHandler;
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
		this.proxy.preInit(event);
		
		ScalarPacketHandler.initNetwork();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		this.proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		this.proxy.postInit(event);
	}
}
