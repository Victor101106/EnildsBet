package com.example.enildsbet.model;

import com.example.enildsbet.controller.BetController;

public class Bet {

    private final BetController controller;
    private final Minefield minefield;

    private final float value;

    private float gain;

    private int hits;

    private Bet (BetController controller, float value, Minefield minefield) {
        this.controller = controller;
        this.minefield = minefield;
        this.value = value;
        this.gain = value;
        this.hits = 0;
    }

    public static Bet create(BetController controller, float value, int mineCount) {

        if (!Bet.validateBetValue(value) || !controller.balanceContainsValue(value))
            return null;

        Minefield minefield = Minefield.create(mineCount);

        if (minefield == null)
            return null;

        controller.decreaseValueFromBalance(value);

        return new Bet(controller, value, minefield);

    }

    public float calculateBetGain() {

        int placesToHit = (this.minefield.columns * this.minefield.rows) - this.minefield.mineCount;

        float coefficient = 0.75F;

        float percent = 1 + coefficient * (this.minefield.mineCount + this.hits) / placesToHit;

        return this.value * percent;

    }

    public void minefieldOnClickListener(int index) {

        if (this.minefield.checkMineInIndex(index)) {
            this.gain = 0;
            this.displayAllIncorrectMinefieldButtons();
            this.controller.terminateBet();
            return;
        }

        this.hits += 1;
        this.gain = calculateBetGain();
        this.controller.setMinefieldButtonState(index, true);
        this.controller.updateGainedValueText();

    }

    public void displayAllIncorrectMinefieldButtons() {
        for (int index = 0; index < this.minefield.columns * this.minefield.rows; index++) {
            if (this.minefield.checkMineInIndex(index)) {
                this.controller.setMinefieldButtonState(index, false);
            }
        }
    }

    public void redeemGainedValue() {
        if (this.gain > 0)
            this.controller.increaseValueFromBalance(this.gain);
    }

    public float getGain() {
        return this.gain;
    }

    public static boolean validateBetValue(float value) {
        return value > 0;
    }

}
