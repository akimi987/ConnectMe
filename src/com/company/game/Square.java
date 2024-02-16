package com.company.game;

import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.Objects;

public class  Square {
    private int line;
    private int column;

    // Type
    private int type;
    //0 : rouge (bloquer)
    //1 : bleu (rotation)
    //2 : violet (deplacement horizontal)
    //3 : voilet (deplacement vertical)
    //4 : vert (deplacement horizontal et vertical)
    //5 : orange (rotation,deplacement horizontal et vertical)

    private Image image;
    private ArrayList<int[]> validDestination;

    //Edge
    private ArrayList<Edge> edges;

    public Square(int line, int column, int type) throws SlickException {
        this.line = line;
        this.column = column;
        this.type = type;

        switch (type) {
            case 0 -> this.image = new Image("images/red_square.png");
            case 1 -> this.image = new Image("images/blue_square.png");
            case 2 -> this.image = new Image("images/purple_square_h.png");
            case 3 -> this.image = new Image("images/purple_square_v.png");
            case 4 -> this.image = new Image("images/green_square.png");
            default -> this.image = new Image("images/orange_square.png");
        }

        this.validDestination = new ArrayList<>();

        this.edges = new ArrayList<>();
    }

    public Square(int line, int column, int type, int nbConnector_top, int nbConnector_bottom, int nbConnector_left, int nbConnector_right) throws SlickException {
        this.line = line;
        this.column = column;
        this.type = type;

        switch (type) {
            case 0 -> this.image = new Image("images/red_square.png");
            case 1 -> this.image = new Image("images/blue_square.png");
            case 2 -> this.image = new Image("images/purple_square_h.png");
            case 3 -> this.image = new Image("images/purple_square_v.png");
            case 4 -> this.image = new Image("images/green_square.png");
            default -> this.image = new Image("images/orange_square.png");
        }

        this.validDestination = new ArrayList<>();

        this.edges = new ArrayList<>();
        if(nbConnector_top > 0)
            this.addEdge(new Edge(nbConnector_top, Direction.TOP, this));
        if(nbConnector_bottom > 0)
            this.addEdge(new Edge(nbConnector_bottom, Direction.BOTTOM, this));
        if(nbConnector_left > 0)
            this.addEdge(new Edge(nbConnector_left, Direction.LEFT, this));
        if(nbConnector_right > 0)
            this.addEdge(new Edge(nbConnector_right, Direction.RIGHT, this));
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getWidth(GameBoard gb){
        return (int) (gb.getWidthBox() - 2*(gb.getWidthBox()*GameBoard.SQUARE_MARGIN));
    }

    public int getHeight(GameBoard gb){
        return (int) (gb.getHeightBox() - 2*(gb.getHeightBox()*GameBoard.SQUARE_MARGIN));
    }

    public ArrayList<int[]> getValidDestination() {
        return validDestination;
    }

    public void setValidDestination(GameBoard gameBoard) {
        this.validDestination.clear();

        switch (this.type){
            case 2:
                for(int c=0; c < gameBoard.getColumn(); c++){
                    if(gameBoard.getSquare(this.line,c) == null){
                        this.validDestination.add(new int[]{this.line,c});
                    }
                }
                break;
            case 3:
                for(int l=0; l < gameBoard.getLine(); l++){
                    if(gameBoard.getSquare(l,this.column) == null){
                        this.validDestination.add(new int[]{l,this.column});
                    }
                }
                break;
            case 4:
            case 5:
                for(int l=0; l < gameBoard.getLine(); l++){
                    for(int c=0; c < gameBoard.getColumn(); c++){
                        if(gameBoard.getSquare(l,c) == null){
                            this.validDestination.add(new int[]{l,c});
                        }
                    }
                }
                break;
            default:
        }
    }

    public void addEdge(Edge edge){
        if(!(this.edges.contains(edge))){
            this.edges.add(edge);
        }
    }

    public Edge getEdge(Direction direction) {
        for(Edge edge : this.edges){
            if(edge.getDirection() == direction){
                return edge;
            }
        }
        return null;
    }

    public int[] getCoordinate(GameBoard gb){
        int[] coordinateBox = gb.getCoordinateBox(this.line, this.column);
        int x = (int) (coordinateBox[0]+ gb.getWidthBox()*GameBoard.SQUARE_MARGIN);
        int y = (int) (coordinateBox[1]+ gb.getHeightBox()*GameBoard.SQUARE_MARGIN);
        return new int[]{x, y};
    }

    public boolean containsValidDestination(int line, int column){
        for(int[] ints : this.validDestination){
            if(ints[0] == line && ints[1] == column){
                return true;
            }
        }
        return false;
    }

    public void displacement(int line, int column){
        if(this.containsValidDestination(line,column)){
            this.line = line;
            this.column = column;
        }
    }

    public void rotation(){
        switch (type){
            case 1, 5 -> {
                for (Edge edge : this.edges){
                    edge.rotation();
                }
            }
        }
    }

    public Square getAdjacentSquare(GameBoard gb, Direction direction){
        switch (direction){
            case TOP -> {
                if(this.line > 0){
                    return gb.getSquare(this.line - 1, this.column);
                }
                return null;
            }
            case BOTTOM -> {
                if(this.line < (gb.getLine() - 1)){
                    return gb.getSquare(this.line + 1, this.column);
                }
                return null;
            }
            case LEFT -> {
                if(this.column > 0){
                    return gb.getSquare(this.line, this.column - 1);
                }
                return null;

            }
            case RIGHT -> {
                if(this.column < (gb.getColumn() - 1)){
                    return gb.getSquare(this.line, this.column + 1);
                }
                return null;
            }
        }
        return null;
    }

    public void setConnected(GameBoard gb){
        for(Edge edge : this.edges){
            edge.setConnected(gb, this);
        }
    }

    public boolean isConnected(){
        for (Edge edge : this.edges){
            if (!(edge.isConnected())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return line == square.line && column == square.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, column);
    }

    public void drawValidDestination(GameBoard gameBoard, Graphics g){
        if(this.validDestination != null){
            for(int[] coordinate : this.validDestination){
                gameBoard.drawBox(coordinate[0],coordinate[1],g,new Color(0,255,0,150));
            }
        }
    }

    public void drawSquare(GameBoard gb, Graphics g){
        //mesure
        int[] coordinate = gb.getCoordinateBox(this.line,this.column);
        int x = (int) (coordinate[0]+ gb.getWidthBox()*GameBoard.SQUARE_MARGIN);
        int y = (int) (coordinate[1]+ gb.getHeightBox()*GameBoard.SQUARE_MARGIN);
        int width = this.getWidth(gb);
        int height = this.getHeight(gb);

        g.drawImage(
                this.image,
                x,
                y,
                x+width,
                y+height,
                0,
                0,
                image.getWidth(),
                image.getHeight()
        );

        for(Edge edge : this.edges){
            edge.drawEdge(gb, this, g);
        }
    }

    @Override
    public String toString() {
        return "Square{" +
                "line=" + line +
                ", column=" + column +
                ", type=" + type +
                '}';
    }
}
