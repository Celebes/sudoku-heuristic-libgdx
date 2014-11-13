package io.github.celebes.sudoku;

import io.github.celebes.sudoku.screens.GameScreen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class SudokuGame extends Game {
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		setScreen(new GameScreen(this));
	}
}
