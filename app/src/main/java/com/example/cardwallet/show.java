package com.example.cardwallet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class show extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    ImageView arow_back_arrow,frontImage,backImage, edit_button,delete_icon;
    TextView name, value;
    CardModel card;
    List<FieldsModel> fieldsList = new ArrayList<FieldsModel>();
    SQLiteHandler database;
    ClipboardManager clipboard;
    int fieldId;
    String Pfieldnme,Pfieldvalue;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);


        arow_back_arrow = findViewById(R.id.arow_back_arrow);
        frontImage = findViewById(R.id.image_add_card);
        backImage = findViewById(R.id.image_add_card_2);
        edit_button = findViewById(R.id.edit_icon);
        name = findViewById(R.id.card_name);
        delete_icon = findViewById(R.id.delete_icon);



        Intent i = getIntent();
        int id = Integer.parseInt(i.getExtras().getString("id"));

        database = new SQLiteHandler(this);

        card = database.getCard(id);


        if(card != null){
            try {

                fieldsList = database.getAllFields(card.id);

                if(fieldsList != null){
                    fieldsList.forEach(this::createField);
                }

                name.setText(card.name);

                Bitmap bitmap = BitmapFactory.decodeFile(card.frontImage);


                frontImage.setImageBitmap(bitmap);
                frontImage.setVisibility(View.VISIBLE);

                if(card.backImage != null){
                    Bitmap bitmap2 = BitmapFactory.decodeFile(card.backImage);
                    backImage.setImageBitmap(bitmap2);
                    backImage.setVisibility(View.VISIBLE);
                } else {
                    backImage.setVisibility(View.GONE);
                }

            } catch (Exception e){
                throw e;
            }
        }

        edit_button.setOnClickListener(v -> {
            Intent intent = new Intent(this , Add_New_Card.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            intent.putExtra("type",""+card.type);
            intent.putExtra("edit",true);
            intent.putExtra("id",""+card.id);
            startActivity(intent);
        });



        delete_icon.setOnClickListener(v -> {

            Log.e("Delete","Button");

            if(database.deleteCard(card.id)){

                Log.e("Delete","DELETED");

                //finish();

                Toast.makeText(this, "Card Deleted", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this , card_list.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("type",""+card.type);
                startActivity(intent);

            } else {

                Toast.makeText(this, "Operation Failed", Toast.LENGTH_LONG).show();

            }
        });

        arow_back_arrow.setOnClickListener(v->{


                super.onBackPressed();
                Intent intent = new Intent(this, card_list.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("type", "" + card.type);
                startActivity(intent);

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this , card_list.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("type",""+card.type);
        startActivity(i);
    }

    public  void showPopup(View v){
        PopupMenu popupMenu=new PopupMenu(this ,v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu_item);
        popupMenu.show();
    }
    @Override
    public
    boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.copy:{

                try {
                    ClipData myClip;

                    myClip = ClipData.newPlainText("text",value.getText().toString());
                    clipboard.setPrimaryClip(myClip);


                    Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
                    Log.e("Copied",value.getText().toString());

                } catch (Exception e){
                    throw e;
                }

                return true;
            }

            case R.id.edit:{

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

                    View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
                    alertDialog.setTitle("Enter details");
                    alertDialog.setMessage("");

                    alertDialog.setView(dialogView);

                    TextInputEditText fieldname = dialogView.findViewById(R.id.field_name);
                    TextInputEditText fieldvalue = dialogView.findViewById(R.id.field_value);

                    fieldname.setText(Pfieldnme);
                    fieldvalue.setText(Pfieldvalue);

                    alertDialog.setPositiveButton(Html.fromHtml("<font color='#34495E'>Yes</font>"),
                            (dialog, which) -> {

                                String name = fieldname.getText().toString();
                                String value = fieldvalue.getText().toString();

                                if (!name.isEmpty()) {
                                    if (!value.isEmpty()) {

                                        database.updateField(new FieldsModel(name,value),fieldId);
                                        onBackPressed();
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

                Toast.makeText(this, "Edit click", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.delete:{
                try {

                    boolean flag = database.deleteFields(fieldId);

                    onBackPressed();
                    //this.recreate();

                    Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
                    Log.e("Deleted",value.getText().toString());

                } catch (Exception e){
                    throw e;
                }
                return true;
            }
            default:
                return false;
        }
    }


    private void createField(FieldsModel fieldsList) {

        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        LinearLayout linearLayout = findViewById(R.id.linear1);

        View newField = getLayoutInflater().inflate(R.layout.field,linearLayout,false);

        TextView name = newField.findViewById(R.id.textView_field_1);
        value = newField.findViewById(R.id.textView_field_2);
        ImageView imageView = newField.findViewById(R.id.imageView_field_3);

        fieldId = fieldsList.id;
        Pfieldnme = fieldsList.fieldname;
        Pfieldvalue = fieldsList.fieldvalue;

        imageView.setOnClickListener(this::showPopup);




        name.setText(fieldsList.fieldname);
        value.setText(fieldsList.fieldvalue);

//        value.setOnClickListener(v -> {
//            try {
//                ClipData myClip;
//
//                myClip = ClipData.newPlainText("hajj",value.getText().toString());
//                clipboard.setPrimaryClip(myClip);
//
//                Log.e("EEEEEEEEE",value.getText().toString());
//
//            } catch (Exception e){
//
//            }
//        });

        linearLayout.addView(newField);


    }
}