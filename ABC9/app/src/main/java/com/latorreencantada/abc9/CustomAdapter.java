package com.latorreencantada.abc9;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.latorreencantada.abc9.Models.Item;

import java.util.List;


class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView textView;
    public ImageView imageView;
    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.player_name);
        imageView = (ImageView)itemView.findViewById(R.id.image_view);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition());
    }
}

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    List<Item> items;
    Context context;
    int row_index = -1;

    public CustomAdapter(List<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.player_layout, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textView.setText(items.get(position).getName());
        if (!items.get(position).isChecked()){
            holder.imageView.setScaleX(1f);
            holder.imageView.setScaleY(1f);
            holder.textView.setTextColor(Color.parseColor("#000000"));
        } else {
            holder.imageView.setScaleX(3);
            holder.imageView.setScaleY(3);
            holder.imageView.setPadding(0,0,0,0);
            holder.textView.setTextColor(Color.parseColor("#c5c5c7"));
        }

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                row_index = position;
                Common.currentItem = items.get(position);
                notifyDataSetChanged();
            }
        });

        if (row_index==position){
            holder.itemView.setScaleX(1.2f);
            holder.itemView.setScaleY(1.2f);
            holder.textView.setTextColor(Color.parseColor("#000000"));
        } else {
            holder.itemView.setScaleX(1f);
            holder.itemView.setScaleY(1f);
            holder.textView.setTextColor(Color.parseColor("#c5c5c7"));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
