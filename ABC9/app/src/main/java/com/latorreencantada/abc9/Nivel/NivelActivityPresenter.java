package com.latorreencantada.abc9.Nivel;

import static android.content.Context.MODE_PRIVATE;
import static com.latorreencantada.abc9.Global.silabas;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.view.View;

import androidx.annotation.Nullable;

import com.latorreencantada.abc9.Card;
import com.latorreencantada.abc9.Global;
import com.latorreencantada.abc9.R;

import java.util.ArrayList;
import java.util.Locale;

public class NivelActivityPresenter implements NivelActivityMVP.Presenter{

    private static final String MUSIC = "music";
    private static final String SOUND = "sound";
    private static final String SHARED_PREFS = "SharedPrefs";

    @Nullable
    //variable que hace referencia a la vista
    private NivelActivityMVP.View view;

    // constructor que configura la dependencia con la vista
    @Override
    public void setView(@Nullable NivelActivityMVP.View view) {
        this.view = view;
    }

    //variable que hace referencia al modelo
    private final NivelActivityMVP.Model model;

    // constructor que configura la dependencia con el modelo
    public NivelActivityPresenter(NivelActivityMVP.Model model){
        this.model = model;
    }

    String stringScore;
    String palabraActual;
    int playerLevel=1;

    int textViewCount = 4 ;
    int tv_Aleatorio;
    int cartaActualInt;
    int score;
    int vidas = 3 ;
    int tipoDeCarta;
    int ultimaCarta=99999;
    int posicion = 0;
    int cardsDrawn=0;
    int cardsToBeDrawn= (Global.defaultLevels.length) * Global.drawsPerLevel;

    // el siguiente booleano debe estar en true por default
    // Yo ahora lo cambio a false para que bauti practique la minuscula hasta que implemente
    // el switch para que el usuario seleccione la opcion que quiera.

    Card cartaActual;

    boolean sonIguales = false;
    boolean[] textViewPresionado = {false, false, false,false};

    ArrayList<Card> levelCardList;

    @Override
    public void NuevaCarta(int playerLevel) {

        cardsDrawn++;

        if (view!=null){
            //vaciar textView principal
            view.setAnswer("");
        }

        clearAllSylButtons();

        //generar tipo de carta aleatoria (0 o 1)
        tipoDeCarta = (int)(Math.random()*2);

        // Obtener palabras del nivel "playerLevel"
        levelCardList = model.getLevelWords(playerLevel, view.getContext());

        //SELECCIONAR UNA CARTA ALEATORIA:
        cartaActualInt = (int)(Math.random()*levelCardList.size());

        // CHEQUEAR QUE LA CARTA ALEATORIA NO SEA IGUAL A LA ANTERIOR
        if (cartaActualInt !=ultimaCarta) {

            cartaActual = levelCardList.get(cartaActualInt);

            String nombreDeImagen = cartaActual.getWord();

            if (nombreDeImagen.equals("MONTAÑA")) {
                nombreDeImagen = "montania";
            }
            if (nombreDeImagen.equals("MOÑO")) {
                nombreDeImagen = "monio";
            }
            if (nombreDeImagen.equals("BAÑO")) {
                nombreDeImagen = "banio";
            }
            if (nombreDeImagen.equals("CABAÑA")) {
                nombreDeImagen = "cabania";
            }
            if (nombreDeImagen.equals("CAÑA")) {
                nombreDeImagen = "cania";
            }

            //colocar imagen
            if (view != null){
                nombreDeImagen = nombreDeImagen.toLowerCase();
                view.setMainImage(nombreDeImagen);
            }

            ultimaCarta = cartaActualInt;
        } // else, si la palabra nueva es igual a la anterior, la imagen no se cambia

        if (tipoDeCarta ==1){ //                                                    TIPO DE CARTA 1: Escribir la palabra con sílabas

            ///////////////////////////////
            // Colocar Silabas correctas //
            ///////////////////////////////

            //para cada silaba correcta
            for (int silabaCorrecta_i = 1; silabaCorrecta_i < 5; silabaCorrecta_i++){

                //escoger un text view aleatorio
                tv_Aleatorio = (int)(Math.random()*textViewCount);

                //si ese textview aleatorio se encuentra vacío:
                if (view.getSyllableButtonText(tv_Aleatorio).equals("ABCD")
                        ||view.getSyllableButtonText(tv_Aleatorio).equals("")){

                    // asignar al textview vacío el texto de la silaba i

                    if (Capslock()&& Global.capsLock){
                        view.setSyllableButtonText(cartaActual.getSyl(silabaCorrecta_i).toUpperCase(),tv_Aleatorio);
                    } else {
                        view.setSyllableButtonText(cartaActual.getSyl(silabaCorrecta_i).toLowerCase(Locale.ROOT),tv_Aleatorio);
                    }


                }
                else {
                    //si el textview aleatorio no está vacío,
                    // volver a generar un textView aleatorio
                    // para colocar la misma silaba correcta:
                    silabaCorrecta_i--;
                }
            }

            /////////////////////////////////////////////////////////////////////
            //      Colocar silabas aleatorias en textViews vacíos:
            /////////////////////////////////////////////////////////////////////

            // Para cada textView...
            for (int textView_t=0 ; textView_t<textViewCount; textView_t++){

                // que esté vacío
                if (view.getSyllableButtonText(textView_t).equals("ABCD")||view.getSyllableButtonText(textView_t).equals("")){

                    // calcular una sílaba aleatoria
                    int intSilabaAleatoria = (int)(Math.random() * silabas.length);
                    String silabaAleatoria = silabas[intSilabaAleatoria];

                    sonIguales  = false;


                    // (de nuevo) para cada textView n
                    for (int tv_n = 0; tv_n<textViewCount; tv_n++){
                        // comparar: silabaAleatoria.toLowerCase(Locale.ROOT) con: view.textView n
                        String aux = view.getSyllableButtonText(tv_n);
                        if (silabaAleatoria.toLowerCase(Locale.ROOT).equals(view.getSyllableButtonText(tv_n).toLowerCase(Locale.ROOT))){
                            sonIguales=true;
                        }
                    }

                    if (!sonIguales){
                        if(Capslock()&& Global.capsLock){
                            view.setSyllableButtonText(silabaAleatoria.toUpperCase(Locale.ROOT),textView_t);
                        } else {
                            view.setSyllableButtonText(silabaAleatoria.toLowerCase(Locale.ROOT),textView_t);
                        }
                    } else {
                        textView_t--;
                        sonIguales=false;
                    }
                }
                // si el textView no se encuentra vacío, continuar con el próximo
            }

        } else { //                                                                 TIPO DE CARTA 0: Completar la letra que falta.


            //generar numero aleatorio menor que longitud de palabra actual
            palabraActual = cartaActual.getWord();
            int num_aleat = (int) (Math.random()*palabraActual.length());

            // extraer de la palabra  la letra que está en la posicion del num_aleat
            String unChar = String.valueOf(palabraActual.charAt(num_aleat));

            // construir la palabra con esa letra por un guión
            palabraActual = palabraActual.replaceFirst(unChar, "_");

            if (Capslock() && Global.capsLock){
                view.setAnswer(palabraActual.toUpperCase(Locale.ROOT));
            } else {
                view.setAnswer(palabraActual.toLowerCase(Locale.ROOT));
            }

            // colocar la letra correcta en un text view aleatorio
            int tv_aleatorio = (int)(Math.random()*textViewCount);

            if (Capslock()&& Global.capsLock){
                view.setSyllableButtonText(unChar.toUpperCase(Locale.ROOT),tv_aleatorio);
            } else {
                view.setSyllableButtonText(unChar.toLowerCase(Locale.ROOT),tv_aleatorio);
            }

            //asignar un char aleatorio a los demas texviews
            for (int i = 0; i < textViewCount; i++){

                //generar letra aleatoria
                String abc = "abcdefghijklmnopqrstuvwxyz";
                char letter = abc.charAt((int)(Math.random()*26));
                String string_letter = Character.toString(letter);

                sonIguales = false;

                //para cada textView
                for (int x= 0; x<textViewCount; x++){
                    // verificar si string_letter es igual al contenido de textview(x)
                    if (string_letter.toLowerCase(Locale.ROOT).equals(view.getSyllableButtonText(x).toLowerCase(Locale.ROOT))){
                        sonIguales=true;
                    }
                }

                // si luego de haber comparado el string_letter con todos los demas textviews,
                // no son iguales en ningun caso
                if (!sonIguales){
                    if (i!=tv_aleatorio){
                        //entonces colocar esa letra en el textView

                        if (Capslock()&& Global.capsLock){
                            view.setSyllableButtonText(string_letter.toUpperCase(),i);
                        } else {
                            view.setSyllableButtonText(string_letter.toLowerCase(),i);
                        }

                    }
                } else {
                    // si la letra aleatoria es igual a cualquiera de los demas textviews,
                    // generar otra letra aleatoria para colocar en el mismo textview
                    i--;
                }
            }
        }

    }

    @Override
    public void sylablePressed(View v) {
        String silaba_presionada;
        String nueva_resp;
        assert view != null;
        String resp_temp = view.getAnswerText();

        view.allowClickOnSend(true);

        int p=0;

        final int int1 = R.id.bt1;
        final int int2 = R.id.bt2;
        final int int3 = R.id.bt3;
        final int int4 = R.id.bt4;

        switch (v.getId()) {

            case int1:
                p=0;
                break;

            case int2:
                p=1;
                break;

            case int3:
                p=2;
                break;

            case int4:
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

                if (Capslock()&& Global.capsLock){
                    view.setAnswer(nueva_resp.toUpperCase(Locale.ROOT));
                } else {
                    view.setAnswer(nueva_resp.toLowerCase(Locale.ROOT));
                }

            } else { // si tipo de carta == 0

                // reestablecer el fondo a los botones que habian quedado "presionados" (fondo oscuro)
                for (int z=0; z<textViewCount;z++) {
                    if (p!=z){
                        view.changeTvBgImageUnpressed(z);
                    }
                }

                if (Capslock()&& Global.capsLock){
                    view.setAnswer(palabraActual.toUpperCase(Locale.ROOT).replaceFirst("_", silaba_presionada));
                } else {
                    view.setAnswer(palabraActual.toLowerCase(Locale.ROOT).replaceFirst("_", silaba_presionada));
                }
            }
        }
    }

    @Override
    public void bt_borrar_clicked() {
        assert view != null;
        view.allowClickOnSend(false);

        if (tipoDeCarta==1){
            view.setAnswer("");
        } else {

            if (Capslock()&& Global.capsLock){
                view.setAnswer(palabraActual.toUpperCase());
            } else {
                view.setAnswer(palabraActual.toLowerCase());
            }
        }

        for (int p=0; p<textViewCount; p++) {
            textViewPresionado[p] = false;
            // cambiar background del boton (al estado inicial)

            view.changeTvBgImageUnpressed(p);
        }
    }


    @Override
    public void bt_options_clicked() {
        //view.goToOptionScreen();
        assert view != null;
        view.showOptions();
    }

    @Override
    public void bt_enviar_clicked() {

        assert view != null;
        view.allowClickOnSend(false);

        // resetear botones de silabas
        for (int p=0; p<textViewCount; p++) {
            textViewPresionado[p] = false;
            // cambiar background del boton (al estado inicial)

            view.changeTvBgImageUnpressed(p);
        }
        /////////////////////////////////////////////


        if ///////////////////// RESPUESTA CORRECTA /////////////////////////////
        (view.getAnswerText().toLowerCase(Locale.ROOT).equals(cartaActual.getWord().toLowerCase(Locale.ROOT))) {

            view.setAnswer("");

            score++;

            //int cardsDrawnNumber = 0;
            if (score%Global.drawsPerLevel==0){
                playerLevel++;
            }

            playCorrectAnswerSound();

            if (cardsDrawn == cardsToBeDrawn){

                view.goToGameOverScreen();

                /*
                if (Capslock() && Global.capsLock){
                    Global.capsLock= false;
                    playerLevel=1;
                    NuevaCarta(playerLevel);
                } else {
                    view.goToGameOverScreen();
                }
                */

            } else {
                NuevaCarta(playerLevel);
            }

            //convertir el INT score en un STRING
            stringScore = Integer.toString(score);
            view.setScore(stringScore);

        }

        else{ ////////////////// RESPUESTA INCORRECTA ///////////////////////////

            playWrongAnswerSound();

            vidas--;

            switch (vidas){
                case 3:
                    view.setThreeStars();
                    break;

                case 2:
                    view.setTwoStars();
                    NuevaCarta(playerLevel);
                    break;

                case 1:
                    view.setOneStar();
                    NuevaCarta(playerLevel);
                    break;

                case 0:
                    view.stopMusic();
                    view.goToGameOverScreen();
                    break;
            }

        }
    }


    private void clearAllSylButtons() {
        //vaciar todos los textviews
        for (int i=0;i<4;i++) {
            assert view != null;
            view.setSyllableButtonText("",i);
        }
    }

    @Override
    public void startMusic(MediaPlayer mp) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        if (sharedPreferences.getBoolean(MUSIC, true)){
            if (!mp.isPlaying()){
                if (posicion!=0){
                    mp.seekTo(posicion);
                }
                mp.start();
                mp.setLooping(true);
            }

        }

    }

    @Override
    public void pauseMusic(MediaPlayer mp) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(MUSIC, true)){
            if (mp.isPlaying()){
                posicion = mp.getCurrentPosition();
                mp.pause();
            }
        }

    }

    public void playCorrectAnswerSound () {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(SOUND, true)){
            assert view != null;
            view.playCorrectAnswerSound();
        }
    }

    public void playWrongAnswerSound () {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(SOUND, true)){
            assert view != null;
            view.playWrongAnswerSound();
        }
    }

    @Override
    public Context getContext() {
        assert view != null;
        return view.getContext();
    }

    private void CapslockOff() {
        String SHARED_PREFS = "SharedPrefs";
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SHARED_PREFS, false);
        editor.apply();
    }


    public boolean Capslock(){
        String SHARED_PREFS = "SharedPrefs";
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        return sharedPreferences.getBoolean("capslock", true);
    }

}
