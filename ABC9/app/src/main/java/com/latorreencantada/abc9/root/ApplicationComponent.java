package com.latorreencantada.abc9.root;

import com.latorreencantada.abc9.Nivel.NivelActivity;
import com.latorreencantada.abc9.Nivel.NivelModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NivelModule.class})
public interface ApplicationComponent {
    void inject (NivelActivity target);
}
