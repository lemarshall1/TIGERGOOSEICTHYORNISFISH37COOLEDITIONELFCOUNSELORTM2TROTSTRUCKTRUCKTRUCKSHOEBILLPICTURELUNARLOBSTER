package io.github.lemarshall1;

public class TargetObstacle extends Obstacle {
    private boolean active;

    public TargetObstacle(String texturePath, float x, float y, float width, float height, float angle) {
        super(texturePath, x, y, width, height, angle);
        this.active = true;
    }

    @Override
    public void onLaserHit(Laser laser) {
        if (active) {
            active = false; // Deactivate the target
            System.out.println("Target hit at position: " + position);
        }
    }

    public boolean isActive() {
        return active;
    }
}