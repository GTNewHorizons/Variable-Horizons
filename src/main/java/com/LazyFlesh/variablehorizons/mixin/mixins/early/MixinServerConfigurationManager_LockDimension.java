package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.ServerConfigurationManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.randomUtil;

@Mixin(ServerConfigurationManager.class)
public class MixinServerConfigurationManager_LockDimension {

    @Inject(
        method = "transferPlayerToDimension(Lnet/minecraft/entity/player/EntityPlayerMP;ILnet/minecraft/world/Teleporter;)V",
        cancellable = true,
        at = @At("HEAD"),
        remap = false)
    private void variablehorizons$lockDimension(EntityPlayerMP player, int dimension,
        net.minecraft.world.Teleporter teleporter, CallbackInfo ci) {
        int lockedDimension = GeneralConfig.startingDimID;
        if (dimension != lockedDimension) {
            player.addChatMessage(new net.minecraft.util.ChatComponentText(randomUtil.getRandomPortalMessage()));
            ci.cancel();
        }
    }

}
