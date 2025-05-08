package io.github.lemarshall1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MainMenuScreen(this)); // Set the main menu as the initial screen
    }

    @Override
    public void render() {
        super.render(); // Ensures only the active screen's render method is called
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}