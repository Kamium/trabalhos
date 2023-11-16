package com.trabalho.box2dtutorial;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.trabalho.box2dtutorial.model.Personagem;
import com.trabalho.box2dtutorial.views.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Box2dTutorial extends Game {

	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private PersonagemScreen personagemScreen;
	private ListaScreen listaScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;
	private VitoriaScreen vitoriaScreen;
	private DerrotaScreen derrotaScreen;
	private AppPreferences preferences = new AppPreferences();
	public List<Personagem> personagems = carregar("personagens.dat");


	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;
	public final static int LISTA = 4;
	public final static int JOGO = 5;
	public final static int VITORIA = 6;
	public final static int DERROTA = 7;

	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if(personagemScreen == null) personagemScreen = new PersonagemScreen(this);
				this.setScreen(personagemScreen);
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
			case LISTA:
				if(listaScreen == null) listaScreen = new ListaScreen(this);
				this.setScreen(listaScreen);
				break;
			case JOGO:
				if(mainScreen == null) mainScreen = new MainScreen(this);
				this.setScreen(mainScreen);
				break;
			case VITORIA:
				if(vitoriaScreen == null) vitoriaScreen = new VitoriaScreen(this);
				this.setScreen(vitoriaScreen);
				break;
			case DERROTA:
				if(derrotaScreen == null) derrotaScreen = new DerrotaScreen(this);
				this.setScreen(derrotaScreen);
				break;
		}
	}
	@Override
	public void create () {
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	public static void salvar(List<Personagem> List, String filename) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(filename);

			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

			objectOutputStream.writeObject(List);

			objectOutputStream.close();
			fileOutputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Personagem> carregar(String filename) {
		List<Personagem> list = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(filename);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			list = (List<Personagem>) objectInputStream.readObject();
			objectInputStream.close();
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			return new ArrayList<Personagem>();
		} catch (IOException | ClassNotFoundException e) {
			return new ArrayList<Personagem>();
		}
		return list;
	}

	public AppPreferences getPreferences() {
		return this.preferences;
	}
}
