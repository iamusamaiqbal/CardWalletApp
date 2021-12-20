package com.example.cardwallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class card_list extends AppCompatActivity {
    RecyclerView recycler_card;
    Recycler_Adapter adapter;
    ImageView add_new_card,back_list;
    String type;
    TextView listHeader,add_card;

    CardModel[] cardList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);


        Intent i = getIntent();
        type = i.getExtras().getString("type");

        SQLiteHandler database = new SQLiteHandler(this);

        cardList = database.getAllCard(type).toArray(new CardModel[0]);

        recycler_card=findViewById(R.id.recycler_card);
        add_new_card=findViewById(R.id.add_card1);
        listHeader = findViewById(R.id.listHeader);
        back_list = findViewById(R.id.back_list);
        add_card = findViewById(R.id.add_card);

        String catName = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();

        listHeader.setText(catName);

        recycler_card.setLayoutManager(new GridLayoutManager(this,2));
        adapter=new Recycler_Adapter(this,cardList);
        recycler_card.setAdapter(adapter);

        add_new_card.setOnClickListener(v -> {
            Intent intent=new Intent(card_list.this,Add_New_Card.class);
            intent.putExtra("type",type);
            startActivity(intent);
        });

        add_card.setOnClickListener(v -> {
            Intent intent=new Intent(card_list.this,Add_New_Card.class);
            intent.putExtra("type",type);
            startActivity(intent);
        });

        back_list.setOnClickListener(v-> {

            Intent intent = new Intent(this , card_categories.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("type",""+type);
            startActivity(intent);

        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(this , card_categories.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("type",""+type);
        startActivity(i);

    }
}