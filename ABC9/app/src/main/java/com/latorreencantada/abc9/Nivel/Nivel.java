package com.latorreencantada.abc9.Nivel;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.latorreencantada.abc9.Global;
import com.latorreencantada.abc9.Pantalla_Game_Over;
import com.latorreencantada.abc9.R;
import com.latorreencantada.abc9.root.App;

import javax.inject.Inject;

public class Nivel extends AppCompatActivity implements NivelMVP.View{

    @Inject
    NivelMVP.Presenter presenter;

    private MediaPlayer wonderful;
    private MediaPlayer wrong;
    private MediaPlayer mp;

    private TextView tv_score;
    private TextView tv_respuesta;
    private TextView tv_nombre;

    private TextView[] textView = new TextView[4];

    private Button bt_borrar, bt_enviar, bt_musica;

    private ImageView iv_estrellas;
    private ImageView iv;

    //private ImageView fondo;

    String jugador;
    String stringScore;
    String palabraActual;

    int textViewCount = 4 ;
    int tv_Aleatorio;
    int cartaActual;
    int score;
    int vidas = 3 ;
    int tipoDeCarta;
    int ultimaCarta=99999;
    int posicion = 0;

    boolean sonIguales = false;
    boolean[] textViewPresionado = {false, false, false,false};

    String [] palabras = {"DINOSAURIO","BALLENA", "CONEJO", "ELEFANTE","JIRAFA","LEON", "LUNA", "TIGRE", "BIANCA", "BICICLETA",
            "CAMELLO" , "CARAMELO" , "CEBRA" , "FRUTILLA" , "FUEGO" , "GATO" , "JUGO", "LECHE" , "MANZANA" , "NARANJA" , "NEMO",
            "PERA" , "PERRO" , "PERROS" , "PEZ", "QUESO" , "SANDIA" , "TREN", "MONTAÑA" , "NUBE" ,  "RASCACIELO" , "SEMAFORO" ,
            "CAMION", "HELADO", "CORAZON",
            "GOTA", "AGUA", "GENIO", "MAGIA","GELATINA"
    };

    String [][] silabasCorrectas= { {"DI","NO","SAU","RIO"}, {"BA","LLE", "NA"},{"CO","NE","JO"},{"E","LE","FAN","TE"},{"JI","RA","FA"},
            {"LE","ON"},{"LU","NA"},{"TI","GRE"}, {"BI", "AN", "CA"} , {"BI", "CI" , "CLE", "TA"} , {"CA", "ME" , "LLO"} ,
            {"CA" , "RA" , "ME" , "LO"} , {"CE", "BRA"}, {"FRU", "TI", "LLA"} , {"FUE","GO"} , {"GA","TO"}, {"JU","GO"},
            {"LE","CHE"}, {"MAN","ZA", "NA"}, {"NA","RAN","JA"}, {"NE", "MO"}, {"P","E", "R", "A"} , {"PE", "RRO"} ,
            {"PE", "RRO", "S"} , {"P", "E","Z"}, {"QUE", "SO"},{"SAN", "DIA"}, {"T","R","E","N"}, {"MON", "TA", "ÑA"},
            {"NU", "BE"}, {"RAS","CA","CIE","LO"},{"SE","MA","FO","RO"}, {"CA", "MI", "ON"}, {"HE","LA","DO"} , {"CO","RA","ZON"},
            {"GO","TA"}, {"A", "GU", "A"},{"GE","NI","O"}, {"MA", "G", "I","A"}, {"GE","LA","TI","NA"}
    };

    String[] silabas = {"PA","MA","CA","SA","PO","TO","CO","A","LO","BUE","LA","DE","DI","DO","DU","PI","ZA","TIO","TIS", "NO"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel2);

        ((App) getApplication()).getComponent().inject(this);

        configView();
       NuevaCarta();
    }

    private void configView() {

        //fuente supersonic
        String fuente1 = "fuentes/supersonic.ttf";
        Typeface supersonic = Typeface.createFromAsset(getAssets(), fuente1);

        // musica y sonidos
        wonderful = MediaPlayer.create(this, R.raw.wonderful);
        wrong = MediaPlayer.create(this, R.raw.bad);
        mp = MediaPlayer.create(this, R.raw.goats);
        if (Global.musica){
            mp.start();
            mp.setLooping(true);
        }

        // elementos gráficos
        iv_estrellas = findViewById(R.id.iv_estrellas);
        iv = findViewById(R.id.mainImage);
        tv_score = findViewById(R.id.tv_puntaje);
        tv_nombre = findViewById(R.id.tv_nombre);
        tv_respuesta = findViewById(R.id.tv_respuesta_2);
        bt_musica = findViewById(R.id.id_switch_musica);
        bt_borrar = findViewById(R.id.bt_borrar);
        bt_enviar = findViewById(R.id.bt_enviar);

        // Declarar Array de TextViews:
        for (int i=0; i<textViewCount; i++){
            textView[i]= new TextView(this);
        }

        textView[0] = findViewById(R.id.bt1);
        textView[1] = findViewById(R.id.bt2);
        textView[2] = findViewById(R.id.bt3);
        textView[3] = findViewById(R.id.bt4);

        jugador = getIntent().getStringExtra("nombre");

        tv_nombre.setText(jugador);
        tv_score.setText("0");
        tv_respuesta.setText("");


        // botones con click listeners:

        bt_musica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.bt_music_clicked(view);
            }
        });

        bt_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.bt_enviar_clicked();
            }
        });

        bt_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.bt_borrar_clicked();
            }
        });

    }

    public void buttonPress(View v) {

        presenter.sylablePressed(v);
    }

    @Override
    protected void onPause() { super.onPause();
        posicion = mp.getCurrentPosition();
        if (Global.musica){
            mp.pause();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onResume() {
        super.onResume();
/*
        if (Global.musica){
            mp.seekTo(posicion);
            mp.start();
            mp.setLooping(true);
        }

        if (Global.BackgroundImage=="rojo"){
            fondo.setImageResource(R.drawable.fondo);
        } else {
            fondo.setImageResource(R.drawable.fondo_azul);
        }
*/

        View decorView = getWindow().getDecorView();
        int uiOptions = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        }

        decorView.setSystemUiVisibility(uiOptions);

    }

    @Override
    public void onBackPressed() {
    }


    //Mudar los siguientes métodos al presenter

    private void NuevaCarta(){
        //borrar textView
        tv_respuesta.setText("");

        //generar tipo de carta aleatoria (0 o 1)
        //tipoDeCarta = (int)(Math.random()*2);
        tipoDeCarta = 1;


        /*  SELECCIONAR UNA CARTA ALEATORIA:
        *
        *   En este caso estoy tomando la cantidad de palabras contenidas en el array "palabras",
        *   declarado junto a las variables globales.
        *   Próximamente, esta info se obtendrá de base de datos. Por lo que aquí comenzaría
        *   la interacción de la vista, primero con el "presenter", y luego con el "model",
        *   encargado de manejar los datos.
        *   Esto facilitará futuras migraciones de base de datos y testing.
        *
        *///                             V V V V
        cartaActual= (int)(Math.random()*palabras.length);
        //                                A A A

        // CHEQUEAR QUE LA CARTA ALEATORIA NO SEA IGUAL A LA ANTERIOR
        if (cartaActual!=ultimaCarta) {

            //colocar imagen
            String nombreDeImagen = palabras[cartaActual];
            if (nombreDeImagen.equals("MONTAÑA")) {
                nombreDeImagen = "montana";
            }
            int id = getResources().getIdentifier(nombreDeImagen.toLowerCase(), "drawable", getPackageName());
            iv.setImageResource(id);

            ultimaCarta = cartaActual;
        }

        if (tipoDeCarta ==1){ //                    TIPO DE CARTA 1: Escribir la palabra con sílabas


            ///////////////////////////////
            // Colocar Silabas correctas //
            ///////////////////////////////

            //para cada silaba correcta
            for (int silaba_i=0; silaba_i < silabasCorrectas[cartaActual].length; silaba_i++){

                //escoger un text view aleatorio
                tv_Aleatorio = (int)(Math.random()*textViewCount);

                //si ese textview aleatorio se encuentra vacío:
                if (textView[tv_Aleatorio].getText().toString().equals("ABCD")
                        ||textView[tv_Aleatorio].getText().toString().equals("")){

                    //colocar la silaba correcta
                    textView[tv_Aleatorio].setText(silabasCorrectas[cartaActual][silaba_i]);
                }
                else {
                    //volver a hacer lo mismo con la misma silaba correcta
                    silaba_i--;
                }
            }

            /////////////////////////////////////////////////////////////////////
            //Colocar silabas aleatorias en textViews vacíos:
            /////////////////////////////////////////////////////////////////////

            // Para cada textView...
            for (int t=0 ; t<textViewCount; t++){

                // que esté vacío
                if (textView[t].getText().toString().equals("ABCD")||textView[t].getText().toString().equals("")){

                    // calcular una sílaba aleatoria
                    int silabaAleatoria = (int)(Math.random() * silabas.length);

                    sonIguales  = false;
                    //para cada respuesta correcta:
                    for (int z=0; z<silabasCorrectas[cartaActual].length; z++){

                        //comparar con silaba aleatoria
                        if (silabasCorrectas[cartaActual][z].equals(silabas[silabaAleatoria])){
                            sonIguales=true;
                            break;
                        }
                    }

                    if (!sonIguales){
                        textView[t].setText(silabas[silabaAleatoria]);
                    } else {
                        t--;
                        sonIguales=false;
                    }
                }
                // si el textView no se encuentra vacío, continuar con el próximo
            }



        } else { // TIPO DE CARTA 0: Completar la letra que falta.

            //generar numero aleatorio menor que longitud de palabra actual
            int num_aleat = (int) (Math.random()*palabras[cartaActual].length());
            palabraActual = palabras[cartaActual];
            String unChar = String.valueOf(palabraActual.charAt(num_aleat));

            palabraActual = palabraActual.replaceFirst(unChar, "_");

            tv_respuesta.setText(palabraActual);

            //asignar letra unChar a un textview aleatorio
            int tv_aleatorio = (int)(Math.random()*textViewCount);
            textView[tv_aleatorio].setText(unChar);

            //asignar un char aleatorio a los demas texviews
            for (int i = 0; i < textViewCount; i++){
                if (i!=tv_aleatorio){
                    //generar letra aleatoria
                    String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    char letter = abc.charAt((int)(Math.random()*26));
                    String string_letter = Character.toString(letter);
                    //por el momento completo así nomas:
                    textView[i].setText(string_letter);
                }
            }
        }
    }
}