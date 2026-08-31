package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.ChunkProviderServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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
    private void variablehorizons$exitGen(IChunkProvider provider, int chunkX, int chunkZ, CallbackInfo ci) {
        randomUtil.WorldGenFlag.exit();

        variablehorizons$sweepChunk(chunkX, chunkZ);

        ChunkProviderServer self = (ChunkProviderServer) (Object) this;

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x == 0 && z == 0) continue;
                int nx = chunkX + x, nz = chunkZ + z;

                if (!self.chunkExists(nx, nz)) continue;

                Chunk neighbor = self.provideChunk(nx, nz);
                if (neighbor.isTerrainPopulated) {
                    variablehorizons$sweepChunk(nx, nz);
                }
            }
        }
    }

    @Unique
    private void variablehorizons$sweepChunk(int chunkX, int chunkZ) {
        Chunk chunk = this.worldObj.getChunkFromChunkCoords(chunkX, chunkZ);
        ExtendedBlockStorage[] storage = chunk.getBlockStorageArray();
        boolean anyChanged = false;
        PlayerManager playerManager = this.worldObj.getPlayerManager();

        for (int sy = 0; sy < storage.length; sy++) {
            ExtendedBlockStorage ebs = storage[sy];
            if (ebs == null) continue;

            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 16; y++) {
                    for (int z = 0; z < 16; z++) {
                        Block block = ebs.getBlockByExtId(x, y, z);
                        if (block == Blocks.air || block == randomUtil.REPLACEMENT_BLOCK) continue;

                        ebs.func_150818_a(x, y, z, randomUtil.REPLACEMENT_BLOCK);
                        ebs.setExtBlockMetadata(x, y, z, randomUtil.REPLACEMENT_META);
                        anyChanged = true;

                        if (playerManager != null) {
                            int worldX = (chunkX << 4) + x;
                            int worldY = (sy << 4) + y;
                            int worldZ = (chunkZ << 4) + z;
                            playerManager.markBlockForUpdate(worldX, worldY, worldZ);
                        }
                    }
                }
            }
        }

        if (anyChanged) {
            chunk.isModified = true;
            chunk.generateSkylightMap();
        }
    }
}
