package com.company.menu;

import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.awt.Font;
import java.util.ArrayList;

public class Menu {
    //Attribut
    protected String title;
    protected TrueTypeFont trueTypeFont;
    protected ArrayList<Button> buttons;
    protected Image background;

    public Menu(String title, Font font, float font_size, Image background) throws SlickException {
        this.title = title;
        this.background = background;
        this.buttons = new ArrayList<>();

        if(font != null)
            this.trueTypeFont = new TrueTypeFont(font.deriveFont(font_size),true);
        else
            this.trueTypeFont = new TrueTypeFont(new Font("Times",Font.BOLD,(int) font_size), true);
    }

    public void addButton(Button button){
        if(!(this.buttons.contains(button))){
            this.buttons.add(button);
        }
    }

    public void setButtonsHovered(int x, int y){
        for(Button button : this.buttons){
            button.setHovered(x,y);
        }
    }

    public TrueTypeFont getTrueTypeFont() {
        return trueTypeFont;
    }

    public void drawButtons(Graphics g){
        for(Button button : this.buttons){
            button.drawButton(g);
        }
    }

    public void drawMenu(GameContainer gc, Graphics g){
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
                (gc.getWidth()/2) - (trueTypeFont.getWidth(title)/2),
                100,
                this.title);

        //Dessin des Boutons
        this.drawButtons(g);
    }
}
