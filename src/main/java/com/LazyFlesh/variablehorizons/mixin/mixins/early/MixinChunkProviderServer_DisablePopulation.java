package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import java.util.Random;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.structure.MapGenVillage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.LazyFlesh.variablehorizons.util.randomUtil;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import rwg.world.ChunkGeneratorRealistic;

@Mixin(ChunkProviderServer.class)
public class MixinChunkProviderServer_DisablePopulation {

    @Shadow
    public WorldServer worldObj;

    @Unique
    private static final boolean IS_SUPERFLAT_ACTIVE = VariantNames.activeContains(VariantNames.SUPERFLAT.id);

    @Redirect(
        at = @At(
            target = "Lnet/minecraft/world/chunk/IChunkProvider;populate(Lnet/minecraft/world/chunk/IChunkProvider;II)V",
            value = "INVOKE"),
        method = "populate(Lnet/minecraft/world/chunk/IChunkProvider;II)V")
    private void variablehorizons$ignoreChunkPopulation(IChunkProvider chunkProvider, IChunkProvider chunkProvider2,
        int chunkX, int chunkZ) {
        if (IS_SUPERFLAT_ACTIVE) {

            MapGenVillage villageGen = null;
            if (chunkProvider instanceof ChunkProviderGenerate) {
                villageGen = ((AccessorChunkProviderGenerate) chunkProvider).getVillageGenerator();
            } else if (chunkProvider instanceof ChunkGeneratorRealistic) {
                villageGen = ((AccessorChunkGeneratorRealistic) chunkProvider).getVillageGenerator();
            }

            if (villageGen != null) {
                Random rand = new Random();
                rand.setSeed(this.worldObj.getSeed());
                long k = rand.nextLong() / 2L * 2L + 1L;
                long l = rand.nextLong() / 2L * 2L + 1L;
                rand.setSeed((long) chunkX * k + (long) chunkZ * l ^ this.worldObj.getSeed());

                villageGen.generateStructuresInChunk(this.worldObj, rand, chunkX, chunkZ);
            }
            return;
        }
        if (randomUtil.generateVoidInThisDim(worldObj.provider.dimensionId)) {
            if (randomUtil.voidIslandVoidCheck(worldObj.provider.dimensionId)) {
                ChunkCoordinates spawn = this.worldObj.getSpawnPoint();
                int spawnChunkX = spawn.posX >> 4;
                int spawnChunkZ = spawn.posZ >> 4;

                if (chunkX == spawnChunkX && chunkZ == spawnChunkZ) {
                    randomUtil.generateVoidIsland(spawn, worldObj, worldObj.provider.dimensionId);
                }

                if (this.worldObj.provider.dimensionId == 7) {
                    // if twilight, lower world spawn to 32 so player doesn't get stuck in a death loop
                    this.worldObj.getSpawnPoint().posY = 32;
                }
            }
            return;
        }
        chunkProvider.populate(chunkProvider2, chunkX, chunkZ);
    }
}
