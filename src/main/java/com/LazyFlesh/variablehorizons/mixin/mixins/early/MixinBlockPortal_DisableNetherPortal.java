package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.block.BlockPortal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.LazyFlesh.variablehorizons.variants.VariantNames;

@Mixin(BlockPortal.class)
public class MixinBlockPortal_DisableNetherPortal {

    @Unique
    private static final java.util.Map<EntityPlayer, Long> variable_Horizons$lastWarned = new java.util.WeakHashMap<>();

    @Inject(
        method = "func_150000_e",
        cancellable = true,
        at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockPortal$Size;func_150859_c()V"))
    private void variablehorizons$disableNetherPortal(World world, int x, int y, int z,
        CallbackInfoReturnable<Boolean> cir) {
        if (VariantNames.activeContains(VariantNames.DIMLOCKED.id)) {
            for (EntityPlayer player : world.playerEntities) {
                double distance = player.getDistance(x + 0.5, y + 0.5, z + 0.5);
                if (distance <= 10.0) {
                    long now = world.getTotalWorldTime();
                    Long last = variable_Horizons$lastWarned.get(player);
                    if (last == null || now - last > 20) {
                        player.addChatMessage(
                            new net.minecraft.util.ChatComponentText(
                                StatCollector.translateToLocal("variants.portals.message")));
                        variable_Horizons$lastWarned.put(player, now);
                    }
                }
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
}
