package com.milne.mw.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.milne.mw.globals.GameData;

import static com.milne.mw.globals.Global.loadTexture;

public class IntroductionScreen implements Screen {


    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Label instructionsLabel;
    private Label pageLabel;
    private int currentPage;
    private String[] instructions = {
        "\nMultiverse wars es un videojuego desarrollado por alumnos de la escuela técnica N°35 Eduardo Latzina.\nSus creadores son:\nSantiago Calvelo y Mateo Milne.",
        "\n\n\n\n¿En que consiste multiverse wars?\nMultiverse wars es un videojuego tanto de un jugador como cooperativo en el cual consiste en ir pasando de mapas a través de distintos niveles, el o los jugadores para poder pasar al siguiente mapa deben de enfrentarse a un mini final boss o también llamado un mini jefe final, así durante los distintos mapas, para así poder llegar al nivel final y poder enfrentarse al jefe final de juego.",
        "\n\n\n¿Como se juega en modo Un Jugador?\nObjetivo:\n• Defender la base evitando que los enemigos lleguen a ella.\n• Sobrevivir las oleadas de enemigos.\n• Derrotar al jefe final del nivel.",
        "\n\n\nControles del Modo Un Jugador:\n• Mouse/Touchpad: Para colocar las torres y recoger dinero.\n• Click izquierdo: Seleccionar y colocar tropas.\n• Click derecho: Vender tropas.\n• Tecla ESC: Pausar el juego.",
        "\n\n\nMecánicas del Juego en Modo Un Jugador:\n• Generación y Consumo de Dinero: El dinero se usa para colocar y mejorar tropas. Adminístralo estratégicamente para sobrevivir.\n• Tipos de Personajes: Distancia, Volador, Cuerpo a Cuerpo, Apoyo.",
        "\n\n\nModo Dos Jugadores\nObjetivo:\n• Trabajar en equipo para defender la base y sobrevivir las oleadas de enemigos.\n• Derrotar al jefe final del nivel cooperativamente.",
        "\n\n\nControles y Mecánicas del Juego en Modo Dos Jugadores:\n• Mouse/Touchpad: Para colocar las torres y recoger dinero.\n• Click izquierdo: Seleccionar y colocar tropas.\n• Click derecho: Vender tropas.\n• Tecla ESC: Pausar el juego.",
        "\n\n\n\nMecánicas del Juego en Modo Dos Jugadores:\n• Generación y Consumo de Dinero: Cada jugador tiene su propio dinero. Ambos ganan dinero por la eliminación de enemigos, pero solo el dueño de una tropa de apoyo recibe el dinero generado por esa tropa.\n• Tipos de Personajes: Distancia, Volador, Cuerpo a Cuerpo, Apoyo."
    };

    public IntroductionScreen() {

        this.stage = new Stage(new FitViewport(800, 600)); // Crear el Stage con un FitViewport
        Gdx.input.setInputProcessor(stage); // Asignar el input al Stage
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Cargar la textura de la imagen de fondo
        backgroundTexture = loadTexture("introduction/introduccion.jpg");
        backgroundImage = new Image(backgroundTexture);

        // Configurar el tamaño de la imagen de fondo
        backgroundImage.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());

        // Agregar la imagen de fondo al stage
        stage.addActor(backgroundImage);

        // Crear y configurar el Label para mostrar las instrucciones
        instructionsLabel = new Label(instructions[0], skin);
        instructionsLabel.setPosition(stage.getViewport().getWorldWidth() / 4f, stage.getViewport().getWorldHeight() / 2f);
        instructionsLabel.setWidth(stage.getViewport().getWorldWidth() / 2f);
        instructionsLabel.setWrap(true);
        stage.addActor(instructionsLabel);

        // Crear y configurar el Label para mostrar el número de página
        pageLabel = new Label("Pagina 1", skin);
        pageLabel.setPosition(stage.getViewport().getWorldWidth() - 150, 75); // Ajustar la posición del número de página
        stage.addActor(pageLabel);

        // Cargar y agregar las flechas para cambiar de página
        Image leftArrow = new Image(loadTexture("introduction/izqui.png"));
        leftArrow.setSize(50, 50); // Ajustar el tamaño de la flecha de la izquierda
        leftArrow.setPosition(50, stage.getViewport().getWorldHeight() / 2f - leftArrow.getHeight() / 2);
        leftArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (currentPage > 0) {
                    currentPage--;
                    instructionsLabel.setText(instructions[currentPage]);
                    pageLabel.setText("Pagina " + (currentPage + 1));
                }
            }
        });
        stage.addActor(leftArrow);

        Image rightArrow = new Image(loadTexture("introduction/dere.png"));
        rightArrow.setSize(50, 50); // Ajustar el tamaño de la flecha de la derecha
        rightArrow.setPosition(stage.getViewport().getWorldWidth() - 100,
            stage.getViewport().getWorldHeight() / 2f - rightArrow.getHeight() / 2);
        rightArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (currentPage < instructions.length - 1) {
                    currentPage++;
                    instructionsLabel.setText(instructions[currentPage]);
                    pageLabel.setText("Pagina " + (currentPage + 1));
                }
            }
        });
        stage.addActor(rightArrow);
    }

    @Override
    public void show() {
        currentPage = 0;
        pageLabel.setText("Pagina 1");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        // Detectar la tecla Esc para volver al menú principal
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            GameData.game.setScreen(new MainMenuScreen());
        }
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
        backgroundTexture.dispose();
    }
}
