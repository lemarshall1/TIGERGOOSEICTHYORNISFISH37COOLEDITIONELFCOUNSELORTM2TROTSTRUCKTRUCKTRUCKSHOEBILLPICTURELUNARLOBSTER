package io.github.lemarshall1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;

public class Player {
	private Texture texture;
	private Vector2 position;
	private float rotation; // Angle in degrees
	private int lives;
	private boolean active;
	private Array<Laser> lasers;

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
        this.lasers = new Array<>();
        System.out.println("Player created at position: " + position + " with " + lives + " lives.");
        System.out.println("Player texture loaded: " + texturePath);
    }

	public void move(float deltaX, float deltaY) {
		if (active) {
			position.add(deltaX, deltaY);
		}
	}

	public void move(float deltaX, float deltaY, Obstacle[] obstacles) {
	    if (active) {
	        Vector2 newPosition = new Vector2(position.x + deltaX, position.y + deltaY);
	        Rectangle playerBounds = new Rectangle(newPosition.x, newPosition.y, texture.getWidth(), texture.getHeight());

	        // Check for collisions with obstacles
	        for (Obstacle obstacle : obstacles) {
	            if (obstacle.collidesWith(playerBounds)) {
	                // Calculate sliding vector
	                Vector2 movementVector = new Vector2(deltaX, deltaY);
	                Vector2 obstacleNormal = calculateObstacleNormal(obstacle, playerBounds);

	                // Project movement vector onto the obstacle's surface
	                Vector2 slideVector = movementVector.sub(obstacleNormal.scl(movementVector.dot(obstacleNormal)));

	                // Update position with the sliding vector
	                newPosition.set(position.x + slideVector.x, position.y + slideVector.y);

	                // Update bounds for the new position
	                playerBounds.setPosition(newPosition.x, newPosition.y);

	                // Check if the new position still collides; if so, stop movement
	                if (obstacle.collidesWith(playerBounds)) {
	                    return;
	                }
	            }
	        }

	        // Boundary checks to prevent moving out of bounds
	        if (newPosition.x < 0) {
	            newPosition.x = 0;
	        }
	        if (newPosition.y < 0) {
	            newPosition.y = 0;
	        }
	        if (newPosition.x > 750) {
	            newPosition.x = 750;
	        }
	        if (newPosition.y > 550) {
	            newPosition.y = 550;
	        }

	        // Update the player's position
	        position.set(newPosition);
	    }
	}

	private Vector2 calculateObstacleNormal(Obstacle obstacle, Rectangle playerBounds) {
	    // Calculate the normal vector of the obstacle's surface
	    Vector2 obstacleCenter = new Vector2(
			obstacle.bounds.getX() + obstacle.bounds.getBoundingRectangle().getWidth() / 2f,
			obstacle.bounds.getY() + obstacle.bounds.getBoundingRectangle().getHeight() / 2f
	    );
	    Vector2 playerCenter = new Vector2(
	        playerBounds.x + playerBounds.width / 2f,
	        playerBounds.y + playerBounds.height / 2f
	    );

	    Vector2 normal = playerCenter.sub(obstacleCenter).nor();
	    return normal;
	}

	public float getRotation() {
		return rotation;
	}
	
	public void rotate(float angle) {
		if (active) {
			rotation += angle;
		}
	}

	public void shoot() {
	    if (active) {
	        System.out.println("Laser shot!");

	        // Calculate the center of the player
	        float centerX = position.x-texture.getWidth() / 8f;
	        float centerY = position.y+texture.getHeight() / 2.5f;

	        // Offset the laser's starting position slightly in the direction of the player's rotation
	        float radians = (float) Math.toRadians(rotation);
	        float laserX = centerX + (float) Math.cos(radians) * (texture.getWidth() / 2f); // Adjust offset as needed
	        float laserY = centerY + (float) Math.sin(radians) * (texture.getHeight() / 2f); // Adjust offset as needed

	        // Create the laser
	        lasers.add(new Laser("laser_texture.png", laserX, laserY, rotation, 10)); // Adjust speed as needed
	    }
	}

	public void updateLasers() {
		for (int i = lasers.size - 1; i >= 0; i--) {
			Laser laser = lasers.get(i);
			if (!laser.isActive()) {
				lasers.removeIndex(i);
			}
		}
	}

	public void updateLasers(Obstacle[] obstacles) {
	    for (int i = lasers.size - 1; i >= 0; i--) {
	        Laser laser = lasers.get(i);
	        laser.update(obstacles); // Pass the obstacles array to the laser's update method
	        if (!laser.isActive()) {
	            lasers.removeIndex(i);
	        }
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

        // Render lasers
        for (Laser laser : lasers) {
            laser.render(batch);
        }
	}

	public void dispose() {
		texture.dispose();
		for (Laser laser : lasers) {
		    laser.dispose();
		}
	}

	public boolean isActive() {
		return active;
	}

	public Laser[] getLasers() {
		return lasers.toArray(Laser.class);
	}

	public void updateRotationWithMouse() {
	    if (active) {
	        // Get the mouse position in screen coordinates
	        float mouseX = Gdx.input.getX();
	        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Flip Y-axis to match game coordinates

	        // Calculate the angle between the player's position and the mouse position
	        float deltaX = mouseX - (position.x + texture.getWidth() / 2f);
	        float deltaY = mouseY - (position.y + texture.getHeight() / 2f);
	        rotation = (float) Math.toDegrees(Math.atan2(deltaY, deltaX));
	    }
	}
}