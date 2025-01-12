package com.example.enildsbet;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.enildsbet.controller.BetController;
import com.example.enildsbet.helper.CurrencyHelper;
import com.example.enildsbet.helper.InputHelper;

public class BetActivity extends AppCompatActivity {

    private BetController controller;

    Button[] betMinefieldButtons = new Button[25];

    EditText betMineCountInput;
    EditText betValueInput;

    Button betPlayStopButton;

    TextView betGainedValueText;
    TextView betGreetingsText;
    TextView betBalanceText;

    TextView betPositiveCashFlowText;
    TextView betNegativeCashFlowText;

    ImageView betBrazinoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bet);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.betPlayStopButton = findViewById(R.id.bet_play_stop_button);

        this.betMineCountInput = findViewById(R.id.bet_mine_count_input);
        this.betValueInput = findViewById(R.id.bet_value_input);

        this.betGainedValueText = findViewById(R.id.bet_gained_value);
        this.betGreetingsText = findViewById(R.id.bet_greetings);
        this.betBalanceText = findViewById(R.id.bet_balance);

        this.betPositiveCashFlowText = findViewById(R.id.bet_positive_cash_flow);
        this.betNegativeCashFlowText = findViewById(R.id.bet_negative_cash_flow);

        this.betBrazinoAd = findViewById(R.id.bet_brazino_ad);

        this.controller = BetController.create(this);

        if (this.controller == null)
            return;

        this.appendMinefieldButtonsInArray(this.betMinefieldButtons);
        this.appendTagAndOnClickListenerInArray(this.betMinefieldButtons);

        this.betPlayStopButton.setOnClickListener(this.controller::playStopButtonOnClickListener);
        this.betBalanceText.setOnClickListener(this::balanceTextOnClickListener);
        this.betBrazinoAd.setOnClickListener(this::brazinoAdOnClickListener);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {

            float transferValue = intent.getFloatExtra("transfer_value", 0);

            if (transferValue >= 1)
                this.controller.increaseValueFromBalance(transferValue);

        }

    }

    public void setPositiveCashFlowText(float absValue) {

        this.betPositiveCashFlowText.setVisibility(View.VISIBLE);
        this.betNegativeCashFlowText.setVisibility(View.INVISIBLE);

        this.betPositiveCashFlowText.setText(getResources().getString(R.string.bet_positive_cash_flow_text, CurrencyHelper.convertFloatToRealCurrency(absValue)));

    }

    public void setNegativeCashFlowText(float absValue) {

        this.betPositiveCashFlowText.setVisibility(View.INVISIBLE);
        this.betNegativeCashFlowText.setVisibility(View.VISIBLE);

        this.betNegativeCashFlowText.setText(getResources().getString(R.string.bet_negative_cash_flow_text, CurrencyHelper.convertFloatToRealCurrency(absValue)));

    }

    public void setGainedValueText(float value) {
        this.betGainedValueText.setVisibility(View.VISIBLE);
        this.betGainedValueText.setText(getResources().getString(R.string.bet_value_gained_text, CurrencyHelper.convertFloatToRealCurrency(value)));
    }

    public void hiddenGainedValueText() {
        this.betGainedValueText.setVisibility(View.INVISIBLE);
    }

    public void setGreetingsText(String name) {
        this.betGreetingsText.setText(getResources().getString(R.string.bet_greetings_text, name));
    }

    public void setBalanceText(float balance) {
        this.betBalanceText.setText(CurrencyHelper.convertFloatToRealCurrency(balance));
    }

    public void setInputFieldsState(boolean enabled) {

        if (enabled) {
            this.betMineCountInput.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.input_field_background, null));
            this.betValueInput.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.input_field_background, null));
            this.betValueInput.setText("");
        } else {
            this.betMineCountInput.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.disabled_input_field_background, null));
            this.betValueInput.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.disabled_input_field_background, null));
        }

        this.betValueInput.setEnabled(enabled);
        this.betMineCountInput.setEnabled(enabled);

    }

    public void setPlayStopButtonState(boolean readToPlay) {
        if (readToPlay) {
            this.betPlayStopButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_green_button_background, null));
            this.betPlayStopButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_play, 0, 0, 0);
        } else {
            this.betPlayStopButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_red_button_background, null));
            this.betPlayStopButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_stop, 0, 0, 0);
        }
    }

    public void setMinefieldButtonState(int index, boolean correct) {

        Button betMinefieldButton = this.betMinefieldButtons[index];

        if (correct) {
            betMinefieldButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.minefield_correct_button_background, null));
        } else {
            betMinefieldButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.minefield_incorrect_button_background, null));
        }

    }

    public void setMinefieldButtonsToActive() {
        for (Button betMinefieldButton : this.betMinefieldButtons) {
            betMinefieldButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.minefield_active_button_background, null));
        }
    }

    public float readValueInput() {
        return InputHelper.readDecimalEditText(this.betValueInput);
    }

    public int readMineCountInput() {
        return InputHelper.readIntegerEditText(this.betMineCountInput);
    }

    public void showInvalidBetDialog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage(R.string.bet_input_error_dialog_message);
        alert.setTitle(R.string.bet_input_error_dialog_title);
        alert.setNeutralButton(R.string.bet_input_error_dialog_button_text, null);
        alert.show();

    }

    private void brazinoAdOnClickListener(View view) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://brazino777.com/pt/"));
        startActivity(intent);
    }

    private void balanceTextOnClickListener(View view) {

        Intent intent = new Intent(this, TransferActivity.class);

        intent.putExtra("balance", this.controller.balance.getBalance());
        intent.putExtra("name", this.controller.name.getName());

        startActivityForResult(intent, 1000);

    }

    private void appendTagAndOnClickListenerInArray(Button[] array) {
        for (int index = 0; index < array.length; index++) {
            array[index].setOnClickListener(this.controller::minefieldButtonOnClickListener);
            array[index].setTag(index);
        }
    }

    private void appendMinefieldButtonsInArray(Button[] array) {
        array[0] = findViewById(R.id.bet_minefield_button_1);
        array[1] = findViewById(R.id.bet_minefield_button_2);
        array[2] = findViewById(R.id.bet_minefield_button_3);
        array[3] = findViewById(R.id.bet_minefield_button_4);
        array[4] = findViewById(R.id.bet_minefield_button_5);
        array[5] = findViewById(R.id.bet_minefield_button_6);
        array[6] = findViewById(R.id.bet_minefield_button_7);
        array[7] = findViewById(R.id.bet_minefield_button_8);
        array[8] = findViewById(R.id.bet_minefield_button_9);
        array[9] = findViewById(R.id.bet_minefield_button_10);
        array[10] = findViewById(R.id.bet_minefield_button_11);
        array[11] = findViewById(R.id.bet_minefield_button_12);
        array[12] = findViewById(R.id.bet_minefield_button_13);
        array[13] = findViewById(R.id.bet_minefield_button_14);
        array[14] = findViewById(R.id.bet_minefield_button_15);
        array[15] = findViewById(R.id.bet_minefield_button_16);
        array[16] = findViewById(R.id.bet_minefield_button_17);
        array[17] = findViewById(R.id.bet_minefield_button_18);
        array[18] = findViewById(R.id.bet_minefield_button_19);
        array[19] = findViewById(R.id.bet_minefield_button_20);
        array[20] = findViewById(R.id.bet_minefield_button_21);
        array[21] = findViewById(R.id.bet_minefield_button_22);
        array[22] = findViewById(R.id.bet_minefield_button_23);
        array[23] = findViewById(R.id.bet_minefield_button_24);
        array[24] = findViewById(R.id.bet_minefield_button_25);
    }

}