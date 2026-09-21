package com.LazyFlesh.variablehorizons.variants.runtime;

import java.lang.reflect.Field;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.WorldEvent;

import com.LazyFlesh.variablehorizons.Config.MobConfig;
import com.LazyFlesh.variablehorizons.VariableHorizons;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import atomicstryker.infernalmobs.common.InfernalMobsCore;
import atomicstryker.infernalmobs.common.modifiers.MM_Ender;
import atomicstryker.infernalmobs.common.modifiers.MM_Lifesteal;
import atomicstryker.infernalmobs.common.modifiers.MM_Ninja;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Configuration.RTConfiguration;
import lumien.randomthings.Configuration.Settings;
import toast.specialMobs.Properties;
import toast.specialMobs.RandomHelper;
import toast.specialMobs._SpecialMobs;
import toast.specialMobs.entity.ISpecialMob;
import toast.specialMobs.entity.SpecialMobData;

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

        Settings.BLOODMOON_DIM_WHITELIST = new int[] {};
        Settings.BLOODMOON_CHANCE = 1;
        Settings.BLOODMOON_INITIAL_PAUSE = 0;
        Settings.BLOODMOON_SPAWNLIMIT_MULTIPLIER = MobConfig.bloodMoonMobcapMultiplier;
        Settings.BLOODMOON_VISUAL_REDLIGHT = MobConfig.bloodMoonTint;

        modifyInfernalMobDifficulty(MobConfig.infernalMobDifficulty);
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
        if (!entityData.getBoolean("GenericCrimsonBuffsApplied")) {
            IAttributeInstance maxHealth = entity.getEntityAttribute(SharedMonsterAttributes.maxHealth);
            if (maxHealth != null) {
                double newMaxHealth = maxHealth.getBaseValue() * MobConfig.mobHealthMultiplier;
                maxHealth.setBaseValue(newMaxHealth);
                entity.setHealth((float) newMaxHealth);
            }
            IAttributeInstance damage = entity.getEntityAttribute(SharedMonsterAttributes.attackDamage);
            if (damage != null) {
                double newDamage = damage.getBaseValue() * MobConfig.mobDamageMultiplier;
                damage.setBaseValue(newDamage);
            }
            entityData.setBoolean("GenericCrimsonBuffsApplied", true);
        }
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        if (!VariantNames.CRIMSON_APOCALYPSE.hasLoaded) {
            return;
        }

        EntityLivingBase entity = event.entityLiving;
        if (entity.worldObj.isRemote) {
            return;
        }

        if (entity.ticksExisted == 1) {
            NBTTagCompound entityData = entity.getEntityData();
            if (!entityData.getBoolean("CrimsonArrowBuffsApplied")) {
                if (entity instanceof ISpecialMob) {
                    SpecialMobData specialData = ((ISpecialMob) entity).getSpecialData();
                    if (specialData != null) {
                        specialData.arrowDamage *= (float) MobConfig.mobDamageMultiplier;
                    }
                }
                entityData.setBoolean("CrimsonArrowBuffsApplied", true);
            }
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

    public static void modifyInfernalMobDifficulty(int level) {
        InfernalMobsCore imCore = InfernalMobsCore.instance();
        imCore.loadConfig();
        imCore.loadMods();

        imCore.dimensionBlackList = new ArrayList<>();
        imCore.modifierLoaders.add(new MM_Lifesteal.Loader());
        imCore.modifierLoaders.add(new MM_Ninja.Loader());
        imCore.modifierLoaders.add(new MM_Ender.Loader());

        int modifierModifier = 0;
        int rarityModifier = 1;
        switch (level) {
            case 1 -> {
                modifierModifier = 2;
            }
            case 2 -> {
                modifierModifier = 3;
                rarityModifier = 2;
            }
            case 3 -> {
                modifierModifier = 20;
                rarityModifier = 100;
            }
        }

        imCore.minEliteModifiers += modifierModifier;
        imCore.maxEliteModifiers += modifierModifier;
        imCore.minUltraModifiers += modifierModifier;
        imCore.maxUltraModifiers += modifierModifier;
        imCore.minInfernoModifiers += modifierModifier;
        imCore.maxInfernoModifiers += modifierModifier;

        imCore.eliteRarity = Math.max(imCore.eliteRarity / rarityModifier, 1);
        imCore.ultraRarity = Math.max(imCore.ultraRarity / rarityModifier, 1);
        imCore.infernoRarity = Math.max(imCore.infernoRarity / rarityModifier, 1);
    }

    @SideOnly(Side.CLIENT)
    private static boolean isDifficultyButton(GuiButton b) {
        return b.id == 108 || b.displayString.startsWith(I18n.format("options.difficulty"));
    }

    public static void applyDifficultyToServer() {
        if (!VariantNames.CRIMSON_APOCALYPSE.hasLoaded) {
            return;
        }
        MinecraftServer server = MinecraftServer.getServer();
        if (server == null) {
            return;
        }
        // difficulty setter
        server.func_147139_a(EnumDifficulty.HARD);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (!VariantNames.CRIMSON_APOCALYPSE.hasLoaded) {
            return;
        }
        if (event.world.isRemote) {
            Minecraft.getMinecraft().gameSettings.difficulty = EnumDifficulty.HARD;
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (!VariantNames.CRIMSON_APOCALYPSE.hasLoaded) {
            return;
        }
        if (!(event.gui instanceof GuiOptions)) {
            return;
        }
        for (Object object : event.buttonList) {
            GuiButton button = (GuiButton) object;
            if (isDifficultyButton(button)) {
                button.enabled = false;
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onAction(GuiScreenEvent.ActionPerformedEvent.Pre event) {
        if (!VariantNames.CRIMSON_APOCALYPSE.hasLoaded) {
            return;
        }
        if (event.gui instanceof GuiOptions && isDifficultyButton(event.button)) {
            event.setCanceled(true);
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

        RTConfiguration.syncConfig();
        InfernalMobsCore imCore = InfernalMobsCore.instance();
        imCore.loadConfig();
        imCore.loadMods();
    }
}
