package io.github.lemarshall1;

public class WallObstacle extends Obstacle {
    public WallObstacle(String texturePath, float x, float y, float width, float height, float angle) {
        super(texturePath, x, y, width, height, angle);
    }

    @Override
    public void onLaserHit(Laser laser) {
        System.out.println("Laser hit the wall at position: " + position);
        laser.dispose(); // Destroy the laser
    }
}