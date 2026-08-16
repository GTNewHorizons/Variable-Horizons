package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.entity.player.EntityPlayerMP;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.randomUtil;

@Mixin(EntityPlayerMP.class)
public class MixinEntityPlayerMP_LockDimension {

    @Inject(method = "travelToDimension", cancellable = true, at = @At("HEAD"))
    private void variablehorizons$lockDimension(int dimension, CallbackInfo ci) {
        EntityPlayerMP self = (EntityPlayerMP) (Object) this;
        int lockedDimension = GeneralConfig.startingDimID;

        if (dimension != lockedDimension) {
            self.addChatMessage(new net.minecraft.util.ChatComponentText(randomUtil.getRandomPortalMessage()));
            ci.cancel();
        }
    }
}
