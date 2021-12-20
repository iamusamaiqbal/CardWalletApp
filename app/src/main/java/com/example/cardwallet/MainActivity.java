package com.example.cardwallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button card_1;
    ImageView share,ratting_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        card_1=findViewById(R.id.card_1);
        //card_3=findViewById(R.id.card_3);
        //share=findViewById(R.id.share);
        //ratting_bar=findViewById(R.id.ratting_bar);

        card_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, card_categories.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
//
//        ratting_bar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(MainActivity.this,rating_bar.class);
//                startActivity(intent);
//            }
//        });
//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                    Intent shareintent = new Intent();
//                    shareintent.setAction(Intent.ACTION_SEND);
//                    shareintent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?1d-h1");
//                    shareintent.setType("Text/Plain");
//                    startActivity(Intent.createChooser(shareintent, "share via"));
//
//            }
//        });
//
//        ratting_bar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
//                i.setData(Uri.parse("https://play.google.com/store/apps"));
//                startActivity(i);
//            }
//        });

    }
}