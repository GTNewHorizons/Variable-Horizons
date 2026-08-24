package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.LazyFlesh.variablehorizons.util.randomUtil;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import cpw.mods.fml.common.registry.GameRegistry;

@Mixin(ChunkProviderServer.class)
public class MixinChunkProviderServer_DisableModGeneration {

    @Unique
    private static final boolean IS_SUPERFLAT_ACTIVE = VariantNames.activeContains(VariantNames.SUPERFLAT.id);

    @Redirect(
        at = @At(
            remap = false,
            target = "Lcpw/mods/fml/common/registry/GameRegistry;generateWorld(IILnet/minecraft/world/World;Lnet/minecraft/world/chunk/IChunkProvider;Lnet/minecraft/world/chunk/IChunkProvider;)V",
            value = "INVOKE"),
        method = "populate(Lnet/minecraft/world/chunk/IChunkProvider;II)V")
    private void variablehorizons$disableModGeneration(int chunkX, int chunkZ, World world,
        IChunkProvider chunkProvider, IChunkProvider chunkGenerator) {
        if (IS_SUPERFLAT_ACTIVE) {
            return;
        }
        if (randomUtil.generateVoidInThisDim(world.provider.dimensionId)) {
            return;
        }
        GameRegistry.generateWorld(chunkX, chunkZ, world, chunkProvider, chunkGenerator);
    }
}
