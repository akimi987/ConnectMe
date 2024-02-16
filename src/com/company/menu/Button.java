package com.company.menu;

import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.awt.Font;
import java.util.Objects;

public class Button {
    //Attribut
    private int x;
    private int y;
    private int width;
    private int height;
    private int cornerRadius;
    private Color color;
    private String title;
    private TrueTypeFont trueTypeFont;
    private boolean isHovered;

    private String path_sound = "sound/Bruitage_bouton_pokemon.ogg";
    private Sound sound;

    public Button(int x, int y, int width, int height, int cornerRadius, String title, Font font, float size) throws SlickException {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.cornerRadius = cornerRadius;
        this.title = title;

        if(font != null)
            this.trueTypeFont = new TrueTypeFont(font.deriveFont(size),true);
        else
            this.trueTypeFont = new TrueTypeFont(new Font("Times",Font.BOLD,(int) size), true);

        this.isHovered = false;

        this.sound = new Sound(path_sound);
    }

    public boolean isHovered() {
        return isHovered;
    }

    public void setHovered(int x, int y) {
        this.isHovered = (x >= this.x && x <= (this.x + this.width)) && (y >= this.y && y <= (this.y + this.height));
    }

    public boolean isClick(Input input){
        this.setHovered(input.getMouseX(), input.getMouseY());
        if((input.getMouseX() >= this.x && input.getMouseX() <= this.x + this.width) && (input.getMouseY() >= this.y && input.getMouseY() <= this.y + this.height)){
            if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
                sound.play();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Button button = (Button) o;
        return x == button.x && y == button.y && Objects.equals(title, button.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, title);
    }

    public void drawButton(Graphics g){
        g.setColor(Color.blue);
        if(this.isHovered){
            g.setColor(Color.blue);
            g.fillRoundRect(
                    this.x,
                    this.y,
                    this.width,
                    this.height,
                    this.cornerRadius
            );
            g.setColor(Color.white);
            g.fillRoundRect(
                    this.x+6,
                    this.y+6,
                    this.width-12,
                    this.height-12,
                    this.cornerRadius
            );
            g.setColor(Color.blue);
        }
        else{
            g.fillRoundRect(
                    this.x,
                    this.y,
                    this.width,
                    this.height,
                    this.cornerRadius
            );
            g.setColor(Color.white);
        }

        //Dessin du Titre
        trueTypeFont.drawString(
                this.x + ((this.width - trueTypeFont.getWidth(title))/2),
                this.y + ((this.height - trueTypeFont.getHeight(title))/2),
                this.title,
                Color.cyan);
    }
}
