package com.example.cardwallet;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    LinearLayout view_custom_desgin;
    ImageView image;

    public ViewHolder(@NonNull View itemView) {

        super(itemView);

        view_custom_desgin = itemView.findViewById(R.id.custom_ds_22);
        textView=itemView.findViewById(R.id.text_view_22);
        image = itemView.findViewById(R.id.categoryImage);
    }
}
