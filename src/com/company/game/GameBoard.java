package com.company.game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

public class GameBoard {
    //Attribut
    private int line;
    private int column;
    private int x;
    private int y;
    private int width;
    private int height;
    private ArrayList<Square> squares;
    private int[] selectBox;
    private Square selectSquare;

    //Variable STATIC FINAL
    final static int MINIMUM = 3;
    final static int MAXIMUM = 10;
    final static int SEPARATION = 2;
    final static double SQUARE_MARGIN = 0.1; //marge du carre dans un emplacement du plateau (en %)

    public GameBoard() {
        line = 5;
        column = 5;
        x = 0;
        y = 0;
        width = 800;
        height = 800;
        squares = new ArrayList<>();
        selectBox = new int[2];
        selectSquare = null;
    }

    public GameBoard(int line, int column, int x, int y, int width, int height) {
        this();
        if (line >= MINIMUM && line <= MAXIMUM)
            this.line = line;
        if (column >= MINIMUM && column <= MAXIMUM)
            this.column = column;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.squares = new ArrayList<>();
        selectBox = new int[2];
        selectSquare = null;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getSelectBox() {
        return selectBox;
    }

    public void setSelectBox(int[] selectBox) {
        if(selectBox != null){
            if (this.getSquare(selectBox[0], selectBox[1]) != null) {
                this.selectBox = selectBox;
            } else if (this.selectSquare != null) {
                if (this.selectSquare.containsValidDestination(selectBox[0], selectBox[1])) {
                    this.selectBox = selectBox;
                }
            } else {
                this.selectBox = null;
            }
        }
    }

    public Square getSelectSquare() {
        return selectSquare;
    }

    public void setSelectSquare(int line, int column) {
        this.selectSquare = this.getSquare(line, column);
    }

    public int[] getCoordinateBox(int line, int column) {
        int x = this.x + column * (this.width / this.column) + SEPARATION;
        int y = this.y + line * (this.height / this.line) + SEPARATION;

        return new int[]{x, y};
    }

    public int getWidthBox() {
        return (this.width / this.column) - SEPARATION;
    }

    public int getHeightBox() {
        return (this.height / this.line) - SEPARATION;
    }

    public int[] whichBox(int x, int y) {
        if ((x >= this.x && x <= this.x + this.width) && (y >= this.y && y <= this.y + this.height)) {
            return new int[]{(y - this.y) / (this.height / line), (x - this.x) / (this.width / column)};
        }
        return null;
    }

    public void addSquare(Square square) {
        if ((square.getLine() >= 0 && square.getLine() < line) && (square.getColumn() >= 0 && square.getColumn() < column)) {
            if (!(this.squares.contains(square))) {
                this.squares.add(square);
            }
        }
    }

    public Square getSquare(int line, int column) {
        for (Square square : this.squares) {
            if (square.getLine() == line && square.getColumn() == column) {
                return square;
            }
        }
        return null;
    }

    public void click(int line, int column) {
        //CLICK SUR CARRE
        if(this.getSquare(line,column)!=null){
            //ROTATION et DESELECTION
            if(this.getSquare(line,column).equals(this.selectSquare)){
                this.selectSquare.rotation();
                this.selectSquare.getValidDestination().clear();
                this.selectSquare = null;

                //Mise a jours de l'etat des connecteurs de chaque carre
                this.setConnected();
            }
            //SELECTION
            else{
                this.setSelectSquare(line,column);
                this.selectSquare.setValidDestination(this);
            }

        }
        else if(this.selectSquare != null){
            //DEPLACEMENT
            if(this.selectSquare.containsValidDestination(line,column)){
                this.selectSquare.displacement(line,column);
                this.selectSquare.getValidDestination().clear();

                //Mise a jours de l'etat des connecteursde chaque carre
                this.setConnected();
            }
            this.selectSquare = null;
        }
    }

    public void setConnected(){
        for(Square square : this.squares){
            square.setConnected(this);
        }
    }

    public boolean isConnected(){
        boolean isConnected = true;
        for(Square square : this.squares){
            if(!(square.isConnected())){
                isConnected = false;
            }
        }
        return isConnected;
    }

    public void clearSquares(){
        this.squares.clear();
    }

    public void drawSquares(Graphics g) {
        for (Square square : this.squares) {
            square.drawSquare(this, g);
        }
    }

    public void drawBox(int line, int column, Graphics g, Color color) {
        int[] coordinateBox = this.getCoordinateBox(line, column);
        int x = coordinateBox[0];
        int y = coordinateBox[1];
        int width = this.getWidthBox();
        int height = this.getHeightBox();

        g.setColor(color);
        g.fillRect(x, y, width, height);

    }

    public void drawSelectBox(Graphics g) {
        if (this.selectBox != null) {
            this.drawBox(selectBox[0], selectBox[1], g, new Color(0, 255, 255, 150));
        }
    }

    public void drawSelectSquare(Graphics g) {
        if (this.selectSquare != null) {
            this.drawBox(selectSquare.getLine(), selectSquare.getColumn(), g, new Color(0, 255, 255));
        }
    }

    public void drawGameBoard(Graphics g) {
        //mesure
        int x, y, width, height;
        g.setColor(new Color(72, 149, 239));
        for (int l = 0; l < this.line; l++) {
            for (int c = 0; c < this.column; c++) {
                x = this.x + c * (this.width / this.column) + SEPARATION;
                y = this.y + l * (this.height / this.line) + SEPARATION;
                width = (this.width / this.column) - SEPARATION;
                height = (this.height / this.line) - SEPARATION;
                g.fillRect(x, y, width, height);
            }
        }
    }
}
