package com.company;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class MainConnectMe {

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new ConnectMe("Connect Me"));
        app.setTargetFrameRate(60);
        app.setShowFPS(false);
        app.setDisplayMode(1000,1000,false);
        app.start();
    }
}
