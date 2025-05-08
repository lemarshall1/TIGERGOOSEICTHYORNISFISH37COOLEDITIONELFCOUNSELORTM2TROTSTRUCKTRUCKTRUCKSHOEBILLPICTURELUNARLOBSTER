package io.github.lemarshall1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenuScreen implements Screen {
    private final Main game;
    private final SpriteBatch batch;
    private final Texture background;
    private final Stage stage;
    private FitViewport viewport;

    public MainMenuScreen(Main game) {
        this.game = game;
        this.batch = game.getBatch();
        this.background = new Texture("lasertlegends.png");
        this.viewport = new FitViewport(800, 600); // Set a virtual resolution
        this.stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        // Create a Skin for button styling
        Skin skin = new Skin(Gdx.files.internal("uiskin.json")); // Ensure you have a uiskin.json file

        // Create buttons
        TextButton singleplayerButton = new TextButton("Singleplayer", skin);
        TextButton multiplayerButton = new TextButton("Multiplayer", skin);
        TextButton optionsButton = new TextButton("Options", skin);

        // Add listeners to buttons
        singleplayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                System.out.println("Singleplayer button clicked!");
                game.setScreen(new SingleplayerGameAdapter()); // Use the adapter
            }
        });

        multiplayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                System.out.println("Multiplayer clicked!");
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                System.out.println("Options clicked!");
            }
        });

        // Use a Table to layout buttons
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(singleplayerButton).pad(10).row();
        table.add(multiplayerButton).pad(10).row();
        table.add(optionsButton).pad(10);

        // Add the table to the stage
        stage.addActor(table);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen with black

        viewport.apply(); // Apply the viewport to handle scaling
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight()); // Draw background with proper scaling
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // Update the viewport on window resize
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
    }
}