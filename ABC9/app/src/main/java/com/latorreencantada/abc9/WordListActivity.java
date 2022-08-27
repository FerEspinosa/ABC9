package com.latorreencantada.abc9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.latorreencantada.abc9.EditWords.CardAdapter;
import com.latorreencantada.abc9.EditWords.EditWordActivity;
import com.latorreencantada.abc9.EditWords.LevelAdapter;
import com.latorreencantada.abc9.Models.Card;
import com.latorreencantada.abc9.Models.Level;

import java.util.ArrayList;
import java.util.List;

public class WordListActivity extends AppCompatActivity {

    RecyclerView list_word;
    RecyclerView.LayoutManager layoutManager;
    List<Card> cards;
    RecyclerView level_list;
    List<Level> levels;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        fab = findViewById(R.id.fab_add_word);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (WordListActivity.this, EditWordActivity.class);
                startActivity(intent);
            }
        });

        list_word = findViewById(R.id.rv_word_list);
        layoutManager = new LinearLayoutManager(this);
        list_word.setLayoutManager(layoutManager);

        level_list = findViewById(R.id.edit_levels_recyclerview);

        getData();

    }

    private void getData() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Cursor c = BD.rawQuery
                ("SELECT * FROM cards ORDER BY level ASC", null);


        // construir un arrayList de niveles
        if (c !=null && c.moveToFirst()) {

            levels = new ArrayList<>();
           ArrayList<Card> levelCards;

            c.moveToLast();
            int levelsCount = c.getInt(5);
            c.moveToFirst();

            // recorrer cada nivel

            for (int i =1; i<levelsCount; i++ ){

                levelCards = new ArrayList<>();

                // mientras el nivel de la card actual sea = i
                while (c.getInt(5)==i){

                    // crear la card a la que apunta el cursor
                    Card card = new Card(

                            c.getString(0),     // word
                            c.getString(1),     // syl1
                            c.getString(2),     // syl2
                            c.getString(3),     // syl3
                            c.getString   (4),  // Syl4
                            c.getInt   (5),     // level
                            c.getString(6) ,    // image
                            c.getString(7)      // id
                    );

                    // agregar esa card al array de cards del nivel i

                    levelCards.add(card);

                    c.moveToNext();
                }
                    // crear nivel i
                    Level level = new Level(
                            i,
                            //Global.currentLevel.getLevelName(),
                            "Nivel: "+ i,
                            levelCards
                    );

                    levels.add(level);

            }

            c.close();
            BD.close();

            LevelAdapter adapter = new LevelAdapter(levels);
            list_word.setAdapter(adapter);


            /*
            // mientras el nivel de la card actual sea el nivel Global.currentLevel
            while (c.getInt(5)==Global.currentLevel.getLevelNum()){

                // crear la card a la que apunta el cursor
                Card card = new Card(
                        c.getString(0), // word
                        c.getString(1), // syl1
                        c.getString(2), // syl2
                        c.getString(3), // syl3
                        c.getString(4), // syl4
                        c.getInt(5),  // level
                        c.getString(6)  // image
                );

                // agregar esa card al array de niveles
                levelCards.add(card);

                c.moveToNext();
            }
            */

        }



        /*

        // construir un ArrayList de todas las cards
        if (c !=null && c.moveToFirst()) {

            cards = new ArrayList<>();
            c.moveToFirst();

            while (!c.isAfterLast()) {
                Card card = new Card(
                        c.getString(0), // word
                        c.getString(1), // syl1
                        c.getString(2), // syl2
                        c.getString(3), // syl3
                        c.getString(4), // syl4
                        c.getInt(5),  // level
                        c.getString(6)  // image
                );

                cards.add(card);
                c.moveToNext();
            }
        } else {
            Toast.makeText(this, "Error al leer base de datos", Toast.LENGTH_SHORT).show();
        }

        BD.close();

        CardAdapter adapter = new CardAdapter(cards);
        list_word.setAdapter(adapter);
        */

    }


}