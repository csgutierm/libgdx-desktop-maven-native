package com.csgm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.csgm.actors.CardActor;
import com.csgm.model.Card;
import com.csgm.utils.CardZone;

public class GameScreen implements Screen {
    private Stage stage;
    private Label turnLabel;
    private boolean playerTurn = true;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Table uiTable;
    private Skin customSkin;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        backgroundTexture = new Texture(Gdx.files.internal("wallpaper_69_1920.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true); // Ocupa toda la pantalla
        stage.addActor(backgroundImage); // Se agrega primero, queda al fondo
        
        
        // Crear skin personalizado
        customSkin = createCustomSkin();

        uiTable = new Table();
        uiTable.setFillParent(true);
        stage.addActor(uiTable);

        turnLabel = new Label("Turno del Jugador", customSkin);
        TextButton endTurnButton = new TextButton("Finalizar Turno", customSkin);

        endTurnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playerTurn = !playerTurn;
                turnLabel.setText(playerTurn ? "Turno del Jugador" : "Turno del Oponente");
            }
        });

        uiTable.top().pad(10);
        uiTable.add(turnLabel).padRight(20);
        uiTable.add(endTurnButton);

        // Campo de batalla
        CardZone battlefield = new CardZone(100, 300, 800, 200, Color.RED);
        stage.addActor(battlefield);

        // Mano
        CardZone handZone = new CardZone(100, 50, 800, 200, Color.WHITE);
        stage.addActor(handZone);

        // Agrega 5 cartas a la mano
        for (int i = 0; i < 5; i++) {
            Card card = new Card("Carta " + (i + 1), 2, 2);
            CardActor actor = new CardActor(card);
            actor.setPosition(120 + i * 110, 30);
            enableDrag(actor, battlefield);
            handZone.addActor(actor);
        }
    }
    
    private Skin createCustomSkin() {
        Skin skin = new Skin();
        
        // Crear fuente personalizada
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SamsungSans-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.BLACK;
        
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        
        skin.add("default-font", font);
        
        // Crear texturas simples para botones
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        
        // Textura para botón normal (azul oscuro)
        pixmap.setColor(0.2f, 0.3f, 0.8f, 1f);
        pixmap.fill();
        skin.add("button-up", new Texture(pixmap));
        
        // Textura para botón presionado (azul más oscuro)
        pixmap.setColor(0.1f, 0.2f, 0.6f, 1f);
        pixmap.fill();
        skin.add("button-down", new Texture(pixmap));
        
        // Textura para botón hover (azul más claro)
        pixmap.setColor(0.3f, 0.4f, 0.9f, 1f);
        pixmap.fill();
        skin.add("button-over", new Texture(pixmap));
        
        pixmap.dispose();
        
        // Crear estilos
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        skin.add("default", labelStyle);
        
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(skin.get("button-up", Texture.class)));
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(skin.get("button-down", Texture.class)));
        buttonStyle.over = new TextureRegionDrawable(new TextureRegion(skin.get("button-over", Texture.class)));
        skin.add("default", buttonStyle);
        
        return skin;
    }

    private void enableDrag(final CardActor actor, final CardZone battlefield) {
        actor.addListener(new DragListener() {
        	
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                actor.toFront(); // <-- esto lo pone arriba
                // Animación: escalar un poco
                actor.clearActions(); // Por si tenía alguna animación previa
                actor.addAction(Actions.scaleTo(1.5f, 1.5f, 0.1f));
				return true;
            }
            
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                actor.moveBy(x - actor.getWidth() / 2, y - actor.getHeight() / 2);
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // Al soltar, vuelve al tamaño original
                actor.addAction(Actions.scaleTo(1f, 1f, 0.1f));
            }

//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                if (battlefield.hit(actor.getX(), actor.getY(), true) != null) {
//                    actor.remove();
//                    actor.setPosition(actor.getX(), 320);
//                    battlefield.addActor(actor);
//
//                    // ANIMACIÓN: escalar brevemente
//                    actor.setScale(0);
//                    actor.addAction(Actions.sequence(
//                        Actions.scaleTo(1.2f, 1.2f, 0.1f),
//                        Actions.scaleTo(1f, 1f, 0.1f)
//                    ));
//                }
//            }
        });
    }

    @Override public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
    }
}

