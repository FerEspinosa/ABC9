package com.latorreencantada.abc9.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.latorreencantada.abc9.CustomAdapter;
import com.latorreencantada.abc9.Models.Item;
import com.latorreencantada.abc9.Nivel.NivelActivity;
import com.latorreencantada.abc9.R;
import com.latorreencantada.abc9.root.App;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity implements HomeMVP.View {

    RecyclerView list_item;
    RecyclerView.LayoutManager layoutManager;
    List<Item> items;

    @Inject
    HomeMVP.Presenter presenter;

    LinearLayout optionMenu;

    private EditText et_nombre;
    private ImageView fondo;
    private TextView tv_caps_mode;

    Button bt_jugar, bt_opciones, bt_mayusc, bt_minusc, bt_mayusminus_in_order, bt_mayuminusc_random;
    SwitchCompat sw_sonido, sw_musica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ((App) getApplication()).getComponent().injectHome(this);

        presenter.SetView(this);
        configView();

        fondo = findViewById(R.id.id_fondo);

    }

    private void configView() {

        list_item = (RecyclerView)findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        list_item.setHasFixedSize(true);
        list_item.setLayoutManager(layoutManager);
        items = new ArrayList<>();
        getData();


        String fuente1 = "fuentes/supersonic.ttf";
        Typeface supersonic = Typeface.createFromAsset(getAssets(), fuente1);

        optionMenu = findViewById(R.id.layout_opciones_home);
        optionMenu.setVisibility(View.INVISIBLE);

        bt_minusc = findViewById(R.id.bt_minusc);
        bt_mayusminus_in_order = findViewById(R.id.bt_mayu_minus_in_order);
        bt_mayuminusc_random = findViewById(R.id.bt_mayuminus_random);
        bt_mayusc = findViewById(R.id.bt_mayusc);

        presenter.FirstRun();

        bt_jugar = findViewById(R.id.bt_jugar);
        bt_jugar.setOnClickListener(view -> {
            presenter.BtJugarPressed();
        });

        bt_opciones = findViewById(R.id.bt_opciones);
        bt_opciones.setOnClickListener(view -> {
            presenter.BtOptionsPressed();
        });

        sw_musica = findViewById(R.id.sw_musica_home);
        sw_musica.setChecked(presenter.MusicOn());
        sw_musica.setOnCheckedChangeListener((compoundButton, b) -> {
            presenter.MusicSwitched(b);
        });

        sw_sonido = findViewById(R.id.sw_sonido_home);
        sw_sonido.setChecked(presenter.SoundOn());
        sw_sonido.setOnCheckedChangeListener((compoundButton, b) -> {
            presenter.SoundSwitched(b);
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        tv_caps_mode = findViewById(R.id.txt_modo_mayuminu);

        presenter.SetInitialCapsMode();

        bt_minusc.setOnClickListener(view -> {
            presenter.BtMinuscPressed();
        });

        bt_mayusc.setOnClickListener(view -> {
            presenter.BtMayuscPressed();
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private void getData() {
        for (int i =0; i<10 ; i++){
            Item item = new Item();
            item.setName("Item "+i);
            if (i%2==0){
                item.setChecked(true);
            } else {
                item.setChecked(false);
            }
            items.add(item);
        }

        CustomAdapter adapter = new CustomAdapter(items, this);
        list_item.setAdapter(adapter);

    }

    @Override
    public void MinuscOn() {
        bt_minusc.setBackgroundResource(R.color.light_grey);
        bt_mayusc.setBackgroundResource(R.color.button_red);
        bt_mayusminus_in_order.setBackgroundResource(R.color.button_red);
        bt_mayuminusc_random.setBackgroundResource(R.color.button_red);
        tv_caps_mode.setText(R.string.mode_capslock_off);
    }

    @Override
    public void SetMayuscOn() {
        bt_mayusc.setBackgroundResource(R.color.light_grey);
        bt_minusc.setBackgroundResource(R.color.button_red);
        bt_mayusminus_in_order.setBackgroundResource(R.color.button_red);
        bt_mayuminusc_random.setBackgroundResource(R.color.button_red);
        tv_caps_mode.setText(R.string.mode_capslock_on);
    }


    @Override
    public void GoToLevel() {
        Intent intent = new Intent(HomeActivity.this, NivelActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void SetOptionMenuVisible() {
        optionMenu.setVisibility(View.VISIBLE);
        bt_opciones.setBackgroundResource(R.color.button_red);
    }

    @Override
    public void SetOptionMenuInvisible() {
        optionMenu.setVisibility(View.INVISIBLE);
        bt_opciones.setBackgroundResource(R.color.light_grey);
    }



    @Override
    public Context GetContext() {
        return this;
    }




    @Override
    protected void onResume() {
        super.onResume();
        presenter.SetView(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_FULLSCREEN|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
    }

}