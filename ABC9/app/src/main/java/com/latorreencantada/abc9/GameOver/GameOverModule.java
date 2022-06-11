package com.latorreencantada.abc9.GameOver;

import dagger.Module;
import dagger.Provides;

@Module
public class GameOverModule {

    @Provides
    public GameOverMVP.Presenter provideGameOverPresenter (GameOverMVP.Model model){
       return new GameOverPresenter(model);
    }

    @Provides
    public GameOverMVP.Model provideGameOverModel (MemoryInterface repository) {
        return new GameOverModel(repository);
    }

    @Provides
    public MemoryInterface provideGameOverRepository(){
        return new MemoryRepository();
        // cambiar aqui si queremos que el repo sea en memoria, en una BdD, un cloud...
    }
}
