package com.LazyFlesh.variablehorizons.mixin.mixins.late;

import static gregtech.api.enums.Mods.HardcoreEnderExpansion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.LazyFlesh.variablehorizons.variants.VariantNames;

import chylex.hee.entity.boss.EntityBossDragon;

@Mixin(EntityBossDragon.class)
public class MixinEntityBossDragon_DisableExitPortalHEE {

    @Inject(method = "createEnderPortal", cancellable = true, at = @At("HEAD"), remap = false)
    private void variablehorizons$disableExitPortalHEE(int x, int z, CallbackInfo ci) {
        if (VariantNames.activeContains(VariantNames.DIMLOCKED.id) && HardcoreEnderExpansion.isModLoaded()) {
            World world = ((Entity) (Object) this).worldObj;
            for (EntityPlayer player : world.playerEntities) {
                player.addChatMessage(
                    new net.minecraft.util.ChatComponentText(
                        StatCollector.translateToLocal("variants.portals.message")));
            }
            ci.cancel();
        }
    }
}
