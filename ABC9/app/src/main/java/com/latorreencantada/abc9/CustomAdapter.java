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
        textView = (TextView)itemView.findViewById(R.id.text_view);
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
        View itemView = inflater.inflate(R.layout.item_employee, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textView.setText(items.get(position).getName());
        if (!items.get(position).isChecked()){
            holder.imageView.setImageResource(R.drawable.ic_baseline_home_24);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_baseline_close_24);
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
            holder.itemView.setBackgroundColor(Color.parseColor("#f8f8fa"));
            holder.textView.setTextColor(Color.parseColor("#c5c5c7"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.textView.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
