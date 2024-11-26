package com.milne.mw.maps;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.milne.mw.difficulty.DifficultySelectionScreen;
import com.milne.mw.globals.GameData;
import com.milne.mw.screens.MainMenuScreen;

import static com.milne.mw.globals.Global.loadTexture;

public class MapSelectionScreen implements Screen {
    private Stage stage;
    private Texture backgroundTexture;
    private Image backgroundImage;

    private Texture map1Texture, map2Texture, map3Texture, map4Texture;
    private Image map1Image, map2Image, map3Image, map4Image;

    public MapSelectionScreen() {
        this.stage = new Stage(new FitViewport(800, 600));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = loadTexture("maps/menu de mapas.jpg");
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
        stage.addActor(backgroundImage);

        map1Texture = loadTexture("maps/mapa1.jpg");
        map2Texture = loadTexture("maps/mapa2.jpg");
        map3Texture = loadTexture("maps/mapa3.jpg");
        map4Texture = loadTexture("maps/mapa4.jpg");

        map1Image = new Image(map1Texture);
        map2Image = new Image(map2Texture);
        map3Image = new Image(map3Texture);
        map4Image = new Image(map4Texture);

        map1Image.setPosition(200, 300);
        map2Image.setPosition(425, 300);
        map3Image.setPosition(200, 150);
        map4Image.setPosition(425, 150);

        map1Image.setSize(170, 90);
        map2Image.setSize(170, 90);
        map3Image.setSize(170, 90);
        map4Image.setSize(170, 90);

        stage.addActor(map1Image);
        stage.addActor(map2Image);
        stage.addActor(map3Image);
        stage.addActor(map4Image);

        map1Image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mapSelected("maps/mapa1.jpg");
            }
        });

        map2Image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mapSelected("maps/mapa2.jpg");
            }
        });

        map3Image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mapSelected("maps/mapa3.jpg");
            }
        });

        map4Image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mapSelected("maps/mapa4.jpg");
            }
        });
    }

    private void mapSelected(String map) {
        System.out.println("Mapa seleccionado correctamente");
        GameData.game.setScreen(new DifficultySelectionScreen(map));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        // Detectar la tecla ESC para volver al menú principal
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            GameData.game.setScreen(new MainMenuScreen());
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        map1Texture.dispose();
        map2Texture.dispose();
        map3Texture.dispose();
        map4Texture.dispose();
    }
}
