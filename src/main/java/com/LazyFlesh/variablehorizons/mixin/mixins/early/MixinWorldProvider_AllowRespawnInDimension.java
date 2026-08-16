package com.LazyFlesh.variablehorizons.mixin.mixins.early;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;

@Mixin(WorldProvider.class)
public class MixinWorldProvider_AllowRespawnInDimension {

    /**
     * @author Variable Horizons
     * @reason Overwrite spawn dim if using a non-OW variant
     */
    @Overwrite(remap = false)
    public int getRespawnDimension(EntityPlayerMP player) {
        return GeneralConfig.startingDimID;
    }
}
