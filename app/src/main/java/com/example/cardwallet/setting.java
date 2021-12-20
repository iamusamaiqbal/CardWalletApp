package com.example.cardwallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class setting extends AppCompatActivity {
    ImageView arrow_back_setting;
    CardView setting_card_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        arrow_back_setting=findViewById(R.id.arrow_back_setting);
        setting_card_1=findViewById(R.id.setting_card_1);


        Switch flag = findViewById(R.id.fingerprintSwitch);

        SharedPreferences preferences = getSharedPreferences("MyPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        flag.setChecked(Constants.isFingerPrint);


        flag.setOnCheckedChangeListener((buttonView, isChecked) -> {


            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
                KeyguardManager keyguardManager =(KeyguardManager) getSystemService(KEYGUARD_SERVICE);

                if(!fingerprintManager.isHardwareDetected()){

                    Constants.isFingerPrint = false;
                    flag.setChecked(false);

                    Toast.makeText(getApplicationContext(),
                            "Fingerprint Scanner not detected in Device", Toast.LENGTH_SHORT).show();

                } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){

                    Constants.isFingerPrint = false;
                    flag.setChecked(false);

                    Toast.makeText(getApplicationContext(),
                            "Permission not granted to use Fingerprint Scanner", Toast.LENGTH_SHORT).show();

                } else if (!keyguardManager.isKeyguardSecure()){

                    Constants.isFingerPrint = false;
                    flag.setChecked(false);

                    Toast.makeText(getApplicationContext(),
                            "Add Lock to your Phone in Settings", Toast.LENGTH_SHORT).show();

                } else if (!fingerprintManager.hasEnrolledFingerprints()){

                    Constants.isFingerPrint = false;
                    flag.setChecked(false);

                    Toast.makeText(getApplicationContext(),
                            "You should add at least 1 Fingerprint to use this Feature", Toast.LENGTH_SHORT).show();

                } else {

                    Constants.isFingerPrint = isChecked;

                    flag.setChecked(Constants.isFingerPrint);

                    if(Constants.isFingerPrint){

                        Toast.makeText(getApplicationContext(),
                                "Fingerprint activated ", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(getApplicationContext(),
                                "Fingerprint deactivated ", Toast.LENGTH_SHORT).show();
                    }
                    editor.putBoolean("isFingerPrint", Constants.isFingerPrint);
                    editor.apply();
                }
            }


        });



        arrow_back_setting.setOnClickListener(v -> onBackPressed());
        setting_card_1.setOnClickListener(v -> {
            Intent intent=new Intent(setting.this,change_password_activity.class);
            startActivity(intent);
        });
    }
}