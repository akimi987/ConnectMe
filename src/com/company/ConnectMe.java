package com.company;

import com.company.gameState.StateLevel;
import com.company.gameState.StateLevelMenu;
import com.company.gameState.StateMainMenu;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class ConnectMe extends StateBasedGame {

    String path_sound = "sound/game_music.ogg";
    Sound sound_game;

    public ConnectMe(String title){
        super(title);
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        sound_game = new Sound(path_sound);
        sound_game.loop();

        this.addState(new StateMainMenu());
        this.addState(new StateLevelMenu());
        this.addState(new StateLevel(5,5));
        this.addState(new StateLevel(5,5));
        this.addState(new StateLevel(5,5));
        this.addState(new StateLevel(8,8));
        this.addState(new StateLevel(8,8));
    }

}
