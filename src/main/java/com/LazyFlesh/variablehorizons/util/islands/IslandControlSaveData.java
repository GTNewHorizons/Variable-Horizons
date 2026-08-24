package com.LazyFlesh.variablehorizons.util.islands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import com.LazyFlesh.variablehorizons.VariableHorizons;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import akka.japi.Pair;
import cpw.mods.fml.common.FMLCommonHandler;

public class IslandControlSaveData extends WorldSavedData {

    public static final String saveDataID = "VHIslands";

    public IslandControlSaveData(String worldName) {
        super(worldName);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagCompound islands = nbt.getCompoundTag("islands");
        NBTTagList islandList = islands.getTagList("islandList", Constants.NBT.TAG_COMPOUND);

        IslandControl.instance.islands.clear();
        IslandControl.instance.playerIsland.clear();

        int[] lastIsland = islands.getIntArray("LastIsland");

        if (lastIsland != null && lastIsland.length == 2) {
            IslandControl.instance.lastIsland = new Pair<>(lastIsland[0], lastIsland[1]);
        }

        int i = 0;
        NBTTagCompound is = islandList.getCompoundTagAt(i++);
        while (is != null && !is.hasNoTags()) {
            NBTTagList players = is.getTagList("players", Constants.NBT.TAG_STRING);

            IslandData island = new IslandData(is.getInteger("x"), is.getInteger("z"), is.getInteger("dimID"), null);
            int j = 0;
            List<String> uuid = new ArrayList<>();
            String u = players.getStringTagAt(j++);
            while (u != null && !u.isEmpty()) {
                uuid.add(u);
                IslandControl.instance.playerIsland.put(u, island);
                u = players.getStringTagAt(j++);
            }
            island.setPlayers(uuid);
            IslandControl.instance.islands.put(island.id, island);
            is = islandList.getCompoundTagAt(i++);
        }

        // clear nbt so no clashes/overwrites happen
        nbt.setTag("islands", new NBTTagCompound());
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound islands = new NBTTagCompound();
        NBTTagList islandList = new NBTTagList();

        islands.setIntArray(
            "LastIsland",
            new int[] { IslandControl.instance.lastIsland.first(), IslandControl.instance.lastIsland.second() });

        for (IslandData data : IslandControl.instance.islands.values()) {
            NBTTagCompound is = new NBTTagCompound();

            if (data.players != null) {
                NBTTagList playerList = new NBTTagList();
                for (String uuid : data.players) {
                    playerList.appendTag(new NBTTagString(uuid));
                }
                is.setTag("players", playerList);
            }

            is.setInteger("x", data.x);
            is.setInteger("z", data.z);
            is.setInteger("dimID", data.dimID);
            islandList.appendTag(is);
        }
        islands.setTag("islandList", islandList);
        nbt.setTag("islands", islands);
        FMLCommonHandler.instance()
            .getMinecraftServerInstance()
            .worldServerForDimension(0).mapStorage
                .loadData(IslandControlSaveData.class, IslandControlSaveData.saveDataID)
                .markDirty();
    }

    public static void init() {
        if (VariantNames.activeContains(VariantNames.VOID_ISLAND.id)) {
            VariableHorizons.LOG.info("Initialising Island Data.");

            final World world = FMLCommonHandler.instance()
                .getMinecraftServerInstance()
                .worldServerForDimension(0);

            IslandControlSaveData data = (IslandControlSaveData) world.mapStorage
                .loadData(IslandControlSaveData.class, IslandControlSaveData.saveDataID);

            if (data == null) {
                data = new IslandControlSaveData(IslandControlSaveData.saveDataID);
                world.mapStorage.setData(IslandControlSaveData.saveDataID, data);
                data.markDirty();
            }
        }
    }
}
