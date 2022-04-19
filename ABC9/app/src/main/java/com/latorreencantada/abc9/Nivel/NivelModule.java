package com.latorreencantada.abc9.Nivel;

import dagger.Module;
import dagger.Provides;

@Module
public class NivelModule {

    @Provides
    public NivelActivityMVP.Presenter provideNivelPresenter (NivelActivityMVP.Model model){
        return new NivelActivityPresenter(model);
    }

    @Provides
    public NivelActivityMVP.Model provideNivelModel (MemoryInterface repository) {
        return new NivelActivityModel(repository);
    }

    @Provides
    public MemoryInterface provideNivelRepository(){
        return new MemoryRepository();
        // cambiar aqui si queremos que el repo sea en memoria, en una BdD, un cloud...
    }
}
