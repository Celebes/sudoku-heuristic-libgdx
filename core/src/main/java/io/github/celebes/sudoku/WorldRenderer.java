package io.github.celebes.sudoku;

import io.github.celebes.sudoku.objects.GuiButton;
import io.github.celebes.sudoku.utils.Constants;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

public class WorldRenderer implements Disposable {
	public static final String TAG = WorldRenderer.class.getName();

	private OrthographicCamera camera;
	private OrthographicCamera cameraGUI;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private WorldController worldController;

	public WorldRenderer(WorldController worldController) {
		this.worldController = worldController;
		init();
	}

	private void init() {
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
		
		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		//cameraGUI.setToOrtho(true);			// odwroc do gory nogami os Y
		cameraGUI.update();

		// wysrodkuj kamere na planszy
		this.worldController.getCameraHelper().setPosition(Constants.VIEWPORT_HEIGHT/2, Constants.VIEWPORT_HEIGHT/2);
		
		// przesun w lewo
		this.worldController.getCameraHelper().setPosition(this.worldController.getCameraHelper().getPosition().x + 3.75f, this.worldController.getCameraHelper().getPosition().y);
		
		// oddal troche
		this.worldController.getCameraHelper().setZoom(1.1f);
		
		this.worldController.setWorldRenderer(this);
	}
	
	public void render() {
		
		renderShape();
		renderSprite();
		renderGui();
		renderPopup();

	}

	private void renderShape() {
		worldController.getCameraHelper().applyTo(camera);
		shapeRenderer.setProjectionMatrix(camera.combined);
		worldController.board.render(shapeRenderer);
	}
	
	private void renderSprite() {
		worldController.getCameraHelper().applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		worldController.board.render(batch);
	}
	
	private void renderGui() {
		worldController.getCameraHelper().applyTo(camera);
		
		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
		
		worldController.menu.render(shapeRenderer);
		worldController.menu.render(batch);
	}
	
	private void renderPopup() {
		worldController.getCameraHelper().applyTo(camera);
		
		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
		
		worldController.popup.render(shapeRenderer);
		worldController.popup.render(batch);
	}

	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
		batch.dispose();
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}
	
}
