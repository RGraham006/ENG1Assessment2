package cs.eng1.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.ui.ButtonManager;
import cs.eng1.piazzapanic.ui.SettingsOverlay;
import cs.eng1.piazzapanic.ui.TutorialOverlay;

public class HomeScreen implements Screen {

  private final Stage uiStage;
  SelectBox<String> customerNum;



  public HomeScreen(final PiazzaPanicGame game) {
    // Initialize the root UI stage and table
    ScreenViewport uiViewport = new ScreenViewport();
    uiStage = new Stage();
    Table table = new Table();
    table.setFillParent(true);
    uiStage.addActor(table);

    final TutorialOverlay tutorialOverlay = game.getTutorialOverlay();
    tutorialOverlay.addToStage(uiStage);

    final SettingsOverlay settingsOverlay = game.getSettingsOverlay();
    settingsOverlay.addToStage(uiStage);

    Label welcomeLabel = new Label("Welcome to Piazza Panic!",
        new Label.LabelStyle(game.getFontManager().getTitleFont(), null));

    // Initialize buttons and callbacks

    TextButton scenarioModeButton = game.getButtonManager()
        .createTextButton("Scenario Mode", ButtonManager.ButtonColour.BLUE);
    scenarioModeButton.sizeBy(3f);
   scenarioModeButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setGameScreen(0, customerNum.getSelected());
      }
    });

    TextButton endlessModeButton = game.getButtonManager()
            .createTextButton("Endless Mode", ButtonManager.ButtonColour.BLUE);
    endlessModeButton.sizeBy(3f);
    endlessModeButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setGameScreen(1, customerNum.getSelected());
      }
    });

    TextButton tutorialButton = game.getButtonManager()
        .createTextButton("Tutorial", ButtonManager.ButtonColour.BLUE);
    tutorialButton.sizeBy(3f);
    tutorialButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        tutorialOverlay.show();
      }
    });

    TextButton settingsButton = game.getButtonManager()
        .createTextButton("Settings", ButtonManager.ButtonColour.BLUE);
    settingsButton.sizeBy(3f);
    settingsButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        settingsOverlay.show();
      }
    });

    TextButton quitButton = game.getButtonManager()
        .createTextButton("Exit to Desktop", ButtonManager.ButtonColour.RED);
    quitButton.sizeBy(3f);
    quitButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        Gdx.app.exit();
      }
    });

    SelectBox.SelectBoxStyle style = new SelectBox.SelectBoxStyle();
    style.font = game.getFontManager().getTitleFont();
    style.fontColor = Color.WHITE;
    style.background = new TextureRegionDrawable(new Texture(Gdx.files.internal("Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/blue_button_flat_up.png")));
    style.scrollStyle = new ScrollPane.ScrollPaneStyle();
    List.ListStyle listStyle = new List.ListStyle();
    listStyle.font = game.getFontManager().getTitleFont();
    listStyle.fontColorSelected = Color.BLACK;
    listStyle.fontColorUnselected = Color.WHITE;
    listStyle.selection = new TextureRegionDrawable(new Texture(Gdx.files.internal("Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/blue_button_flat_up.png")));
    style.listStyle = listStyle;
    customerNum = new SelectBox<String>(style);
    customerNum.setItems("Customers: 1","Customers: 2","Customers: 3","Customers: 4","Customers: 5");

    // Add UI elements to the table and position them
    table.add(welcomeLabel).padBottom(100f);
    table.row();
    table.add(scenarioModeButton).padBottom(20f);
    table.add(customerNum).padBottom(20f);
    table.row();
    table.add(endlessModeButton).padBottom(20f);
    table.row();
    table.add(tutorialButton).padBottom(20f);
    table.row();
    table.add(settingsButton).padBottom(20f);
    table.row();
    table.add(quitButton);
    table.row();
  }


  @Override
  public void show() {
    Gdx.input.setInputProcessor(uiStage);
  }

  @Override
  public void render(float delta) {
    // Initialize screen
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    uiStage.getCamera().update();

    // Render stage
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
