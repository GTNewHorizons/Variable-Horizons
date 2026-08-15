package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityDragon.class)
public class MixinEntityDragon_DisableExitPortal {

    @Inject(method = "createEnderPortal", cancellable = true, at = @At("HEAD"))
    private void variablehorizons$disableExitPortal(int x, int z, CallbackInfo ci) {
        World world = ((Entity) (Object) this).worldObj;
        for (EntityPlayer player : world.playerEntities) {
            player.addChatMessage(
                new net.minecraft.util.ChatComponentText(StatCollector.translateToLocal("variants.portals.message")));
        }
        ci.cancel();
    }
}
