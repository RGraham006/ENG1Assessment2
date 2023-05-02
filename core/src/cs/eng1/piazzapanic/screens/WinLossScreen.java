package cs.eng1.piazzapanic.screens;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.ui.ButtonManager;
import cs.eng1.piazzapanic.ui.Timer;

public class WinLossScreen implements Screen {

    private final Stage uiStage;
    private final Label resultLabel;
    private final Label resultTimer;

    public WinLossScreen(final PiazzaPanicGame game) {
        uiStage = new Stage();
        Table table = new Table();
        table.setFillParent(true);
        uiStage.addActor(table);

        Label title = new Label("Game Finished",
            new Label.LabelStyle(game.getFontManager().getHeaderFont(), null));
        
        resultLabel = new Label("", 
            new Label.LabelStyle(game.getFontManager().getSubHeaderFont(), null));

        TextButton homeButton = game.getButtonManager().createTextButton("Return to Home Screen", ButtonManager.ButtonColour.BLUE);
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.loadHomeScreen();
            }
        });

        resultTimer = new Label("", 
            new Label.LabelStyle(game.getFontManager().getSubHeaderFont(), null));

        // Add everything to screen
        table.add(title).padBottom(50f);
        table.row();
        table.add(resultLabel).padBottom(10f);
        table.row();
        table.add(resultTimer).padBottom(50f);
        table.row();
        table.add(homeButton);
    }    

    public void finishGame(boolean win, Timer timer) {
        // Configure time string
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode((RoundingMode.FLOOR));

        int seconds = ((int) (timer.getTime() % 60));
        int minutes = (int) (timer.getTime() / 60);
        String timeString = minutes + ":";
        if (seconds < 10) {
            timeString += "0";
        }
        timeString += df.format(seconds);

        if (win) {
            resultLabel.setText("Orders complete!");
        }
        else {
            resultLabel.setText("Out of reputation points!");
        }
        resultTimer.setText("Your final time was " + timeString);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        uiStage.getCamera().update();

        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        uiStage.dispose();
    }
}