package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.LazyFlesh.variablehorizons.util.randomUtil;

@Mixin(ChunkProviderServer.class)
public abstract class MixinChunkProviderServer_ReplaceBlocks {

    @Shadow
    public WorldServer worldObj;

    @Inject(method = "populate", at = @At("HEAD"))
    private void variablehorizons$enterGen(IChunkProvider provider, int chunkX, int chunkZ, CallbackInfo ci) {
        randomUtil.WorldGenFlag.enter();
    }

    @Inject(method = "populate", at = @At("RETURN"))
    private void variablehorizons$exitGenAndSweep(IChunkProvider provider, int chunkX, int chunkZ, CallbackInfo ci) {

        randomUtil.WorldGenFlag.exit();
        int startX = chunkX * 16 + 8;
        int startZ = chunkZ * 16 + 8;

        for (int x = startX; x < startX + 16; x++) {
            for (int z = startZ; z < startZ + 16; z++) {

                Chunk chunk = this.worldObj.getChunkFromBlockCoords(x, z);
                int localX = x & 15;
                int localZ = z & 15;

                for (int y = 0; y < 256; y++) {
                    Block block = chunk.getBlock(localX, y, localZ);

                    if (block != null && block != Blocks.air && block != randomUtil.REPLACEMENT_BLOCK) {
                        chunk.removeTileEntity(localX, y, localZ);
                        chunk.func_150807_a(
                            localX,
                            y,
                            localZ,
                            randomUtil.REPLACEMENT_BLOCK,
                            randomUtil.REPLACEMENT_META);
                    }
                }
            }
        }
    }

}
