package com.LazyFlesh.variablehorizons.util.islands;

import java.util.HashMap;

import akka.japi.Pair;

public class IslandControl {

    public static IslandControl instance = new IslandControl();

    public HashMap<String, IslandData> playerIsland = new HashMap<>();
    public HashMap<String, IslandData> island = new HashMap<>();
    public Pair<Integer, Integer> lastIsland = new Pair<>(0, 0);

    private int[] spiralCache = new int[] { 0, 0, 1, 999999999 };

    public Pair<Integer, Integer> nextIslandLocation() {
        // start of spiral is 0, 0
        // to simplify, divide x and z coords of lastIsland by 8,000 (inter-island distance)
        int x = IslandControl.instance.lastIsland.first() / 8000;
        int z = IslandControl.instance.lastIsland.second() / 8000;

        // Current position
        int currentRow = spiralCache[0];
        int currentCol = spiralCache[1];
        int stepSize = spiralCache[2];

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
                int steps = Math.min(spiralCache[3], direction[2]); // if cached steps exist, they'll always be less

                boolean completionFlag = false;

                // Move in the current direction for the specified number of steps
                while (steps > 0) {
                    currentRow += rowDelta;
                    currentCol += colDelta;

                    // Check if current position is the last island generated, if it is, set flag, next one is returned
                    if (currentRow == x && currentCol == z) {
                        completionFlag = true;
                    } else if (completionFlag) {
                        this.spiralCache = new int[] { currentRow, currentCol, stepSize, --steps };
                        return lastIsland = new Pair<>(currentRow * 8000, currentCol * 8000);
                    }

                    steps--;
                }
            }
        }

    }
}
