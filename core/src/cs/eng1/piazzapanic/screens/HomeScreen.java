package cs.eng1.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.ui.ButtonManager;
import cs.eng1.piazzapanic.ui.SettingsOverlay;
import cs.eng1.piazzapanic.ui.TutorialOverlay;

public class HomeScreen implements Screen {

  private final Stage uiStage;
  SelectBox<String> customerNum;
  SelectBox<String> difficulty;

  private Preferences save_game = Gdx.app.getPreferences("Saved Game State");
  private TextButton loadButton;


  public HomeScreen(final PiazzaPanicGame game) {

    // Initialise the root UI stage and table
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

    // Initialise buttons and callbacks

    Label newGameLabel = new Label("----- Start New Game -----",
        new Label.LabelStyle(game.getFontManager().getSubHeaderFont(), null));
    
    Label scenarioModeLabel = new Label("Play with a set number of\ncustomers",
        new Label.LabelStyle(game.getFontManager().getLabelFontItalic(), null));
    scenarioModeLabel.setAlignment(Align.right);

    TextButton scenarioModeButton = game.getButtonManager()
        .createTextButton("Scenario Mode", ButtonManager.ButtonColour.BLUE);
    scenarioModeButton.sizeBy(3f);
   scenarioModeButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setGameScreen(0, customerNum.getSelected(), difficulty.getSelected());
      }
    });

    Label endlessModeLabel = new Label("Play until your reputation\npoints run out",
        new Label.LabelStyle(game.getFontManager().getLabelFontItalic(), null));
    endlessModeLabel.setAlignment(Align.right);

    TextButton endlessModeButton = game.getButtonManager()
            .createTextButton("Endless Mode", ButtonManager.ButtonColour.BLUE);
    endlessModeButton.sizeBy(3f);
    endlessModeButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setGameScreen(1, customerNum.getSelected(), difficulty.getSelected());
      }
    });

    Label loadGameLabel = new Label("-------- Load Game --------",
        new Label.LabelStyle(game.getFontManager().getSubHeaderFont(), null));

    // Load button will only be available to click if there is a save file
    loadButton = game.getButtonManager()
        .createTextButton("Load Game", ButtonManager.ButtonColour.BLUE);
    loadButton.sizeBy(3f);
    loadButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.loadGameScreen();
        game.getGameScreen().loadGame();
      }
    });
    loadButton.getStyle().disabled = new TextureRegionDrawable(new Texture("Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/blue_button_flat_disabled.png"));

    if (save_game.get().isEmpty()) {
      loadButton.setTouchable(Touchable.disabled);
      loadButton.setDisabled(true);
    } 

    Label optionsLabel = new Label("---------- Options ----------",
        new Label.LabelStyle(game.getFontManager().getSubHeaderFont(), null));

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

    // Initialise customer number and difficult selection boxes, different colour to distinguish from buttons
    SelectBox.SelectBoxStyle style = new SelectBox.SelectBoxStyle();
    style.font = game.getFontManager().getLabelFont();
    style.fontColor = Color.BLACK;
    style.background = new TextureRegionDrawable(
      new Texture(Gdx.files.internal("Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_button_flat_up.png")));
    style.scrollStyle = new ScrollPane.ScrollPaneStyle();
    List.ListStyle listStyle = new List.ListStyle();
    listStyle.font = game.getFontManager().getLabelFont();
    listStyle.fontColorSelected = Color.BLACK;
    listStyle.fontColorUnselected = Color.WHITE;
    listStyle.selection = new TextureRegionDrawable(
      new Texture(Gdx.files.internal("Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_button_flat_up.png")));
    style.listStyle = listStyle;

    customerNum = new SelectBox<String>(style);
    customerNum.setAlignment(Align.center);
    customerNum.getList().setAlignment(Align.center);
    customerNum.setItems("Customers: 1","Customers: 2","Customers: 3","Customers: 4","Customers: 5");

    difficulty  = new SelectBox<String>(style);
    difficulty.setAlignment(Align.center);
    difficulty.getList().setAlignment(Align.center);
    difficulty.setItems("Difficulty 1", "Difficulty 2", "Difficulty 3");

    // Add UI elements to the table and position them
    table.add(welcomeLabel).pad(20f).colspan(3);
    table.row().pad(5f);
    table.add(newGameLabel).colspan(3);
    table.row().padBottom(5f);
    table.add(scenarioModeLabel).padLeft(42f).padRight(10f);
    table.add(scenarioModeButton);
    table.add(customerNum).padLeft(20f);
    table.row().padBottom(10f);
    table.add(endlessModeLabel).padLeft(42f).padRight(10f);
    table.add(endlessModeButton);
    table.add(difficulty).padLeft(20f);
    table.row().pad(5f);
    table.add(loadGameLabel).colspan(3);
    table.row().padBottom(10f);
    table.add(loadButton).colspan(3);
    table.row().pad(5f);
    table.add(optionsLabel).colspan(3);
    table.row();
    table.add(tutorialButton);
    table.add(quitButton);
    table.add(settingsButton);

  }


  @Override
  public void show() {
    Gdx.input.setInputProcessor(uiStage);

    // Check if loadButton needs to be enabled when screen is shown
    save_game = Gdx.app.getPreferences("Saved Game State");
    if (!save_game.get().isEmpty()) {
      loadButton.setTouchable(Touchable.enabled);
      loadButton.setDisabled(false);
    }
  }

  @Override
  public void render(float delta) {
    // Initialise screen
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
