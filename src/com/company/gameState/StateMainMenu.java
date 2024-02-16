package com.company.gameState;


import com.company.menu.Button;
import com.company.menu.Menu;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.awt.*;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StateMainMenu extends BasicGameState {

    Menu mainMenu;
    Font font;
    Image background;
    Button levelsButton;
    Button quitButton;
    Input input;

    //Variable STATIC FINAL
    static final String font_cocosharp_path = "font/cocosharp/Coco-Sharp-Heavy-trial.ttf";
    static final String background_path = "images/background.png";

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        //Chargement de la police
        File file_font_cocosharp = new File(font_cocosharp_path);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file_font_cocosharp);
            font = Font.createFont(Font.TRUETYPE_FONT, fileInputStream);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        //Menu Principal
        background = new Image(background_path);
        mainMenu = new Menu("CONNECT  ME", font, 50f, background);

        levelsButton = new Button((gameContainer.getWidth()/2)-200, (gameContainer.getHeight()/2)-50, 400, 100, 40, "Niveaux", font, 40f);
        quitButton = new Button((gameContainer.getWidth()/2)-200, (gameContainer.getHeight()/2)+100, 400, 100, 40, "Quitter", font, 40f);

        mainMenu.addButton(levelsButton);
        mainMenu.addButton(quitButton);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        mainMenu.drawMenu(gameContainer, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        input = gameContainer.getInput();
        mainMenu.setButtonsHovered(input.getMouseX(), input.getMouseY());

        if(this.levelsButton.isClick(input)){
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }

        if(this.quitButton.isClick(input)){
            System.exit(0);
        }
    }
}
