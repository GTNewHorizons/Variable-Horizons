package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.LazyFlesh.variablehorizons.util.randomUtil;

@Mixin(ChunkProviderServer.class)
public class MixinChunkProviderServer_DisablePopulation {

    @Shadow
    public WorldServer worldObj;

    @Redirect(
        at = @At(
            target = "Lnet/minecraft/world/chunk/IChunkProvider;populate(Lnet/minecraft/world/chunk/IChunkProvider;II)V",
            value = "INVOKE"),
        method = "populate(Lnet/minecraft/world/chunk/IChunkProvider;II)V")
    private void variablehorizons$ignoreChunkPopulation(IChunkProvider chunkProvider, IChunkProvider chunkProvider2,
        int x, int z) {
        if (randomUtil.generateVoidInThisDim(worldObj.provider.dimensionId)) {
            return;
        }
        chunkProvider.populate(chunkProvider2, x, z);
    }

}
