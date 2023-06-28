package com.trabalho.box2dtutorial.views;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.trabalho.box2dtutorial.Box2dTutorial;
import com.trabalho.box2dtutorial.model.Builder;

public class PersonagemScreen implements Screen {

    private Box2dTutorial parent;
    private Stage stage;
    TextureAtlas textureAtlas;
    private Builder builder = new Builder();
    private Label titleLabel;
    private TextField nomeTextField;
    private TextField profissaoTextField;
    private TextField forcaTextField;
    private TextField destrezaTextField;
    private TextField constituicaoTextField;
    private TextField inteligenciaTextField;
    private TextField cabeloTextField;
    private String[] coresCabelo = {"Loiro", "Castanho", "Preto", "Ruivo"};
    private String[] profissoes = {"Swordsman", "Thief", "Wizard", "Archer"};
    private int cabeloIndex = 0;
    private int equipIndex =0;
    private int profIndex =0;
    private Image prof;
    private Image cabelo;
    private String forcaValor = Integer.toString(builder.getForca());
    private String destrezaValor = Integer.toString(builder.getDestreza());
    private String constituicaoValor = Integer.toString(builder.getConstituicao());
    private String inteligenciaValor = Integer.toString(builder.getInteligencia());
    private Button cabeloPreviousButton;
    private Button cabeloNextButton;
    private OrthographicCamera camera;
    private Viewport viewport;

    public PersonagemScreen(Box2dTutorial parent) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(1080, 720, camera);
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
        TextureRegion spriteRegion = textureAtlas.findRegion(profissoes[profIndex]);
        prof = new Image(spriteRegion);
        spriteRegion = textureAtlas.findRegion(coresCabelo[cabeloIndex]);
        cabelo = new Image(spriteRegion);
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        titleLabel = new Label("Criar Personagem",skin);
        Label nameLabel = new Label("Nome:", skin);
        nomeTextField = new TextField("", skin);

        Label profissaoLabel = new Label("Profissao:", skin);
        profissaoTextField = new TextField(profissoes[profIndex], skin);
        final TextButton profPreviousButton = new TextButton("Anterior", skin, "small");
        final TextButton profNextButton = new TextButton("Proximo", skin, "small");
        profissaoTextField.setDisabled(true);
        profPreviousButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeprof(-1);
            }
        });

        profNextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeprof(1);
            }
        });
        final TextButton backButton = new TextButton("Cancelar", skin, "small");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Box2dTutorial.MENU);
            }
        });
        final TextButton createButton = new TextButton("Criar Personagem", skin, "small");
        createButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                builder.setNome(nomeTextField.getText());
                builder.setProfissao(profissaoTextField.getText());
                builder.setCabelo(cabeloTextField.getText());
                parent.personagems.add(builder.buildPersonagem());
                parent.changeScreen(Box2dTutorial.MENU);
            }
        });
        cabeloPreviousButton = new TextButton("Anterior", skin, "small");
        cabeloNextButton = new TextButton("Proximo", skin, "small");

        Label cabeloLabel = new Label("Cabelo:", skin);
        cabeloTextField = new TextField(coresCabelo[cabeloIndex], skin);
        cabeloTextField.setDisabled(true);
        cabeloPreviousButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeCabelo(-1);
            }
        });

        cabeloNextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeCabelo(1);
            }
        });

        Label inteligenciaLabel = new Label("Inteligencia:", skin);
        inteligenciaTextField = new TextField(inteligenciaValor, skin);
        final TextButton inteligenciaPreviousButton = new TextButton("-", skin, "small");
        final TextButton inteligenciaNextButton = new TextButton("+", skin, "small");
        inteligenciaTextField.setDisabled(true);
        inteligenciaPreviousButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                diminuiinteligencia();
            }
        });

        inteligenciaNextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                aumentainteligencia();
            }
        });

        Label forcaLabel = new Label("Forca:", skin);
        forcaTextField = new TextField(forcaValor, skin);
        final TextButton forcaPreviousButton = new TextButton("-", skin, "small");
        final TextButton forcaNextButton = new TextButton("+", skin, "small");
        forcaTextField.setDisabled(true);
        forcaPreviousButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                diminuiforca();
            }
        });

        forcaNextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                aumentaforca();
            }
        });

        Label destrezaLabel = new Label("destreza:", skin);
        destrezaTextField = new TextField(destrezaValor, skin);
        final TextButton destrezaPreviousButton = new TextButton("-", skin, "small");
        final TextButton destrezaNextButton = new TextButton("+", skin, "small");
        destrezaTextField.setDisabled(true);
        destrezaPreviousButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                diminuidestreza();
            }
        });

        destrezaNextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                aumentadestreza();
            }
        });

        Label constituicaoLabel = new Label("constituicao:", skin);
        constituicaoTextField = new TextField(constituicaoValor, skin);
        final TextButton constituicaoPreviousButton = new TextButton("-", skin, "small");
        final TextButton constituicaoNextButton = new TextButton("+", skin, "small");
        constituicaoTextField.setDisabled(true);
        constituicaoPreviousButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                diminuiconstituicao();
            }
        });

        constituicaoNextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                aumentaconstituicao();
            }
        });
        Table nestedTable = new Table();

        table.add(titleLabel).colspan(2);
        table.row().pad(10,0,0,10);
        table.add(nameLabel).left();
        table.add(nomeTextField);
        table.row().pad(10,0,0,10);
        table.add(cabeloLabel).left();
        table.add(cabeloPreviousButton);
        nestedTable.add(prof).padBottom(-110).expandX();
        nestedTable.row();
        nestedTable.add(cabelo).padBottom(0).expandX();
        table.add(nestedTable);
        table.add(cabeloNextButton);
        table.row().pad(100,0,0,10);
        table.add(profissaoLabel).left();
        table.add(profPreviousButton);
        table.add(profissaoTextField).minWidth(200);
        table.add(profNextButton);
        table.row().pad(10,0,0,10);
        table.add(constituicaoLabel).left();
        table.add(constituicaoPreviousButton);
        table.add(constituicaoTextField).maxWidth(50);
        table.add(constituicaoNextButton);
        table.row().pad(10,0,0,10);
        table.add(destrezaLabel).left();
        table.add(destrezaPreviousButton);
        table.add(destrezaTextField).maxWidth(50);
        table.add(destrezaNextButton);
        table.row().pad(10,0,0,10);
        table.add(forcaLabel).left();
        table.add(forcaPreviousButton);
        table.add(forcaTextField).maxWidth(50);
        table.add(forcaNextButton);
        table.row().pad(10,0,0,10);
        table.add(inteligenciaLabel).left();
        table.add(inteligenciaPreviousButton);
        table.add(inteligenciaTextField).maxWidth(50);
        table.add(inteligenciaNextButton);
        table.row().pad(10,0,0,10);
        table.add(backButton).colspan(2);
        table.add(createButton).colspan(2);
    }

    private void changeprof(int increment) {
        profIndex += increment;

        // Verifica se o índice está dentro dos limites do vetor
        if (profIndex < 0) {
            profIndex = 0;
        } else if (profIndex >= profissoes.length) {
            profIndex = profissoes.length - 1;
        }

        TextureRegion newRegion = textureAtlas.findRegion(profissoes[profIndex]);
        prof.setDrawable(new TextureRegionDrawable(newRegion));
        profissaoTextField.setText(profissoes[profIndex]);
    }


    private void changeCabelo(int increment) {
        cabeloIndex += increment;

        // Verifica se o índice está dentro dos limites do vetor
        if (cabeloIndex < 0) {
            cabeloIndex = 0;
        } else if (cabeloIndex >= coresCabelo.length) {
            cabeloIndex = coresCabelo.length - 1;
        }

        TextureRegion newRegion = textureAtlas.findRegion(coresCabelo[cabeloIndex]);
        cabelo.setDrawable(new TextureRegionDrawable(newRegion));
        cabeloTextField.setText(coresCabelo[cabeloIndex]);
    }

    private void diminuiforca() {
        if (builder.getForca() != 1) {
            builder.setForca(builder.getForca()-1);
            forcaValor = Integer.toString(builder.getForca());
            forcaTextField.setText(forcaValor);
        }
    }

    private void aumentaforca() {
        builder.setForca(builder.getForca()+1);
        forcaValor = Integer.toString(builder.getForca());
        forcaTextField.setText(forcaValor);

    }

    private void diminuiinteligencia() {
        if (builder.getInteligencia() != 1) {
            builder.setInteligencia(builder.getInteligencia()-1);
            inteligenciaValor = Integer.toString(builder.getInteligencia());
            inteligenciaTextField.setText(inteligenciaValor);
        }
    }

    private void aumentainteligencia() {
        builder.setInteligencia(builder.getInteligencia()+1);
        inteligenciaValor = Integer.toString(builder.getInteligencia());
        inteligenciaTextField.setText(inteligenciaValor);

    }

    private void diminuiconstituicao() {
        if (builder.getConstituicao() != 1) {
            builder.setConstituicao(builder.getConstituicao()-1);
            constituicaoValor = Integer.toString(builder.getConstituicao());
            constituicaoTextField.setText(constituicaoValor);
        }
    }

    private void aumentaconstituicao() {
        builder.setConstituicao(builder.getConstituicao()+1);
        constituicaoValor = Integer.toString(builder.getConstituicao());
        constituicaoTextField.setText(constituicaoValor);

    }

    private void diminuidestreza() {
        if (builder.getDestreza() != 1) {
            builder.setDestreza(builder.getDestreza()-1);
            destrezaValor = Integer.toString(builder.getDestreza());
            destrezaTextField.setText(destrezaValor);
        }
    }

    private void aumentadestreza() {
        builder.setDestreza(builder.getDestreza()+1);
        destrezaValor = Integer.toString(builder.getDestreza());
        destrezaTextField.setText(destrezaValor);

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
