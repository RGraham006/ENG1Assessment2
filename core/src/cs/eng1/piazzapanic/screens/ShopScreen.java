package cs.eng1.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import cs.eng1.piazzapanic.ui.ButtonManager;
import cs.eng1.piazzapanic.chef.ChefManager;

public class ShopScreen implements Screen {

    private final Stage uiStage;
    private boolean chefUnlocked = false;

    public ShopScreen(final PiazzaPanicGame game){
        ScreenViewport uiViewport = new ScreenViewport();
        uiStage = new Stage();
        Table table = new Table();
        table.setFillParent(true);
        uiStage.addActor(table);

        Label shopLabel = new Label("Game Shop",
                new Label.LabelStyle(game.getFontManager().getHeaderFont(), null));

        TextButton chefButton = game.getButtonManager().createTextButton("3rd Chef 200C", ButtonManager.ButtonColour.BLUE);
        chefButton.sizeBy(3f);
        chefButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chefUnlocked = true;
                game.loadGameScreen();
            }
        });

        TextButton cuttingButton = game.getButtonManager().createTextButton("New Cutting Board 400C", ButtonManager.ButtonColour.BLUE);
        cuttingButton.sizeBy(3f);
        cuttingButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.loadGameScreen();
            }
        });

        TextButton ovenButton = game.getButtonManager().createTextButton("New Baking Oven 400C", ButtonManager.ButtonColour.BLUE);
        ovenButton.sizeBy(3f);
        ovenButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.loadGameScreen();
            }
        });

        TextButton fryingButton = game.getButtonManager().createTextButton("New Frying Pan 400C", ButtonManager.ButtonColour.BLUE);
        fryingButton.sizeBy(3f);
        fryingButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.loadGameScreen();
            }
        });

        table.add(shopLabel).padBottom(100f);
        table.row();
        table.add(chefButton).padBottom(20f);
        table.row();
        table.add(ovenButton).padBottom(20f);
        table.row();
        table.add(cuttingButton).padBottom(20f);
        table.row();
        table.add(fryingButton);
        table.row();
    }

    public boolean getChefUnlocked(){
        return chefUnlocked;
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
