package poi.giftiacoder.civil_mod.building;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class FileBuidingBase extends ABuildingModel {
	
	private Map<IBlockState, Set<BlockPos>> blockMap = new HashMap<>();
	
	public FileBuidingBase(String path) {
		try {
			DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
			int blockStateNum = in.readInt();
			for (int blockStateIdx = 0; blockStateIdx < blockStateNum; ++blockStateIdx) {
				int blockId = in.readInt();
				int blockStateMeta = in.readInt();
				
				int stateNameLen = in.readInt();
				byte[] stateNameBuf = new byte[stateNameLen];
				in.read(stateNameBuf);
				String stateName = new String(stateNameBuf);
				
				IBlockState state = Block.getStateById(blockStateMeta);
				Set<BlockPos> posSet = blockMap.get(state);
				if (posSet == null) {
					blockMap.put(state, posSet = new HashSet<>());
				}
				
				int posNum = in.readInt();
				for (int posIdx = 0; posIdx < posNum; ++posIdx) {
					BlockPos pos = new BlockPos(in.readInt(), in.readInt(), in.readInt());
					posSet.add(pos);
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void generate(Chunk chunk, int height) {
		for (Entry<IBlockState, Set<BlockPos>> ent : blockMap.entrySet()) {
			IBlockState state = ent.getKey();
			for (BlockPos pos : ent.getValue()) {
				chunk.setBlockState(pos.up(height), state);
			}
		}
	}

}
