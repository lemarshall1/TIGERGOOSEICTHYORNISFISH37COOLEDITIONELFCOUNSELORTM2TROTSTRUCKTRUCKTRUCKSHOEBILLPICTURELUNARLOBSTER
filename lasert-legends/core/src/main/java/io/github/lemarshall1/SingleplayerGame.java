package io.github.lemarshall1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.math.Rectangle;

public class SingleplayerGame {
    private SpriteBatch batch;
    private Texture background;
    private Player player;
    private FitViewport viewport;
    private Array<Obstacle> obstacles;
    private Array<TargetObstacle> targets;
    private Stage stage;
    private TextButton resetButton;
    private int score;

    public SingleplayerGame() {
        batch = new SpriteBatch();
        try {
            background = new Texture("singleplayer_background.png");
            System.out.println("Background texture loaded: singleplayer_background.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewport = new FitViewport(800, 600); // Set a virtual resolution
        player = new Player("player_texture.png", viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 3);
        obstacles = new Array<>();
        targets = new Array<>();
        score = 0;

        generateObstacles();
        generateTargets();

        // Initialize Stage and Reset Button
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json")); // Ensure you have a valid uiskin.json file
        resetButton = new TextButton("Reset", skin);
        resetButton.setPosition(Gdx.graphics.getWidth() - resetButton.getWidth() - 10, Gdx.graphics.getHeight() - resetButton.getHeight() - 10); // Top-right corner
        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetGame();
            }
        });

        stage.addActor(resetButton);
    }

    private void generateObstacles() {
        obstacles.clear();
        int numObstacles = 2 + (int) (Math.random() * 19); // Random number of obstacles between 2 and 20
        for (int i = 0; i < numObstacles; i++) {
            float x = (float) (Math.random() * viewport.getWorldWidth());
            float y = (float) (Math.random() * viewport.getWorldHeight());
            float width = 10 + (float) (Math.random() * 50);
            float height = 10 + (float) (Math.random() * 50);
            float angle = (float) (Math.random() * 360);

            obstacles.add(new WallObstacle("wall_texture.png", x, y, width, height, angle));
        }
    }

    private void generateTargets() {
        targets.clear();
        int numTargets = 3 + (int) (Math.random() * 5); // Random number of targets between 3 and 7
        for (int i = 0; i < numTargets; i++) {
            float x = (float) (Math.random() * viewport.getWorldWidth());
            float y = (float) (Math.random() * viewport.getWorldHeight());
            float width = 20;
            float height = 20;
            float angle = 0;

            targets.add(new TargetObstacle("target_texture.png", x, y, width, height, angle));
        }
    }

    private void resetGame() {
        System.out.println("Resetting game...");
        player = new Player("player_texture.png", viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 3);
        generateObstacles();
        generateTargets();
    }

    public void update() {
        // Update player rotation based on mouse position

        if (Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.LEFT)) {
            player.updateRotationWithMouse();
        }

        // Handle input for movement
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) {
            float radians = (float) Math.toRadians(player.getRotation());
            float deltaX = (float) Math.cos(radians) * 3;
            float deltaY = (float) Math.sin(radians) * 3;
            player.move(deltaX, deltaY, obstacles.toArray(Obstacle.class));
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) {
            float radians = (float) Math.toRadians(player.getRotation());
            float deltaX = (float) Math.cos(radians) * -3;
            float deltaY = (float) Math.sin(radians) * -3;
            player.move(deltaX, deltaY, obstacles.toArray(Obstacle.class));
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            player.shoot();
        }

        player.updateLasers(obstacles.toArray(Obstacle.class));

        for (Laser laser : player.getLasers()) {
            for (TargetObstacle target : targets) {
                if (target.isActive() && target.collidesWith(new Rectangle(laser.getPosition().x, laser.getPosition().y, laser.getTexture().getWidth(), laser.getTexture().getHeight()))) {
                    target.onLaserHit(laser);
                    score++;
                }
            }
        }

        // Check if all targets are destroyed
        boolean allTargetsDestroyed = true;
        for (TargetObstacle target : targets) {
            if (target.isActive()) {
                allTargetsDestroyed = false;
                break;
            }
        }
        if (allTargetsDestroyed) {
            generateObstacles();
            generateTargets();
        }

        stage.act(); // Update the stage
    }

    public void render() {
        System.out.println("Rendering SingleplayerGame...");
        ScreenUtils.clear(0, 0, 0, 1);

        viewport.apply(); // Apply the viewport to handle scaling
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight()); // Draw background with proper scaling
        for (Obstacle obstacle : obstacles) {
            obstacle.render(batch);
        }
        for (TargetObstacle target : targets) {
            if (target.isActive()) {
                target.render(batch);
            }
        }
        player.render(batch); // Player rendering already uses scaling
        batch.end();

        stage.draw(); // Draw the stage (UI elements)
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true); // Update the viewport on window resize
        stage.getViewport().update(width, height, true); // Update the stage's viewport

        // Reposition the reset button relative to the new viewport dimensions
        resetButton.setPosition(stage.getViewport().getWorldWidth() - resetButton.getWidth() - 10, 
                                stage.getViewport().getWorldHeight() - resetButton.getHeight() - 10);
    }

    public void dispose() {
        batch.dispose();
        background.dispose();
        player.dispose();
        stage.dispose();
    }
}