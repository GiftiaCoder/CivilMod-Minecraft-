package poi.giftiacoder.civil_mod.commands;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import poi.giftiacoder.civil_mod.R;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkCastle;
import poi.giftiacoder.civil_mod.tileentity.TileEntityChunkData;

public class CmdCheckChunkMaster extends CommandBase {

	private static final List<String> ALIASES = Lists.newArrayList(R.MODID, "chkcm");
	
	@Override
	public String getName() {
		return "chkcm";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "chkcm";
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
		BlockPos pos = sender.getPosition();
		TileEntityChunkData chunkData = TileEntityChunkData.getChunkData(sender.getEntityWorld(), pos.getX() >> 4, pos.getZ() >> 4);
		
		if (chunkData != null) {
			sender.sendMessage(new TextComponentString(
					String.format("chunk class:%s%s%s, chunk type:%s%s%s, productivity:%s%f%s, demon pollution:%s%f%s, sum productivity:%s%f%s, coord:%s(%d, %d)%s\n"
							, TextFormatting.RED, chunkData.getClass(), TextFormatting.WHITE 
							, TextFormatting.RED, chunkData.chunkType, TextFormatting.WHITE 
							, TextFormatting.RED, chunkData.productivity, TextFormatting.WHITE 
							, TextFormatting.RED, chunkData.demonPollution, TextFormatting.WHITE
							, TextFormatting.RED, chunkData instanceof TileEntityChunkCastle ? ((TileEntityChunkCastle)chunkData).sumProductivity : 0.0F, TextFormatting.WHITE 
							, TextFormatting.RED, chunkData.chunkX, chunkData.chunkZ, TextFormatting.WHITE)));
		}
		else {
			sender.sendMessage(new TextComponentString(String.format("%sCannot get chunk master, block state: %s, pos: %s, tileentity: %s", 
					TextFormatting.RED, sender.getEntityWorld().getBlockState(pos), pos, sender.getEntityWorld().getTileEntity(pos))));
		}
	}

}
