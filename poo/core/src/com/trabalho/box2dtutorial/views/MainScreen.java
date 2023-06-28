package com.trabalho.box2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.trabalho.box2dtutorial.Box2dTutorial;
import com.trabalho.box2dtutorial.model.Inimigo;
import com.trabalho.box2dtutorial.model.Nivel;

import java.util.Random;

public class MainScreen implements Screen {

    private Random random = new Random();
    private Box2dTutorial parent;
    private Stage stage;
    private Inimigo inimigo;
    private Nivel nivel;
    TextureAtlas textureAtlas;
    private Label titleLabel;
    private int personagemIndex =0;
    private Image prof;
    private Image cabelo;
    private Image profEn;
    private Image cabeloEn;
    private OrthographicCamera camera;
    private Viewport viewport;
    private TextField hpTextField;
    private TextField atkTextField;
    private TextField hpEnTextField;
    private TextField atkEnTextField;

    public MainScreen(Box2dTutorial parent) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(1080, 720, camera);
        this.parent = parent;
        inimigo = new Inimigo();
        nivel = new Nivel();
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
            personagemIndex = random.nextInt( parent.personagems.size());
        }
        inimigo.setAtk(random.nextInt(50));
        inimigo.setHp(random.nextInt(100));
        inimigo.setExp(random.nextInt(parent.personagems.get(personagemIndex).getLvl()*120));
        parent.personagems.get(personagemIndex).setAtk((parent.personagems.get(personagemIndex).getDestreza()+parent.personagems.get(personagemIndex).getForca()*2+parent.personagems.get(personagemIndex).getInteligencia())*parent.personagems.get(personagemIndex).getLvl());
        parent.personagems.get(personagemIndex).setHp(parent.personagems.get(personagemIndex).getConstituicao()*14*parent.personagems.get(personagemIndex).getLvl());
        TextureRegion spriteRegion = textureAtlas.findRegion(parent.personagems.get(personagemIndex).getProfissao());
        prof = new Image(spriteRegion);
        spriteRegion = textureAtlas.findRegion(parent.personagems.get(personagemIndex).getCabelo());
        cabelo = new Image(spriteRegion);if(parent.personagems.size()>1) {
            personagemIndex = random.nextInt( parent.personagems.size());
        }
        spriteRegion = textureAtlas.findRegion(parent.personagems.get(personagemIndex).getProfissao());
        profEn = new Image(spriteRegion);
        spriteRegion = textureAtlas.findRegion(parent.personagems.get(personagemIndex).getCabelo());
        cabeloEn = new Image(spriteRegion);
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        titleLabel = new Label("Simulacao",skin);

        Label hpLabel = new Label("HP:", skin);
        hpTextField = new TextField(Integer.toString(parent.personagems.get(personagemIndex).getHp()), skin);
        hpTextField.setDisabled(true);
        Label atkLabel = new Label("ATK:", skin);
        atkTextField = new TextField(Integer.toString(parent.personagems.get(personagemIndex).getAtk()), skin);
        atkTextField.setDisabled(true);

        Label atkEnLabel = new Label("ATK:", skin);
        atkEnTextField = new TextField(Integer.toString(inimigo.getAtk()), skin);
        atkEnTextField.setDisabled(true);
        Label hpEnLabel = new Label("HP:", skin);
        hpEnTextField = new TextField(Integer.toString(inimigo.getHp()), skin);
        hpEnTextField.setDisabled(true);
        Label expEnLabel = new Label("EXP:", skin);
        TextField expEnTextField = new TextField(Integer.toString(inimigo.getExp()), skin);
        expEnTextField.setDisabled(true);
        Label expLabel = new Label("EXP:", skin);
        TextField expTextField = new TextField(Integer.toString(parent.personagems.get(personagemIndex).getExp()), skin);
        expTextField.setDisabled(true);
        TextField jogador = new TextField("Jogador",skin);
        jogador.setDisabled(true);
        TextField oponente = new TextField("Oponente",skin);
        oponente.setDisabled(true);
        parent.personagems.get(personagemIndex).attach(inimigo);
        parent.personagems.get(personagemIndex).attachLvl(nivel);

        final TextButton attackButton = new TextButton("Atacar",skin,"small");
        attackButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ataque();
                verificaHp();
            }
        });

        final TextButton backButton = new TextButton("Cancelar", skin, "small");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                voltar();
            }
        });

        Table nestedTable = new Table();
        Table enemyTable = new Table();

        table.add(titleLabel).colspan(2);
        table.row().pad(100,0,0,10);
        table.add(jogador).colspan(2).left();
        table.add(oponente).colspan(2);
        table.row().pad(10,0,0,10);
        nestedTable.add(prof).padBottom(-110).expandX();
        nestedTable.row();
        nestedTable.add(cabelo).padBottom(0).expandX();
        table.add(nestedTable);
        table.add().pad(100);
        enemyTable.add(profEn).padBottom(-110).expandX();
        enemyTable.row();
        enemyTable.add(cabeloEn).padBottom(0).expandX();
        table.add(enemyTable);
        table.row().pad(100,0,0,10);
        table.add(hpLabel);
        table.add(hpTextField);
        table.add(hpEnLabel);
        table.add(hpEnTextField);
        table.row().pad(0,0,0,10);
        table.add(atkLabel);
        table.add(atkTextField);
        table.add(atkEnLabel);
        table.add(atkEnTextField);
        table.row().pad(0,0,0,10);
        table.add(expLabel);
        table.add(expTextField);
        table.add(expEnLabel);
        table.add(expEnTextField);
        table.row().pad(100,0,0,10);
        table.add(attackButton);
        table.row().pad(100,0,0,10);
        table.add(backButton).colspan(2);
    }

    public void voltar(){
        parent.personagems.get(personagemIndex).detach(inimigo);
        parent.personagems.get(personagemIndex).detachLvl(nivel);
        parent.changeScreen(Box2dTutorial.MENU);
    }

    public void ataque(){
        parent.personagems.get(personagemIndex).atacar();
        hpTextField.setText(Integer.toString(parent.personagems.get(personagemIndex).getHp()));
        hpEnTextField.setText(Integer.toString(inimigo.getHp()));
    }

    public void verificaHp(){
        if (parent.personagems.get(personagemIndex).getHp() < 0){
            parent.personagems.get(personagemIndex).detach(inimigo);
            parent.personagems.get(personagemIndex).detachLvl(nivel);
            parent.changeScreen(Box2dTutorial.DERROTA);
        }
        if (inimigo.getHp() < 0){
            parent.personagems.get(personagemIndex).detach(inimigo);
            parent.personagems.get(personagemIndex).detachLvl(nivel);
            parent.changeScreen(Box2dTutorial.VITORIA);
        }
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
        textureAtlas.dispose();
        stage.dispose();
    }
}