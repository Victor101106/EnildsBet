package com.example.enildsbet.model;

import java.util.ArrayList;
import java.util.Random;

public class Minefield {

    public final int columns = 5;
    public final int rows = 5;
    public final int mineCount;

    private final boolean[] minefield = new boolean[this.columns * this.rows];

    private Minefield (int mineCount) {
        this.mineCount = mineCount;
    }

    private void generateMinefieldArray() {

        Random random = new Random();

        ArrayList<Integer> indexes = new ArrayList<>();

        for (int index = 0; index <= 24; index++)
            indexes.add(index, index);

        for (int count = 0; count < this.mineCount; count++) {
            int index = indexes.remove(random.nextInt(indexes.size()));
            this.minefield[index] = true;
        }

    }

    public boolean checkMineInIndex(int index) {
        return index >= 0 && index < this.minefield.length && this.minefield[index];
    }

    public static Minefield create(int mineCount) {

        if (!Minefield.validateMinefield(mineCount))
            return null;

        Minefield minefield = new Minefield(mineCount);

        minefield.generateMinefieldArray();

        return minefield;

    }

    public static boolean validateMinefield(int mineCount) {
        return mineCount > 0 && mineCount <= 24;
    }

}
