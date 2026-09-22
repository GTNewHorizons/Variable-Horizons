package com.LazyFlesh.variablehorizons.variants.runtime;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingEvent;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import toast.specialMobs.entity.ISpecialMob;
import toast.specialMobs.entity.SpecialMobData;
import toast.specialMobs.entity.creeper.Entity_SpecialCreeper;

public class Chaos extends VariantLoader implements IRuntimeVariant {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.CHAOS.hasLoaded = true;
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // none to add
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        if (!VariantNames.CHAOS.hasLoaded) {
            return;
        }

        EntityLivingBase entity = event.entityLiving;
        if (entity.worldObj.isRemote) {
            return;
        }

        if (entity.ticksExisted == 2) {
            NBTTagCompound entityData = entity.getEntityData();
            if (!entityData.getBoolean("ChaosModified")) {
                Random statRandomizer = new Random();
                IAttributeInstance maxHealth = entity.getEntityAttribute(SharedMonsterAttributes.maxHealth);
                if (maxHealth != null) {
                    float factor = 1 - statRandomizer.nextFloat();
                    float factor2 = (1 - statRandomizer.nextFloat()) * 100;
                    float healthModifier = statRandomizer.nextBoolean() ? factor2 * factor : 1 / (factor2 * factor);
                    double newMaxHealth = maxHealth.getBaseValue() * healthModifier;
                    maxHealth.setBaseValue(newMaxHealth);
                    entity.setHealth((float) newMaxHealth);
                }
                IAttributeInstance damage = entity.getEntityAttribute(SharedMonsterAttributes.attackDamage);
                if (damage != null) {
                    float factor = 1 - statRandomizer.nextFloat();
                    float factor2 = (1 - statRandomizer.nextFloat()) * 100;
                    float damageModifier = statRandomizer.nextBoolean() ? factor2 * factor : 1 / (factor2 * factor);
                    double newDamage = damage.getBaseValue() * damageModifier;
                    damage.setBaseValue(newDamage);
                }
                IAttributeInstance speed = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
                if (speed != null) {
                    float factor = 1 - statRandomizer.nextFloat();
                    float factor2 = (1 - statRandomizer.nextFloat()) * 3;
                    float speedModifier = statRandomizer.nextBoolean() ? factor2 * factor : 1 / (factor2 * factor);
                    double newSpeed = speed.getBaseValue() * speedModifier;
                    speed.setBaseValue(newSpeed);
                }
                if (entity instanceof ISpecialMob) {
                    SpecialMobData specialData = ((ISpecialMob) entity).getSpecialData();
                    if (specialData != null) {
                        float factor = 1 - statRandomizer.nextFloat();
                        float factor2 = (1 - statRandomizer.nextFloat()) * 100;
                        float arrowDamageModifier = statRandomizer.nextBoolean() ? factor2 * factor
                            : 1 / (factor2 * factor);
                        specialData.arrowDamage *= arrowDamageModifier;
                    }
                    if (entity instanceof Entity_SpecialCreeper) {
                        float factor = 1 - statRandomizer.nextFloat();
                        float factor2 = (1 - statRandomizer.nextFloat()) * 5;
                        float explosionModifier = statRandomizer.nextBoolean() ? factor2 * factor
                            : 1 / (factor2 * factor);
                        ((Entity_SpecialCreeper) entity).explosionRadius = (int) (((Entity_SpecialCreeper) entity).explosionRadius
                            * explosionModifier);
                    }
                }
                entityData.setBoolean("ChaosModified", true);
            }
        }
    }

    @Override
    public void undoVariant(VariantNames... activeVariants) {
        VariantNames.CHAOS.hasLoaded = false;
    }
}
