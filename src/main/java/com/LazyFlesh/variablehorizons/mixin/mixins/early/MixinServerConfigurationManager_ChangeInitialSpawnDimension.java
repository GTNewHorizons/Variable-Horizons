package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.server.management.ServerConfigurationManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;

@Mixin(ServerConfigurationManager.class)
public abstract class MixinServerConfigurationManager_ChangeInitialSpawnDimension {

    @ModifyArg(
        method = "createPlayerForUser",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/MinecraftServer;worldServerForDimension(I)Lnet/minecraft/world/WorldServer;"),
        index = 0)
    private int gtnhvariants$createPlayerForUser(int dimension) {
        return GeneralConfig.startingDimID;
    }
}
