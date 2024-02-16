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

public class StateLevelMenu extends BasicGameState {

    Menu levelsMenu;
    Font font;
    Image background;

    Button level1Button;
    Button level2Button;
    Button level3Button;
    Button level4Button;
    Button level5Button;
    Button backButton;

    Input input;

    //Variable STATIC FINAL
    static final String font_cocosharp_path = "font/cocosharp/Coco-Sharp-Heavy-trial.ttf";
    static final String background_path = "images/background.png";

    @Override
    public int getID() {
        return 1;
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

        background = new Image(background_path);
        levelsMenu = new Menu("CONNECT  ME", font, 50f, background);
        level1Button = new Button((gameContainer.getWidth()/2)-200, (gameContainer.getHeight()/2)-200, 400, 75, 40, "Niveau 1", null, 40);
        level2Button = new Button((gameContainer.getWidth()/2)-200, (gameContainer.getHeight()/2)-100, 400, 75, 40, "Niveau 2", null, 40);
        level3Button = new Button((gameContainer.getWidth()/2)-200, (gameContainer.getHeight()/2), 400, 75, 40, "Niveau 3", null, 40);
        level4Button = new Button((gameContainer.getWidth()/2)-200, (gameContainer.getHeight()/2)+100, 400, 75, 40, "Niveau 4", null, 40);
        level5Button = new Button((gameContainer.getWidth()/2)-200, (gameContainer.getHeight()/2)+200, 400, 75, 40, "Niveau 5", null, 40);
        backButton = new Button((gameContainer.getWidth()/2)-200, (gameContainer.getHeight())-100, 400, 75, 40, "Retour", null, 40);

        levelsMenu.addButton(level1Button);
        levelsMenu.addButton(level2Button);
        levelsMenu.addButton(level3Button);
        levelsMenu.addButton(level4Button);
        levelsMenu.addButton(level5Button);
        levelsMenu.addButton(backButton);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        levelsMenu.drawMenu(gameContainer,graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        input = gameContainer.getInput();
        levelsMenu.setButtonsHovered(input.getMouseX(), input.getMouseY());

        if(this.level1Button.isClick(input)){
            stateBasedGame.enterState(2, new FadeOutTransition(), new FadeInTransition());
        }

        if(this.level2Button.isClick(input)){
            stateBasedGame.enterState(3, new FadeOutTransition(), new FadeInTransition());
        }

        if(this.level3Button.isClick(input)){
            stateBasedGame.enterState(4, new FadeOutTransition(), new FadeInTransition());
        }

        if(this.level4Button.isClick(input)){
            stateBasedGame.enterState(5, new FadeOutTransition(), new FadeInTransition());
        }

        if(this.level5Button.isClick(input)){
            stateBasedGame.enterState(6, new FadeOutTransition(), new FadeInTransition());
        }

        if(this.backButton.isClick(input)){
            stateBasedGame.enterState(0, new FadeOutTransition(), new FadeInTransition());
        }
    }
}
