package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemEnderEye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEnderEye.class)
public class MixinItemEnderEye_DisableEndPortalActivation {

    @Unique
    private boolean variablehorizons$portalBlocked = false;

    @Inject(method = "onItemUse", at = @At("HEAD"))
    private void variablehorizons$resetFlag(ItemStack stack, EntityPlayer player, World world, int x, int y, int z,
        int side, float hitX, float hitY, float hitZ, CallbackInfoReturnable<Boolean> cir) {
        this.variablehorizons$portalBlocked = false;
    }

    @Redirect(
        method = "onItemUse",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlock(IIILnet/minecraft/block/Block;II)Z"))
    private boolean variablehorizons$blockPortalPlacement(World world, int x, int y, int z, Block block, int meta,
        int flags) {
        if (block == Blocks.end_portal) {
            this.variablehorizons$portalBlocked = true;
            return false;
        }
        return world.setBlock(x, y, z, block, meta, flags);
    }

    @Inject(method = "onItemUse", at = @At("RETURN"))
    private void variablehorizons$notifyIfBlocked(ItemStack stack, EntityPlayer player, World world, int x, int y,
        int z, int side, float hitX, float hitY, float hitZ, CallbackInfoReturnable<Boolean> cir) {
        if (this.variablehorizons$portalBlocked) {
            player.addChatMessage(
                new net.minecraft.util.ChatComponentText(StatCollector.translateToLocal("variants.portal_activation.message")));
            this.variablehorizons$portalBlocked = false;
        }
    }
}
