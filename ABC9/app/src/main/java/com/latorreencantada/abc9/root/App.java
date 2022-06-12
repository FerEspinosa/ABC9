package com.latorreencantada.abc9.root;

import android.app.Application;

import com.latorreencantada.abc9.GameOver.GameOverModule;
import com.latorreencantada.abc9.Home.HomeModule;
import com.latorreencantada.abc9.Nivel.NivelModule;


public class App extends Application {
    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .nivelModule (new NivelModule())
                .gameOverModule (new GameOverModule())
                .homeModule(new HomeModule())
                .build();
    }

    public ApplicationComponent getComponent(){
        return component;
    }
}
