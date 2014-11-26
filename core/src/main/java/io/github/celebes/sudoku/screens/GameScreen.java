package io.github.celebes.sudoku.screens;

import io.github.celebes.sudoku.Assets;
import io.github.celebes.sudoku.WorldController;
import io.github.celebes.sudoku.WorldRenderer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen implements Screen {
	
	private Game game;
	
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	
	private boolean paused;
	
	public GameScreen(Game game) {
		this.game = game;
	}
	
	public InputProcessor getInputProcessor() {
		return worldController;
	}

	@Override
	public void render(float deltaTime) {
		if (!paused) {
			worldController.update(deltaTime);
		}
		
		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		worldRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void show() {
		worldController = new WorldController(game);
		worldRenderer = new WorldRenderer(worldController);
	}

	@Override
	public void hide() {
		worldRenderer.dispose();
	}

	@Override
	public void pause() {
		//paused = true;
	}

	@Override
	public void resume() {
		Assets.instance.init(new AssetManager());
		paused = false;
	}

	@Override
	public void dispose() {
		Assets.instance.dispose();
	}

}
