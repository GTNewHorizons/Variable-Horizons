package com.LazyFlesh.variablehorizons.util.islands;

import java.util.List;

public class IslandData {

    public String id;
    public int dimID;
    public int x;
    public int z;
    public List<String> players;

    public IslandData(int x, int z, int dimID, List<String> players) {
        this.dimID = dimID;
        this.x = x;
        this.z = z;
        this.players = players;
        this.id = x + "," + z;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

}
