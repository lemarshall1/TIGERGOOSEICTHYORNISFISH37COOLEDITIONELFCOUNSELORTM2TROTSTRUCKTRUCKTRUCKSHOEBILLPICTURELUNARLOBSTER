package io.github.lemarshall1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Obstacle {
    protected Texture texture;
    protected Vector2 position;
    protected Polygon bounds; // Use Polygon for rotated bounding area
    protected float angle; // Rotation angle in degrees
    protected float width;
    protected float height;

    public Obstacle(String texturePath, float x, float y, float width, float height, float angle) {
        try {
            this.texture = new Texture(texturePath);
            System.out.println("Obstacle texture loaded: " + texturePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.angle = angle;

        // Define the polygon bounds to match the texture dimensions
        float[] vertices = {
            0, 0, // Bottom-left
            width, 0, // Bottom-right
            width, height, // Top-right
            0, height // Top-left
        };
        this.bounds = new Polygon(vertices);
        this.bounds.setOrigin(width / 2f, height / 2f); // Set origin for rotation to the center
        this.bounds.setPosition(x, y);
        this.bounds.setRotation(angle);
    }

    public abstract void onLaserHit(Laser laser);

    public void render(SpriteBatch batch) {
        batch.draw(
            texture,
            position.x, position.y, // Bottom-left corner of the obstacle
            width / 2f, height / 2f, // Origin for rotation
            width, height, // Width and height
            1, 1, // Scale
            angle, // Rotation angle
            0, 0, // Texture region origin
            texture.getWidth(), texture.getHeight(), // Texture region dimensions
            false, false // Flip horizontally or vertically
        );
    }

    // Overloaded collidesWith method to handle both Rectangle and Polygon
    public boolean collidesWith(Rectangle otherBounds) {
        Polygon rectangleAsPolygon = new Polygon(new float[]{
            otherBounds.x, otherBounds.y, // Bottom-left
            otherBounds.x + otherBounds.width, otherBounds.y, // Bottom-right
            otherBounds.x + otherBounds.width, otherBounds.y + otherBounds.height, // Top-right
            otherBounds.x, otherBounds.y + otherBounds.height // Top-left
        });
        rectangleAsPolygon.setOrigin(otherBounds.width / 2f, otherBounds.height / 2f);
        return Intersector.overlapConvexPolygons(bounds, rectangleAsPolygon);
    }

    public boolean collidesWith(Polygon otherBounds) {
        return Intersector.overlapConvexPolygons(bounds, otherBounds);
    }

    public void dispose() {
        texture.dispose();
    }
}