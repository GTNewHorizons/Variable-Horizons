package com.LazyFlesh.variablehorizons.util.islands;

public class IslandData {

    public String id;
    public int dimID;
    public int x;
    public int z;
    public String[] players;

    public IslandData(int x, int z, int dimID, String[] players) {
        this.dimID = dimID;
        this.x = x;
        this.z = z;
        this.players = players;
        this.id = String.valueOf(x + ',' + z);
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }

}
