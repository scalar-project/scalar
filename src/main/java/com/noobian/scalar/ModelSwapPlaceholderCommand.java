package com.noobian.scalar;

import com.noobian.scalar.network.ModelSwapMessage;
import com.noobian.scalar.network.ScalarPacketHandler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class ModelSwapPlaceholderCommand extends CommandBase {

	@Override
	public String getName() {
		return "model";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/model <model>. Provide no arguments for a list of allowed models.";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 0) {
			// Get list of models.
			ScalarPacketHandler.INSTANCE
					.sendToServer(new ModelSwapMessage(ModelSwapMessage.REQUEST_MODEL_SCREEN, null));
			return;
		}

		ScalarPacketHandler.INSTANCE
				.sendToServer(new ModelSwapMessage(ModelSwapMessage.SWAP_MODEL, args[0].toLowerCase()));
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender instanceof EntityPlayer;
	}
}
