package com.latorreencantada.abc9.root;

import com.latorreencantada.abc9.GameOver.GameOverActivity;
import com.latorreencantada.abc9.GameOver.GameOverModule;
import com.latorreencantada.abc9.Home.HomeActivity;
import com.latorreencantada.abc9.Home.HomeModule;
import com.latorreencantada.abc9.Nivel.NivelActivity;
import com.latorreencantada.abc9.Nivel.NivelModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NivelModule.class, HomeModule.class, GameOverModule.class})
public interface ApplicationComponent {
    void injectNivel (NivelActivity target);
    void injectGameOver (GameOverActivity target);
    void injectHome (HomeActivity target);
}
