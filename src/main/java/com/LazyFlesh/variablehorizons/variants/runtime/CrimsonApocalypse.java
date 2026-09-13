package com.LazyFlesh.variablehorizons.variants.runtime;

import java.lang.reflect.Field;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.LazyFlesh.variablehorizons.Config.MobConfig;
import com.LazyFlesh.variablehorizons.VariableHorizons;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import toast.specialMobs.Properties;
import toast.specialMobs.RandomHelper;
import toast.specialMobs._SpecialMobs;

public class CrimsonApocalypse extends VariantLoader implements IRuntimeVariant {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.CRIMSON_APOCALYPSE.hasLoaded = true;
        for (String mobName : _SpecialMobs.MONSTER_KEY) {
            modifySpecialMobsWeight(mobName, "_vanilla", 1);
        }

        for (int i = 0; i < _SpecialMobs.MONSTER_TYPES.length; i++) {
            for (String mobVariant : _SpecialMobs.MONSTER_TYPES[i]) {
                modifySpecialMobsWeight(_SpecialMobs.MONSTER_KEY[i], mobVariant, 3);
            }
        }
        modifySpecialMobsWeight("Skeleton", "Thief", 0);
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

    private void modifySpecialMobsWeight(String mobName, String targetType, int newWeight) {
        int mobIndex = -1;
        int typeIndex = -1;

        for (int i = 0; i < _SpecialMobs.MONSTER_KEY.length; i++) {
            if (_SpecialMobs.MONSTER_KEY[i].equalsIgnoreCase(mobName)) {
                mobIndex = i;
                break;
            }
        }

        if (mobIndex == -1) {
            VariableHorizons.LOG.warn("Could not find Special Mobs base mob: {}", mobName);
            return;
        }

        if (targetType.equalsIgnoreCase("_vanilla")) {
            typeIndex = 0;
        } else {
            for (int j = 0; j < _SpecialMobs.MONSTER_TYPES[mobIndex].length; j++) {
                if (_SpecialMobs.MONSTER_TYPES[mobIndex][j].equalsIgnoreCase(targetType)) {
                    // +1 because index 0 is taken by _vanilla
                    typeIndex = j + 1;
                    break;
                }
            }
        }

        if (typeIndex == -1) {
            VariableHorizons.LOG.warn("Could not find mob variant: {} for {}", targetType, mobName);
            return;
        }

        int clampedNewWeight = Math.max(0, newWeight);
        int[][] liveWeights = Properties.monsterWeights();
        int oldWeight = liveWeights[mobIndex][typeIndex];
        int weightDifference = clampedNewWeight - oldWeight;
        liveWeights[mobIndex][typeIndex] = clampedNewWeight;

        if (weightDifference != 0) {
            try {
                Field totalWeightsField = RandomHelper.class.getDeclaredField("totalMonsterWeights");
                totalWeightsField.setAccessible(true);
                int[] totalWeights = (int[]) totalWeightsField.get(null);
                totalWeights[mobIndex] += weightDifference;
            } catch (Exception e) {
                VariableHorizons.LOG.warn("Failed to update total mob weights");
            }
        }
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // none to add
    }

    @Override
    public void undoVariant(VariantNames... activeVariants) {
        VariantNames.CRIMSON_APOCALYPSE.hasLoaded = false;
        for (String mobName : _SpecialMobs.MONSTER_KEY) {
            modifySpecialMobsWeight(mobName, "_vanilla", 3);
        }

        for (int i = 0; i < _SpecialMobs.MONSTER_TYPES.length; i++) {
            for (String mobVariant : _SpecialMobs.MONSTER_TYPES[i]) {
                modifySpecialMobsWeight(_SpecialMobs.MONSTER_KEY[i], mobVariant, 3);
            }
        }
        modifySpecialMobsWeight("Skeleton", "Thief", 0);
        modifySpecialMobsWeight("Enderman", "Thief", 0);
        modifySpecialMobsWeight("Witch", "Wind", 0);
    }
}
