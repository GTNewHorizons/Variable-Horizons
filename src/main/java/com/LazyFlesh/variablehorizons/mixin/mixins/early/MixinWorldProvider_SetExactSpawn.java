package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.LazyFlesh.variablehorizons.util.randomUtil;

@Mixin(WorldProvider.class)
public class MixinWorldProvider_SetExactSpawn {

    @Inject(method = "getRandomizedSpawnPoint", at = @At("HEAD"), cancellable = true, remap = false)
    private void variablehorizons$exactVoidSpawn(CallbackInfoReturnable<ChunkCoordinates> cir) {
        WorldProvider provider = (WorldProvider) (Object) this;
        if (randomUtil.voidIslandVoidCheck(provider.dimensionId)) {
            ChunkCoordinates spawnPoint = provider.worldObj.getSpawnPoint();
            spawnPoint.posY = 65;
            cir.setReturnValue(spawnPoint);
        }
    }
}
