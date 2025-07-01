package com.csgm.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CardZone extends Group {
    public CardZone(float x, float y, float width, float height, Color color) {
        setBounds(x, y, width, height);
        
        // Crea una textura sólida (color de fondo)
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        //pixmap.setColor(new Color(0.2f, 0.2f, 0.4f, 0.8f)); // azul semitransparente
        pixmap.setColor(color);
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        Image background = new Image(texture);
        background.setSize(width, height);
        background.setPosition(0, 0);

        addActor(background); // Agrega fondo al grupo
    }
}
