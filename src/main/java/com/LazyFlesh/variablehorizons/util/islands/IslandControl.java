package com.LazyFlesh.variablehorizons.util.islands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;

import akka.japi.Pair;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class IslandControl {

    public static IslandControl instance = new IslandControl();

    public HashMap<String, IslandData> playerIsland = new HashMap<>();
    public HashMap<String, IslandData> islands = new HashMap<>();
    public Pair<Integer, Integer> lastIsland = new Pair<>(0, 0);

    @SubscribeEvent
    private void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (!playerIsland.containsKey(
            event.player.getUniqueID()
                .toString())) {
            if (islands.containsKey("0,0")) {
                playerIsland.put(
                    event.player.getUniqueID()
                        .toString(),
                    islands.get("0,0"));
            } else {
                IslandData is = new IslandData(
                    0,
                    0,
                    GeneralConfig.startingDimID,
                    new ArrayList<>(
                        Collections.singleton(
                            event.player.getUniqueID()
                                .toString())));

                islands.put(is.id, is);
                playerIsland.put(
                    event.player.getUniqueID()
                        .toString(),
                    is);
            }
        }
    }

    public Pair<Integer, Integer> nextIslandLocation() {
        // start of spiral is 0, 0
        // to simplify, divide x and z coords of lastIsland by 8,000 (inter-island distance)
        int x = IslandControl.instance.lastIsland.first() / 8000;
        int z = IslandControl.instance.lastIsland.second() / 8000;

        // Current position
        int currentRow = 0;
        int currentCol = 0;
        int stepSize = 1;

        boolean flag = x == 0 && z == 0;

        // got initial code from https://algo.monster/liteproblems/885
        // Spiral outward with increasing step sizes
        // Step size increases by 2 each complete rotation (1, 1, 3, 3, 5, 5, ...)
        for (;; stepSize += 2) {
            // Define directions: right, down, left, up
            // Each direction has: row delta, column delta, and number of steps
            int[][] directions = new int[][] { { 0, 1, stepSize }, // Move right for 'stepSize' steps
                { 1, 0, stepSize }, // Move down for 'stepSize' steps
                { 0, -1, stepSize + 1 }, // Move left for 'stepSize + 1' steps
                { -1, 0, stepSize + 1 } // Move up for 'stepSize + 1' steps
            };

            // Process each direction in the current spiral layer
            for (int[] direction : directions) {
                int rowDelta = direction[0];
                int colDelta = direction[1];
                int steps = direction[2];

                // Move in the current direction for the specified number of steps
                while (steps > 0) {
                    currentRow += rowDelta;
                    currentCol += colDelta;

                    // Check if previous position is the last island generated
                    if (currentRow == x && currentCol == z) {
                        flag = true;
                    } else if (flag) {
                        return lastIsland = new Pair<>(currentRow * 8000, currentCol * 8000);
                    }

                    steps--;
                }

                if (Math.abs(currentCol * 8000) > 30_000_000 || Math.abs(currentRow * 8000) > 30_000_000)
                    throw new RuntimeException("Whops. Things spiraled out of this world!");
            }
        }

    }
}
