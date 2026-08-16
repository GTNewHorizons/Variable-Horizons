package com.LazyFlesh.variablehorizons.mixin.mixins.late;

import net.minecraft.entity.player.EntityPlayerMP;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.LazyFlesh.variablehorizons.util.randomUtil;

import micdoodle8.mods.galacticraft.core.client.gui.screen.GuiCelestialSelection;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;

@Mixin(WorldUtil.class)
public class MixinWorldUtil_DisableCelestialSelection {

    /**
     * @author Variable Horizons
     * @reason Disable celestial selection menu if dimlocked
     */
    @Overwrite(remap = false)
    public static void toCelestialSelection(EntityPlayerMP player, GCPlayerStats stats, int tier,
        GuiCelestialSelection.MapMode mapMode) {
        player.addChatMessage(new net.minecraft.util.ChatComponentText(randomUtil.getRandomPortalMessage()));
    }
}
