package cs.eng1.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import cs.eng1.piazzapanic.ui.ButtonManager;

public class ShopScreen implements Screen {

    private final Stage uiStage;
    private boolean chefUnlocked = false;
    private boolean ovenUnlocked = false;
    private boolean cuttingUnlocked = false;
    private boolean fryingUnlocked = false;

    private final Label purchaseFailedLabel;

    public ShopScreen(final PiazzaPanicGame game){
        uiStage = new Stage();
        Table table = new Table();
        table.setFillParent(true);
        uiStage.addActor(table);

        TextButton exitButton = game.getButtonManager().createTextButton("Back to Game", ButtonManager.ButtonColour.BLUE);
        exitButton.sizeBy(2f);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                purchaseFailedLabel.setVisible(false);
                game.loadGameScreen();
            }
        });

        Label shopLabel = new Label("Game Shop",
                new Label.LabelStyle(game.getFontManager().getHeaderFont(), null));

        TextButton chefButton = game.getButtonManager().createTextButton("3rd Chef 200C", ButtonManager.ButtonColour.BLUE);
        chefButton.sizeBy(3f);
        chefButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                purchaseFailedLabel.setVisible(false);
                if(game.getMoney().getMoney() >= 200) {
                    if (!chefUnlocked) {
                        game.getMoney().addMoney((-200));
                        chefUnlocked = true;
                        game.loadGameScreen();
                    } else {
                        setPurchaseFailedLabel("Chef has already been purchased");
                    }
                } else {
                    setPurchaseFailedLabel("Insufficient Funds");
                }
            }
        });

        TextButton cuttingButton = game.getButtonManager().createTextButton("New Cutting Board 400C", ButtonManager.ButtonColour.BLUE);
        cuttingButton.sizeBy(3f);
        cuttingButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(game.getMoney().getMoney() >= 400){
                    if (!cuttingUnlocked) {
                        game.getMoney().addMoney((-400));
                        cuttingUnlocked = true;
                        game.loadGameScreen();
                    } else {
                        setPurchaseFailedLabel("Cutting board has already been purchased");
                    }
                } else {
                    setPurchaseFailedLabel("Insufficient Funds");
                }
            }
        });

        TextButton ovenButton = game.getButtonManager().createTextButton("New Baking Oven 400C", ButtonManager.ButtonColour.BLUE);
        ovenButton.sizeBy(3f);
        ovenButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                purchaseFailedLabel.setVisible(false);
                if(game.getMoney().getMoney() >= 400){
                    if (!ovenUnlocked) {
                        game.getMoney().addMoney((-400));
                        ovenUnlocked = true;
                        game.loadGameScreen();
                    } else {
                        setPurchaseFailedLabel("Oven has already been purchased");
                    }
                } else {
                    setPurchaseFailedLabel("Insufficient Funds");
                }
            }
        });

        TextButton fryingButton = game.getButtonManager().createTextButton("New Frying Pan 400C", ButtonManager.ButtonColour.BLUE);
        fryingButton.sizeBy(3f);
        fryingButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                purchaseFailedLabel.setVisible(false);
                if(game.getMoney().getMoney() >= 400){
                    if (!fryingUnlocked) {
                        game.getMoney().addMoney((-400));
                        fryingUnlocked = true;
                        game.loadGameScreen();
                    } else {
                        setPurchaseFailedLabel("Frying pan has already been purchased");
                    }
                } else {
                    setPurchaseFailedLabel("Insufficient Funds");
                }
            }
        });

        // Labels for if the item they are trying to buy has already been unlocked or insufficient funds
        purchaseFailedLabel = new Label(null, 
            new Label.LabelStyle(game.getFontManager().getSubHeaderFont(), null));
        purchaseFailedLabel.setVisible(false);

        table.add(shopLabel).padBottom(50f);
        table.row();
        table.add(chefButton).padBottom(20f);
        table.row();
        table.add(ovenButton).padBottom(20f);
        table.row();
        table.add(cuttingButton).padBottom(20f);
        table.row();
        table.add(fryingButton).padBottom(20f);
        table.row();
        table.add(exitButton).padBottom(20f);
        table.row();
        table.add(purchaseFailedLabel);
        table.row();
    }

    public boolean getChefUnlocked(){
        return chefUnlocked;
    }

    public boolean getOvenLocked(){
        return !ovenUnlocked;
    }

    public boolean getCuttingLocked(){
        return !cuttingUnlocked;
    }

    public boolean getFryingLocked(){
        return !fryingUnlocked;
    }

    private void setPurchaseFailedLabel(String message) {
        purchaseFailedLabel.setText(message);
        purchaseFailedLabel.setVisible(true);
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
