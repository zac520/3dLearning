package com.zsoft.threedlearning;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.zsoft.threedlearning.Screens.GameScreen;

import java.util.Random;

public class MainGame extends Game {
	public boolean saveEncrypted = true;
	public boolean showAds = false;//default to false, and we will set from the android screen for now
	public boolean needCameraResize = false;

	public int SCREEN_WIDTH = 360;
	public int SCREEN_HEIGHT = 480;
	private int BANNER_PIXEL_HEIGHT=50;

	public int EASY_MAZE_TYPE = 1;
	public int MEDIUM_MAZE_TYPE = 2;
	public int HARD_MAZE_TYPE = 4;
	public int RIDICULOUS_MAZE_TYPE = 8;

	public Random rand;
	/** shared textures **/
	public TextureAtlas atlas;
	public Skin skin;
	public TextureAtlas loadingAtlas;
	public Skin popupSkin;

	/** shared variables **/
	public Stage stage;
	public SpriteBatch batch;
	public BitmapFont font;
	public Box2DDebugRenderer box2DRenderer;
	//public MyInputProcessor myInputProcessor;
	public String loadingProgress;
	public float loadingProgressPercent = 0;
	public int textRowHeight;
	public int BANNER_DIP_HEIGHT;

	/**loading screen variables**/
	public Stage loadingStage;
	public SpriteBatch loadingBatch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new GameScreen(this));

	}


}
