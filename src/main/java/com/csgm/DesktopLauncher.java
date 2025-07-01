package com.csgm;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	
	public static int resolutionX = 1920;
	public static int resolutionY = 1080;
	
    public static void launch() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Cartas");
        config.setResizable(false);
        config.setWindowedMode(resolutionX, resolutionY); // Tama�o inicial de la ventana
        config.useVsync(true); // Sincronizaci�n vertical
        config.setForegroundFPS(60); // FPS m�ximos cuando al frente/activa
        config.setWindowIcon("imw_ncs.png");

       // new Lwjgl3Application(new MyGdxGame(), config);
        
        new Lwjgl3Application(new GameMain(), config);
    }
}
