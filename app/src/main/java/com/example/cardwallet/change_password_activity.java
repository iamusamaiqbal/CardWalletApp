package com.example.cardwallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class change_password_activity extends AppCompatActivity {

    EditText pin;
    EditText pin2;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        pin = findViewById(R.id.input_dialog_1);
        pin2 = findViewById(R.id.input_dialog_2);
        submit = findViewById(R.id.submit);

        pin.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        pin2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);


        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String userPin = preferences.getString("pin", "");

//
//        flag.setChecked(Constants.isFingerPrint);
//        checkBox.setChecked(false);
//
//        flag.setOnCheckedChangeListener((buttonView, isChecked) -> {
//
//
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//                FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
//                KeyguardManager keyguardManager =(KeyguardManager) getSystemService(KEYGUARD_SERVICE);
//
//                if(!fingerprintManager.isHardwareDetected()){
//
//                    Constants.isFingerPrint = false;
//                    flag.setChecked(false);
//
//                    Toast.makeText(getApplicationContext(),
//                            "Fingerprint Scanner not detected in Device", Toast.LENGTH_SHORT).show();
//
//                } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
//
//                    Constants.isFingerPrint = false;
//                    flag.setChecked(false);
//
//                    Toast.makeText(getApplicationContext(),
//                            "Permission not granted to use Fingerprint Scanner", Toast.LENGTH_SHORT).show();
//
//                } else if (!keyguardManager.isKeyguardSecure()){
//
//                    Constants.isFingerPrint = false;
//                    flag.setChecked(false);
//
//                    Toast.makeText(getApplicationContext(),
//                            "Add Lock to your Phone in Settings", Toast.LENGTH_SHORT).show();
//
//                } else if (!fingerprintManager.hasEnrolledFingerprints()){
//
//                    Constants.isFingerPrint = false;
//                    flag.setChecked(false);
//
//                    Toast.makeText(getApplicationContext(),
//                            "You should add at least 1 Fingerprint to use this Feature", Toast.LENGTH_SHORT).show();
//
//                } else {
//
//                    Constants.isFingerPrint = !Constants.isFingerPrint;
//
//                    flag.setChecked(Constants.isFingerPrint);
//
//                    if(Constants.isFingerPrint){
//
//                        Toast.makeText(getApplicationContext(),
//                                "Fingerprint activated ", Toast.LENGTH_SHORT).show();
//                    } else {
//
//                        Toast.makeText(getApplicationContext(),
//                                "Fingerprint deactivated ", Toast.LENGTH_SHORT).show();
//                    }
//                    editor.putBoolean("isFingerPrint", Constants.isFingerPrint);
//                    editor.apply();
//                }
//            }
//
//
//        });


        submit.setOnClickListener(v -> {

            if (!pin.getText().toString().isEmpty()) {

                if(pin2.getText().length()>=5){

                    if (pin.getText().toString().equals(userPin)) {

                        editor.putString("pin", pin2.getText().toString());
                        editor.apply();

                        Toast.makeText(getApplicationContext(), "Pin is Changed", Toast.LENGTH_SHORT).show();

                        onBackPressed();

                    } else {
                        Toast.makeText(getApplicationContext(), "Pin did not match", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter at least 5 digits", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Pin is Empty", Toast.LENGTH_SHORT).show();

        }
        });

    }
}