package com.csgm;

import com.badlogic.gdx.Game;
import com.csgm.screens.GameScreen;

public class GameMain extends Game {
    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}
