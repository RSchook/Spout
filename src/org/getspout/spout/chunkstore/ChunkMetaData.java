/*
 * This file is part of Spout (http://wiki.getspout.org/).
 * 
 * Spout is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Spout is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.getspout.spout.chunkstore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class ChunkMetaData implements Serializable {

	private static final long serialVersionUID = 1L;

	// This data is saved.  This means data can handle different map heights
	// Changes may be needed to the positionToKey method
	
	private int cx;
	private int cz;
	private UUID worldUid;
	
	private int[] blockX = new int[1];
	private int[] blockY = new int[1];
	private int[] blockZ = new int[1];
	@SuppressWarnings("unchecked")
	private HashMap<String,byte[]>[] blockData = new HashMap[1];
	private HashMap<String,byte[]> chunkData = new HashMap<String,byte[]>();
	
	private int blocks = 0;
	
	
	// This is just a fast lookup for the data
	volatile private HashMap<Integer,HashMap<String,byte[]>> fastLookup = null;
	volatile private HashMap<Integer,Integer> indexLookup = null;
	
	volatile private boolean firstRead = true;
	
	volatile private boolean dirty = false;
	
	ChunkMetaData(UUID worldId, int cx, int cz) {
		this.cx = cx;
		this.cz = cz;
		this.worldUid = worldId;
	}
	
	public boolean getDirty() {
		return dirty;
	}
	
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	public int getChunkX() {
		return cx;
	}
	
	public int getChunkZ() {
		return cz;
	}
	
	public UUID getWorldUID() {
		return worldUid;
	}
	
	public Serializable removeChunkData(String id) {
		byte[] serial = chunkData.remove(id);
		if (serial != null) {
			dirty = true;
			return Utils.deserialize(serial);
		} else {
			return null;
		}
	}
	
	public Serializable getChunkData(String id) {
		byte[] serial = chunkData.get(id);
		return Utils.deserialize(serial);
	}
	
	public Serializable putChunkData(String id, Serializable o) {
		
		byte[] oldBytes = chunkData.put(id, Utils.serialize(o));

		dirty = true;
		
		if (oldBytes == null) {
			return null;
		} else {
			return Utils.deserialize(oldBytes);
		}
		
	}
	
	public Serializable removeBlockData(String id, int x, int y, int z) {
		
		int key = positionToKey(x, y, z);
		
		HashMap<String,byte[]> localBlockData = fastLookup.get(key);
		
		if (localBlockData == null) {
			return null;
		} else {
			dirty = true;
			byte[] serial = localBlockData.remove(id);
			if (localBlockData.size() == 0) {
				int index = indexLookup.get(key);
				blockData[index] = null;
				fastLookup.remove(key);
				indexLookup.remove(key);
			}
			return Utils.deserialize(serial);
		}
		
	}
	
	public Serializable getBlockData(String id, int x, int y, int z) {
		
		int key = positionToKey(x, y, z);
		
		HashMap<String,byte[]> localBlockData = fastLookup.get(key);
		
		if (localBlockData == null) {
			return null;
		} else {
			byte[] serial = localBlockData.get(id);
			return Utils.deserialize(serial);
		}
		
	}
	
	public Serializable putBlockData(String id, int x, int y, int z, Serializable o) {
		
		int key = positionToKey(x, y, z);
		
		HashMap<String,byte[]> localBlockData = fastLookup.get(key);
		
		if (localBlockData == null) {
			if (blocks >= this.blockData.length) {
				resizeArrays(1 + ((blocks * 3) / 2));
			}
			localBlockData = new HashMap<String,byte[]>();
			blockX[blocks] = x;
			blockY[blocks] = y;
			blockZ[blocks] = z;
			blockData[blocks] = localBlockData;
			fastLookup.put(key, blockData[blocks]);
			indexLookup.put(key, blocks);
			blocks++;
			updateHashMap();
		}
		
		byte[] oldBytes = localBlockData.put(id, Utils.serialize(o));
		dirty = true;

		return Utils.deserialize(oldBytes);

	}
	
	@SuppressWarnings("unchecked")
	private void resizeArrays(int size) {
		
		dirty = true;
		
		if (size < blocks) {
			throw new IllegalArgumentException("Attempted to reduce array size below its limit");
		}
		
		int[] oldBlockX = blockX;
		int[] oldBlockY = blockY;
		int[] oldBlockZ = blockZ;
		
		HashMap<String,byte[]>[] oldBlockData = blockData;
		
		blockX = new int[size];
		blockY = new int[size];
		blockZ = new int[size];

		blockData = new HashMap[size];
		
		int dest = 0;
		for (int i = 0; i < blocks; i++) {
			if (oldBlockData[i] != null) {
				blockX[dest] = oldBlockX[i];
				blockY[dest] = oldBlockY[i];
				blockZ[dest] = oldBlockZ[i];
				blockData[dest] = oldBlockData[i];
				dest++;
			}
		}
		
		blocks = dest;
		
	}
	
	private int positionToKey(int x, int y, int z) {
		
		if (firstRead) {
			firstRead = false;
			updateHashMap();
		}
		
		int xx = x & 0xF;
		int yy = y & 0xFF;
		int zz = z & 0xF;
		
		return (xx << 24) | (yy << 8) | (zz << 0);
	}
	
	private void updateHashMap() {
		fastLookup = new HashMap<Integer,HashMap<String,byte[]>>();
		indexLookup = new HashMap<Integer,Integer>();
		for (int i = 0; i < blocks; i++) {
			int key = positionToKey(blockX[i], blockY[i], blockZ[i]);
			fastLookup.put(key, blockData[i]);
			indexLookup.put(key, i);
		}
	}

}
