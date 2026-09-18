package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.LazyFlesh.variablehorizons.util.blockUtils.GridGenerator;

@Mixin({ ChunkProviderServer.class })
public class MixinChunkProviderServer_ForceSkygridTerrain {

    @Shadow
    public WorldServer worldObj;

    @ModifyVariable(
        at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/world/chunk/IChunkProvider;provideChunk(II)Lnet/minecraft/world/chunk/Chunk;"),
        method = "originalLoadChunk")
    public Chunk variablehorizons$forceSkygridTerrain(Chunk chunk) {
        long seed = chunk.worldObj.getSeed();
        GridGenerator.GridResult result = GridGenerator.generateGrid(seed, chunk.xPosition, chunk.zPosition);
        Block[] ids = result.blocks;
        byte[] metadata = result.metadata;

        byte[] chunkData = chunk.getBiomeArray();

        Chunk newChunk = new Chunk(chunk.worldObj, ids, metadata, chunk.xPosition, chunk.zPosition);
        newChunk.setBiomeArray(chunkData);

        for (GridGenerator.TEWrapper wrapper : result.teData) {
            Block block = wrapper.block;
            byte meta = metadata[wrapper.index];

            TileEntity te = null;
            if (block.hasTileEntity(meta)) {
                te = block.createTileEntity(chunk.worldObj, meta);
            } else if (block instanceof ITileEntityProvider) {
                te = ((ITileEntityProvider) block).createNewTileEntity(chunk.worldObj, meta);
            }

            if (te != null) {
                int localX = (wrapper.index >> 12) & 15;
                int localZ = (wrapper.index >> 8) & 15;
                int y = wrapper.index & 255;
                te.xCoord = chunk.xPosition * 16 + localX;
                te.yCoord = y;
                te.zCoord = chunk.zPosition * 16 + localZ;
                te.setWorldObj(chunk.worldObj);

                if (wrapper.damage > 0) {
                    NBTTagCompound tag = new NBTTagCompound();
                    te.writeToNBT(tag);
                    // GT specifically checks for this integer tag to load the MetaTileEntity
                    tag.setInteger("mID", wrapper.damage);
                    te.readFromNBT(tag);
                }
                newChunk.addTileEntity(te);
            }
        }
        return newChunk;
    }
}
