package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import java.util.List;

import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.StructureVillagePieces;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StructureVillagePieces.class)
public class MixinStructureVillagePieces_AllowAnyBiome {

    @Redirect(
        method = "getNextVillageStructureComponent",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/biome/WorldChunkManager;areBiomesViable(IIILjava/util/List;)Z"))
    private static boolean variablehorizons$allowVillageStructureBiomes(WorldChunkManager chunkManager, int x, int z,
        int radius, List allowedBiomes) {
        return true;
    }

    @Redirect(
        method = "getNextComponentVillagePath",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/biome/WorldChunkManager;areBiomesViable(IIILjava/util/List;)Z"))
    private static boolean variablehorizons$allowVillagePathBiomes(WorldChunkManager chunkManager, int x, int z,
        int radius, List allowedBiomes) {
        return true;
    }
}
