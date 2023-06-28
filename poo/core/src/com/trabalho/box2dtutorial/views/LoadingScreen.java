package com.trabalho.box2dtutorial.views;

import com.badlogic.gdx.Screen;
import com.trabalho.box2dtutorial.Box2dTutorial;

public class LoadingScreen implements Screen {

    private Box2dTutorial parent;

    public LoadingScreen(Box2dTutorial box2dTutorial){
        parent = box2dTutorial;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        parent.changeScreen(Box2dTutorial.MENU);
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
