package io.github.lemarshall1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends Game {
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private ScreenViewport backgroundViewport;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("lasertlegends.png");

        // Create a stage for the menu
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Create a viewport for the background
        backgroundViewport = new ScreenViewport();

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
                try {
                    setScreen(new SingleplayerGameAdapter());
                    System.out.println("Screen transitioned to SingleplayerGameAdapter.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
    public void render() {
        ScreenUtils.clear(0.05f, 0.05f, 0.02f, .1f);

        // Set the background viewport and draw the background
        backgroundViewport.apply();
        batch.setProjectionMatrix(backgroundViewport.getCamera().combined);

        batch.begin();
        batch.draw(background, 0, 0, backgroundViewport.getWorldWidth(), backgroundViewport.getWorldHeight());
        batch.end();

        // Draw the stage
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        backgroundViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        stage.dispose();
    }
}