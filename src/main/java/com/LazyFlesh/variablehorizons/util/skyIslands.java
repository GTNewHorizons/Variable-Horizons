package com.LazyFlesh.variablehorizons.util;

import net.minecraft.init.Blocks;

import cpw.mods.fml.common.registry.GameRegistry;

public class skyIslands {

    // spotless:off
    public static Object[][] createIslandOW() {
        return new Object[][] { { -7, 6, -3 }, // offset x, y, z
            {   'L', Blocks.leaves, 0,
                'W', Blocks.log, 0,
                'G', Blocks.grass, 0,
                'D', Blocks.dirt, 0,
                'C', Blocks.chest, 0}, // key, block, meta
            { "        ", "        ", "LLLL    ", " LLL    ", "        ", "        ", "        ", "        ", "        " },
            { "  L     ", "  LL    ", "LLLLL   ", "LLLLL   ", "        ", "        ", "        ", "        ", "        " },
            { " LLL    ", " LWL    ", "LLWLL   ", "LLWLL   ", "  W     ", "  W     ", "  GGG   ", "  DDD   ", "  DDD   " },
            { "  L     ", "  LL    ", "LLLLL   ", "LLLLL   ", "        ", "        ", "  GGG   ", "  DDD   ", "  DDD   " },
            { "        ", "        ", "LLLL    ", "LLLL    ", "        ", "        ", "  GGG   ", "  DDD   ", "  DDD   " },
            { "        ", "        ", "        ", "        ", "        ", "        ", "  GGGGGG", "  DDDDDD", "  DDDDDD" },
            { "        ", "        ", "        ", "        ", "        ", "       C", "  GGGGGG", "  DDDDDD", "  DDDDDD" },
            { "        ", "        ", "        ", "        ", "        ", "        ", "  GGGGGG", "  DDDDDD", "  DDDDDD" }};
    }

    public static Object[][] createIslandNether() {
        return new Object[][]{{-9, 0, -7}, // offset x, y, z
            {'A', GameRegistry.findBlock("BiomesOPlenty", "ash"), 1,
                'B', GameRegistry.findBlock("Natura", "bloodwood"), 15,
                'C', GameRegistry.findBlock("BiomesOPlenty", "bopGrass"), 1,
                'D', GameRegistry.findBlock("BiomesOPlenty", "flesh"), 3,
                'E', Blocks.netherrack, 0,
                'F', GameRegistry.findBlock("Natura", "bloodwood"), 3,
                'G', GameRegistry.findBlock("Natura", "floraleavesnocolor"), 2,
                'H', GameRegistry.findBlock("Natura", "bloodwood"), 2,
                'I', GameRegistry.findBlock("Natura", "bloodwood"), 0,
                'J', GameRegistry.findBlock("Natura", "bloodwood"), 1,
                'K', GameRegistry.findBlock("BiomesOPlenty", "flowers2"), 2,
                'L', Blocks.chest, 0}, // key, block, meta
            {"             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","   GGG  GGG  ","             ","        GGG  ","             ","             ","             "},
            {"             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","   GGG  GGG  ","  GGGGGGGGGG ","   GGGGGGGG  ","        GGGG ","        GGG  ","             ","             "},
            {"             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","   GGG  GGG  ","  GGBGGGGBGG ","   GGGGGGGG  ","    GGGGGBGG ","        GGG  ","             ","             "},
            {"             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","   GGG GGGG  ","  GGGGGGGGGG ","   GGBGGBGG  ","    GGGGGGGG ","     GGGGGG  ","             ","             "},
            {"             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","       GGG   ","   GGGGGBGG  ","  GGGGGGGGGG ","    GGBBGGG  ","     GGGGG   ","      GGG    ","             "},
            {"      K      ","      EAA    ","      AAA    ","      AEE    ","      IH     ","      IH     ","      IH     ","      IH     ","      IH     ","      IH     ","      IH     ","      IH     ","      IH     ","      IHGGG  ","  GGGGIHBGGG "," GGGGGIBGGGGG"," GGGGGGBBGGG ","    GGGBGG   ","    GGGGG    ","             "},
            {"             ","      CCC    ","      AEA    ","      AEE    ","      JF     ","      JF     ","      JF     ","      JF     ","      JF     ","      JF     ","      JF     ","      JF     ","      JF     ","     GJFGGG  ","  GGGGJFGBGG "," GGBGGBBGGBGG","GGGGGBGGGGGG "," GGGGGGBGGG  ","   GGGGGG    ","    GGGGG    "},
            {"             ","      CEA    ","      EEE    ","      EEA    ","             ","             ","             ","             ","             ","             ","             ","             ","      GGG    ","     GGGGGG  ","  GGGGBGGGGG "," GGGGGGGGGGGG","GGBGBGGGBBGG "," GGGGGGGGGG  ","   GGBBBG    ","    GGGGG    "},
            {"             ","      ECCDCE ","      EEADDD ","      EEADDE ","             ","             ","             ","             ","             ","             ","             ","             ","      GGG    ","     GGBGG   ","    GGGGGGG  "," GGGGGGGGGGG ","GGGGGGGGGGGG "," GGGGGGGGGG  ","   GGGGGG    ","    GGGGG    "},
            {"           L ","      CEEDDD ","      EEAEEE ","      EEEDED ","             ","             ","             ","             ","             ","             ","             ","             ","      GGG    ","     GGGGG   ","    GGBGG    ","     GGG     "," GGGGG GGGG  ","             ","    GGGGG    ","             "},
            {"             ","      ECECCD ","      DEEDED ","      DEEDDE ","             ","             ","             ","             ","             ","             ","             ","             ","             ","     GGGG    ","    GGGG     ","     GGG     ","             ","             ","             ","             "},
            {"             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","     GGG     ","             ","             ","             ","             ","             "}};
    }

    public static Object[][] createIslandDD() {
        return new Object[][] {{ -5, 19, -1 },
            { 'A', Blocks.dirt, 0,
                'B', GameRegistry.findBlock("etfuturum", "cobbled_deepslate"), 0,
                'C', GameRegistry.findBlock("etfuturum", "deepslate"), 0,
                'D', GameRegistry.findBlock("BiomesOPlenty", "leaves1"), 3,
                'E', GameRegistry.findBlock("BiomesOPlenty", "logs1"), 2,
                'F', GameRegistry.findBlock("BiomesOPlenty", "mushrooms"), 3,
                'G', Blocks.stone, 0, 'H', Blocks.chest, 2, 'I', Blocks.mycelium, 0},
            { "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "     G     ", "           ", "           ", "           ", "           ", "           ", "           ", "           " },
            { "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "    GGI    ", "     C     ", "           ", "           ", "           ", "           ", "           ", "           " },
            { "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "     F     ", "   GGIIG   ", "    CCC    ", "     C     ", "           ", "           ", "           ", "           ", "           " },
            { "           ", "           ", "           ", "    DDD    ", "           ", "    DDD    ", "           ", "    DDD    ", "           ", "    DDD    ", "           ", "    DDD    ", "           ", "    DDD    ", "           ", "    DDD    ", "           ", "    DDD    ", "           ", "  GGGGIGG  ", "   CCCCC   ", "    BBB    ", "    BC     ", "     C     ", "     C     ", "           ", "           " },
            { "     D     ", "           ", "     D     ", "   DDDDD   ", "     D     ", "   DDDDD   ", "     D     ", "   DDDDD   ", "     D     ", "   DDDDD   ", "     D     ", "   DDDDD   ", "     D     ", "   DDDDD   ", "     D     ", "   DDDDD   ", "     D     ", "   DDDDD   ", "           ", " IGIGGGGIG ", "  CCCCCCC  ", "    BCBC   ", "     CC    ", "           ", "           ", "           ", "           " },
            { "    DDD    ", "     D     ", "    DED    ", "   DDEDD   ", "    DED    ", "   DDEDD   ", "    DED    ", "   DDEDD   ", "    DED    ", "   DDEDD   ", "    DED    ", "   DDEDD   ", "    DED    ", "   DDEDD   ", "    DED    ", "   DDEDD   ", "    DED    ", "   DDEDD   ", "H F  E  F  ", "GIIGGAGIIII", " CCBBCCCCC ", "  CBBBCBB  ", "   BB CCC  ", "       CC  ", "        C  ", "           ", "           " },
            { "     D     ", "           ", "     D     ", "   DDDDD   ", "     D     ", "   DDDDD   ", "     D     ", "   DDDDD   ", "     D     ", "   DDDDD   ", "     D     ", "   DDDDD   ", "     D     ", "   DDDDD   ", "     D     ", "   DDDDD   ", "     D     ", "   DDDDD   ", "           ", " GGGGGGGGG ", "  CCCCCCC  ", "   CBBCBC  ", "     C C   ", "     B     ", "     B     ", "     C     ", "     B     " },
            { "           ", "           ", "           ", "    DDD    ", "           ", "    DDD    ", "           ", "    DDD    ", "           ", "    DDD    ", "           ", "    DDD    ", "           ", "    DDD    ", "           ", "    DDD    ", "           ", "    DDD    ", "           ", "  GGIIGGG  ", "   CCBCC   ", "    BB     ", "    CB     ", "    C      ", "           ", "           ", "           " },
            { "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "     F     ", "   GGIIG   ", "    CCC    ", "     B     ", "     C     ", "           ", "           ", "           ", "           " },
            { "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "    GGG    ", "     C     ", "           ", "           ", "           ", "           ", "           ", "           " },
            { "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "     G     ", "           ", "           ", "           ", "           ", "           ", "           ", "           " }};
    }

    public static Object[][] createIslandSpaceStation() {
        return new Object[][]{ {-8, 3, -5},
        {'A', Blocks.glass, 0,
            'B', GameRegistry.findBlock("GalacticraftCore", "tile.airLockSeal"), 0,
            'D', Blocks.dirt, 0,
            'E', Blocks.grass, 0,
            'F', Blocks.leaves, 0,
            'G', Blocks.log, 0,
            'H', Blocks.glass_pane, 0,
            'I', GameRegistry.findBlock("Ztones", "tile.tankBlock"), 0,
            'J', GameRegistry.findBlock("Ztones", "tile.tankBlock"), 13,
            'K', Blocks.crafting_table, 0,
            'L', GameRegistry.findBlock("GalacticraftCore", "tile.sealer"), 1,
            'M', GameRegistry.findBlock("GalacticraftCore", "tile.oxygenCompressor"), 1,
            'N', GameRegistry.findBlock("OpenBlocks", "elevator"), 0,
            'P', GameRegistry.findBlock("EnderIO", "blockCapBank"), 1,
            'Q', GameRegistry.findBlock("GalacticraftCore", "tile.enclosed"), 1,
            'R', GameRegistry.findBlock("GalacticraftCore", "tile.oxygenCollector"), 1,
            'S', GameRegistry.findBlock("GalacticraftCore", "tile.solar"), 7,
            'U', GameRegistry.findBlock("GalacticraftCore", "tile.enclosed"), 14,
            'V', Blocks.chest, 4},
            {"           ", "  IIIIIII  ", "  IIIIIII  ", "  HHHHHHH  ", "  HHHHHHH  ", "  HHHHHHH  ", "  IIIIIII  ", "           ", "           ", "           "},
            {"   JJJJJ   ", "  I     I  ", "  I     I  ", "  H     H  ", "  HFFFF H  ", "  H FFF H  ", "  I     I  ", "   JJJJJ   ", "   JJJJJ   ", "   JJJJJ   "},
            {"   JAAAJ   ", "  I     I  ", "  I  F  I  ", "  H  F  H  ", "  HFFFFFH  ", "  HFFFFFH  ", "  I     I  ", "   J   J   ", "   JEEEJ   ", "   JJJJJ   "},
            {"   JAAAJ   ", "  I     I  ", "  I FFF I  ", "  H FGF H  ", "  HFFGFFH  ", "  HFFGFFH  ", "  I  G  I  ", "   J G J   ", "   JEDEJ   ", "   JJJJJ   "},
            {"   JAAAJ   ", "  I     I  ", "  I  F  I  ", "  H  F  H  ", "  HFFFFFH  ", "  HFFFFFH  ", "  I     I  ", "   J   J   ", "   JEEEJ   ", "   JJJJJ   "},
            {"   JJJJJ   ", "  I     I  ", "  I     I  ", "  I     UUP", "IIIFFFFFUII", "HHIFFFFFUHH", "III     UII", "   JQRUJU  ", "   JJJJJU  ", "   JJJJJ   "},
            {"           ", "   IIIII   ", "   IIIII   ", " IIIIIIIIIS", "I         I", "H         H", "I         I", " IIIQLUIII ", "      UUU  ", "           "},
            {"           ", "           ", "           ", " IIIIIIIII ", "I         I", "H         H", "I         I", " IIIQMUIII ", "           ", "           "},
            {"           ", "           ", "           ", " IIIINIIII ", "I         I", "H         H", "I         I", " IIIINIIII ", "           ", "           "},
            {"           ", "           ", "           ", " IIIIIIIII ", "I         I", "H         H", "I         I", " IIIIKIIII ", "           ", "           "},
            {"           ", "           ", "           ", " IIIIIIIII ", "I         I", "H         H", "I         I", " IIIIIIIII ", "           ", "           "},
            {"           ", "           ", "           ", " IIIIIIIII ", "I         I", "H         H", "I      V  I", " IIIIIIIII ", "           ", "           "},
            {"           ", "           ", "           ", "   IIIII   ", "IIIIIIIIIII", "HHHHHHHHHHH", "IIIIIIIIIII", "   IIIII   ", "           ", "           "}};
    }

    public static Object[][] createIslandEnd() {
        return new Object[][]{{-6, 21, -3}, // offset x, y, z
            {'A', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 2,
                'B', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 4,
                'C', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 5,
                'D', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 6,
                'E', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 7,
                'F', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 8,
                'G', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 10,
                'H', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 11,
                'I', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 12,
                'J', GameRegistry.findBlock("HardcoreEnderExpansion", "end_stone_terrain"), 0,
                'K', GameRegistry.findBlock("HardcoreEnderExpansion", "end_stone_terrain"), 1,
                'L', GameRegistry.findBlock("HardcoreEnderExpansion", "end_stone_terrain"), 2,
                'M', GameRegistry.findBlock("HardcoreEnderExpansion", "ender_goo"), 0,
                'N', GameRegistry.findBlock("etfuturum", "chorus_flower"), 0,
                'O', Blocks.glowstone, 0,
                'P', GameRegistry.findBlock("thaumicbases", "genLogs"), 2,
                'Q', GameRegistry.findBlock("thaumicbases", "genLeaves"), 3,
                'R', Blocks.obsidian, 0,
                'S', GameRegistry.findBlock("HardcoreEnderExpansion", "obsidian_end"), 0,
                'T', GameRegistry.findBlock("HardcoreEnderExpansion", "obsidian_special"), 0,
                'U', GameRegistry.findBlock("HardcoreEnderExpansion", "obsidian_special"), 1,
                'V', Blocks.end_stone, 0,
                'W', Blocks.chest, 0}, // key, block, meta
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        O        ", "        S        ", "        S        ", "        S        ", "        S        ", "        S        ", "        S        ", "        S        ", "        S        ", "        S        ", "        R        ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        S        ", "        S        ", "       SSS       ", "       SSS       ", "       SSS       ", "       SSS       ", "       RSR       ", "        S        ", "        S        ", "        S        ", "        R        ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        S        ", "        S        ", "        S        ", "        S        ", "       SSS       ", "       SSS       ", "       SSS       ", "       SSS       ", "       RRR       ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        S        ", "        S        ", "        S        ", "        S        ", "        S        ", "      UUUUU      ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "     UTTTTTU     ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "        V        ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "         A       ", "    UTJJJJJTU    ", "       VVV       ", "       VV        ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "       VVV       ", "      VVVVV      ", "      VVVVV      ", "      VVVVV      ", "       VVV       ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "       QQQ       ", "       QQQ       ", "                 ", "      B    G     ", "   UTJJJLJKKTU   ", "      VVVVV      ", "      VVVV       ", "       VV        ", "       VV        ", "        V        ", "        V        ", "                 "},
            {"                 ", "      VMMMV      ", "      VVVVV      ", "      VVVVV      ", "      VVVVV      ", "      VVVVV      ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", " S             S ", " S             S ", " S             S ", " R      Q      R ", " RS   QQQQQ   SR ", "  S   QQQQQ   S  ", "  S     Q     S  ", "  S      F    S  ", "  RUTJJLLLKKTUR  ", "     VVVVVV      ", "     V VVV       ", "     V VVV       ", "        VV       ", "        V        ", "                 ", "                 "},
            {"        N        ", "      VMVMV      ", "      VVVVV      ", "     VVVVVVV     ", "      VVVVV      ", "      VVVVV      ", "        V        ", "                 ", "O               O", "S               S", "S               S", "SS             SS", "SS             SS", "SSS           SSS", "SSS           SSS", "SSS           SSS", "RRRS   QQQ   SRRR", "SSSS  QQPQQ  SSSS", "RSSS  QQPQQ  SSSR", " SSS   QPQ   SSS ", " SSS   EP    SSS ", " RRUTJLLVLLKTURR ", "      VVVVVV     ", "      VVVVV      ", "      VVVVV      ", "      VVVVV      ", "       VV V      ", "        V V      ", "        V        "},
            {"                 ", "      VMMMV      ", "      VVVVV      ", "      VVVVV      ", "      VVVVV      ", "      VVVVV      ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", " S             S ", " S             S ", " S             S ", " R      Q      R ", " RS   QQQQQ   SR ", "  S   QQQQQ   S  ", "  S     Q     S  ", "  S     E  H  S  ", "  RUTJJLLLKKTUR  ", "     VVVVVV      ", "      VVVVV      ", "      VVVV       ", "       V V       ", "                 ", "                 ", "                 "},
            {"                 ", "       VVV       ", "      VVVVV      ", "      VVVVV      ", "      VVVVV      ", "       VVV       ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "       QQQ       ", "       QQQ       ", "                 ", "     C           ", "   UTJJKLKKKTU   ", "      VVVVV      ", "       VVVV      ", "        V        ", "        V        ", "        V        ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "        V        ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "       I  D      ", "    UTKKKKKTU    ", "       VVVV      ", "        V        ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        W        ", "     UTTTTTU     ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        S        ", "        S        ", "        S        ", "        S        ", "        S        ", "      UUUUU      ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        S        ", "        S        ", "        S        ", "        S        ", "       SSS       ", "       SSS       ", "       SSS       ", "       SSS       ", "       RRR       ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        S        ", "        R        ", "       SSS       ", "       SSS       ", "       SSS       ", "       RRR       ", "       RSR       ", "        S        ", "        S        ", "        S        ", "        R        ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        O        ", "        S        ", "        S        ", "        S        ", "        S        ", "        R        ", "        S        ", "        S        ", "        R        ", "        S        ", "        R        ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "}};
    }
    // spotless:on

}
