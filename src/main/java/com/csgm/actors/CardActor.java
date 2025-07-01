package com.csgm.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.csgm.model.Card;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class CardActor extends Actor {
    private final Texture texture;
    private final Card card;
    private final BitmapFont font;

    public CardActor(Card card) {
        this.card = card;
        this.texture = new Texture("88246-mineria.jpg"); // Usa tu asset
        
        /*
        this.font = new BitmapFont(); // Usa fuente por defecto
        this.font.setColor(Color.WHITE);
        */
        // Cargar fuente personalizada
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SamsungSans-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16; // Tamaño base de la fuente
        parameter.color = Color.WHITE;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.BLACK; // Opcional: borde para mejor legibilidad
        
        this.font = generator.generateFont(parameter);
        generator.dispose(); // No olvides liberar el generador
        
        setSize(100, 150);
		setOrigin(getWidth() / 2f, getHeight() / 2f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        batch.draw(texture,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation(),
                0, 0,
                texture.getWidth(), texture.getHeight(),
                false, false
            );
        
        // Posición relativa al actor, ajustada por escala y origen
        float scale = getScaleX(); // asumimos escala uniforme
        font.getData().setScale(scale);
        
        // Usar las mismas transformaciones que la textura
        // Posición base + offset transformado por origen y escala
        float baseX = getX() + getOriginX() * (1 - scale);
        float baseY = getY() + getOriginY() * (1 - scale);

        font.draw(batch, card.name, baseX + 10 * scale, baseY + 130 * scale);
        font.draw(batch, "ATK: " + card.attack, baseX + 10 * scale, baseY + 35 * scale);
        font.draw(batch, "DEF: " + card.defense, baseX + 10 * scale, baseY + 20 * scale);
        
        // Restaurar la escala de la fuente
        font.getData().setScale(1f);
    }

    public Card getCard() {
        return card;
    }

    @Override
    public boolean remove() {
        texture.dispose();
        font.dispose();
        return super.remove();
    }
}
