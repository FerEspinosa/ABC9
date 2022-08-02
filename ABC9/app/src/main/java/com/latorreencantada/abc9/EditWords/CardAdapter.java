package com.latorreencantada.abc9.EditWords;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.latorreencantada.abc9.root.ItemClickListener;
import com.latorreencantada.abc9.Models.Card;
import com.latorreencantada.abc9.Models.Level;
import com.latorreencantada.abc9.R;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CustomViewHolder> {
    List<Level> levels;
    List<Card> cards;
    int row_index = -1;

    public CardAdapter(List<Card> cards) {
        this.cards = cards;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.word_card_layout, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        /*  // obtener el máx nivel desbloqueado
        SharedPreferences sharedPreferences = context.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE);
        int highestUnlockedLevel = sharedPreferences.getInt("HighestUnlockedLevel", 0);
        */

//      Level level = levels.get(position);
        Card card = cards.get(position);

        // definir de donde se sacará la info de cada card
        holder.iv_image.setImageBitmap(card.getImage());
        holder.tv_word.setText(card.getWord());
        holder.tv_level.setText(Integer.toString(card.getLevel()));


        // definir cómo se ve la card en caso de estar seleccionada y en caso contrario
        if (row_index==position){
            holder.expandableLayout.setVisibility( card.isExpanded() ? View.VISIBLE : View.GONE);
            holder.iv_arrow.setImageResource(card.isExpanded() ? R.drawable.ic_arrow_drop_up : R.drawable.ic_arrow_drop_down);
        } else {
            holder.expandableLayout.setVisibility(View.GONE);
            holder.iv_arrow.setImageResource(R.drawable.ic_arrow_drop_down);
            card.setExpanded(false);
        }

        holder.setLevelClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (row_index==position){

                    holder.expandableLayout.setVisibility( card.isExpanded() ? View.GONE : View.VISIBLE);
                    card.setExpanded(!card.isExpanded());
                    holder.iv_arrow.setImageResource(card.isExpanded() ? R.drawable.ic_arrow_drop_up : R.drawable.ic_arrow_drop_down);

                } else {
                    row_index = position;
                    card.setExpanded(!card.isExpanded());
                    holder.iv_arrow.setImageResource(R.drawable.ic_arrow_drop_down);
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView iv_image;
        public TextView tv_word;
        public TextView tv_level;
        public Button bt_editCard;
        public ImageView iv_arrow;


        public LinearLayout expandableLayout;
        public RecyclerView nestedRecyclerView;

        ItemClickListener itemClickListener;

        public void setCardImage(ImageView cardImage) {
            this.iv_image = cardImage;
        }

        public void setLevelClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_word = itemView.findViewById(R.id.tv_editable_word);
            iv_image = itemView.findViewById(R.id.iv_word_image);
            tv_level = itemView.findViewById(R.id.tv_card_level);

            bt_editCard = itemView.findViewById(R.id.bt_edit_card);
            iv_arrow = itemView.findViewById(R.id.dropdown_arrow);
            expandableLayout = itemView.findViewById(R.id.expandableCardLayout);
            nestedRecyclerView = itemView.findViewById(R.id.edit_levels_recyclerview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition());
        }
    }
}