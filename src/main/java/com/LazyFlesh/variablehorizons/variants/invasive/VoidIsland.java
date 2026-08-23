package com.LazyFlesh.variablehorizons.variants.invasive;

import static gregtech.api.enums.Mods.BiomesOPlenty;
import static gregtech.api.enums.Mods.DraconicEvolution;
import static gregtech.api.enums.Mods.GalacticraftCore;
import static gregtech.api.enums.Mods.HardcoreEnderExpansion;
import static gregtech.api.enums.Mods.Minecraft;
import static gregtech.api.enums.Mods.TinkerConstruct;
import static gregtech.api.enums.Mods.UniversalSingularities;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeConstants.DEFC_CASING_TIER;
import static kubatech.loaders.DEFCRecipes.fusionCraftingRecipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ItemFluidContainer;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.islands.IslandControl;
import com.LazyFlesh.variablehorizons.util.islands.IslandControlSaveData;
import com.LazyFlesh.variablehorizons.util.islands.IslandData;
import com.LazyFlesh.variablehorizons.util.islands.skyIslands;
import com.LazyFlesh.variablehorizons.util.randomUtil;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import akka.japi.Pair;
import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTModHandler;

public class VoidIsland extends VariantLoader {

    private static ItemStack[] createChestOW() {

        return new ItemStack[] { new ItemStack(Items.lava_bucket), new ItemStack(Blocks.ice, 2),
            new ItemStack(Items.reeds), new ItemStack(Items.melon_seeds), new ItemStack(Items.pumpkin_seeds),
            new ItemStack(Blocks.cactus), new ItemStack(Items.flint, 5) };

    }

    private static ItemStack[] createChestNether() {
        ItemStack bopBucket = GTModHandler.getModItem(BiomesOPlenty.ID, "bopBucket");
        ((ItemFluidContainer) bopBucket.getItem())
            .fill(bopBucket, new FluidStack(FluidRegistry.getFluid("hell_blood"), 1000), true);
        return new ItemStack[] { bopBucket, bopBucket, new ItemStack(Items.reeds),
            GTModHandler.getModItem(Minecraft.ID, "red_mushroom"),
            GTModHandler.getModItem(Minecraft.ID, "brown_mushroom"), new ItemStack(Blocks.cactus),
            new ItemStack(Items.flint, 5) };
    }

    private static ItemStack[] createChestDD() {
        return new ItemStack[] { new ItemStack(Items.lava_bucket), new ItemStack(Blocks.ice, 2),
            GTModHandler.getModItem(Minecraft.ID, "red_mushroom"),
            GTModHandler.getModItem(Minecraft.ID, "brown_mushroom"), new ItemStack(Items.flint, 5) };
    }

    private static ItemStack[] createChestEnd() {
        return new ItemStack[] { new ItemStack(Items.lava_bucket, 1),
            GTModHandler.getModItem(TinkerConstruct.ID, "buckets", 1, 23),
            GTModHandler.getModItem(TinkerConstruct.ID, "buckets", 1, 23), new ItemStack(Items.reeds),
            GTModHandler.getModItem(HardcoreEnderExpansion.ID, "death_flower"),
            GTModHandler.getModItem(HardcoreEnderExpansion.ID, "spooky_leaves"), new ItemStack(Blocks.cactus),
            new ItemStack(Items.flint, 5) };
    }

    private static ItemStack[] createChestSpaceStation() {
        return new ItemStack[] { GTModHandler.getModItem(GalacticraftCore.ID, "item.oxygenTankLightFull"),
            GTModHandler.getModItem(GalacticraftCore.ID, "item.oxygenGear"),
            GTModHandler.getModItem(GalacticraftCore.ID, "item.oxygenMask"),
            GTModHandler.getModItem(GalacticraftCore.ID, "item.basicItem", 1, 20) };
    }

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.VOID_ISLAND.hasLoaded = true;

        // recipes for chaos shard if end is a void (the other dim resources are not block-dependent)
        if (randomUtil.generateVoidInThisDim(1)) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    ItemList.ChaosLocator.get(1),
                    // Awakened Draconium Singularity
                    getModItem(UniversalSingularities.ID, "universal.draconicEvolution.singularity", 1, 1))
                .itemOutputs(getModItem(DraconicEvolution.ID, "chaosShard", 1, 0))
                .duration(100 * SECONDS)
                .eut(TierEU.RECIPE_UMV)
                .metadata(DEFC_CASING_TIER, 4)
                .addTo(fusionCraftingRecipes);
        }
    }

    public static Object[][] getIsland(int dimID) {
        switch (dimID) {
            case (-1) -> {
                return skyIslands.createIslandNether();
            }
            case 100 -> {
                return skyIslands.createIslandDD();
            }
            case (1) -> {
                return skyIslands.createIslandEnd();
            }
            case 25, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 51, 52, 53, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88 -> {
                return skyIslands.createIslandSpaceStation();
            }
            default -> {
                return skyIslands.createIslandOW();
            }
        }
    }

    public static ItemStack[] getChestLoot(int dimID) {
        switch (dimID) {
            case -1 -> {
                return createChestNether();
            }
            case 100 -> {
                return createChestDD();
            }
            case 1 -> {
                return createChestEnd();
            }
            case 25, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 51, 52, 53, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88 -> {
                return createChestSpaceStation();
            }
            default -> {
                return createChestOW();
            }
        }
    }

    public static class IslandCommands extends CommandBase {

        @Override
        public String getCommandName() {
            return "island";
        }

        @Override
        public List<String> getCommandAliases() {
            return new ArrayList<>(Collections.singleton("Island"));
        }

        @Override
        public String getCommandUsage(ICommandSender sender) {
            return "/island <create|join> [args...]";
        }

        @Override
        public int getRequiredPermissionLevel() {
            return 0;
        }

        @Override
        public void processCommand(ICommandSender sender, String[] args) {
            if (args.length < 1) {
                printHelpFull(sender);
                return;
            }
            String subCommand = args[0].toLowerCase();
            EntityPlayerMP pSender = getPlayer(sender, sender.getCommandSenderName());

            switch (subCommand) {
                case "join" -> {
                    if (args.length > 1) {
                        EntityPlayerMP player = getPlayer(sender, args[1]);

                        IslandData island = IslandControl.instance.playerIsland.get(
                            player.getUniqueID()
                                .toString());
                        if (island != null) {
                            // remove from old island
                            IslandData islandOld = IslandControl.instance.playerIsland.get(
                                player.getUniqueID()
                                    .toString());
                            if (islandOld != null) {
                                islandOld.players.remove(
                                    pSender.getUniqueID()
                                        .toString());
                            }
                            // add to new island
                            IslandControl.instance.playerIsland.replace(
                                pSender.getUniqueID()
                                    .toString(),
                                island);
                            island.players.add(
                                pSender.getUniqueID()
                                    .toString());

                            // go to proper dimension
                            if (pSender.dimension != island.dimID) pSender.travelToDimension(island.dimID);

                            pSender.inventory.clearInventory(null, -1);

                            pSender.setPositionAndUpdate(island.x, 75, island.z);
                            pSender.setSpawnChunk(player.playerLocation, true, island.dimID);

                            sender.addChatMessage(
                                new ChatComponentText(
                                    "Joined " + player.getDisplayName()
                                        + "'s island. Spawnpoint set to island origin."));

                            FMLCommonHandler.instance()
                                .getMinecraftServerInstance()
                                .worldServerForDimension(0).mapStorage
                                    .loadData(IslandControlSaveData.class, IslandControlSaveData.saveDataID)
                                    .markDirty();
                        }

                    }
                }
                case "create" -> {
                    switch (args.length) {
                        case 2 -> {
                            try {
                                createIsland(getPlayer(sender, args[1]));
                            } catch (PlayerNotFoundException e) {
                                // ignore exception. It just means it's island for sender, but with dimID
                                // so we just do a new island

                                // wait, nvm, it can just be a misspelled username
                                try {
                                    createIsland(pSender, Integer.parseInt(args[1]));
                                } catch (NumberFormatException ex) {
                                    sender.addChatMessage(new ChatComponentText("Unknown Player " + args[1] + "."));
                                }
                            }
                        }
                        case 3 -> createIsland(getPlayer(sender, args[2]));
                        default -> createIsland(pSender);
                    }
                }
            }
        }

        @Override
        public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
            List<String> completions = new ArrayList<>();
            String currentArg = args.length == 0 ? "" : args[args.length - 1].trim();

            if (args.length == 1) {
                Stream.of("join", "create")
                    .filter(s -> s.startsWith(currentArg))
                    .forEach(completions::add);
            } else if (args.length == 2) {
                String subCommand = args[0].toLowerCase();
                if ("join".equals(subCommand) || "create".equals(subCommand)) {
                    Arrays.stream(
                        MinecraftServer.getServer()
                            .getAllUsernames())
                        .filter(s -> s.startsWith(currentArg))
                        .forEach(completions::add);
                }
            }

            return completions;
        }

        private void printHelpFull(ICommandSender sender) {
            sender.addChatMessage(new ChatComponentText("Usage: /island <subcommand> [args...]"));
            sender.addChatMessage(new ChatComponentText(" Subcommands:"));
            sender.addChatMessage(
                new ChatComponentText(
                    "   join <player name> - CLEARS YOUR INVENTORY, sets your spawn, and teleports you to that player."));
            sender.addChatMessage(
                new ChatComponentText(
                    "   create - Creates a new island in the spawn dimension and sets your spawn there."));
            sender.addChatMessage(
                new ChatComponentText(
                    "      create <dimID> - Creates a new island in that dimension, or if dimlocked, of that dimension."));
            sender.addChatMessage(
                new ChatComponentText("      create <player name> - Creates a new island for that player."));
            sender.addChatMessage(
                new ChatComponentText(
                    "      create <player name> <dimID> - Creates a new island in that dimension, or if dimlocked, of that dimension for player <player name>."));
        }

        private void createIsland(EntityPlayerMP player) {
            // generate island in spawn dim in next valid position
            createIsland(player, GeneralConfig.startingDimID);
        }

        private void createIsland(EntityPlayerMP player, int dimID) {
            int dimToTPTo = dimID;
            if (VariantNames.activeContains(VariantNames.DIMLOCKED.id)) {
                dimToTPTo = GeneralConfig.startingDimID;
            }

            // go to proper dimension
            if (player.dimension != dimToTPTo) player.travelToDimension(dimToTPTo);

            WorldServer dimProvider = player.mcServer.worldServerForDimension(dimToTPTo);

            Pair<Integer, Integer> pos = IslandControl.instance.nextIslandLocation();
            int posX = pos.first();
            int posZ = pos.second();

            // Load chunks so stuff can happen in them
            int chunkX = posX >> 4;
            int chunkZ = posZ >> 4;
            IChunkProvider chunkProvider = dimProvider.getChunkProvider();

            for (int cx = -1; cx <= 1; cx++) {
                for (int cz = -1; cz <= 1; cz++) {
                    chunkProvider.loadChunk(chunkX + cx, chunkZ + cz);
                }
            }

            // dimProvider is the dimension its being built in, dimID is the island type
            randomUtil.generateVoidIsland(new ChunkCoordinates(posX, 72, posZ), dimProvider, dimID);

            player.inventory.clearInventory(null, -1);

            player.setPositionAndUpdate(posX, 74, posZ);

            player.setSpawnChunk(new ChunkCoordinates(posX, 74, posZ), true, dimToTPTo);

            IslandData newIsland = new IslandData(
                posX,
                posZ,
                dimToTPTo,
                new ArrayList<>(
                    Collections.singleton(
                        player.getUniqueID()
                            .toString())));

            // remove from old island
            if (IslandControl.instance.playerIsland.containsKey(
                player.getUniqueID()
                    .toString())) {
                IslandData oldIsland = IslandControl.instance.playerIsland.get(
                    player.getUniqueID()
                        .toString());
                oldIsland.players.remove(
                    player.getUniqueID()
                        .toString());
            }
            // add to new
            IslandControl.instance.playerIsland.put(
                player.getUniqueID()
                    .toString(),
                newIsland);

            IslandControl.instance.islands.put(newIsland.id, newIsland);
            FMLCommonHandler.instance()
                .getMinecraftServerInstance()
                .worldServerForDimension(0).mapStorage
                    .loadData(IslandControlSaveData.class, IslandControlSaveData.saveDataID)
                    .markDirty();

            // kill player so the island renders. For some reason it just... doesn't.
            player.setHealth(-1);
        }
    }
}
