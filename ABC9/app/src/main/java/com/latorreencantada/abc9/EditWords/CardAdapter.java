package com.latorreencantada.abc9.EditWords;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import com.latorreencantada.abc9.AdminSQLiteOpenHelper;
import com.latorreencantada.abc9.root.ItemClickListener;
import com.latorreencantada.abc9.Models.Card;
import com.latorreencantada.abc9.Models.Level;
import com.latorreencantada.abc9.R;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CustomViewHolder> {
    List<Level> levels;
    List<Card> cards;
    int row_index = -1;
    SQLiteDatabase db;

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
        holder.tv_word.setText(card.getWord().replaceAll("ny","ñ"));
        holder.et_level.setText(String.valueOf(card.getLevel()));

        holder.et_syl1.setText(card.getSyl(1));
        holder.et_syl2.setText(card.getSyl(2));
        holder.et_syl3.setText(card.getSyl(3));
        holder.et_syl4.setText(card.getSyl(4));

        holder.iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: agregar función "abrir galería o cámara"
            }
        });

        holder.bt_editCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Todo: agregar función editar tarjeta

                    Bitmap image = Card.resizeBitmap(((BitmapDrawable)holder.iv_image.getDrawable()).getBitmap());


                    Card card = new Card(

                            holder.et_syl1.getText().toString()+                  // word
                                    holder.et_syl2.getText().toString()+
                                    holder.et_syl3.getText().toString()+
                                    holder.et_syl4.getText().toString(),
                            holder.et_syl1.getText().toString(),                        // syl 1
                            holder.et_syl2.getText().toString(),                        // syl 2
                            holder.et_syl3.getText().toString(),                        // syl 3
                            holder.et_syl4.getText().toString(),                        // syl 4
                            Integer.parseInt(holder.et_level.getText().toString()),     // level
                            Card.bitmapToString(image),                                 // image
                            cards.get(holder.getAdapterPosition()).getId()              // id

                    );

                    // update the card here
                    int update = new AdminSQLiteOpenHelper(holder.et_level.getContext()).updateCard(card);


               // todo: revisar si lo anterior funciona !!! probablemente no porque esto no edita una card sino que la crea
            }
        });

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
        public EditText et_level;
        public Button bt_editCard;
        public ImageView iv_arrow;
        public EditText et_syl1;
        public EditText et_syl2;
        public EditText et_syl3;
        public EditText et_syl4;


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
            et_level = itemView.findViewById(R.id.tv_card_level);

            bt_editCard = itemView.findViewById(R.id.bt_edit_card);
            iv_arrow = itemView.findViewById(R.id.dropdown_arrow);
            expandableLayout = itemView.findViewById(R.id.expandableCardLayout);
            nestedRecyclerView = itemView.findViewById(R.id.edit_levels_recyclerview);
            et_syl1 = itemView.findViewById(R.id.et_syl1);
            et_syl2 = itemView.findViewById(R.id.et_syl2);
            et_syl3 = itemView.findViewById(R.id.et_syl3);
            et_syl4 = itemView.findViewById(R.id.et_syl4);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition());
        }
    }
}