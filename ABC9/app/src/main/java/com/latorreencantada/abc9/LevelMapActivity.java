package com.latorreencantada.abc9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.latorreencantada.abc9.Models.Level;

import java.util.ArrayList;
import java.util.List;

public class LevelMapActivity extends AppCompatActivity {

    RecyclerView list_item;
    RecyclerView.LayoutManager layoutManager;
    List<Level> levels = new ArrayList<>();

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
        
        getData();
    }

    private void getData() {

        // generar los niveles.
        int levelsQuantity = Global.defaultLevels.length;

        for (int i =1 ; i<=levelsQuantity; i++){
            Level level = new Level ();
            level.setLevel_num(String.valueOf(i));
            levels.add(level);
        }

        LevelAdapter adapter = new LevelAdapter(levels, this);
        list_item.setAdapter(adapter);
    }
}