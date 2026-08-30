package com.LazyFlesh.variablehorizons.mixin;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.Config.GogConfig;
import com.LazyFlesh.variablehorizons.variants.VariantNames;
import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;
import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.ITargetMod;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;
import com.gtnewhorizon.gtnhmixins.builders.TargetModBuilder;

import cpw.mods.fml.common.versioning.ComparableVersion;

public enum Mixins implements IMixins {

    DISABLE_CHUNK_TERRAIN_GENERATION(new MixinBuilder().addCommonMixins("MixinChunkProviderServer_DisableTerrain")
        .setApplyIf(
            () -> (VariantNames.activeContains(VariantNames.VOID_WORLD.id)
                || VariantNames.activeContains(VariantNames.VOID_ISLAND.id)) && !GeneralConfig.disableVariants)
        .addExcludedMod(TargetedMod.ENDLESSIDS)
        .setPhase(Phase.EARLY)),
    DISABLE_CHUNK_TERRAIN_GENERATION_ENDLESS_IDS(
        new MixinBuilder().addCommonMixins("MixinChunkProviderServer_DisableTerrain_EndlessIDs")
            .setApplyIf(
                () -> (VariantNames.activeContains(VariantNames.VOID_WORLD.id)
                    || VariantNames.activeContains(VariantNames.VOID_ISLAND.id)) && !GeneralConfig.disableVariants)
            .addRequiredMod(TargetedMod.ENDLESSIDS)
            .setPhase(Phase.EARLY)),
    DISABLE_WORLD_TYPE_CHUNK_POPULATION(
        new MixinBuilder("Disable chunk population tied to chunk generation (ores/structure)")
            .addCommonMixins("MixinChunkProviderServer_DisablePopulation", "AccessorChunkProviderGenerate")
            .setApplyIf(
                () -> (VariantNames.activeContains(VariantNames.VOID_WORLD.id)
                    || VariantNames.activeContains(VariantNames.VOID_ISLAND.id)
                    || (VariantNames.activeContains(VariantNames.SUPERFLAT.id)
                        && !GeneralConfig.allowSuperflatPopulation))
                    && !GeneralConfig.disableVariants)
            .setPhase(Phase.EARLY)),
    DISABLE_MODDED_CHUNK_POPULATION(new MixinBuilder("Disable all other mod chunk population (e.g. Natura clouds)")
        .addCommonMixins("MixinChunkProviderServer_DisableModGeneration")
        .setApplyIf(
            () -> (VariantNames.activeContains(VariantNames.VOID_WORLD.id)
                || VariantNames.activeContains(VariantNames.VOID_ISLAND.id)
                || (VariantNames.activeContains(VariantNames.SUPERFLAT.id) && !GeneralConfig.allowSuperflatPopulation))
                && !GogConfig.dragonTime
                && !GeneralConfig.disableVariants)
        .setPhase(Phase.EARLY)),
    SUPERFLAT_CHUNK_TERRAIN_GENERATION(new MixinBuilder("Make the world and all dims superflat")
        .addCommonMixins("MixinChunkProviderServer_ForceSuperflatTerrain")
        .setApplyIf(() -> (VariantNames.activeContains(VariantNames.SUPERFLAT.id) && !GeneralConfig.disableVariants))
        .addExcludedMod(TargetedMod.ENDLESSIDS)
        .setPhase(Phase.EARLY)),
    SUPERFLAT_CHUNK_TERRAIN_GENERATION_ENDLESS_IDS(new MixinBuilder("Make the world and all dims superflat")
        .addCommonMixins("MixinChunkProviderServer_ForceSuperflatTerrain_EndlessIDs")
        .setApplyIf(() -> (VariantNames.activeContains(VariantNames.SUPERFLAT.id) && !GeneralConfig.disableVariants))
        .addRequiredMod(TargetedMod.ENDLESSIDS)
        .setPhase(Phase.EARLY)),
    SKYGRID_CHUNK_TERRAIN_GENERATION(new MixinBuilder("Make the world and all dims a skygrid")
        .addCommonMixins("MixinChunkProviderServer_ForceSkygridTerrain")
        .setApplyIf(() -> (VariantNames.activeContains(VariantNames.SKYGRID.id) && !GeneralConfig.disableVariants))
        .addExcludedMod(TargetedMod.ENDLESSIDS)
        .setPhase(Phase.EARLY)),
    SKYGRID_CHUNK_TERRAIN_GENERATION_ENDLESS_IDS(new MixinBuilder("Make the world and all dims a skygrid")
        .addCommonMixins("MixinChunkProviderServer_ForceSkygridTerrain_EndlessIDs")
        .setApplyIf(() -> (VariantNames.activeContains(VariantNames.SKYGRID.id) && !GeneralConfig.disableVariants))
        .addRequiredMod(TargetedMod.ENDLESSIDS)
        .setPhase(Phase.EARLY)),
    ALLOW_VILLAGE_GENERATION_IN_ANY_BIOME(new MixinBuilder("Allow villages to generate in any biome")
        .addCommonMixins(
            "MixinStructureVillagePieces_AllowAnyBiome",
            "AccessorChunkGeneratorRealistic",
            "MixinMapGenVillage_AllowAnyBiome")
        .setApplyIf(() -> (VariantNames.activeContains(VariantNames.SUPERFLAT.id) && !GeneralConfig.disableVariants))
        .addRequiredMod(TargetedMod.RWG)
        .setPhase(Phase.EARLY)),
    ALLOW_RESPAWN_IN_DIMENSION(new MixinBuilder("Allow respawning in another dimension")
        .addCommonMixins("MixinWorldProvider_AllowRespawnInDimension")
        .setApplyIf(
            () -> (VariantNames.activeContains(VariantNames.CUSTOM_DIM_START.id)
                || VariantNames.activeContains(VariantNames.VOID_ISLAND.id)) && !GeneralConfig.disableVariants)
        .setPhase(Phase.EARLY)),
    SET_INITIAL_SPAWN_DIMENSION(new MixinBuilder("Set spawn dimension to another dimension")
        .addCommonMixins("MixinServerConfigurationManager_ChangeInitialSpawnDimension")
        .setApplyIf(
            () -> VariantNames.activeContains(VariantNames.CUSTOM_DIM_START.id) && !GeneralConfig.disableVariants)
        .setPhase(Phase.EARLY)),
    SET_EXACT_SPAWN_LOCATION(new MixinBuilder("Set exact spawn location without variance and y = 65")
        .addCommonMixins("MixinWorldProvider_SetExactSpawn")
        .setApplyIf(() -> VariantNames.activeContains(VariantNames.VOID_ISLAND.id) && !GeneralConfig.disableVariants)
        .setPhase(Phase.EARLY)),
    LOCK_TO_DIMENSION_TRAVEL_TO_DIM(new MixinBuilder(
        "Forcibly return a player to the specified dim upon trying to leave it, travelToDimension method")
            .addCommonMixins("MixinEntityPlayerMP_LockDimension")
            .setApplyIf(() -> VariantNames.activeContains(VariantNames.DIMLOCKED.id) && !GeneralConfig.disableVariants)
            .setPhase(Phase.EARLY)),
    LOCK_TO_DIMENSION_TRANSFER_PLAYER_TO_DIM(new MixinBuilder(
        "Forcibly return a player to the specified dim upon trying to leave, transferPlayerToDimension method")
            .addCommonMixins("MixinServerConfigurationManager_LockDimension")
            .setApplyIf(() -> VariantNames.activeContains(VariantNames.DIMLOCKED.id) && !GeneralConfig.disableVariants)
            .setPhase(Phase.EARLY)),
    INCREASE_SLIME_SPAWNING_HEIGHT(
        new MixinBuilder("Increase slime spawns to y = 80").addCommonMixins("MixinEntitySlime_ExtendedSpawnRange")
            .setApplyIf(() -> VariantNames.activeContains(VariantNames.SUPERFLAT.id) && !GeneralConfig.disableVariants)
            .setPhase(Phase.EARLY)),
    DISABLE_CELESTIAL_SELECTION(new MixinBuilder("Disable the galacticraft planet map")
        .addCommonMixins("MixinWorldUtil_DisableCelestialSelection")
        .setApplyIf(() -> VariantNames.activeContains(VariantNames.NO_ROCKET.id) && !GeneralConfig.disableVariants)
        .addRequiredMod(TargetedMod.GALACTICRAFT_CORE)
        .setPhase(Phase.LATE)),
    ADD_SEMINING_RECIPE(
        new MixinBuilder("Add custom space mining recipes").addCommonMixins("MixinSpaceMiningRecipes_AddSEminingrecipe")
            .setApplyIf(
                () -> VariantNames.activeContains(VariantNames.DIMLOCKED.id) && GeneralConfig.startingDimID != 100
                    && !GeneralConfig.disableVariants)
            .addRequiredMod(TargetedMod.GREGTECH)
            .setPhase(Phase.LATE)),
    REMOVE_ORECHID_IGNEM_DIMRESTRICTION(new MixinBuilder("Remove dimension restriction for OrechidIgnem")
        .addCommonMixins("MixinSubTileOrechidIgnem_LetOrechidIgnemRunBesidesNether")
        .setApplyIf(
            () -> VariantNames.activeContains(VariantNames.DIMLOCKED.id)
                && VariantNames.activeContains(VariantNames.NO_QUEST_REWARDS.id)
                && GeneralConfig.startingDimID != -1
                && !GeneralConfig.disableVariants)
        .addRequiredMod(TargetedMod.BOTANIA)
        .setPhase(Phase.LATE))

    ;

    private final MixinBuilder builder;

    Mixins(MixinBuilder builder) {
        this.builder = builder;
    }

    @Nonnull
    @Override
    public MixinBuilder getBuilder() {
        return builder;
    }

    public enum TargetedMod implements ITargetMod {

        ENDLESSIDS("com.falsepattern.endlessids.asm.EndlessIDsCore", "endlessids"),
        GALACTICRAFT_CORE("GalacticraftCore"),
        GREGTECH("gregtech"),
        BOTANIA("Botania"),
        RWG(null, "RWG", "rwg.world.ChunkGeneratorRealistic");

        private final TargetModBuilder builder;

        TargetedMod(TargetModBuilder builder) {
            this.builder = builder;
        }

        TargetedMod(String modId) {
            this(null, modId, null);
        }

        TargetedMod(String coreModClass, String modId) {
            this(coreModClass, modId, null);
        }

        TargetedMod(String coreModClass, String modId, String targetClass) {
            this.builder = new TargetModBuilder().setCoreModClass(coreModClass)
                .setModId(modId)
                .setTargetClass(targetClass);
        }

        @Nonnull
        @Override
        public TargetModBuilder getBuilder() {
            return builder;
        }

        private static boolean isVersionLessThan(String version, String target) {
            return new ComparableVersion(version).compareTo(new ComparableVersion(target)) < 0;
        }
    }

    @LateMixin
    public static class LateMixinLoader implements ILateMixinLoader {

        @Override
        public String getMixinConfig() {
            return "mixins.variablehorizons.late.json";
        }

        @Override
        public @NotNull List<String> getMixins(Set<String> loadedMods) {
            return IMixins.getLateMixins(Mixins.class, loadedMods);
        }
    }
}
