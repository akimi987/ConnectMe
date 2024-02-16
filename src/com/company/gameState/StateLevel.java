package com.company.gameState;

import com.company.game.Square;
import com.company.menu.Level;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.awt.Font;
import java.util.ArrayList;

public class StateLevel extends BasicGameState {

    int id;
    int line_gameboard;
    int column_gameboard;
    Level level;
    Input input;

    private static int nbLevel = 1;

    //Variable STATIC FINAL
    static final String font_cocosharp_path = "font/cocosharp/Coco-Sharp-Heavy-trial.ttf";
    static final String background_path = "images/background.png";


    public StateLevel(int line_gameboard, int column_gameboard) {
        this.id = nbLevel++;
        this.line_gameboard = line_gameboard;
        this.column_gameboard = column_gameboard;
    }

    @Override
    public int getID() {
        return this.id + 1;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

        level = new Level("Niveau " + id,
                null,
                50f,
                new Image(background_path),
                this.line_gameboard,
                this.column_gameboard,
                gameContainer);

        switch (this.id){
            case 1 -> level.initialize(this.getSquaresLevel1());
            case 2 -> level.initialize(this.getSquaresLevel2());
            case 3 -> level.initialize(this.getSquaresLevel3());
            case 4 -> level.initialize(this.getSquaresLevel4());
            case 5 -> level.initialize(this.getSquaresLevel5());
        }

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        level.drawLevel(gameContainer, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        input = gameContainer.getInput();

        if(!(this.level.isFinished())){
            level.play(input);
        }

        if(this.level.getBackButton().isClick(input)){
            stateBasedGame.enterState(1,new FadeOutTransition(), new FadeInTransition());
        }

        if(this.level.getReloadButton().isClick(input)){
            switch (this.id){
                case 1 -> level.initialize(this.getSquaresLevel1());
                case 2 -> level.initialize(this.getSquaresLevel2());
                case 3 -> level.initialize(this.getSquaresLevel3());
                case 4 -> level.initialize(this.getSquaresLevel4());
                case 5 -> level.initialize(this.getSquaresLevel5());
            }
        }
    }

    public ArrayList<Square> getSquaresLevel1() throws SlickException {
        ArrayList<Square> squares = new ArrayList<>();
        squares.add(new Square(2, 2, 0, 4, 1, 0, 3));
        squares.add(new Square(2, 3, 1, 3, 0, 0, 0));
        squares.add(new Square(1, 3, 4, 0, 4, 0, 0));
        squares.add(new Square(0, 3, 5, 1, 0, 0, 0));
        return squares;

    }

    public ArrayList<Square> getSquaresLevel2() throws SlickException {
        ArrayList<Square> squares = new ArrayList<>();
        squares.add(new Square(0, 1, 3, 0, 0, 0, 4));
        squares.add(new Square(4, 4, 5, 2, 4, 0, 3));
        squares.add(new Square(2, 3, 1, 0, 0, 2, 0));
        squares.add(new Square(3, 4, 2, 3, 0, 0, 0));
        return squares;

    }

    public ArrayList<Square> getSquaresLevel3() throws SlickException {
        ArrayList<Square> squares = new ArrayList<>();
        squares.add(new Square(0, 0, 4, 0, 0, 2, 3));
        squares.add(new Square(0, 3, 3, 1, 4, 0, 0));
        squares.add(new Square(1, 3, 1, 0, 1, 0, 0));
        squares.add(new Square(2, 2, 5, 0, 4, 0, 3));
        squares.add(new Square(3, 1, 0, 0, 0, 0, 2));
        return squares;

    }

    public ArrayList<Square> getSquaresLevel4() throws SlickException {
        ArrayList<Square> squares = new ArrayList<>();
        squares.add(new Square(0, 2, 1, 3, 0, 0, 0));
        squares.add(new Square(0, 5, 3, 1, 3, 4, 0));
        squares.add(new Square(1, 1, 5, 0, 2, 0, 0));
        squares.add(new Square(2, 3, 0, 3, 4, 1, 2));
        squares.add(new Square(2, 7, 2, 0, 4, 2, 0));
        squares.add(new Square(3, 1, 2, 4, 2, 0, 1));
        squares.add(new Square(3, 5, 0, 0, 1, 2, 0));
        squares.add(new Square(4, 4, 1, 0, 4, 4, 0));
        squares.add(new Square(5, 0, 2, 3, 0, 0, 0));
        squares.add(new Square(5, 2, 3, 4, 0, 0, 1));
        squares.add(new Square(5, 6, 4, 3, 4, 0, 1));
        squares.add(new Square(6, 4, 5, 2, 1, 4, 4));
        squares.add(new Square(7, 0, 5, 3, 0, 0, 1));
        return squares;

    }

    public ArrayList<Square> getSquaresLevel5() throws SlickException {
        ArrayList<Square> squares = new ArrayList<>();
        squares.add(new Square(0, 6, 4, 0, 4, 3, 0));
        squares.add(new Square(1, 1, 4, 4, 3, 4, 0));
        squares.add(new Square(1, 3, 0, 0, 0, 2, 3));
        squares.add(new Square(1, 4, 5, 4, 0, 2, 0));
        squares.add(new Square(2, 1, 5, 2, 4, 2, 3));
        squares.add(new Square(2, 6, 2, 0, 3, 3, 4));
        squares.add(new Square(3, 2, 1, 2, 0, 1, 0));
        squares.add(new Square(3, 4, 0, 3, 4, 2, 4));
        squares.add(new Square(4, 0, 4, 0, 0, 0, 3));
        squares.add(new Square(4, 4, 4, 2, 0, 2, 0));
        squares.add(new Square(4, 5, 4, 4, 4, 3, 3));
        squares.add(new Square(5, 0, 4, 4, 3, 4, 0));
        squares.add(new Square(5, 1, 5, 2, 0, 0, 3));
        squares.add(new Square(5, 3, 1, 0, 1, 1, 0));
        squares.add(new Square(5, 3, 5, 1, 2, 0, 3));
        squares.add(new Square(5, 5, 4, 3, 3, 1, 2));
        squares.add(new Square(6, 1, 3, 0, 4, 0, 2));
        squares.add(new Square(6, 4, 0, 3, 0, 0, 0));
        squares.add(new Square(6, 5, 4, 2, 3, 1, 0));
        return squares;

    }



}
