package com.example.cardwallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class PinActivity extends AppCompatActivity {

    TextInputEditText pin;
    TextInputEditText pin2;
    CheckBox checkBox;
    Button submit;
    String firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        pin = findViewById(R.id.pin1);
        pin2 = findViewById(R.id.pin2);
        checkBox = findViewById(R.id.pincheck);
        submit = findViewById(R.id.submitPin);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        firstTime = preferences.getString("firstTime", "");

        pin.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        pin2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }
        });

        submit.setOnClickListener(v -> {

            if (!pin.getText().toString().isEmpty() && pin.getText().toString().length() >= 5) {

                if (!pin2.getText().toString().isEmpty() && pin2.getText().toString().length() >= 5) {

                    if (pin.getText().toString().equals(pin2.getText().toString())) {

                        if (checkBox.isChecked()) {

                            Toast.makeText(getApplicationContext(),
                                    "Pin activated " + checkBox.isChecked(), Toast.LENGTH_SHORT).show();

                            editor.putString("pin", pin2.getText().toString());
                            editor.apply();


                            Intent i = new Intent(this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);

                            editor.putString("firstTime", "no");
                            editor.apply();

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Please verify the statement", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Pin did not match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please confirm the pin", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Please enter at least 5 digits", Toast.LENGTH_SHORT).show();
            }

        });

    }
}