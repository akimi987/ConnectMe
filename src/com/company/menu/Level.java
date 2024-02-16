package com.company.menu;

import com.company.game.GameBoard;
import com.company.game.Square;

import org.newdawn.slick.*;

import java.awt.Font;
import java.util.ArrayList;

public class Level extends Menu{
    //Attribut
    private GameBoard gameBoard;
    private Button backButton;
    private Button reloadButton;
    private int line_select, column_select;
    private boolean isFinished;

    //Variable STATIC FINAL
    static final double MARGIN_GAMEBOARD = 0.1; //Marge en la bord de la fenetre et le plateau de jeu (%)
    static final double WIDTH_WINDOW_END_LEVEL = 0.5;
    static final double HEIGHT_WINDOW_END_LEVEL = 0.1;

    public Level(String title, Font font, float font_size, Image background, int line_gameBoard, int column_gameBoard, GameContainer gameContainer) throws SlickException {
        super(title, font, font_size, background);
        this.gameBoard = new GameBoard(
                line_gameBoard,
                column_gameBoard,
                (int) (gameContainer.getWidth()*MARGIN_GAMEBOARD),
                (int) (gameContainer.getHeight()*MARGIN_GAMEBOARD),
                (int) (gameContainer.getWidth()-(gameContainer.getWidth()*MARGIN_GAMEBOARD*2)),
                (int) (gameContainer.getHeight()-(gameContainer.getHeight()*MARGIN_GAMEBOARD*2)));

        this.backButton = new Button(
                (int) (gameContainer.getWidth()*MARGIN_GAMEBOARD),
                (int) (gameContainer.getHeight() - (gameContainer.getHeight()*MARGIN_GAMEBOARD) + (gameContainer.getHeight()*MARGIN_GAMEBOARD*0.1)),
                (int) (gameContainer.getWidth()-(gameContainer.getWidth()*MARGIN_GAMEBOARD*3))/2,
                (int) (gameContainer.getHeight()*MARGIN_GAMEBOARD*0.8),
                30,
                "Niveaux",
                font,
                30f);

        this.reloadButton = new Button(
                (int) (gameContainer.getWidth()*MARGIN_GAMEBOARD*2 + ((gameContainer.getWidth()-(gameContainer.getWidth()*MARGIN_GAMEBOARD*3))/2)),
                (int) (gameContainer.getHeight() - (gameContainer.getHeight()*MARGIN_GAMEBOARD) + (gameContainer.getHeight()*MARGIN_GAMEBOARD*0.1)),
                (int) (gameContainer.getWidth()-(gameContainer.getWidth()*MARGIN_GAMEBOARD*3))/2,
                (int) (gameContainer.getHeight()*MARGIN_GAMEBOARD*0.8),
                30,
                "Recommencer",
                font,
                30f);

        this.isFinished = false;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getReloadButton() {
        return reloadButton;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void initialize(ArrayList<Square> squares){
        this.isFinished = false;
        this.gameBoard.clearSquares();
        for (Square square : squares) {
            this.gameBoard.addSquare(square);
        }
        this.gameBoard.setConnected();
    }

    public void play(Input input){
        if((input.getMouseX() >= 0 && input.getMouseX() <= gameBoard.getX() + gameBoard.getWidth()) && (input.getMouseY() >= 0 && input.getMouseY() <= gameBoard.getY() + gameBoard.getHeight())){
            gameBoard.setSelectBox(gameBoard.whichBox(input.getMouseX(), input.getMouseY()));

            //CLICK
            if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
                if(gameBoard.whichBox(input.getMouseX(),input.getMouseY()) != null){
                    line_select = gameBoard.whichBox(input.getMouseX(),input.getMouseY())[0];
                    column_select = gameBoard.whichBox(input.getMouseX(),input.getMouseY())[1];
                    gameBoard.click(line_select,column_select);
                }
            }

            if(this.gameBoard.isConnected()){
                this.isFinished = true;
            }
        }
    }

    public void drawWindowEndLevel(GameContainer gc, Graphics g){
        g.setColor(new Color(170,200,255,200));
        g.fillRoundRect(
                (float) (gc.getWidth()-(gc.getWidth()*WIDTH_WINDOW_END_LEVEL))/2,
                (float) (gc.getHeight()-(gc.getHeight()*HEIGHT_WINDOW_END_LEVEL))/2,
                (float) (gc.getWidth()*WIDTH_WINDOW_END_LEVEL),
                (float) (gc.getHeight()*HEIGHT_WINDOW_END_LEVEL),
                30
        );


        trueTypeFont.drawString(
                (float) (((gc.getWidth()-(gc.getWidth()*WIDTH_WINDOW_END_LEVEL))/2) + ((gc.getWidth()*WIDTH_WINDOW_END_LEVEL-(trueTypeFont.getWidth("Niveau Termine")))/2)),
                (float) (((gc.getHeight()-(gc.getHeight()*HEIGHT_WINDOW_END_LEVEL))/2) + ((gc.getHeight()*HEIGHT_WINDOW_END_LEVEL-(trueTypeFont.getHeight("Niveau Termine ")))/2)),
                "Niveau Termine",
                Color.white);
    }

    public void drawLevel(GameContainer gc, Graphics g){
        //Dessin de l'image de fond
        g.drawImage(
                this.background,
                0,
                0,
                gc.getWidth(),
                gc.getHeight(),
                0,
                0,
                this.background.getWidth(),
                this.background.getHeight()
        );

        //Dessin du Titre
        g.setColor(Color.white);
        trueTypeFont.drawString(
                (gc.getWidth() - trueTypeFont.getWidth(title))/2,
                (float) ((gc.getWidth()*MARGIN_GAMEBOARD) - trueTypeFont.getHeight(title))/2,
                this.title);

        //Dessin du gameBoard
        this.gameBoard.drawGameBoard(g);
        gameBoard.drawGameBoard(g);
        gameBoard.drawSelectBox(g);
        gameBoard.drawSelectSquare(g);
        if(gameBoard.getSelectSquare() != null){
            gameBoard.getSelectSquare().drawValidDestination(gameBoard,g);
        }
        gameBoard.drawSquares(g);

        //Dessin des Boutons
        this.backButton.drawButton(g);
        this.reloadButton.drawButton(g);

        if(this.isFinished()){
            this.drawWindowEndLevel(gc, g);
        }
    }




}
