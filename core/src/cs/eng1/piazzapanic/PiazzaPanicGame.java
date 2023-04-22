package cs.eng1.piazzapanic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import cs.eng1.piazzapanic.screens.GameScreen;
import cs.eng1.piazzapanic.screens.HomeScreen;
import cs.eng1.piazzapanic.screens.ShopScreen;
import cs.eng1.piazzapanic.ui.*;

public class PiazzaPanicGame extends Game {

  private FontManager fontManager;
  private ButtonManager buttonManager;
  private GameScreen gameScreen;
  private HomeScreen homeScreen;
  public ShopScreen shopScreen;
  private TutorialOverlay tutorialOverlay;
  private SettingsOverlay settingsOverlay;
  public Money money;

  @Override
  public void create() {
    fontManager = new FontManager();
    buttonManager = new ButtonManager(fontManager);
    tutorialOverlay = new TutorialOverlay(this);
    settingsOverlay = new SettingsOverlay(this);
    shopScreen = new ShopScreen(this);
    homeScreen = new HomeScreen(this);
    money = new Money(new Label.LabelStyle(getFontManager().getHeaderFont(), null));
    loadHomeScreen();
  }

  @Override
  public void dispose() {
    if (gameScreen != null) {
      gameScreen.dispose();
    }
    if (homeScreen != null) {
      homeScreen.dispose();
    }
    if (shopScreen != null) {
      shopScreen.dispose();
    }
    fontManager.dispose();
    buttonManager.dispose();
  }

  public void loadHomeScreen() {
    setScreen(homeScreen);
  }

  public void setGameScreen(int mode, String customerNum, String diffStr) {
    int customers;
    int difficulty;
    switch(customerNum){
      case "Customers: 1":
        customers = 1;
        break;
      case "Customers: 2":
        customers = 2;
        break;
      case "Customers: 3":
        customers = 3;
        break;
      case "Customers: 4":
        customers = 4;
        break;
      default:
        customers  = 5;
        break;
    }

    switch(diffStr){
      case "Difficulty 1":
        difficulty = 1;
        break;
      case "Difficulty 3":
        difficulty = 3;
        break;
      default:
        difficulty = 2;
        break;
    }

    if (gameScreen == null) {
      gameScreen = new GameScreen(this, mode, customers, difficulty);
      loadGameScreen();
    }

  }

  public void loadGameScreen() {

    setScreen(gameScreen);
  }

  public void loadShopScreen() {
    setScreen(shopScreen);
  }

  public Money getMoney() {
    return gameScreen.getMoney();
  }

  public GameScreen getGameScreen() {
    return gameScreen;
  }

  public TutorialOverlay getTutorialOverlay() {
    return tutorialOverlay;
  }

  public SettingsOverlay getSettingsOverlay() {
    return settingsOverlay;
  }

  public FontManager getFontManager() {
    return fontManager;
  }

  public ButtonManager getButtonManager() {
    return buttonManager;
  }

}

