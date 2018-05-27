package poi.giftiacoder.civil_mod.commands;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import poi.giftiacoder.civil_mod.R;
import poi.giftiacoder.civil_mod.RegistryHandler;
import poi.giftiacoder.civil_mod.civilmaterial.Resources;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkCastle;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkData;

public class CmdCheckResources extends CommandBase {
	
	private static final List<String> ALIASES = Lists.newArrayList(R.MODID, "resources");
	
	@Override
	public String getName() {
		return "resources";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "resources";
	}
	
	@Override
	public List<String> getAliases() {
		return ALIASES;
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		try {
			RegistryHandler.writeResourceData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sender.sendMessage(new TextComponentString("num: " + RegistryHandler.resourceDataList[0].size()));
		/*BlockPos pos = sender.getPosition();
		TileEntityChunkData chunkData = TileEntityChunkData.getChunkData(sender.getEntityWorld(), pos.getX() >> 4, pos.getZ() >> 4);
		
		if (chunkData != null) {
			String msg = TextFormatting.RED + "----------------------------" + TextFormatting.WHITE + "\n";;
			for (int i = 0; i < chunkData.resourcesBasicAmount.length; ++i) {
				msg += (Resources.values()[i].name().toLowerCase() + ": " + TextFormatting.GREEN + chunkData.resourcesBasicAmount[i] + TextFormatting.WHITE + "\t");
			}
			sender.sendMessage(new TextComponentString(msg));
		}
		else {
			sender.sendMessage(new TextComponentString(String.format("%sCannot get chunk master, block state: %s, pos: %s, tileentity: %s", 
					TextFormatting.RED, sender.getEntityWorld().getBlockState(pos), pos, sender.getEntityWorld().getTileEntity(pos))));
		}*/
	}
}
