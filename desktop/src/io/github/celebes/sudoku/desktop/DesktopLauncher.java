package io.github.celebes.sudoku.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

import io.github.celebes.sudoku.SudokuGame;

public class DesktopLauncher {
	
	private static boolean rebuildAtlas = true;
	
	public static void main (String[] arg) {
		
		if(rebuildAtlas) {
			Settings settings = new Settings();
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;
			TexturePacker2.process(settings, "assets-raw/numbers", "../android/assets/images", "numbers.pack");
		}
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "I3A3S4 - Krzysztof GURNIAK - Systemy Eksperckie II";
		config.width = 1280;
		config.height = 720;
		
		new LwjglApplication(new SudokuGame(), config);
	}
}
