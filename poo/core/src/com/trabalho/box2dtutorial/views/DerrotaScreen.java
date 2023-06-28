package com.trabalho.box2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.trabalho.box2dtutorial.Box2dTutorial;
import com.trabalho.box2dtutorial.model.Personagem;

import java.util.Random;

public class DerrotaScreen implements Screen {

    private Label titleLabel;
    private Box2dTutorial parent;
    private Personagem personagem;
    private Stage stage;
    TextureAtlas textureAtlas;
    private Image prof;
    private Image cabelo;
    private int personagemIndex =0;
    private Random random = new Random();

    public DerrotaScreen(Box2dTutorial parent){
        this.parent = parent;
        textureAtlas = new TextureAtlas("assets/sprites/sprites.txt");
        stage = new Stage(new ScreenViewport());
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        if(parent.personagems.size()>1) {
            personagemIndex = random.nextInt(0, parent.personagems.size() - 1);
        }
        TextureRegion spriteRegion = textureAtlas.findRegion(parent.personagems.get(personagemIndex).getProfissao());
        prof = new Image(spriteRegion);
        spriteRegion = textureAtlas.findRegion(parent.personagems.get(personagemIndex).getCabelo());
        cabelo = new Image(spriteRegion);
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        titleLabel = new Label("Derrota",skin, "big");

        final TextButton backButton = new TextButton("OK", skin, "small");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Box2dTutorial.MENU);
            }
        });
        Table nestedTable = new Table();

        table.add(titleLabel).colspan(2);
        table.row().pad(10,0,0,10);
        nestedTable.add(prof).padBottom(-110).expandX();
        nestedTable.row();
        nestedTable.add(cabelo).padBottom(0).expandX();
        table.add(nestedTable);
        table.row().pad(100,0,0,10);
        table.add(backButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
