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
package org.getspout.spout.block;

import java.io.Serializable;

import net.minecraft.server.EnumSkyBlock;
import net.minecraft.server.World;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.block.SpoutChunk;

public class SpoutCraftBlock extends CraftBlock implements SpoutBlock {
	protected final int x, y, z;
	protected final SpoutCraftChunk chunk;
	public SpoutCraftBlock(SpoutCraftChunk chunk, int x, int y, int z) {
		super(chunk, x, y, z);
		this.x = x;
		this.y = y;
		this.z = z;
		this.chunk = chunk;
	}

	@Override
	public SpoutChunk getChunk() {
		return chunk;
	}
	
	@Override
	public void setTypeAsync(Material type) {
		setTypeIdAsync(type.getId());
	}
	
	@Override
	public void setTypeIdAsync(int type) {
		chunk.queuedId.put(getIndex(), type);
		SpoutCraftChunk.queuedChunks.add(chunk);
	}
	
	@Override
	public void setDataAsync(byte data) {
		chunk.queuedData.put(getIndex(), data);
		SpoutCraftChunk.queuedChunks.add(chunk);
	}
	
	@Override
	public void setTypeIdAndDataAsync(int type, byte data) {
		chunk.queuedId.put(getIndex(), type);
		chunk.queuedData.put(getIndex(), data);
		SpoutCraftChunk.queuedChunks.add(chunk);
	}
	
	@Override
	public Serializable setData(String id, Serializable data) {
		return SpoutManager.getChunkDataManager().setBlockData(id, getWorld(), getX(), getY(), getZ(), data);
	}
	
	@Override
	public Serializable getData(String id) {
		return SpoutManager.getChunkDataManager().getBlockData(id, getWorld(), getX(), getY(), getZ());
	}
	
	@Override
	public Serializable removeData(String id) {
		return SpoutManager.getChunkDataManager().removeBlockData(id, getWorld(), getX(), getY(), getZ());
	}
	
	@Override
	public BlockState getState() {
		Material material = getType();

		switch (material) {
			case CHEST:
				return new SpoutCraftChest(this);
			default:
				return super.getState();
		}
	}
	
	@Override
	public SpoutBlock getFace(BlockFace face) {
		return getFace(face, 1);
	}
	
	@Override
	public SpoutBlock getFace(BlockFace face, int distance) {
		return getRelative(face.getModX() * distance, face.getModY() * distance, face.getModZ() * distance);
	}
	
	@Override
	public SpoutBlock getRelative(int modX, int modY, int modZ) {
		Block result = super.getRelative(modX, modY, modZ);
		if (result instanceof SpoutBlock) {
			return (SpoutBlock)result;
		}
		//XXX should not happen!
		net.minecraft.server.Chunk chunk = ((CraftChunk)result.getChunk()).getHandle();
		chunk.bukkitChunk = new SpoutCraftChunk(chunk);
		return (SpoutBlock)chunk.bukkitChunk.getBlock(result.getX() & 0xF, result.getY() & 0x7F, result.getZ() & 0xF);
	}
	
	@Override
	public SpoutBlock getRelative(BlockFace face) {
		return getRelative(face.getModX(), face.getModY(), face.getModZ());
	}
	
	private int getIndex() {
		return (x & 0xF) << 11 | (z & 0xF) << 7 | (y & 0x7F);
	}

	@Override
	public String getName() {
		return SpoutManager.getItemManager().getItemName(getType(), getData());
	}
	
	@Override
	public void setBlockPowered(boolean power) {
		setBlockPowered(power, null);
	}
	
	@Override
	public void setBlockPowered(boolean power, BlockFace face) {
		int powerbits = 0;
		int index = getIndex();
		if (chunk.powerOverrides.containsKey(index)) {
			powerbits = chunk.powerOverrides.get(index);
		}
		if (face != null) {
			if (face == BlockFace.UP ){
				powerbits = power ? powerbits | (1 << 0) : powerbits &  ~(1 << 0);
			}
			else if (face == BlockFace.DOWN) {
				powerbits = power ? powerbits | (1 << 1) : powerbits &  ~(1 << 1);
			}
			else if (face == BlockFace.EAST) {
				powerbits = power ? powerbits | (1 << 2) : powerbits &  ~(1 << 2);
			}
			else if (face == BlockFace.WEST) {
				powerbits = power ? powerbits | (1 << 3) : powerbits &  ~(1 << 3);
			}
			else if (face == BlockFace.NORTH) {
				powerbits = power ? powerbits | (1 << 4) : powerbits &  ~(1 << 4);
			}
			else if (face == BlockFace.SOUTH) {
				powerbits = power ? powerbits | (1 << 5) : powerbits &  ~(1 << 5);
			}
			else {
				throw new IllegalArgumentException("Valid block faces are up, down, east, west, north, south, or null.");
			}
		}
		else {
			powerbits = power ? ~0 : 0;
		}
		chunk.powerOverrides.put(index, powerbits);
	}
	
	@Override
	public void resetBlockPower() {
		chunk.powerOverrides.remove(getIndex());
	}

	@Override
	public void setLightLevel(byte level) {
		chunk.lightOverrides.put(getIndex(), (int) level);
		//this.chunk.getHandle().g.a(x, y, z, level);
		CraftWorld cw = (CraftWorld)chunk.getWorld();
		World handle = cw.getHandle();
		handle.b(EnumSkyBlock.SKY, x, y, z, level);
		handle.b(EnumSkyBlock.BLOCK, x, y, z, level);
	}
	
	@Override
	public void resetLightLevel() {
		chunk.lightOverrides.remove(getIndex());
	}

	@Override
	public float getHardness() {
		int index = getIndex();
		if (chunk.hardnessOverrides.containsKey(index)) {
			return chunk.hardnessOverrides.get(index);
		}
		return net.minecraft.server.Block.byId[getTypeId()].j();
	}

	@Override
	public void setHardness(float hardness) {
		chunk.hardnessOverrides.put(getIndex(), hardness);
	}

	@Override
	public void resetHardness() {
		chunk.hardnessOverrides.remove(getIndex());
	}
}
