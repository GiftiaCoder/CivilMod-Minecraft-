package poi.giftiacoder.civil_mod.commands.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class CivilWorldTeleporter extends Teleporter {

	private final WorldServer world;
	private double x = 0, y = 128, z = 0;
	
	public CivilWorldTeleporter(WorldServer worldIn, double x, double y, double z) {
		super(worldIn);
		world = worldIn;
	}

	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw) {
		this.world.getBlockState(new BlockPos(x, y, z));
		entityIn.setPosition(x, y, z);
		entityIn.motionX = 0f;
		entityIn.motionY = 0f;
		entityIn.motionZ = 0f;
	}
	
	public static void teleportDimension(EntityPlayer player, int dimension, double x, double y, double z) {
		int oldDimension = player.getEntityWorld().provider.getDimension();
		EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
		MinecraftServer server = player.getEntityWorld().getMinecraftServer();
		WorldServer worldServer = server.getWorld(dimension);
		
		if (worldServer == null || server == null) {
			throw new IllegalArgumentException(String.format("dimension %d dosenot exist", dimension));
		}
		
		worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new CivilWorldTeleporter(worldServer, x, y, z));
		player.setPositionAndUpdate(x, 128.0, z);
	}
	
}
