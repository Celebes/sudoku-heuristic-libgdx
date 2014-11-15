package io.github.celebes.sudoku;

import io.github.celebes.sudoku.objects.Board;
import io.github.celebes.sudoku.utils.CameraHelper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class WorldController extends InputAdapter {
	public static final String TAG = WorldController.class.getSimpleName();
	
	private Game game;
	private CameraHelper cameraHelper;
	
	private boolean sudokuSolved;
	
	public Board board;
	
	public WorldController(Game game) {
		this.game = game;
		cameraHelper = new CameraHelper();
		init();
		Gdx.input.setInputProcessor(this);
	}
	
	private void init() {
		board = new Board();
	}
	
	public void update(float deltaTime) {
		handleDebugInput(deltaTime);
	}
	
	private void handleDebugInput(float deltaTime) {

		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			camMoveSpeed *= camMoveSpeedAccelerationFactor;
		}
		
		if (Gdx.input.isKeyPressed(Keys.A)) {
			moveCamera(-camMoveSpeed, 0);
		}
		
		if (Gdx.input.isKeyPressed(Keys.D)) {
			moveCamera(camMoveSpeed, 0);
		}
		
		if (Gdx.input.isKeyPressed(Keys.W)) {
			moveCamera(0, camMoveSpeed);
		}
		
		if (Gdx.input.isKeyPressed(Keys.S)) {
			moveCamera(0, -camMoveSpeed);
		}
		
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			cameraHelper.setPosition(0, 0);
		}
		
		// przyblizanie kamery
		float camZoomSpeed = 10 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		}
		
		if (Gdx.input.isKeyPressed(Keys.COMMA)) {
			cameraHelper.addZoom(camZoomSpeed);
		}
		
		if (Gdx.input.isKeyPressed(Keys.PERIOD)) {
			cameraHelper.addZoom(-camZoomSpeed);
		}
		
		if (Gdx.input.isKeyPressed(Keys.SLASH)) {
			cameraHelper.setZoom(1);
		}
		
		if(Gdx.input.isKeyPressed(Keys.R)) {
			Gdx.app.log(TAG, "RESET!");
			init();
		}
		
		if(Gdx.input.isKeyPressed(Keys.V)) {
			Gdx.app.log(TAG, "VALIDATION RESULT = " + (board.validateBoard() ? "OK" : "BAD"));
		}
	}
	
	private void moveCamera(float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}

	public CameraHelper getCameraHelper() {
		return cameraHelper;
	}

	public void setCameraHelper(CameraHelper cameraHelper) {
		this.cameraHelper = cameraHelper;
	}

	public boolean isSudokuSolved() {
		return sudokuSolved;
	}
	
}
