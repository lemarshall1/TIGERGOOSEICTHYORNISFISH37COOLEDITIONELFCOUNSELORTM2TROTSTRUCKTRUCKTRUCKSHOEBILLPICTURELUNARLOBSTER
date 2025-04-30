package io.github.lemarshall1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class SingleplayerGame {
    private SpriteBatch batch;
    private Texture background;
    private Player player;

    public SingleplayerGame() {
        batch = new SpriteBatch();
        background = new Texture("singleplayer_background.png"); // Replace with your background image
        player = new Player("player_texture.png", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 3);
    }

    public void update() {
        // Handle input for movement and rotation
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) {
            player.move(0, 5);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) {
            player.move(0, -5);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) {
            player.rotate(5);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) {
            player.rotate(-5);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            player.shoot();
        }
    }

    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.render(batch);
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        background.dispose();
        player.dispose();
    }
}