package com.latorreencantada.abc9.Nivel;

import dagger.Module;
import dagger.Provides;

@Module
public class NivelModule {

    @Provides
    public NivelMVP.Presenter provideNivelPresenter (NivelMVP.Model model){
        return new NivelPresenter(model);
    }

    @Provides
    public NivelMVP.Model provideNivelModel (NivelRepository repository) {
        return new NivelModel(repository);
    }

    @Provides
    public NivelRepository provideLoginRepository(){
        return new MemoryRepository();
        // cambiar aqui si queremos que el repo sea en memoria, en una BdD, un cloud...
    }
}
