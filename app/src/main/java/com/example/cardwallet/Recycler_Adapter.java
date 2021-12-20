package com.example.cardwallet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Recycler_Adapter extends RecyclerView.Adapter<ViewHolder> {

    CardModel[] data;
    Context context;

    public Recycler_Adapter( Context context,  CardModel[] data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.custom_design_2,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.image.setImageURI(Uri.parse(data[position].frontImage));
        holder.textView.setText(data[position].name);
        holder.view_custom_desgin.setOnClickListener(v -> {

            Intent i = new Intent(context,show.class);
            i.putExtra("id",""+data[position].id);
            context.startActivity(i);

        });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }


}
