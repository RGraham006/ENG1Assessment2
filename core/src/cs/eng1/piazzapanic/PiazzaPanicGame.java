package cs.eng1.piazzapanic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import cs.eng1.piazzapanic.screens.GameScreen;
import cs.eng1.piazzapanic.screens.HomeScreen;
import cs.eng1.piazzapanic.screens.ShopScreen;
import cs.eng1.piazzapanic.screens.WinLossScreen;
import cs.eng1.piazzapanic.ui.*;

public class PiazzaPanicGame extends Game {

  private FontManager fontManager;
  private ButtonManager buttonManager;
  private GameScreen gameScreen;
  private HomeScreen homeScreen;
  public ShopScreen shopScreen;
  private WinLossScreen winLossScreen;
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
    winLossScreen = new WinLossScreen(this);
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
    if (winLossScreen != null) {
      winLossScreen.dispose();
    }
    fontManager.dispose();
    buttonManager.dispose();
  }

  public void loadHomeScreen() {
    setScreen(homeScreen);
  }

  /**
   * Create a game screen with the specified parameters entered on the home screen.
   * This new game screen replaces any previous game screen.
   * @param mode The game mode, either scenario or endless (0 or 1).
   * @param customerNum The number of orders to be placed in scenario mode.
   * @param diffStr The selected difficulty.
   */
  public void setGameScreen(int mode, String customerNum, String diffStr) {

    if (gameScreen != null) {
      gameScreen.dispose();
    }

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

    gameScreen = new GameScreen(this, mode, customers, difficulty);
    loadGameScreen();
  }

  public void loadGameScreen() {
    if (gameScreen == null) { // Only true if the loadGame btn is pressed, so values will be overwritten
      gameScreen = new GameScreen(this, 0, 1, 1);
    }
    setScreen(gameScreen);
  }

  public void loadShopScreen() {
    setScreen(shopScreen);
  }

  public void loadWinLossScreen(boolean win, Timer timer) {
    winLossScreen.finishGame(win, timer);
    setScreen(winLossScreen);
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

