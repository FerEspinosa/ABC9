package com.latorreencantada.abc9.Home;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    @Provides
    public HomeMVP.Presenter provideHomePresenter (HomeMVP.Model model){
        return new HomePresenter(model);
    }

    @Provides
    public HomeMVP.Model provideHomeModel (MemoryInterface repository) {
        return new HomeModel(repository);
    }

    @Provides
    public MemoryInterface provideNivelRepository(){
        return new MemoryRepository();
        // cambiar aqui si queremos que el repo sea en memoria, en una BdD, un cloud...
    }

}
