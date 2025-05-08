package io.github.lemarshall1;

import com.badlogic.gdx.Screen;

public class SingleplayerGameAdapter implements Screen {
	private SingleplayerGame game;

	public SingleplayerGameAdapter() {
		game = new SingleplayerGame();
	}

	@Override
	public void show() {
		// Called when this screen becomes the current screen
        System.out.println("SingleplayerGameAdapter is now active.");
	}

    @Override
    public void render(float delta) {
        System.out.println("Rendering SingleplayerGameAdapter...");
        game.update();
        game.render();
    }

	@Override
	public void resize(int width, int height) {
		// Handle resizing if necessary
	}

	@Override
	public void pause() {
		// Handle pause logic if necessary
	}

	@Override
	public void resume() {
		// Handle resume logic if necessary
	}

	@Override
	public void hide() {
		// Called when this screen is no longer the current screen
	}

	@Override
	public void dispose() {
		game.dispose();
	}
}