package com.example.cardwallet;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class card_categories extends AppCompatActivity {
    CardView business, credit, debit, voting, driving, employeeCard, idcard, passport, shopingcard, other;
    ImageView setting_categories;
    List<CardModel> cardList;
    private Toolbar toolbar1;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_categories);
//        setting_categories = findViewById(R.id.setting_categories);
        toolbar1 = findViewById(R.id.toolbar1);

        setSupportActionBar(toolbar1);
        setTitle("");
//        toolbar1.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar1.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);
//


        SQLiteHandler database = new SQLiteHandler(this);




        credit = findViewById(R.id.credit);
        credit.setOnClickListener(v -> {
            cardList = database.getAllCard("credit");

            if (cardList.isEmpty()) {
                Intent intent = new Intent(card_categories.this, Add_New_Card.class);
                intent.putExtra("type", "credit");
                intent.putExtra("edit", false);
                intent.putExtra("id", "");
                startActivity(intent);
            } else {
                Intent intent = new Intent(card_categories.this, card_list.class);
                intent.putExtra("type", "credit");
                startActivity(intent);
            }
        });


        business = findViewById(R.id.business);
        business.setOnClickListener(v -> {
            cardList = database.getAllCard("business");

            if (cardList.isEmpty()) {
                Intent intent = new Intent(card_categories.this, Add_New_Card.class);
                intent.putExtra("type", "business");
                intent.putExtra("edit", false);
                intent.putExtra("id", "");
                startActivity(intent);
            } else {
                Intent intent = new Intent(card_categories.this, card_list.class);
                intent.putExtra("type", "business");
                startActivity(intent);
            }
        });


        debit = findViewById(R.id.debit);
        debit.setOnClickListener(v -> {
            cardList = database.getAllCard("debit");

            if (cardList.isEmpty()) {
                Intent intent = new Intent(card_categories.this, Add_New_Card.class);
                intent.putExtra("type", "debit");
                intent.putExtra("edit", false);
                intent.putExtra("id", "");
                startActivity(intent);
            } else {
                Intent intent = new Intent(card_categories.this, card_list.class);
                intent.putExtra("type", "debit");
                startActivity(intent);
            }
        });


        voting = findViewById(R.id.voting);
        voting.setOnClickListener(v -> {
            cardList = database.getAllCard("voting");

            if (cardList.isEmpty()) {
                Intent intent = new Intent(card_categories.this, Add_New_Card.class);
                intent.putExtra("type", "voting");
                intent.putExtra("edit", false);
                intent.putExtra("id", "");
                startActivity(intent);
            } else {
                Intent intent = new Intent(card_categories.this, card_list.class);
                intent.putExtra("type", "voting");
                startActivity(intent);
            }
        });


        driving = findViewById(R.id.driving);
        driving.setOnClickListener(v -> {
            cardList = database.getAllCard("driving");

            if (cardList.isEmpty()) {
                Intent intent = new Intent(card_categories.this, Add_New_Card.class);
                intent.putExtra("type", "driving");
                intent.putExtra("edit", false);
                intent.putExtra("id", "");
                startActivity(intent);
            } else {
                Intent intent = new Intent(card_categories.this, card_list.class);
                intent.putExtra("type", "driving");
                startActivity(intent);
            }
        });

        employeeCard = findViewById(R.id.employeeCard);
        employeeCard.setOnClickListener(v -> {
            cardList = database.getAllCard("employeeCard");

            if (cardList.isEmpty()) {
                Intent intent = new Intent(card_categories.this, Add_New_Card.class);
                intent.putExtra("type", "employeeCard");
                intent.putExtra("edit", false);
                intent.putExtra("id", "");
                startActivity(intent);
            } else {
                Intent intent = new Intent(card_categories.this, card_list.class);
                intent.putExtra("type", "employeeCard");
                startActivity(intent);
            }
        });


        idcard = findViewById(R.id.idcard);
        idcard.setOnClickListener(v -> {
            cardList = database.getAllCard("idcard");

            if (cardList.isEmpty()) {
                Intent intent = new Intent(card_categories.this, Add_New_Card.class);
                intent.putExtra("type", "idcard");
                intent.putExtra("edit", false);
                intent.putExtra("id", "");
                startActivity(intent);
            } else {
                Intent intent = new Intent(card_categories.this, card_list.class);
                intent.putExtra("type", "idcard");
                startActivity(intent);
            }
        });

        passport = findViewById(R.id.passport);
        passport.setOnClickListener(v -> {
            cardList = database.getAllCard("passport");

            if (cardList.isEmpty()) {
                Intent intent = new Intent(card_categories.this, Add_New_Card.class);
                intent.putExtra("type", "passport");
                intent.putExtra("edit", false);
                intent.putExtra("id", "");
                startActivity(intent);
            } else {
                Intent intent = new Intent(card_categories.this, card_list.class);
                intent.putExtra("type", "passport");
                startActivity(intent);
            }
        });

        shopingcard = findViewById(R.id.shopingcard);
        shopingcard.setOnClickListener(v -> {
            cardList = database.getAllCard("shopingcard");

            if (cardList.isEmpty()) {
                Intent intent = new Intent(card_categories.this, Add_New_Card.class);
                intent.putExtra("type", "shopingcard");
                intent.putExtra("edit", false);
                intent.putExtra("id", "");
                startActivity(intent);
            } else {
                Intent intent = new Intent(card_categories.this, card_list.class);
                intent.putExtra("type", "shopingcard");
                startActivity(intent);
            }
        });

        other = findViewById(R.id.other_card);
        other.setOnClickListener(v -> {
            cardList = database.getAllCard("other");

            if (cardList.isEmpty()) {
                Intent intent = new Intent(card_categories.this, Add_New_Card.class);
                intent.putExtra("type", "other");
                intent.putExtra("edit", false);
                intent.putExtra("id", "");
                startActivity(intent);
            } else {
                Intent intent = new Intent(card_categories.this, card_list.class);
                intent.putExtra("type", "other");
                startActivity(intent);
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.catageres_menu, menu);
        ((MenuBuilder) menu).setOptionalIconsVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.setting_c:
//                Toast.makeText(this, "setting clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.ratting_c:
//                Toast.makeText(this, "rating clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.share_c:
//                Toast.makeText(this, "share clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
            if (item.getItemId() == R.id.setting_c) {


                Toast.makeText(this, "clicked setting", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(card_categories.this, setting.class);
                startActivity(intent);

            } else if (item.getItemId() == R.id.ratting_c) {
                Toast.makeText(this, "ratting click", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(card_categories.this, rating_bar.class);
                startActivity(intent);

            } else if (item.getItemId() == R.id.share_c) {
                Toast.makeText(this, "click share", Toast.LENGTH_SHORT).show();

                Intent shareintent = new Intent();
                shareintent.setAction(Intent.ACTION_SEND);
                shareintent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?1d-h1");
                shareintent.setType("Text/Plain");
                startActivity(Intent.createChooser(shareintent, "share via"));

            }
            return super.onOptionsItemSelected(item);


        }
    }





