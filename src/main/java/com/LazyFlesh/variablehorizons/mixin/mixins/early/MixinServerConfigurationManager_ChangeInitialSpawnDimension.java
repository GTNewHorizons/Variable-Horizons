package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.WorldProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;

@Mixin(ServerConfigurationManager.class)
public abstract class MixinServerConfigurationManager_ChangeInitialSpawnDimension {

    @ModifyArg(
        method = "createPlayerForUser",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/MinecraftServer;worldServerForDimension(I)Lnet/minecraft/world/WorldServer;"),
        index = 0)
    private int variablehorizons$createPlayerForUser(int dimension) {
        return GeneralConfig.startingDimID;
    }

    @Redirect(
        method = "respawnPlayer",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldProvider;canRespawnHere()Z"))
    private boolean variablehorizons$forceRespawnDimensionQuery(WorldProvider provider) {
        return false;
    }
}
