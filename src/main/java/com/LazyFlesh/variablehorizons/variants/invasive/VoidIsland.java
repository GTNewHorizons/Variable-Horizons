package com.LazyFlesh.variablehorizons.variants.invasive;

import static gregtech.api.enums.Mods.BiomesOPlenty;
import static gregtech.api.enums.Mods.GalacticraftCore;
import static gregtech.api.enums.Mods.HardcoreEnderExpansion;
import static gregtech.api.enums.Mods.Minecraft;
import static gregtech.api.enums.Mods.TinkerConstruct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ItemFluidContainer;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.randomUtil;
import com.LazyFlesh.variablehorizons.util.skyIslands;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

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
                        // go to proper dimension
                        if (pSender.dimension != GeneralConfig.startingDimID)
                            pSender.travelToDimension(GeneralConfig.startingDimID);

                        pSender.inventory.clearInventory(null, -1);

                        EntityPlayerMP player = getPlayer(sender, args[1]);

                        Vec3 pos = player.getPosition(0F);
                        pSender.setPositionAndUpdate(pos.xCoord, pos.yCoord, pos.zCoord);
                        pSender.setSpawnChunk(player.playerLocation, true, GeneralConfig.startingDimID);

                        sender.addChatMessage(
                            new ChatComponentText(
                                "Joined " + player.getDisplayName() + ". Spawnpoint set to their position."));

                    }
                }
                case "create" -> {
                    int i = 50000 + pSender.getEntityWorld().rand.nextInt(10000);

                    Vec3 pos = pSender.getPosition(0F);

                    // go to proper dimension
                    if (pSender.dimension != GeneralConfig.startingDimID)
                        pSender.travelToDimension(GeneralConfig.startingDimID);

                    WorldServer dimProvider = pSender.mcServer.worldServerForDimension(GeneralConfig.startingDimID);

                    // i dont care if it exists or not, load it so stuff can happen in it!
                    int chunkX = (int) (pos.xCoord + i) >> 4;
                    int chunkZ = (int) (pos.zCoord + i) >> 4;
                    IChunkProvider chunkProvider = dimProvider.getChunkProvider();

                    for (int cx = -1; cx <= 1; cx++) {
                        for (int cz = -1; cz <= 1; cz++) {
                            chunkProvider.loadChunk(chunkX + cx, chunkZ + cz);
                        }
                    }

                    randomUtil.generateVoidIsland(
                        new ChunkCoordinates((int) (pos.xCoord + i), 71, (int) (pos.zCoord + i)),
                        dimProvider,
                        GeneralConfig.startingDimID);

                    pSender.inventory.clearInventory(null, -1);

                    pSender.setPositionAndUpdate(pos.xCoord + i, 72, pos.zCoord + i);

                    pSender.setSpawnChunk(
                        new ChunkCoordinates((int) (pos.xCoord + i), 72, (int) (pos.zCoord + i)),
                        true,
                        GeneralConfig.startingDimID);

                    pSender.setHealth(-1);
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
                if ("join".equals(subCommand)) {
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
                    "  join <player name> - CLEARS YOUR INVENTORY, sets your spawn, and teleports you to that player."));
            sender.addChatMessage(
                new ChatComponentText(
                    "  create - Creates a new island randomly (at least 5,000 blocks away) in the spawn dimension and sets your spawn there."));
        }
    }
}
