package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.LazyFlesh.variablehorizons.util.superflat.SuperflatBlocks;

@Mixin(ChunkProviderServer.class)
public class MixinChunkProviderServer_ForceSuperflatTerrain {

    @Shadow
    public WorldServer worldObj;

    @Unique
    private static final int BLOCKS_PER_CHUNK = 65536;

    @ModifyVariable(
        at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/world/chunk/IChunkProvider;provideChunk(II)Lnet/minecraft/world/chunk/Chunk;"),
        method = "originalLoadChunk")
    public Chunk variablehorizons$forceSuperflatTerrain(Chunk chunk) {
        final Block[] ids = new Block[BLOCKS_PER_CHUNK];
        final byte[] metadata = new byte[BLOCKS_PER_CHUNK];
        Block[] blockLayers = SuperflatBlocks.getSuperflatBlocks(worldObj.provider.dimensionId);
        Arrays.fill(ids, Blocks.air);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 4; y++) {
                    int index = (x << 12) | (z << 8) | y + 61;
                    ids[index] = blockLayers[y];
                }
            }
        }

        byte[] chunkData = chunk.getBiomeArray();
        Chunk newChunk = new Chunk(chunk.worldObj, ids, metadata, chunk.xPosition, chunk.zPosition);
        newChunk.setBiomeArray(chunkData);
        newChunk.generateSkylightMap();
        return newChunk;
    }
}
