package io.github.lemarshall1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {
	private Texture texture;
	private Vector2 position;
	private float rotation; // Angle in degrees
	private int lives;
	private boolean active;

    public Player(String texturePath, float x, float y, int lives) {
        try {
            this.texture = new Texture(texturePath);
            System.out.println("Player texture loaded: " + texturePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.position = new Vector2(x, y);
        this.rotation = 0;
        this.lives = lives;
        this.active = true;
        System.out.println("Player created at position: " + position + " with " + lives + " lives.");
        System.out.println("Player texture loaded: " + texturePath);
    }

	public void move(float deltaX, float deltaY) {
		if (active) {
			position.add(deltaX, deltaY);
		}
	}

	public void rotate(float angle) {
		if (active) {
			rotation += angle;
		}
	}

	public void shoot() {
		if (active) {
			System.out.println("Laser shot!");
			// Add logic for shooting a laser
		}
	}

	public void takeDamage() {
		if (active) {
			lives--;
			if (lives <= 0) {
				active = false;
				System.out.println("Player is out of lives!");
			}
		}
	}

	public void render(SpriteBatch batch) {
        System.out.println("Rendering Player at position: " + position);
        batch.draw(texture, position.x, position.y, texture.getWidth() / 2f, texture.getHeight() / 2f,
                texture.getWidth(), texture.getHeight(), 1, 1, rotation, 0, 0, texture.getWidth(),
                texture.getHeight(), false, false);
	}

	public void dispose() {
		texture.dispose();
	}

	public boolean isActive() {
		return active;
	}
}