package io.github.lemarshall1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public class Laser {
    private Texture texture;
    private Vector2 position;
    private Vector2 direction;
    private float speed;
    private boolean active;

    public Laser(String texturePath, float x, float y, float angle, float speed) {
        try {
            this.texture = new Texture(texturePath);
            System.out.println("Laser texture loaded: " + texturePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.position = new Vector2(x, y);
        this.direction = new Vector2((float) Math.cos(Math.toRadians(angle)), (float) Math.sin(Math.toRadians(angle))).nor();
        this.speed = speed;
        this.active = true;
    }

    public void update(Obstacle[] obstacles) {
        if (active) {
            position.add(direction.x * speed, direction.y * speed);

            // Check for collisions with obstacles
            Rectangle laserBounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
            for (Obstacle obstacle : obstacles) {
                if (obstacle.collidesWith(laserBounds)) {
                    obstacle.onLaserHit(this); // Notify the obstacle of the hit
                    active = false; // Deactivate the laser
                    return;
                }
            }

            // Terminate the laser if it goes out of bounds
            if (position.x < 0 || position.x > Gdx.graphics.getWidth() || position.y < 0 || position.y > Gdx.graphics.getHeight()) {
                active = false;
            }
        }
    }

    public void render(SpriteBatch batch) {
        if (active) {
            batch.draw(texture, position.x, position.y, texture.getWidth() / 2f, texture.getHeight() / 2f,
                    texture.getWidth(), texture.getHeight(), 1, 1, direction.angleDeg(), 0, 0,
                    texture.getWidth(), texture.getHeight(), false, false);
        }
    }

    public void dispose() {
        texture.dispose();
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
    }

    public Texture getTexture() {
        return texture;
    }
}