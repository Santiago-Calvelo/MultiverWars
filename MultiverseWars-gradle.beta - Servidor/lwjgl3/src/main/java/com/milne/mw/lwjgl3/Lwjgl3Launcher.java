package com.milne.mw.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.milne.mw.MultiverseWars;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (!StartupHelper.startNewJvmIfRequired()) createApplication();
    }

    private static void createApplication() {
        new Lwjgl3Application(new MultiverseWars(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Multiverse-Wars");
        configuration.useVsync(true);
        configuration.setForegroundFPS(60);
        configuration.setWindowedMode(640, 480);

        // Aquí se configuran los iconos de la ventana
        configuration.setWindowIcon(
            "logo128.jpg",  // Icono de mayor tamaño
            "logo64.png",   // Icono mediano
            "logo32.jpg",   // Icono pequeño
            "logo16.jpg"    // Icono muy pequeño
        );

        return configuration;
    }
}
