package com.LazyFlesh.variablehorizons.util.superflat;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import cpw.mods.fml.common.registry.GameRegistry;

public enum SuperflatBlocks {

    OVERWORLD(0, new Block[] { Blocks.bedrock, Blocks.dirt, Blocks.dirt, Blocks.grass }),
    NETHER(-1,
        new Block[] { Blocks.bedrock, Blocks.netherrack, Blocks.netherrack,
            GameRegistry.findBlock("BiomesOPlenty", "overgrownNetherrack") }),
    END(1, new Block[] { Blocks.obsidian, Blocks.end_stone, Blocks.end_stone, Blocks.end_stone });

    public final int dimensionID;
    public final Block[] blockLayers;
    private static final SuperflatBlocks[] VALUES = values();
    private static final Map<Integer, Block[]> allSuperflatBlockConfigurations = new HashMap<>();

    static {
        for (SuperflatBlocks blocks : VALUES) {
            allSuperflatBlockConfigurations.put(blocks.dimensionID, blocks.blockLayers);
        }
    }

    SuperflatBlocks(int dimID, Block[] blocks) {
        this.dimensionID = dimID;
        this.blockLayers = blocks;
    }

    public static Block[] getSuperflatBlocks(int dimID) {
        return allSuperflatBlockConfigurations.getOrDefault(dimID, OVERWORLD.blockLayers);
    }
}
