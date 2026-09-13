package com.LazyFlesh.variablehorizons.variants.runtime;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.LazyFlesh.variablehorizons.Config.MobConfig;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CrimsonApocalypse extends VariantLoader implements IRuntimeVariant {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.CRIMSON_APOCALYPSE.hasLoaded = true;

    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onEntitySpawn(EntityJoinWorldEvent event) {
        if (!VariantNames.CRIMSON_APOCALYPSE.hasLoaded) {
            return;
        }

        if (event.world.isRemote || !(event.entity instanceof EntityLivingBase entity)
            || event.entity instanceof EntityPlayer) {
            return;
        }

        NBTTagCompound entityData = entity.getEntityData();
        if (!entityData.getBoolean("CrimsonBuffsApplied")) {
            IAttributeInstance maxHealth = entity.getEntityAttribute(SharedMonsterAttributes.maxHealth);
            if (maxHealth != null) {
                double newMaxHealth = maxHealth.getBaseValue() * MobConfig.mobHealthMultiplier;
                maxHealth.setBaseValue(newMaxHealth);
                entity.setHealth((float) newMaxHealth);
            }
            IAttributeInstance damage = entity.getEntityAttribute(SharedMonsterAttributes.attackDamage);
            if (damage != null) {
                double newDamage = damage.getBaseValue() * MobConfig.mobMeleeMultiplier;
                damage.setBaseValue(newDamage);
            }
            entityData.setBoolean("CrimsonBuffsApplied", true);
        }
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // none to add
    }

    @Override
    public void undoVariant(VariantNames... activeVariants) {
        VariantNames.CRIMSON_APOCALYPSE.hasLoaded = false;
    }
}
