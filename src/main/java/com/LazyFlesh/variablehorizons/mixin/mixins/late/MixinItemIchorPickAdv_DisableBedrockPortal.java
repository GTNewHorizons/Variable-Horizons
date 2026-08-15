package com.LazyFlesh.variablehorizons.mixin.mixins.late;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.kami.BlockBedrockPortal;
import thaumic.tinkerer.common.item.kami.tool.ItemIchorPickAdv;

@Mixin(value = ItemIchorPickAdv.class, remap = false)
public class MixinItemIchorPickAdv_DisableBedrockPortal {

    @Redirect(
        method = "onBlockStartBreak",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlock(IIILnet/minecraft/block/Block;)Z"),
        remap = false)
    private boolean variablehorizons$disableBedrockPortal(World world, int x, int y, int z, Block block,
        ItemStack Stack, int origX, int origY, int origZ, EntityPlayer player) {
        if (block == ThaumicTinkerer.registry.getFirstBlockFromClass(BlockBedrockPortal.class)) {
            player.addChatMessage(
                new net.minecraft.util.ChatComponentText(
                    StatCollector.translateToLocal("variants.portal_activation.message")));
            return false;
        }
        return world.setBlock(x, y, z, block);
    }

}
