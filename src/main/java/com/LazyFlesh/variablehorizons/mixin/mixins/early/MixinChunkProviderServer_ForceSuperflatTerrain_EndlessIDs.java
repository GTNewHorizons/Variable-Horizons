package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.block.Block;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.LazyFlesh.variablehorizons.util.randomUtil;
import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;

@Mixin({ ChunkProviderServer.class })
public class MixinChunkProviderServer_ForceSuperflatTerrain_EndlessIDs {

    @Shadow
    public WorldServer worldObj;

    public MixinChunkProviderServer_ForceSuperflatTerrain_EndlessIDs() {}

    @ModifyVariable(
        at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/world/chunk/IChunkProvider;provideChunk(II)Lnet/minecraft/world/chunk/Chunk;"),
        method = { "originalLoadChunk" })
    public Chunk variablehorizons$forceSuperflatTerrainEndlessIds(Chunk chunk) {
        int dimensionID = worldObj.provider.dimensionId;
        Block[] ids = randomUtil.getOrBuildChunkBlocks(dimensionID);
        byte[] metadata = randomUtil.getOrBuildChunkMetadata(dimensionID);

        short[] biomeArray = ((ChunkBiomeHook) chunk).getBiomeShortArray();
        Chunk newChunk = new Chunk(chunk.worldObj, ids, metadata, chunk.xPosition, chunk.zPosition);
        ((ChunkBiomeHook) newChunk).setBiomeShortArray(biomeArray);
        newChunk.generateSkylightMap();
        return newChunk;
    }
}
