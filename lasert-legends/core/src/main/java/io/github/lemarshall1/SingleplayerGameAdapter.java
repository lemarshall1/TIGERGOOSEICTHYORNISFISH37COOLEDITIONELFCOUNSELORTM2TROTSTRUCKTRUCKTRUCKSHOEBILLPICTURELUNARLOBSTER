package io.github.lemarshall1;

import com.badlogic.gdx.Screen;

public class SingleplayerGameAdapter implements Screen {
    private final SingleplayerGame singleplayerGame;

    public SingleplayerGameAdapter() {
        this.singleplayerGame = new SingleplayerGame();
    }

    @Override
    public void show() {
        // Called when this screen becomes the current screen
    }

    @Override
    public void render(float delta) {
        singleplayerGame.update(); // Update game logic
        singleplayerGame.render(); // Render the game
    }

    @Override
    public void resize(int width, int height) {
        singleplayerGame.resize(width, height); // Handle resizing
    }

    @Override
    public void pause() {
        // Handle pause logic if needed
    }

    @Override
    public void resume() {
        // Handle resume logic if needed
    }

    @Override
    public void hide() {
        // Called when this screen is no longer the current screen
    }

    @Override
    public void dispose() {
        singleplayerGame.dispose(); // Dispose of resources
    }
}