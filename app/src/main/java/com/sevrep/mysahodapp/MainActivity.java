package com.sevrep.mysahodapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText edtDailyRate;
    private EditText edtRegular;
    private EditText edtOvertime;
    private TextView txtSahod;
    private TextView txtHourlyRate;
    private TextView txtOvertimeRate;
    private TextView txtRegularTotal;
    private TextView txtOvertimeTotal;

    private double regularHourRate;
    private double regularHourTotal;
    private double regularOvertimeRate;
    private double regularOvertimeTotal;

    private final double REGULAR_WORKING_HOURS = 8.00;
    private final double OVERTIME_MULTIPLIER = 1.25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtDailyRate = findViewById(R.id.edtDailyRate);
        edtRegular = findViewById(R.id.edtRegular);
        edtOvertime = findViewById(R.id.edtOvertime);
        txtSahod = findViewById(R.id.txtSahod);
        txtHourlyRate = findViewById(R.id.txtHourlyRate);
        txtOvertimeRate = findViewById(R.id.txtOvertimeRate);
        txtRegularTotal = findViewById(R.id.txtRegularTotal);
        txtOvertimeTotal = findViewById(R.id.txtOvertimeTotal);

        edtDailyRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                regularHourRate = s.length() != 0 ? Double.parseDouble(edtDailyRate.getText().toString()) / REGULAR_WORKING_HOURS : 0.0;
                regularOvertimeRate = regularHourRate * OVERTIME_MULTIPLIER;
                String regularRate = "Php " + String.format(Locale.ENGLISH, "%.2f", regularHourRate);
                String overtimeRate = "Php " + String.format(Locale.ENGLISH, "%.2f", regularOvertimeRate);
                txtHourlyRate.setText(regularRate);
                txtOvertimeRate.setText(overtimeRate);
                if (regularHourRate != 0.0) {
                    regularHourTotal = multiplyUserInputByReg();
                    displayRegularTotal(regularHourTotal);
                }
                if (regularOvertimeRate != 0.0) {
                    regularOvertimeTotal = multiplyUserInputByOt();
                    displayOvertimeTotal(regularOvertimeTotal);
                }
                displaySahodTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtRegular.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                regularHourTotal = s.length() != 0 ? multiplyUserInputByReg() : 0.0;
                displayRegularTotal(regularHourTotal);
                displaySahodTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtOvertime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                regularOvertimeTotal = s.length() != 0 ? multiplyUserInputByOt() : 0.0;
                displayOvertimeTotal(regularOvertimeTotal);
                displaySahodTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void displayOvertimeTotal(double regularOvertimeTotal) {
        String overtimeTotal = "Php " + String.format(Locale.ENGLISH, "%.2f", regularOvertimeTotal);
        txtOvertimeTotal.setText(overtimeTotal);
    }

    private double multiplyUserInputByOt() {
        return Double.parseDouble(edtOvertime.getText().toString()) * regularOvertimeRate;
    }

    private double multiplyUserInputByReg() {
        return Double.parseDouble(edtRegular.getText().toString()) * regularHourRate;
    }

    private void displayRegularTotal(double regularHourTotal) {
        String regularTotal = "Php " + String.format(Locale.ENGLISH, "%.2f", regularHourTotal);
        txtRegularTotal.setText(regularTotal);
    }

    private void displaySahodTotal() {
        double totalHourOt = regularHourTotal + regularOvertimeTotal;
        String totalSahod = "Php " + String.format(Locale.ENGLISH, "%.2f", totalHourOt);
        txtSahod.setText(totalSahod);
    }

}