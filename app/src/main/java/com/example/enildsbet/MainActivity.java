package com.example.enildsbet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.enildsbet.helper.InputHelper;
import com.example.enildsbet.model.Balance;
import com.example.enildsbet.model.Name;

public class MainActivity extends AppCompatActivity {

    EditText mainBalanceInput;
    EditText mainNameInput;
    Button mainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.mainBalanceInput = findViewById(R.id.main_balance_input);
        this.mainNameInput = findViewById(R.id.main_name_input);
        this.mainButton = findViewById(R.id.main_button);

        this.mainButton.setOnClickListener(this::mainButtonOnClickListener);

    }

    private void mainButtonOnClickListener(View view) {

        float balance = InputHelper.readDecimalEditText(this.mainBalanceInput);
        String name = InputHelper.readStringEditText(this.mainNameInput);

        if (!Balance.validateBalance(balance) || !Name.validateName(name)) {
            this.showInvalidInformationDialog();
            return;
        }

        Intent intent = new Intent(this, BetActivity.class);

        intent.putExtra("balance", balance);
        intent.putExtra("name", name);

        this.clearNameAndBalanceInputsText();

        startActivity(intent);

    }

    private void clearNameAndBalanceInputsText() {
        this.mainNameInput.setText("");
        this.mainBalanceInput.setText("");
    }

    private void showInvalidInformationDialog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage(R.string.main_input_error_dialog_message);
        alert.setTitle(R.string.main_input_error_dialog_title);
        alert.setNeutralButton(R.string.main_input_error_dialog_button_text, null);
        alert.show();

    }

}