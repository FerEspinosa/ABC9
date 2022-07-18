package com.latorreencantada.abc9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.latorreencantada.abc9.Models.Level;
import com.latorreencantada.abc9.Nivel.NivelActivity;
import com.latorreencantada.abc9.Nivel.NivelActivityPresenter;

import java.util.ArrayList;
import java.util.List;

public class LevelMapActivity extends AppCompatActivity {

    RecyclerView list_item;
    RecyclerView.LayoutManager layoutManager;
    List<Level> levels = new ArrayList<>();

    FloatingActionButton playFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_map);

        config_view();
    }

    private void config_view() {
        list_item = (RecyclerView) findViewById(R.id.levels_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        list_item.setHasFixedSize(true);
        list_item.setLayoutManager(layoutManager);

        playFab = findViewById(R.id.play_fab);
        playFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LevelMapActivity.this, NivelActivity.class);
                startActivity(intent);
            }
        });
        
        getData();
    }

    private void getData() {

        // generar los niveles. por ahora, los saco del array de default.
        // Luego, cuando el usuario pueda crear palabras y niveles, se sacaran los niveles de la base de datos
        int levelsQuantity = Global.defaultLevels.length;

        for (int i =1 ; i<=levelsQuantity; i++){
            Level level = new Level ();
            level.setLevel_num(String.valueOf(i));
            levels.add(level);
        }

        LevelAdapter adapter = new LevelAdapter(levels, this);
        list_item.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
            View.SYSTEM_UI_FLAG_FULLSCREEN|
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onBackPressed() {
    }
}