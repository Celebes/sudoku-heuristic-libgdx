package io.github.celebes.sudoku;

import io.github.celebes.sudoku.utils.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Disposable;

public class WorldRenderer implements Disposable {
	public static final String TAG = WorldRenderer.class.getName();

	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private WorldController worldController;
	
	public WorldRenderer(WorldController worldController) {
		this.worldController = worldController;
		init();
	}
	
	private void init() {
		shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);

		// wysrodkuj kamere na planszy
		this.worldController.getCameraHelper().setPosition(Constants.VIEWPORT_HEIGHT/2, Constants.VIEWPORT_HEIGHT/2);
		
		// oddal troche
		this.worldController.getCameraHelper().setZoom(1.1f);
	}
	
	public void render() {
		
		shapeRenderer.begin(ShapeType.Line);
		renderGrid(shapeRenderer);
		shapeRenderer.end();

	}
	
	private void renderGrid(ShapeRenderer shapeRenderer) {
		worldController.getCameraHelper().applyTo(camera);
		shapeRenderer.setProjectionMatrix(camera.combined);
		Gdx.gl.glLineWidth(3);
		shapeRenderer.setColor(0, 0, 0, 1);
		
		// linie pionowe
		for(int i=0; i<=Constants.GRID_SIZE; i++) {
			shapeRenderer.line(i, 0, i, Constants.VIEWPORT_HEIGHT);
		}
		
		// linie poziome
		for(int i=0; i<=Constants.GRID_SIZE; i++) {
			shapeRenderer.line(0, i, Constants.VIEWPORT_HEIGHT, i);
		}
	}
	
	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}
	
}
