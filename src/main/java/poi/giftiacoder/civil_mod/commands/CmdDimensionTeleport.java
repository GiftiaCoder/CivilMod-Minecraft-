package poi.giftiacoder.civil_mod.commands;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import poi.giftiacoder.civil_mod.R;
import poi.giftiacoder.civil_mod.commands.util.CivilWorldTeleporter;

public class CmdDimensionTeleport extends CommandBase {

	private final List<String> aliases = Lists.newArrayList(R.MODID, "tpdim", "tpdimension", "teleportdimension");
	
	@Override
	public String getName() {
		return "tpdimension";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "tpdim <id>";
	}
	
	@Override
	public List<String> getAliases() {
		return aliases;
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length < 1) {
			return;
		}
		
		int dimensionId = 0;
		try {
			dimensionId = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			sender.sendMessage(new TextComponentString(String.format("%sDimension id invalid: %s", TextFormatting.RED, args[0])));
		}
		
		if (sender instanceof EntityPlayer) {
			CivilWorldTeleporter.teleportDimension((EntityPlayer)sender, dimensionId, 0, 32, 0);
		}
	}

}
