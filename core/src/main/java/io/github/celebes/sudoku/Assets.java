package io.github.celebes.sudoku;

import io.github.celebes.sudoku.utils.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	private AssetManager assetManager;
	
	// pogrupowane logicznie textury
	public AssetNumbers numbers;
	public AssetGui gui;
	
	// prywatny konstruktor oznacza, ze klasa jest Singletonem - nie mozna jej inicjalizowac z innych klas
	private Assets() {}
	
	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		assetManager.setErrorListener(this);
		
		// wczytaj atlasy textur
		assetManager.load(Constants.TEXTURE_ATLAS_NUMBERS, TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_GUI, TextureAtlas.class);
		
		// zacznij wczytywac rzeczy i poczekaj do konca
		assetManager.finishLoading();
		
		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		
		for(String a : assetManager.getAssetNames()) {
			Gdx.app.debug(TAG, "asset: " + a);
		}
		
		// zaladuj atlasy textur
		TextureAtlas atlasNumbers = assetManager.get(Constants.TEXTURE_ATLAS_NUMBERS);
		TextureAtlas atlasGUI = assetManager.get(Constants.TEXTURE_ATLAS_GUI);
		
		// zaladuj textury
		numbers = new AssetNumbers(atlasNumbers);
		gui = new AssetGui(atlasGUI);
	}
	
	public class AssetGui {
		public final AtlasRegion logo;
		public final AtlasRegion btnCancel;
		public final AtlasRegion btnClear;
		public final AtlasRegion btnConfirm;
		public final AtlasRegion btnContinue;
		public final AtlasRegion btnEasy;
		public final AtlasRegion btnEdit;
		public final AtlasRegion btnHard;
		public final AtlasRegion btnMedium;
		public final AtlasRegion btnPlay;
		public final AtlasRegion btnSolve;
		public final AtlasRegion btnStart;
		public final AtlasRegion btnStop;
		public final AtlasRegion btnValidate;
		
		public AssetGui(TextureAtlas atlas) {
			logo = atlas.findRegion("sudoku-logo");
			btnCancel = atlas.findRegion("btn-cancel");
			btnClear = atlas.findRegion("btn-clear");
			btnConfirm = atlas.findRegion("btn-confirm");
			btnContinue = atlas.findRegion("btn-continue");
			btnEasy = atlas.findRegion("btn-easy");
			btnEdit = atlas.findRegion("btn-edit");
			btnHard = atlas.findRegion("btn-hard");
			btnMedium = atlas.findRegion("btn-medium");
			btnPlay = atlas.findRegion("btn-play");
			btnSolve = atlas.findRegion("btn-solve");
			btnStart = atlas.findRegion("btn-start");
			btnStop = atlas.findRegion("btn-stop");
			btnValidate = atlas.findRegion("btn-validate");
		}
	}
	
	public class AssetNumbers {
		public final AtlasRegion number0;
		public final AtlasRegion number1;
		public final AtlasRegion number2;
		public final AtlasRegion number3;
		public final AtlasRegion number4;
		public final AtlasRegion number5;
		public final AtlasRegion number6;
		public final AtlasRegion number7;
		public final AtlasRegion number8;
		public final AtlasRegion number9;
		
		public AssetNumbers(TextureAtlas atlas) {
			number0 = atlas.findRegion("number0");
			number1 = atlas.findRegion("number1");
			number2 = atlas.findRegion("number2");
			number3 = atlas.findRegion("number3");
			number4 = atlas.findRegion("number4");
			number5 = atlas.findRegion("number5");
			number6 = atlas.findRegion("number6");
			number7 = atlas.findRegion("number7");
			number8 = atlas.findRegion("number8");
			number9 = atlas.findRegion("number9");
		}
		
		public AtlasRegion getNumber(int number) {
			switch(number) {
			case 0:
				return number0;
			case 1:
				return number1;
			case 2:
				return number2;
			case 3:
				return number3;
			case 4:
				return number4;
			case 5:
				return number5;
			case 6:
				return number6;
			case 7:
				return number7;
			case 8:
				return number8;
			case 9:
				return number9;
			default:
				return null;
			}
		}
	}
	
	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
	}

	@Override
	public void dispose() {
		assetManager.dispose();
	}

}
