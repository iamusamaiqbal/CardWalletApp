package com.example.cardwallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class UserPinActivity extends AppCompatActivity {

    TextInputEditText userPin;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pin);

        SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        String pin = preferences.getString("pin", "");

        userPin = findViewById(R.id.userEnterPin);
        submit = findViewById(R.id.userSubmitPin);

        userPin.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        submit.setOnClickListener(v -> {

            if (!userPin.getText().toString().isEmpty()) {

                if (userPin.getText().toString().equals(pin)) {

                    Intent i = new Intent(this, card_categories.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Pin did not match", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getApplicationContext(),
                        "Pin is empty", Toast.LENGTH_SHORT).show();
            }

        });

    }
}