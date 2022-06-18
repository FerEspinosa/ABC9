package com.latorreencantada.abc9.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

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

import com.latorreencantada.abc9.Nivel.NivelActivity;
import com.latorreencantada.abc9.R;
import com.latorreencantada.abc9.root.App;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity implements HomeMVP.View {

    @Inject
    HomeMVP.Presenter presenter;

    private static final String FIRST_RUN = "firstRun";
    public static final String SHARED_PREFS = "SharedPrefs";
    public static final String CAPSLOCK = "capslock";
    public static final String MUSIC = "music";
    public static final String SOUND = "sound";
    LinearLayout optionMenu;

    private EditText et_nombre;
    //private MediaPlayer mp;
    private ImageView fondo;
    private TextView tv_caps_mode;

    Button bt_jugar, bt_opciones, bt_mayusc, bt_minusc, bt_mayusminus_in_order, bt_mayuminusc_random;
    SwitchCompat sw_sonido, sw_musica;

    int posicion=0;
    boolean optionsMenuShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ((App) getApplication()).getComponent().injectHome(this);

        presenter.setView(this);
        configView();

        fondo = findViewById(R.id.id_fondo);

        /*
        // gestionar BdD
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Cursor consulta = BD.rawQuery(
                "select * from puntaje where score = (select max (score) from puntaje)", null);

        if (consulta.moveToFirst()){
            String temp_nom = consulta.getString(0);
            String temp_score = consulta.getString(1);
            String txt_bestScoreIs="Record: "+ temp_score + " de " + temp_nom;
            tv_bestScore.setText(txt_bestScoreIs);

            consulta.close();
            BD.close();

        } else {
            BD.close();
        }

         */

    }

    private void configView() {

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
            presenter.btJugarPressed();
        });

        bt_opciones = findViewById(R.id.bt_opciones);
        bt_opciones.setOnClickListener(view -> {
            presenter.btOptionsPressed();
        });

        sw_musica = findViewById(R.id.sw_musica_home);
        sw_musica.setChecked(presenter.musicOn());
        sw_musica.setOnCheckedChangeListener((compoundButton, b) -> {
            presenter.musicSwitched(b);
        });

        sw_sonido = findViewById(R.id.sw_sonido_home);
        sw_sonido.setChecked(presenter.soundOn());
        sw_sonido.setOnCheckedChangeListener((compoundButton, b) -> {
            presenter.soundSwitched(b);
        });

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        tv_caps_mode = findViewById(R.id.txt_modo_mayuminu);

        presenter.SetInitialCapsMode();

        bt_minusc.setOnClickListener(view -> {
            presenter.btMinuscPressed();
        });

        bt_mayusc.setOnClickListener(view -> {
            presenter.btMayuscPressed();
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    public void setMayuscOn() {
        bt_mayusc.setBackgroundResource(R.color.light_grey);
        bt_minusc.setBackgroundResource(R.color.button_red);
        bt_mayusminus_in_order.setBackgroundResource(R.color.button_red);
        bt_mayuminusc_random.setBackgroundResource(R.color.button_red);
        tv_caps_mode.setText(R.string.mode_capslock_on);
    }


    @Override
    public void goToLevel() {
        Intent intent = new Intent(HomeActivity.this, NivelActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setOptionMenuVisible() {
        optionMenu.setVisibility(View.VISIBLE);
        bt_opciones.setBackgroundResource(R.color.button_red);
    }

    @Override
    public void setOptionMenuInvisible() {
        optionMenu.setVisibility(View.INVISIBLE);
        bt_opciones.setBackgroundResource(R.color.light_grey);
    }



    @Override
    public Context getContext() {
        return this;
    }




    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);

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