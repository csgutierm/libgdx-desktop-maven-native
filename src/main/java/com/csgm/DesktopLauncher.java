package com.csgm;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	
	public static int resolutionX = 1920;
	public static int resolutionY = 1080;
	
    public static void launch() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Mi LibGDX App");
        config.setResizable(false);
        config.setWindowedMode(resolutionX, resolutionY); // Tamaño inicial de la ventana
        config.useVsync(true); // Sincronización vertical
        config.setForegroundFPS(30); // FPS máximos

        new Lwjgl3Application(new MyGdxGame(), config);
    }
}
