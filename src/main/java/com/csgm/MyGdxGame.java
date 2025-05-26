package com.csgm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.csgm.utility.FontFactory;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private BitmapFont font;
	private ShapeRenderer shapeRenderer;
	private int defaultWidth = DesktopLauncher.resolutionX;
	private int defaultHeight = DesktopLauncher.resolutionY;

	// Opciones de resolución
	private int[] widths = { 1280, 1920, 2560 };
	private int[] heights = { 720, 1080, 1440 };
	private int currentResolutionIndex = 1;

	private boolean showMenu = true;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("wallpaper_69_1920.jpg");
		img.setFilter(TextureFilter.Linear, TextureFilter.Linear); // mejora calidad al escalar la imagen	
		//font = new BitmapFont();
		//font.getData().setScale(3);	    
	    font = FontFactory.createFont("SamsungSans-Regular.ttf", 36);		
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render() {
		
		handleInput();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderBackground();

		if (showMenu) {
			renderMenu();
		}
	}

	private void handleInput() {
		// Alternar menú con la tecla M
		if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			showMenu = !showMenu;
		}

		// Cambiar tamaño de ventana
		if (showMenu) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
				cycleResolution(1);
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
				cycleResolution(-1);
			}

			// Alternar pantalla completa con Enter
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				toggleFullscreen();
			}
		}
	}

	private void cycleResolution(int direction) {
		currentResolutionIndex = (currentResolutionIndex + direction + widths.length) % widths.length;
		Gdx.graphics.setWindowedMode(widths[currentResolutionIndex], heights[currentResolutionIndex]);		
	}

	private void toggleFullscreen() {
		if (Gdx.graphics.isFullscreen()) {
			Gdx.graphics.setWindowedMode(widths[currentResolutionIndex], heights[currentResolutionIndex]);
		} else {
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		}
	}

	private void renderBackground() {
		batch.begin();
		batch.draw(img, 0, 0, defaultWidth, defaultHeight);
		batch.end();
	}

	private void renderMenu() {
		float menuWidth = defaultWidth * 0.5f; // 50% del ancho de la pantalla
		float menuHeight = defaultHeight * 0.5f; // 50% del alto de la pantalla
		float menuX = (defaultWidth - menuWidth) / 2f; // Centrado horizontalmente
		float menuY = (defaultHeight - menuHeight) / 2f; // Centrado verticalmente

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0, 0, 0, 0.7f);
		shapeRenderer.rect(menuX, menuY, menuWidth, menuHeight);
		shapeRenderer.end();

		batch.begin();
		font.draw(batch, "Menu de Configuración", menuX + menuWidth * 0.1f, menuY + menuHeight * 0.9f);
		font.draw(batch, "Resolución actual: " + widths[currentResolutionIndex] + "x" + heights[currentResolutionIndex],
				menuX + menuWidth * 0.1f, menuY + menuHeight * 0.7f);
		font.draw(batch, "Flechas arriba/abajo: Cambiar resolución", menuX + menuWidth * 0.1f,
				menuY + menuHeight * 0.6f);
		font.draw(batch, "Enter: Alternar pantalla completa", menuX + menuWidth * 0.1f, menuY + menuHeight * 0.5f);
		font.draw(batch, "M: Cerrar menú", menuX + menuWidth * 0.1f, menuY + menuHeight * 0.4f);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
		font.dispose();
		shapeRenderer.dispose();
	}
}