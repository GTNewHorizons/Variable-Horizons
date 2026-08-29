package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.structure.MapGenVillage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkProviderGenerate.class)
public interface AccessorChunkProviderGenerate {

    @Accessor("villageGenerator")
    MapGenVillage getVillageGenerator();
}
