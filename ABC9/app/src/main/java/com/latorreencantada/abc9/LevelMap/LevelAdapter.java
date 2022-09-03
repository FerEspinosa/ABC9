package com.latorreencantada.abc9.LevelMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.latorreencantada.abc9.Global;
import com.latorreencantada.abc9.Models.Level;
import com.latorreencantada.abc9.R;
import com.latorreencantada.abc9.root.ItemClickListener;

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
        View itemView = inflater.inflate(R.layout.levelmap_item, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.levelName.setText(String.valueOf(levels.get(position).getLevelNum()));

        // obtener el máx nivel desbloqueado
        SharedPreferences sharedPreferences = context.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE);
        int highestUnlockedLevel = sharedPreferences.getInt("HighestUnlockedLevel",0);

        if (position<=highestUnlockedLevel){
            // dar formato a nivel no seleccionado y desbloqueado
            holder.itemView.setClickable(true);
            holder.levelImage.setBackgroundColor(Color.parseColor("#FFF1BE"));
            holder.levelName.setTextColor(Color.parseColor("#AB6410"));
        } else {
            // dar formato a nivel no seleccionado y bloqueado
            holder.itemView.setClickable(false);
            holder.levelImage.setBackgroundColor(Color.parseColor("#7c7c7c"));
            holder.levelName.setTextColor(Color.parseColor("#2B2B2B"));
        }

        holder.setLevelClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                row_index=position;
                Global.currentLevel= levels.get(position);
                // la variable "Global.currentLevel" (luego de haber sido seteada en la línea anterior)
                // me va a servir cuando quiera iniciar la "NivelActivity"

                notifyDataSetChanged();

            }
        });

        if (row_index==position){
            // dar formato al nivel seleccionado:
            holder.itemView.setScaleX(1.25f);
            holder.itemView.setScaleY(1.25f);
            holder.levelImage.setBackgroundColor(Color.parseColor("#E4B81C"));
            holder.levelName.setTextColor(Color.parseColor("#AB6410"));
        } else {

            // dar formato a nivel no seleccionado y desbloqueado
            if (position <= highestUnlockedLevel){
                holder.itemView.setScaleX(1);
                holder.itemView.setScaleY(1);
                holder.levelImage.setBackgroundColor(Color.parseColor("#FFF1BE"));
                holder.levelName.setTextColor(Color.parseColor("#AB6410"));
            } else {
                // dar formato a nivel no seleccionado y bloqueado
                holder.levelImage.setBackgroundColor(Color.parseColor("#7c7c7c"));
                holder.levelName.setTextColor(Color.parseColor("#2B2B2B"));
            }

        }
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView levelName;
        public ImageView levelImage;

        ItemClickListener itemClickListener;

        public void setLevelImage(ImageView levelImage) {
            this.levelImage = levelImage;
        }

        public void setLevelClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            levelName =  itemView.findViewById(R.id.level_text);
            levelImage = itemView.findViewById(R.id.level_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition());
        }
    }
}