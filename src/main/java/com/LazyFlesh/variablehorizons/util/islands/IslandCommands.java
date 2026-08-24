package com.LazyFlesh.variablehorizons.util.islands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.randomUtil;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import akka.japi.Pair;
import cpw.mods.fml.common.FMLCommonHandler;

public class IslandCommands extends CommandBase {

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
                // permission level 0, anyone can do
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
                                "Joined " + player.getDisplayName() + "'s island. Spawnpoint set to island origin."));

                        FMLCommonHandler.instance()
                            .getMinecraftServerInstance()
                            .worldServerForDimension(0).mapStorage
                                .loadData(IslandControlSaveData.class, IslandControlSaveData.saveDataID)
                                .markDirty();
                    }

                }
            }
            case "create" -> {
                // permission level 1, need some kind of perm to do it
                // make sure person can actually send command
                if (!sender.canCommandSenderUseCommand(1, getCommandName())) {
                    sender.addChatMessage(new ChatComponentText("Command requires permission level 1 to execute."));
                    return;
                }
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
