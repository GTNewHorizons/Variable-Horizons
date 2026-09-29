package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.world.gen.structure.MapGenVillage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import rwg.world.ChunkGeneratorRealistic;

@Mixin(value = ChunkGeneratorRealistic.class, remap = false)
public interface AccessorChunkGeneratorRealistic {

    @Accessor("villageGenerator")
    MapGenVillage getVillageGenerator();
}
