package com.noobian.scalar;

import com.noobian.scalar.network.ModelSwapMessage;
import com.noobian.scalar.network.ScalarPacketHandler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class SkinSwapPlaceholderCommand extends CommandBase {

	@Override
	public String getName() {
		return "skin";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/skin <skin>. Provide no arguments for a list of skins.";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 0) {
			// Get list of skins.
			ScalarPacketHandler.INSTANCE.sendToServer(new ModelSwapMessage(ModelSwapMessage.REQUEST_SKIN_SCREEN, null));
			return;
		}

		ScalarPacketHandler.INSTANCE
				.sendToServer(new ModelSwapMessage(ModelSwapMessage.SWAP_SKIN, args[0].toLowerCase()));
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender instanceof EntityPlayer;
	}
}
