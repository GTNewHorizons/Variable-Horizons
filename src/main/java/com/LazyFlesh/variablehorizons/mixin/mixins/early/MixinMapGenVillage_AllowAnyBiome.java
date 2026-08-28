package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import java.util.List;

import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.MapGenVillage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MapGenVillage.class)
public class MixinMapGenVillage_AllowAnyBiome {

    @Redirect(
        method = "canSpawnStructureAtCoords(II)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/biome/WorldChunkManager;areBiomesViable(IIILjava/util/List;)Z"))
    private boolean variablehorizons$allowVillageBiomes(WorldChunkManager chunkManager, int x, int z, int radius,
        List allowedBiomes) {
        return true;
    }
}
