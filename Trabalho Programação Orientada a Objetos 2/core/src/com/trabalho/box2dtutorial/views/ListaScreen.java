package com.trabalho.box2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.trabalho.box2dtutorial.Box2dTutorial;
import com.trabalho.box2dtutorial.model.Builder;

public class ListaScreen implements Screen{




        private Box2dTutorial parent;
        private Stage stage;
        private Label titleLabel;
        private TextField nomeTextField;
        private TextArea textArea;
        private int index =0;
        private OrthographicCamera camera;
        private Viewport viewport;

        public ListaScreen(Box2dTutorial parent) {
            camera = new OrthographicCamera();
            viewport = new FitViewport(1080, 720, camera);
            this.parent = parent;
            stage = new Stage(new ScreenViewport());
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        }

        @Override
        public void show() {
            stage.clear();
            Gdx.input.setInputProcessor(stage);
            Table table = new Table();
            table.setFillParent(true);

            Table controle = new Table();
            //table.setDebug(true);
            stage.addActor(table);
            Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
            titleLabel = new Label("Personagens Criados",skin);
            Label nameLabel = new Label("Nome:", skin);
            Label infoLabel = new Label("Informacoes:", skin);
            if(parent.personagems.size()==0){
                nomeTextField = new TextField("--------", skin);
            }else{
                nomeTextField = new TextField(parent.personagems.get(index).getNome(), skin);
            }

            final TextButton PreviousButton = new TextButton("Anterior", skin, "small");
            final TextButton NextButton = new TextButton("Proximo", skin, "small");
            nomeTextField.setDisabled(true);
            if(parent.personagems.size()==0){
                textArea = new TextArea("Nenhum Personagem Encontrado",skin);
            }else{
                textArea = new TextArea(parent.personagems.get(index).toString(),skin);
            }
            PreviousButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    change(-1);
                }
            });

            NextButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    change(1);
                }
            });
            final TextButton backButton = new TextButton("Cancelar", skin, "small");
            backButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    parent.changeScreen(Box2dTutorial.MENU);
                }
            });

            table.add(titleLabel).colspan(4);
            table.row().pad(10,0,0,10);
            table.add(nameLabel).left();
            table.add();
            controle.add(PreviousButton).left();
            controle.add(nomeTextField);
            controle.add(NextButton).left();
            table.add(controle);
            table.row().pad(10,0,0,10);
            table.add(infoLabel).left();
            table.add().width(20);
            table.add(textArea).minWidth(1000);
            table.row().pad(10,0,0,10);
            table.add(backButton).colspan(2);
        }

        private void change(int increment) {
            index += increment;

            // Verifica se o índice está dentro dos limites do vetor
            if (index < 0) {
                index = 0;
            } else if (index >= parent.personagems.size()) {
                index = parent.personagems.size() - 1;
            }

            textArea.setText(parent.personagems.get(index).toString());
            nomeTextField.setText(parent.personagems.get(index).getNome());
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
            stage.getViewport().update(width, height, true);
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
            stage.dispose();
        }


}
