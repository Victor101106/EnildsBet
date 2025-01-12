package com.example.enildsbet.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.enildsbet.BetActivity;
import com.example.enildsbet.helper.InputHelper;
import com.example.enildsbet.model.Balance;
import com.example.enildsbet.model.Bet;
import com.example.enildsbet.model.Name;

import java.util.Objects;

public class BetController {

    private final BetActivity activity;

    public final Balance balance;
    public final Name name;

    private boolean readyToBet = true;
    private Bet bet;

    private BetController (BetActivity activity, Balance balance, Name name) {
        this.activity = activity;
        this.balance = balance;
        this.name = name;
    }

    public static BetController create(BetActivity activity) {

        Intent intent = activity.getIntent();
        Bundle extras = intent.getExtras();

        if (extras == null) {
            activity.finish();
            return null;
        }

        Balance balance = new Balance(extras.getFloat("balance", 0));
        Name name = new Name(extras.getString("name", "Unknown"));

        activity.setPositiveCashFlowText(balance.getBalance());
        activity.setBalanceText(balance.getBalance());
        activity.setGreetingsText(name.getName());

        return new BetController(activity, balance, name);

    }

    public void minefieldButtonOnClickListener(View view) {

        if (readyToBet)
            return;

        int index = InputHelper.convertStringToInteger(view.getTag().toString());

        this.bet.minefieldOnClickListener(index);

    }

    public void playStopButtonOnClickListener(View view) {

        if (!this.readyToBet) {
            this.terminateBet();
            return;
        }

        initializeBet(this.activity.readValueInput(), this.activity.readMineCountInput());

    }

    public void initializeBet(float value, int mineCount) {

        if (!this.readyToBet)
            return;

        Bet createdBetOrNull = Bet.create(this, value, mineCount);

        if (createdBetOrNull == null) {
            this.activity.showInvalidBetDialog();
            return;
        }

        this.bet = createdBetOrNull;
        this.readyToBet = false;

        this.activity.setMinefieldButtonsToActive();
        this.activity.setInputFieldsState(false);
        this.activity.setPlayStopButtonState(false);
        this.updateGainedValueText();

    }

    public void terminateBet() {

        this.activity.setInputFieldsState(true);
        this.activity.setPlayStopButtonState(true);
        this.activity.hiddenGainedValueText();

        this.bet.redeemGainedValue();
        this.readyToBet = true;

    }

    public void increaseValueFromBalance(float value) {
        this.balance.setBalance(this.balance.getBalance() + value);
        this.activity.setBalanceText(this.balance.getBalance());
        this.activity.setPositiveCashFlowText(value);
    }

    public void decreaseValueFromBalance(float value) {
        this.balance.setBalance(this.balance.getBalance() - value);
        this.activity.setBalanceText(this.balance.getBalance());
        this.activity.setNegativeCashFlowText(value);
    }

    public void setMinefieldButtonState(int index, boolean correct) {
        this.activity.setMinefieldButtonState(index, correct);
    }

    public boolean balanceContainsValue(float value) {
        return this.balance.balanceContainsValue(value);
    }

    public void updateGainedValueText() {
        this.activity.setGainedValueText(this.bet.getGain());
    }

}
