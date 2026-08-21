package com.LazyFlesh.variablehorizons.variants;

import java.util.Arrays;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;

public class DemonInvasionBlacklistCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "DemonInvasionBlacklist";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/demoninvasionblacklist";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        int[] blacklist = AlchemicalWizardry.demonRitualDimensionBlacklist;
        sender.addChatMessage(new ChatComponentText("Blood Magic demon blacklist: " + Arrays.toString(blacklist)));
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
