package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.entity.monster.EntitySlime;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntitySlime.class)
public class MixinEntitySlime_ExtendedSpawnRange {

    @ModifyConstant(method = "getCanSpawnHere", constant = @Constant(doubleValue = 40.0D))
    private double variablehorizons$extendSlimeSpawnRange(double original) {
        return 80.0D;
    }
}
