package com.example.enildsbet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.enildsbet.helper.CurrencyHelper;
import com.example.enildsbet.helper.InputHelper;
import com.example.enildsbet.model.Balance;
import com.example.enildsbet.model.Name;

public class TransferActivity extends AppCompatActivity {

    TextView transferBalanceText;
    TextView transferBackText;

    Button transferForwardButton;

    EditText transferValueInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transfer);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.transferForwardButton = findViewById(R.id.transfer_forward_button);
        this.transferValueInput = findViewById(R.id.transfer_value_input);
        this.transferBalanceText = findViewById(R.id.transfer_balance);
        this.transferBackText = findViewById(R.id.transfer_back);

        this.transferForwardButton.setOnClickListener(this::forwardButtonOnClickListener);
        this.transferBackText.setOnClickListener(this::backOnClickListener);

        this.readIntentExtraAndSetBalanceText();

    }

    private void readIntentExtraAndSetBalanceText() {

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras == null)
            return;

        String balance = CurrencyHelper.convertFloatToRealCurrency(extras.getFloat("balance"));

        this.transferBalanceText.setText(balance);

    }

    private void backOnClickListener(View view) {
        finish();
    }

    private void forwardButtonOnClickListener(View view) {

        float value = InputHelper.readDecimalEditText(this.transferValueInput);

        if (!TransferActivity.validateTransfer(value)) {
            this.showInvalidTransferDialog();
            return;
        }

        Intent intent = new Intent();

        intent.putExtra("transfer_value", value);

        setResult(Activity.RESULT_OK, intent);

        finish();

    }

    private void showInvalidTransferDialog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage(R.string.transfer_input_error_dialog_message);
        alert.setTitle(R.string.transfer_input_error_dialog_title);
        alert.setNeutralButton(R.string.transfer_input_error_dialog_button_text, null);
        alert.show();

    }

    public static boolean validateTransfer(float value) {
        return value >= 1;
    }

}