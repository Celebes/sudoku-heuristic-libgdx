package io.github.celebes.sudoku.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.github.celebes.sudoku.SudokuGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "I3A3S4 - Krzysztof GURNIAK - Systemy Eksperckie II";
		config.width = 1280;
		config.height = 720;
		
		new LwjglApplication(new SudokuGame(), config);
	}
}
