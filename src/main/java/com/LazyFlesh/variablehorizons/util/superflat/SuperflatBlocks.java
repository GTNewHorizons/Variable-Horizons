package com.LazyFlesh.variablehorizons.util.superflat;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import com.github.bsideup.jabel.Desugar;

import cpw.mods.fml.common.registry.GameRegistry;

public enum SuperflatBlocks {

    NETHER(-1,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, Blocks.netherrack, Blocks.netherrack,
                GameRegistry.findBlock("BiomesOPlenty", "overgrownNetherrack") })),
    OVERWORLD(0, createSuperflatLayers(new Block[] { Blocks.bedrock, Blocks.dirt, Blocks.dirt, Blocks.grass })),
    END(1,
        createSuperflatLayers(new Block[] { Blocks.obsidian, Blocks.end_stone, Blocks.end_stone, Blocks.end_stone })),
    SPECTRE(2,
        createSuperflatLayers(
            new Block[] { GameRegistry.findBlock("RandomThings", "spectreBlock"),
                GameRegistry.findBlock("RandomThings", "spectreBlock"),
                GameRegistry.findBlock("RandomThings", "spectreBlock"),
                GameRegistry.findBlock("RandomThings", "spectreBlock") },
            new int[] { 0, 15, 15, 15 })),
    MAKEMAKE(25,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "makemakegrunt"),
                GameRegistry.findBlock("GalaxySpace", "makemakegrunt"),
                GameRegistry.findBlock("GalaxySpace", "makemakegrunt") },
            new int[] { 0, 1, 1, 0 })),
    MOON(28,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalacticraftCore", "tile.moonBlock"),
                GameRegistry.findBlock("GalacticraftCore", "tile.moonBlock"),
                GameRegistry.findBlock("GalacticraftCore", "tile.moonBlock") },
            new int[] { 0, 4, 3, 5 })),
    MARS(29,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalacticraftMars", "tile.mars"),
                GameRegistry.findBlock("GalacticraftMars", "tile.mars"),
                GameRegistry.findBlock("GalacticraftMars", "tile.mars") },
            new int[] { 0, 9, 6, 5 })),
    ASTEROIDS(30,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalacticraftMars", "tile.asteroidsBlock"),
                GameRegistry.findBlock("GalacticraftMars", "tile.asteroidsBlock"),
                GameRegistry.findBlock("GalacticraftMars", "tile.asteroidsBlock") },
            new int[] { 0, 0, 1, 2 })),
    ALPHA_CENTAURI_BB(31,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "acentauribbsubgrunt"),
                GameRegistry.findBlock("GalaxySpace", "acentauribbsubgrunt"),
                GameRegistry.findBlock("GalaxySpace", "acentauribbgrunt") })),
    BARNARDA_C(32,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "barnardaCdirt"),
                GameRegistry.findBlock("GalaxySpace", "barnardaCdirt"),
                GameRegistry.findBlock("GalaxySpace", "barnardaCgrass") })),
    KUIPER_BELT(33,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalacticraftMars", "tile.asteroidsBlock"),
                GameRegistry.findBlock("GalacticraftMars", "tile.asteroidsBlock"),
                GameRegistry.findBlock("GalacticraftMars", "tile.denseIce") },
            new int[] { 0, 0, 1, 0 })),
    EUROPA(35,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, Blocks.water, GameRegistry.findBlock("GalaxySpace", "europagrunt"),
                GameRegistry.findBlock("GalaxySpace", "europagrunt") },
            new int[] { 0, 0, 1, 0 })),
    IO(36,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "ioblocks"),
                GameRegistry.findBlock("GalaxySpace", "ioblocks"), GameRegistry.findBlock("GalaxySpace", "ioblocks") },
            new int[] { 0, 2, 2, 1 })),
    MERCURY(37,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "mercuryblocks"),
                GameRegistry.findBlock("GalaxySpace", "mercuryblocks"),
                GameRegistry.findBlock("GalaxySpace", "mercuryblocks") },
            new int[] { 0, 2, 1, 0 })),
    PHOBOS(38,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "phobosblocks"),
                GameRegistry.findBlock("GalaxySpace", "phobosblocks"),
                GameRegistry.findBlock("GalaxySpace", "phobosblocks") },
            new int[] { 0, 2, 1, 0 })),
    VENUS(39,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "venusblocks"),
                GameRegistry.findBlock("GalaxySpace", "venusblocks"),
                GameRegistry.findBlock("GalaxySpace", "venusblocks") },
            new int[] { 0, 1, 1, 0 })),
    DEIMOS(40,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "deimosblocks"),
                GameRegistry.findBlock("GalaxySpace", "deimosblocks"),
                GameRegistry.findBlock("GalaxySpace", "deimosblocks") },
            new int[] { 0, 1, 1, 0 })),
    ENCELADUS(41,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "enceladusblocks"),
                GameRegistry.findBlock("GalaxySpace", "enceladusblocks"),
                GameRegistry.findBlock("GalaxySpace", "enceladusblocks") },
            new int[] { 0, 1, 3, 0 })),
    CERES(42,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "ceresblocks"),
                GameRegistry.findBlock("GalaxySpace", "ceresblocks"),
                GameRegistry.findBlock("GalaxySpace", "ceresblocks") },
            new int[] { 0, 1, 1, 0 })),
    GANYMEDE(43,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "ganymedeblocks"),
                GameRegistry.findBlock("GalaxySpace", "ganymedeblocks"),
                GameRegistry.findBlock("GalaxySpace", "ganymedeblocks") },
            new int[] { 0, 1, 1, 0 })),
    TITAN(44,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "titanblocks"),
                GameRegistry.findBlock("GalaxySpace", "titanblocks"),
                GameRegistry.findBlock("GalaxySpace", "titanblocks") },
            new int[] { 0, 2, 1, 0 })),
    CALLISTO(45,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "callistoblocks"),
                GameRegistry.findBlock("GalaxySpace", "callistoblocks"),
                GameRegistry.findBlock("GalaxySpace", "callistoblocks") },
            new int[] { 0, 1, 1, 0 })),
    OBERON(46,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "oberonblocks"),
                GameRegistry.findBlock("GalaxySpace", "oberonblocks"),
                GameRegistry.findBlock("GalaxySpace", "oberonblocks") },
            new int[] { 0, 2, 1, 0 })),
    PROTEUS(47,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "proteusblocks"),
                GameRegistry.findBlock("GalaxySpace", "proteusblocks"),
                GameRegistry.findBlock("GalaxySpace", "proteusblocks") },
            new int[] { 0, 2, 1, 0 })),
    TRITON(48,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "tritonblocks"),
                GameRegistry.findBlock("GalaxySpace", "tritonblocks"),
                GameRegistry.findBlock("GalaxySpace", "tritonblocks") },
            new int[] { 0, 2, 1, 0 })),
    PLUTO(49,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "plutoblocks"),
                GameRegistry.findBlock("GalaxySpace", "plutoblocks"),
                GameRegistry.findBlock("GalaxySpace", "plutoblocks") },
            new int[] { 0, 5, 4, 3 })),
    OUTER_LANDS(50,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("Thaumcraft", "blockEldritchNothing"),
                GameRegistry.findBlock("Thaumcraft", "blockCosmeticSolid"),
                GameRegistry.findBlock("Thaumcraft", "blockCosmeticSolid") },
            new int[] { 0, 0, 12, 11 })),
    BEDROCK(60, createSuperflatLayers(new Block[] { Blocks.bedrock, Blocks.bedrock, Blocks.bedrock, Blocks.bedrock, })),
    ROSS128_BA(63,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalacticraftCore", "tile.moonBlock"),
                GameRegistry.findBlock("GalacticraftCore", "tile.moonBlock"),
                GameRegistry.findBlock("GalacticraftCore", "tile.moonBlock") },
            new int[] { 0, 4, 3, 5 })),
    BARNARDA_E(81,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "barnardaEsubgrunt"),
                GameRegistry.findBlock("GalaxySpace", "barnardaEsubgrunt"),
                GameRegistry.findBlock("GalaxySpace", "barnardaEgrunt") })),
    BARNARDA_F(82,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "barnardaFsubgrunt"),
                GameRegistry.findBlock("GalaxySpace", "barnardaFsubgrunt"),
                GameRegistry.findBlock("GalaxySpace", "barnardaFgrunt") })),
    HAUMEA(83,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "haumeablocks"),
                GameRegistry.findBlock("GalaxySpace", "haumeablocks"),
                GameRegistry.findBlock("GalaxySpace", "haumeablocks") })),
    VEGA_B(84,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "vegabsubgrunt"),
                GameRegistry.findBlock("GalaxySpace", "vegabsubgrunt"),
                GameRegistry.findBlock("GalaxySpace", "vegabgrunt") })),
    TCETI_E(85,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "tcetieblocks"), Blocks.water,
                Blocks.water },
            new int[] { 0, 2, 0, 0 })),
    MIRANDA(86,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalaxySpace", "mirandablocks"),
                GameRegistry.findBlock("GalaxySpace", "mirandablocks"),
                GameRegistry.findBlock("GalaxySpace", "mirandablocks") },
            new int[] { 0, 2, 1, 0 })),
    MAAHES(91,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalacticraftAmunRa", "tile.baseBlockGround"),
                GameRegistry.findBlock("GalacticraftAmunRa", "tile.baseBlockGround"),
                GameRegistry.findBlock("GalacticraftAmunRa", "tile.baseGrass") })),
    ANUBIS(92,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalacticraftAmunRa", "tile.baseBlockRock"),
                GameRegistry.findBlock("GalacticraftAmunRa", "tile.baseFalling"),
                GameRegistry.findBlock("GalacticraftAmunRa", "tile.baseBlockGround") },
            new int[] { 0, 1, 2, 1 })),
    HORUS(93, createSuperflatLayers(
        new Block[] { Blocks.bedrock, Blocks.obsidian, GameRegistry.findBlock("GalacticraftAmunRa", "tile.baseFalling"),
            GameRegistry.findBlock("GalacticraftAmunRa", "tile.baseFalling") },
        new int[] { 0, 0, 1, 0 })),
    SETH(94,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalacticraftAmunRa", "tile.baseGrass"), Blocks.water,
                Blocks.snow },
            new int[] { 0, 1, 0, 0 })),
    MEHEN_BELT(95,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("GalacticraftMars", "tile.asteroidsBlock"),
                GameRegistry.findBlock("GalacticraftMars", "tile.asteroidsBlock"),
                GameRegistry.findBlock("GalacticraftMars", "tile.denseIce") },
            new int[] { 0, 0, 1, 0 })),
    DEEP_DARK(100,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, Blocks.cobblestone, Blocks.cobblestone, Blocks.cobblestone })),
    LAST_MILLENIUM(112,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, Blocks.stonebrick, Blocks.stonebrick,
                GameRegistry.findBlock("ExtraUtilities", "decorativeBlock1") })),
    OUTER_LANDS_GADOMANCY(173,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("Thaumcraft", "blockEldritchNothing"),
                GameRegistry.findBlock("Thaumcraft", "blockCosmeticSolid"),
                GameRegistry.findBlock("Thaumcraft", "blockCosmeticSolid") },
            new int[] { 0, 0, 12, 11 })),
    TOXIC_EVERGLADES(227,
        createSuperflatLayers(
            new Block[] { Blocks.bedrock, GameRegistry.findBlock("ToxicEverglades", "blockDarkWorldGround2"),
                GameRegistry.findBlock("ToxicEverglades", "blockDarkWorldGround2"),
                GameRegistry.findBlock("ToxicEverglades", "blockDarkWorldGround") }));

    public final int dimensionID;
    public final SuperflatLayer[] blockLayers;
    private static final SuperflatBlocks[] VALUES = values();
    private static final Map<Integer, SuperflatLayer[]> allSuperflatBlockConfigurations = new HashMap<>();

    static {
        for (SuperflatBlocks blocks : VALUES) {
            allSuperflatBlockConfigurations.put(blocks.dimensionID, blocks.blockLayers);
        }
    }

    SuperflatBlocks(int dimID, SuperflatLayer[] blocks) {
        this.dimensionID = dimID;
        this.blockLayers = blocks;
    }

    public static SuperflatLayer[] getSuperflatLayers(int dimID) {
        return allSuperflatBlockConfigurations.getOrDefault(dimID, OVERWORLD.blockLayers);
    }

    private static SuperflatLayer[] createSuperflatLayers(Block[] blocks) {
        SuperflatLayer[] superflat = new SuperflatLayer[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            superflat[i] = new SuperflatLayer(blocks[i]);
        }
        return superflat;
    }

    private static SuperflatLayer[] createSuperflatLayers(Block[] blocks, int[] meta) {
        SuperflatLayer[] superflat = new SuperflatLayer[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            superflat[i] = new SuperflatLayer(blocks[i], meta[i]);
        }
        return superflat;
    }

    @Desugar
    public record SuperflatLayer(Block block, int metadata) {

        public SuperflatLayer(Block block) {
            this(block, 0);
        }

    }
}
