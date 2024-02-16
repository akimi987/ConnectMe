package com.company.game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.ArrayList;
import java.util.Objects;

public class Edge {
    private int nbConnector;
    private boolean isConnected;
    private Direction direction;
    private Square square;

    String path_sound = "sound/connect.ogg";
    Sound sound_game;

    public Edge() throws SlickException {
        this.nbConnector = 0;
        this.isConnected = true;
        this.sound_game = new Sound(path_sound);
    }

    public Edge(int nbConnector, Direction direction, Square square) throws SlickException {
        if (nbConnector >= 0 && nbConnector <= 4)
            this.nbConnector = nbConnector;
        else
            this.nbConnector = 0;

        this.isConnected = nbConnector == 0;
        this.direction = direction;
        this.square = square;
        this.sound_game = new Sound(path_sound);
    }

    public boolean isConnected() {
        return isConnected;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getWidth(GameBoard gb, Square square) {
        return switch (this.direction) {
            case TOP, BOTTOM -> square.getWidth(gb);
            case LEFT, RIGHT -> (gb.getWidthBox() - square.getWidth(gb)) / 2;
        };
    }

    public int getHeight(GameBoard gb, Square s) {
        return switch (this.direction) {
            case TOP, BOTTOM -> (gb.getHeightBox() - s.getHeight(gb)) / 2;
            case LEFT, RIGHT -> s.getHeight(gb);
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return direction == edge.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction);
    }

    public Direction getReverseDirection(Direction direction){
        switch (direction){
            case TOP -> { return Direction.BOTTOM; }
            case BOTTOM -> { return Direction.TOP; }
            case LEFT -> { return Direction.RIGHT; }
            case RIGHT -> { return Direction.LEFT; }
        }
        return direction;
    }

    public void setConnected(GameBoard gb, Square s) {
        Square adjacent_square = s.getAdjacentSquare(gb, direction);
        if (adjacent_square != null) {
            if(adjacent_square.getEdge(this.getReverseDirection(direction)) != null){
                boolean tempIsConnected = this.isConnected;
                this.isConnected = (this.nbConnector == adjacent_square.getEdge(this.getReverseDirection(direction)).nbConnector);

                //Son si le connecteur passe de non connecter a connecter
                if(!tempIsConnected && this.isConnected){
                    if(!sound_game.playing()){
                        sound_game.play();
                    }
                }

            }
        } else {
            this.isConnected = false;
        }

    }

    public void rotation() {
        switch (this.direction) {
            case TOP -> this.direction = Direction.RIGHT;
            case BOTTOM -> this.direction = Direction.LEFT;
            case LEFT -> this.direction = Direction.TOP;
            case RIGHT -> this.direction = Direction.BOTTOM;
        }
    }

    public void drawEdge(GameBoard gb, Square square, Graphics g) {
        int[] coordinateSquare = this.square.getCoordinate(gb);
        int x = 0, y = 0, width = 0, height = 0;
        switch (this.direction) {
            case TOP -> {
                x = coordinateSquare[0];
                y = coordinateSquare[1];
                width = this.getWidth(gb, square);
                height = this.getHeight(gb, square);
            }
            case BOTTOM -> { // -1 et +1 : marge d'erreur sur affichage
                x = coordinateSquare[0];
                y = coordinateSquare[1] + this.square.getHeight(gb);
                width = this.getWidth(gb, square);
                height = this.getHeight(gb, square);
            }
            case LEFT -> {
                x = coordinateSquare[0];
                y = coordinateSquare[1];
                width = this.getWidth(gb, square);
                height = this.getHeight(gb, square);
            }
            case RIGHT -> { // -1 et +1 : marge d'erreur sur affichage
                x = coordinateSquare[0] + this.square.getWidth(gb);
                y = coordinateSquare[1];
                width = this.getWidth(gb, square);
                height = this.getHeight(gb, square);
            }
        }
        g.setColor(new Color(255, 255, 255));
        this.drawConnectors(x, y, width, height, g);
    }

    private void drawConnectors(int x, int y, int width, int height, Graphics g) {
        int width_connector, height_connector;
        ArrayList<Integer> connector_place = new ArrayList<Integer>();
        switch (this.nbConnector) {
            case 1 -> connector_place.add(4);
            case 2 -> {
                connector_place.add(3);
                connector_place.add(5);
            }
            case 3 -> {
                connector_place.add(2);
                connector_place.add(4);
                connector_place.add(6);
            }
            case 4 -> {
                connector_place.add(1);
                connector_place.add(3);
                connector_place.add(5);
                connector_place.add(7);
            }
            default -> {
            }
        }

        switch (this.direction) {
            case TOP -> {
                width_connector = width / 9;
                height_connector = -height;

                if (this.isConnected)
                    height_connector -= 2;

                for (int c = 0; c < 9; c++) {
                    if (connector_place.contains(c)) {
                        g.setColor(Color.white);
                    } else {
                        g.setColor(Color.transparent);
                    }
                    g.fillRect(
                            x + c * width_connector,
                            y,
                            width_connector,
                            height_connector);
                }
            }
            case BOTTOM -> {
                width_connector = width / 9;
                height_connector = height;

                if (this.isConnected)
                    height_connector += 2;

                for (int c = 0; c < 9; c++) {
                    if (connector_place.contains(c)) {
                        g.setColor(Color.white);
                    } else {
                        g.setColor(Color.transparent);
                    }
                    g.fillRect(
                            x + c * width_connector,
                            y,
                            width_connector,
                            height_connector);
                }
            }
            case LEFT -> {
                width_connector = -width;
                height_connector = height / 9;

                if (this.isConnected)
                    width_connector -= 2;

                for (int l = 0; l < 9; l++) {
                    if (connector_place.contains(l)) {
                        g.setColor(Color.white);
                    } else {
                        g.setColor(Color.transparent);
                    }
                    g.fillRect(
                            x,
                            y + l * height_connector,
                            width_connector,
                            height_connector);
                }
            }
            case RIGHT -> {
                width_connector = width;
                height_connector = height / 9;

                if (this.isConnected)
                    width_connector += 2;

                for (int l = 0; l < 9; l++) {
                    if (connector_place.contains(l)) {
                        g.setColor(Color.white);
                    } else {
                        g.setColor(Color.transparent);
                    }
                    g.fillRect(
                            x,
                            y + l * height_connector,
                            width_connector,
                            height_connector);
                }
            }
        }
    }
}
