package com.example.cardwallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Add_New_Card extends AppCompatActivity {

    String frontImage;
    String backImage;
    String type;

    ImageView front_image_btn,back_image_btn,back_list_1;
//    ImageButton front_image_btn,back_image_btn;
    TextInputEditText card_name;
    TextView newCardHeader;
    Button add_field,submit_card;

    List<FieldsModel> fieldsList = new ArrayList<FieldsModel>();

    boolean flag = false;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        type = intent.getExtras().getString("type");
        boolean edit = intent.getExtras().getBoolean("edit");

        setContentView(R.layout.activity_add_new_card);


        SQLiteHandler database = new SQLiteHandler(this);

        card_name = findViewById(R.id.card_name);

//        image_add_card=findViewById(R.id.image_add_card);
//        image_add_card2=findViewById(R.id.image_add_card_2);
        newCardHeader = findViewById(R.id.newCardHeader);
        back_list_1=findViewById(R.id.back_list_1);

        front_image_btn=findViewById(R.id.front_image);
        back_image_btn=findViewById(R.id.back_image);
        submit_card = findViewById(R.id.save_btn);
        add_field = findViewById(R.id.add_btn);

        String catName = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();

        newCardHeader.setText(catName);


        if(edit){

            String cardid = intent.getExtras().getString("id");

            CardModel card = database.getCard(Integer.parseInt(cardid));

            if(card != null){

                card_name.setText(card.name);
                frontImage = card.frontImage;
                backImage = card.backImage;


                Bitmap bitmap = BitmapFactory.decodeFile(card.frontImage);
                front_image_btn.setImageBitmap(bitmap);
                front_image_btn.setVisibility(View.VISIBLE);

                Bitmap bitmap2 = BitmapFactory.decodeFile(card.backImage);
                back_image_btn.setImageBitmap(bitmap2);
                back_image_btn.setVisibility(View.VISIBLE);


            }


        }


        front_image_btn.setOnClickListener(v -> {
            if (checkPermission()){
                flag = true;
                callCamera();
            } else {
                askPermission();
            }
        });

        back_image_btn.setOnClickListener(v -> {

            if (checkPermission()){
                flag = false;
                callCamera();
            } else {
                askPermission();
            }

        });

        add_field.setOnClickListener(v -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
            alertDialog.setTitle("Enter details");
            //alertDialog.setMessage("");

            alertDialog.setView(dialogView);

            TextInputEditText fieldname = dialogView.findViewById(R.id.field_name);
            TextInputEditText fieldvalue = dialogView.findViewById(R.id.field_value);

            alertDialog.setPositiveButton(Html.fromHtml("<font color='#34495E'>Yes</font>"),
                    (dialog, which) -> {

                        String name = fieldname.getText().toString();
                        String value = fieldvalue.getText().toString();

                        if (!name.isEmpty()) {
                            if (!value.isEmpty()) {

                                fieldsList.add(new FieldsModel(name,value));

                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Value is empty", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(getApplicationContext(),
                                    "Name is empty", Toast.LENGTH_SHORT).show();
                        }
                    });

            alertDialog.setNegativeButton(Html.fromHtml("<font color='#34495E'>No</font>"),
                    (dialog, which) -> dialog.cancel());
            alertDialog.show();
        });

        back_list_1.setOnClickListener(v-> {

            onBackPressed();
        });

        submit_card.setOnClickListener(v -> {

            if(edit){

                if(frontImage != null && card_name != null){
                    try {
                        int new_id;
                        new_id = Integer.parseInt(intent.getExtras().getString("id"));

                        if(fieldsList != null){

                            fieldsList.forEach(fieldsModel -> database.addField(fieldsModel,new_id));

                        }

                        if(frontImage != null && card_name != null){

                            CardModel card = new CardModel(card_name.getText().toString(),type,frontImage,backImage);

                            database.updateCard(card,new_id);

                            Toast.makeText(this,"Updated",Toast.LENGTH_LONG).show();
//                            onBackPressed();

                        } else {

                            Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show();

                        }

                        Intent i = new Intent(this , card_list.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("type",""+type);
                        startActivity(i);

                    } catch (Exception e){
                        throw e;
                    }

                }
                else{
                    Toast.makeText(this,"Images not found",Toast.LENGTH_LONG).show();
                }

            } else {
                if(frontImage != null && card_name != null){
                    if(card_name.getText().length()<=10){
                        try {
                            int new_id;
                            new_id = database.addCard(new CardModel(card_name.getText().toString(),type,frontImage,backImage));

                            if(fieldsList != null){

                                fieldsList.forEach(fieldsModel -> database.addField(fieldsModel,new_id));

                            }

                            Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show();

//                        onBackPressed();
                            Intent i = new Intent(this , card_list.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra("type",""+type);
                            startActivity(i);

                        } catch (Exception e){
                            throw e;
                        }
                    } else {
                        Toast.makeText(this,"Name should be characters 10 long",Toast.LENGTH_LONG).show();
                    }


                }
                else{
                    Toast.makeText(this,"Images not found",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent i = new Intent(this , card_list.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        i.putExtra("type",""+type);
//        startActivity(i);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){

                boolean p = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                if (p) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
                }

            } else {
                boolean p = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean p1 = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                boolean p2 = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                if (p && p1 && p2) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



       // if (requestCode == 100) {
            switch (resultCode) {
                case RESULT_OK:
                    Log.e("Pic Taken", "Picture taken! :)");
                    if (data != null) {
                        
                        Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);

//                        Bitmap bitmap = data.getParcelableExtra("data");

                        Bitmap bitmap = null;

                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        getContentResolver().delete(uri, null, null);


                        if(flag){
                            front_image_btn.setImageBitmap(bitmap);
                            front_image_btn.setVisibility(View.VISIBLE);

                            frontImage = saveImage(bitmap).getAbsolutePath();

                        }
                        else {
                            back_image_btn.setImageBitmap(bitmap);
                            back_image_btn.setVisibility(View.VISIBLE);

                            backImage = saveImage(bitmap).getAbsolutePath();

                        }
                    }
                    break;
                case RESULT_CANCELED:
                    Log.e("Pic Canceled", "Picture canceled! : ");
                    break;
            }
       // }
    }

    public void askPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {

                ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.CAMERA},101);

                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            int result2 = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA);
            return Environment.isExternalStorageManager() && result2 == PackageManager.PERMISSION_GRANTED ;

        } else {

            int result = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int result2 = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA);

            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
        }
    }

    public void callCamera()
    {

        int preference = ScanConstants.OPEN_CAMERA;
        Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        startActivityForResult(intent, 100);
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, 100);
    }

    public File saveImage(Bitmap finalBitmap) {

        String root = getFilesDir().getAbsolutePath();

        File myDir = new File(root);

        if (!myDir.exists()) {
            myDir.mkdir();
        }


        Random generator = new Random();
        int n = 10000;

        n = generator.nextInt(n);

        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);

        if (file.exists())
            file.delete ();

        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}