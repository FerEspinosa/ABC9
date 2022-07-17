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

import com.latorreencantada.abc9.Models.Level;

import java.util.List;


public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.CustomViewHolder>{

    List<Level> levels;
    Context context;
    int row_index = -1;

    public LevelAdapter(List<Level> levels, Context context) {
        this.levels = levels;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.level_item, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.levelName.setText(levels.get(position).getLevel_num());
        if (!levels.get(position).isChecked()){
            holder.levelImage.setBackgroundColor(Color.parseColor("#454545"));
            holder.levelName.setTextColor(Color.parseColor("#000000"));
        } else {
            holder.levelImage.setBackgroundColor(Color.BLACK);
            holder.levelName.setTextColor(Color.BLUE);
        }

        holder.setLevelClickListener(new LevelClickListener() {
            @Override
            public void onClick(View view, int position) {
                row_index=position;
                Global.currentLevel= levels.get(position);
                notifyDataSetChanged();

            }
        });

        if (row_index==position){
            holder.levelImage.setBackgroundColor(Color.parseColor("#454545"));
            holder.levelName.setTextColor(Color.parseColor("#000000"));
        } else {
            holder.levelImage.setBackgroundColor(Color.BLACK);
            holder.levelName.setTextColor(Color.BLUE);
        }
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView levelName;
        public ImageView levelImage;

        LevelClickListener levelClickListener;

        public void setLevelImage(ImageView levelImage) {
            this.levelImage = levelImage;
        }

        public void setLevelClickListener(LevelClickListener levelClickListener) {
            this.levelClickListener = levelClickListener;
        }

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            levelName = (TextView) itemView.findViewById(R.id.level_text);
            levelImage = (ImageView) itemView.findViewById(R.id.level_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            levelClickListener.onClick(view, getAdapterPosition());
        }
    }
}
