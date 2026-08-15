package com.LazyFlesh.variablehorizons.mixin.mixins.late;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.brandon3055.draconicevolution.common.entity.EntityCustomDragon;

@Mixin(EntityCustomDragon.class)
public class MixinEntityCustomDragon_DisableExitPortalDE {

    @Inject(method = "createEnderPortal", cancellable = true, at = @At("HEAD"), remap = false)
    private void variablehorizons$disableExitPortalDE(int x, int z, CallbackInfo ci) {
        World world = ((Entity) (Object) this).worldObj;
        for (EntityPlayer player : world.playerEntities) {
            player.addChatMessage(
                new net.minecraft.util.ChatComponentText(StatCollector.translateToLocal("variants.portals.message")));
        }
        ci.cancel();
    }
}
