package com.milne.mw.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.milne.mw.globals.GameData;

public class ExitConfirmationScreen implements Screen {

    private Stage stage;
    private Skin skin;

    public ExitConfirmationScreen() {
        this.stage = new Stage(new FitViewport(800, 600));
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
    }

    @Override
    public void show() {
        createExitConfirmationDialog();
    }

    private void createExitConfirmationDialog() {
        Dialog dialog = new Dialog("Salir del Juego", skin) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    Gdx.app.exit();
                } else {
                    GameData.game.setScreen(new MainMenuScreen());
                }
            }
        };
        dialog.text("¿Desea salir del juego?");
        dialog.button("Si", true);
        dialog.button("No", false);
        dialog.show(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
