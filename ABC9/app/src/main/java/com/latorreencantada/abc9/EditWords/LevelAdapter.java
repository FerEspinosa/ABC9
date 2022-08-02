package com.latorreencantada.abc9.EditWords;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.latorreencantada.abc9.Global;
import com.latorreencantada.abc9.Models.Card;
import com.latorreencantada.abc9.Models.Level;
import com.latorreencantada.abc9.R;

import java.util.ArrayList;
import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelViewHolder> {

    List<Level> levels;
    List<Card> cards = new ArrayList<>();

    public LevelAdapter (List<Level> levels){
        this.levels = levels;
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_list_layout, parent, false);
        return new LevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, int position) {

        Level level = levels.get(position);
        String levelName = level.getLevelNum() +": "+  level.getLevelName();
        holder.levelName.setText(levelName);

        boolean isChecked = level.isExpanded();
        holder.expandableLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        holder.arrowImage.setImageResource(isChecked ? R.drawable.ic_arrow_drop_up : R.drawable.ic_arrow_drop_down);

        // esta es la parte nueva:
        CardAdapter adapter = new CardAdapter(cards);
        holder.nestedRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.nestedRecyclerView.setHasFixedSize(true);
        holder.nestedRecyclerView.setAdapter(adapter);

        holder.level_rv_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level.setExpanded(!level.isExpanded());
                Global.currentLevel = level;
                cards = level.getCards();
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    class LevelViewHolder extends RecyclerView.ViewHolder {

        LinearLayout level_rv_layout;
        RelativeLayout expandableLayout;
        TextView levelName;
        ImageView arrowImage;
        RecyclerView nestedRecyclerView;


        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);

            level_rv_layout = itemView.findViewById(R.id.level_rv_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_level_layout);
            levelName = itemView.findViewById(R.id.level_name);
            arrowImage = itemView.findViewById(R.id.arrow_imageView);
            nestedRecyclerView = itemView.findViewById(R.id.edit_levels_recyclerview);
        }
    }
}
