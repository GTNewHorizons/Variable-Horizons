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
import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;

@Mixin({ ChunkProviderServer.class })
public class MixinChunkProviderServer_ForceSuperflatTerrain_EndlessIDs {

    @Shadow
    public WorldServer worldObj;

    @Unique
    private static final int BLOCKS_PER_CHUNK = 65536;

    public MixinChunkProviderServer_ForceSuperflatTerrain_EndlessIDs() {}

    @ModifyVariable(
        at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/world/chunk/IChunkProvider;provideChunk(II)Lnet/minecraft/world/chunk/Chunk;"),
        method = { "originalLoadChunk" })
    public Chunk variablehorizons$forceSuperflatTerrainEndlessIds(Chunk chunk) {
        Block[] ids = new Block[BLOCKS_PER_CHUNK];
        byte[] metadata = new byte[BLOCKS_PER_CHUNK];
        SuperflatBlocks.SuperflatLayer[] blockLayers = SuperflatBlocks
            .getSuperflatLayers(worldObj.provider.dimensionId);
        Arrays.fill(ids, Blocks.air);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 4; y++) {
                    int index = (x << 12) | (z << 8) | y + 61;
                    ids[index] = blockLayers[y].block();
                    metadata[index] = (byte) blockLayers[y].metadata();
                }
            }
        }

        short[] biomeArray = ((ChunkBiomeHook) chunk).getBiomeShortArray();
        Chunk newChunk = new Chunk(chunk.worldObj, ids, metadata, chunk.xPosition, chunk.zPosition);
        ((ChunkBiomeHook) newChunk).setBiomeShortArray(biomeArray);
        newChunk.generateSkylightMap();

        return newChunk;
    }
}
