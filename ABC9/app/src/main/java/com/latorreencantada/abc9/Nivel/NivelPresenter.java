package com.latorreencantada.abc9.Nivel;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.latorreencantada.abc9.Global;
import com.latorreencantada.abc9.R;

import java.util.Locale;

public class NivelPresenter implements NivelMVP.Presenter{


    @Nullable
    //variable que hace referencia a la vista
    private NivelMVP.View view;

    // constructor que configura la dependencia con la vista
    @Override
    public void setView(@Nullable NivelMVP.View view) {
        this.view = view;
    }


    //variable que hace referencia al modelo
    private final NivelMVP.Model model;

    // constructor que configura la dependencia con el modelo
    public NivelPresenter (NivelMVP.Model model){
        this.model = model;
    }



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
    public void NuevaCarta() {

        if (view!=null){
            //borrar textView
            view.setAnswer("");
        }

        //generar tipo de carta aleatoria (0 o 1)
        tipoDeCarta = (int)(Math.random()*2);


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


            String nombreDeImagen = palabras[cartaActual];
            if (nombreDeImagen.equals("MONTAÑA")) {
                nombreDeImagen = "montana";
            }

            //colocar imagen
            if (view != null){
                nombreDeImagen = nombreDeImagen.toLowerCase();
                view.setMainImage(nombreDeImagen);
            }

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
                if (view.getSyllableButtonText(tv_Aleatorio).equals("ABCD")
                        ||view.getSyllableButtonText(tv_Aleatorio).equals("")){

                    view.setSyllableButtonText(silabasCorrectas[cartaActual][silaba_i],tv_Aleatorio);
                }
                else {
                    //volver a hacer lo mismo con la misma silaba correcta
                    silaba_i--;
                }
            }

            /////////////////////////////////////////////////////////////////////
            //      Colocar silabas aleatorias en textViews vacíos:
            /////////////////////////////////////////////////////////////////////

            // Para cada textView...
            for (int t=0 ; t<textViewCount; t++){

                // que esté vacío
                if (view.getSyllableButtonText(t).equals("ABCD")||view.getSyllableButtonText(t).equals("")){

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
                        view.setSyllableButtonText(silabas[silabaAleatoria],t);
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

            view.setAnswer(palabraActual);

            //asignar letra unChar a un textview aleatorio
            int tv_aleatorio = (int)(Math.random()*textViewCount);
            view.setSyllableButtonText(unChar,tv_aleatorio);

            //asignar un char aleatorio a los demas texviews
            for (int i = 0; i < textViewCount; i++){
                if (i!=tv_aleatorio){
                    //generar letra aleatoria
                    String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    char letter = abc.charAt((int)(Math.random()*26));
                    String string_letter = Character.toString(letter);
                    //por el momento completo así nomas:
                    view.setSyllableButtonText(string_letter,i);
                }
            }
        }
    }

    @Override
    public void sylablePressed(View v) {
        String silaba_presionada;
        String nueva_resp;
        String resp_temp = view.getAnswerText();

        int p=0;

        switch (v.getId()) {

            case R.id.bt1:
                p=0;
                break;

            case R.id.bt2:
                p=1;
                break;

            case R.id.bt3:
                p=2;
                break;

            case R.id.bt4:
                p=3;
                break;
        }

        //si el boton aún no había sido presionado
        if (!textViewPresionado[p]){

            //cambiar background del boton (mas oscuro, que parezca desabilitado)
            view.changeTvBgImagePressed(p);

            silaba_presionada = view.getSyllableButtonText(p);

            if (tipoDeCarta==1){
                textViewPresionado[p]=true;
                nueva_resp = resp_temp+silaba_presionada;
                view.setAnswer(nueva_resp);

            } else { // si tipo de carta == 0

                // reestablecer el fondo a los botones que habian quedado "presionados" (fondo oscuro)
                for (int z=0; z<textViewCount;z++) {
                    if (p!=z){
                        view.changeTvBgImageUnpressed(z);
                    }
                }
                view.setAnswer(palabraActual.replaceFirst("_", silaba_presionada));
            }
        }
    }

    @Override
    public void bt_borrar_clicked() {
        if (tipoDeCarta==1){
            view.setAnswer("");
        } else {
            view.setAnswer(palabraActual);
        }

        for (int p=0; p<textViewCount; p++) {
            textViewPresionado[p] = false;
            // cambiar background del boton (al estado inicial)

            view.changeTvBgImageUnpressed(p);
        }
    }


    @Override
    public void bt_music_clicked(MediaPlayer mp, View view) {
        if (Global.musica){
            Global.musica= false;
            posicion = mp.getCurrentPosition();
            mp.pause();
            view.setBackgroundResource(android.R.drawable.ic_lock_silent_mode);

        } else {
            Global.musica=true;
            mp.seekTo(posicion);
            mp.start();
            mp.setLooping(true);
            view.setBackgroundResource(android.R.drawable.ic_lock_silent_mode_off);
        }
    }

    @Override
    public void bt_enviar_clicked() {
        // resetear botones de silabas
        for (int p=0; p<textViewCount; p++) {
            textViewPresionado[p] = false;
            // cambiar background del boton (al estado inicial)

            view.changeTvBgImageUnpressed(p);
        }
        /////////////////////////////////////////////


        if ///////////////////// RESPUESTA CORRECTA /////////////////////////////
        (view.getAnswerText().equals(palabras[cartaActual])) {

            playCorrectAnswerSound();

            score ++;
            //BaseDeDatos();


            view.setAnswer("");

            //vaciar todos los textviews
            for (int i=0;i<4;i++) {
                view.setSyllableButtonText("",i);
            }

            NuevaCarta();

            //convertir el INT score en un STRING
            stringScore = Integer.toString(score);
            view.setScore(stringScore);

        }

        else{ ////////////////// RESPUESTA INCORRECTA ///////////////////////////

            if (tipoDeCarta==1){
                view.setAnswer("");
            } else {
                view.setAnswer(palabraActual);
            }

            playWrongAnswerSound();

            vidas--;

            switch (vidas){
                case 3:
                    view.setThreeStars();
                    break;

                case 2:
                    view.setTwoStars();
                    break;

                case 1:
                    view.setOneStar();
                    break;

                case 0:

                    view.stopMusic();

                    view.goToGameOverScreen();

                    break;
            }
        }
    }

    @Override
    public void startMusic(MediaPlayer mp) {
        if (Global.musica){
            if (!mp.isPlaying()){
                if (posicion!=0){
                    mp.seekTo(posicion);
                    mp.start();
                    mp.setLooping(true);
                }
            }
        }
    }

    @Override
    public void pauseMusic(MediaPlayer mp) {

        if (Global.musica){
            if (mp.isPlaying()){
                posicion = mp.getCurrentPosition();
                mp.pause();
            }
        }
    }


    public void playCorrectAnswerSound () {
        if (Global.sonidos){
            view.playCorrectAnswerSound();
        }
    }

    public void playWrongAnswerSound () {
        if (Global.sonidos){
            view.playWrongAnswerSound();
        }
    }


}
