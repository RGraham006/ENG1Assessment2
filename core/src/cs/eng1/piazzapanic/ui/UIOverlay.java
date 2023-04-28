package cs.eng1.piazzapanic.ui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.food.recipes.Recipe;
import cs.eng1.piazzapanic.ui.ButtonManager.ButtonColour;

public class UIOverlay {

  private final Table table;
  private final ImageButton homeButton;
  private final ImageButton powerupButton;
  private final ImageButton shopButton;
  private final Stack chefDisplay;
  private final Image chefImage;
  private final Image ingredientImagesBG;
  private final VerticalGroup ingredientImages;
  private final TextureRegionDrawable removeBtnDrawable;
  private final Table recipeImages;
  private final Timer timer;
  private final Label orderLabel;
  private final Label resultLabel;
  private final Timer resultTimer;
  private final PiazzaPanicGame game;
  private final Money money;
  private final ReputationPoint points;

  private final Stage uiStage;

  private int moneyToAdd = 100; // Add money whenever order is complete

  private boolean isGameFinished = false;


  public UIOverlay(Stage uiStage, final PiazzaPanicGame game) {
    this.game = game;
    this.uiStage = uiStage;

    // Initialise table
     table = new Table();
    table.setFillParent(true);
    table.center().top().pad(15f);
    this.uiStage.addActor(table);

    // Initialise UI for showing current chef
    chefDisplay = new Stack();
    chefDisplay.add(new Image(new Texture(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_button_square_gradient_down.png")));
    chefImage = new Image();
    chefImage.setScaling(Scaling.fit);
    chefDisplay.add(chefImage);

    // Initialise UI for showing current chef's ingredient stack
    Stack ingredientStackDisplay = new Stack();
    ingredientImagesBG = new Image(new Texture(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_button_square_gradient_down.png"));
    ingredientImagesBG.setVisible(false);
    ingredientStackDisplay.add(ingredientImagesBG);
    ingredientImages = new VerticalGroup();
    ingredientImages.padBottom(10f);
    ingredientStackDisplay.add(ingredientImages);

    // Initialise the timer
    LabelStyle timerStyle = new Label.LabelStyle(game.getFontManager().getHeaderFont(), null);
    timerStyle.background = new TextureRegionDrawable(new Texture(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/green_button_gradient_down.png"));
    timer = new Timer(timerStyle);
    timer.setAlignment(Align.center);

    // Initialise the Reputation Points
    LabelStyle repPointsStyle = new Label.LabelStyle(game.getFontManager().getHeaderFont(), null);
    repPointsStyle.background = new TextureRegionDrawable(new Texture(
      "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/green_button_gradient_down.png"));
    points = new ReputationPoint(repPointsStyle);
    points.setAlignment(Align.center);

    //  Initialise money
    LabelStyle moneyStyle = new Label.LabelStyle(game.getFontManager().getHeaderFont(), null);
    moneyStyle.background = new TextureRegionDrawable(new Texture(
      "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/green_button_gradient_down.png"));
    money = new Money(moneyStyle);
    money.setAlignment(Align.center);
    
    //Initialise the Shop Button
    shopButton = game.getButtonManager().createImageButton(new TextureRegionDrawable(
            new Texture(
                    Gdx.files.internal("Kenney-Game-Assets-1/2D assets/Game Icons/PNG/White/2x/shoppingCart.png"))),
            ButtonColour.BLUE, -1.5f);

    shopButton.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y){
        game.loadShopScreen();
      }
    });
        
    // Initialise the powerup button
    powerupButton = game.getButtonManager().createImageButton(new TextureRegionDrawable(
            new Texture(
                Gdx.files.internal("Kenney-Game-Assets-1/2D assets/Game Icons/PNG/White/2x/star.png"))),
        ButtonManager.ButtonColour.BLUE, -1.5f);

    powerupButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.getGameScreen().getPowerupManager().generatePowerup();;
      }
    });

    // Initialise the home button, game saves when button is pressed
    homeButton = game.getButtonManager().createImageButton(new TextureRegionDrawable(
            new Texture(
                Gdx.files.internal("Kenney-Game-Assets-1/2D assets/Game Icons/PNG/White/2x/home.png"))),
        ButtonManager.ButtonColour.BLUE, -1.5f);

    homeButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.getGameScreen().saveGame(); 
        game.loadHomeScreen();
      }
    });
    removeBtnDrawable = new TextureRegionDrawable(
        new Texture("Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_crossWhite.png"));

    recipeImages = new Table();
    recipeImages.setBackground(new TextureRegionDrawable(
      new Texture("Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_button_square_gradient_down.png")));

    // Initialise counter for showing remaining recipes
    LabelStyle orderStyle = new LabelStyle(game.getFontManager().getSubHeaderFont(), Color.BLACK);
    orderLabel = new Label("Orders", orderStyle);

    // Initialise winning label
    LabelStyle labelStyle = new Label.LabelStyle(game.getFontManager().getHeaderFont(), Color.BLACK);
    resultLabel = new Label(null, labelStyle);
    resultTimer = new Timer(labelStyle);

    // Add everything
    table.add(powerupButton).left().width(Value.percentWidth(.08f, table))
        .height(Value.percentHeight(.05f, table));
    table.add().expandX();
    table.add(homeButton).right().width(Value.percentWidth(.08f, table))
        .height(Value.percentHeight(.05f, table));
    table.row().padTop(Value.percentWidth(.01f, table));
    table.add(chefDisplay).left().width(Value.percentWidth(.03f, table))
        .height(Value.percentHeight(.05f, table)).padLeft(Value.percentWidth(.025f, table));
    table.add().expandX();
    table.add(shopButton).right().width(Value.percentWidth(.08f, table))
        .height(Value.percentHeight(.05f, table));
    table.row().padTop(10f).expand();
    table.add(ingredientStackDisplay).left().top().width(Value.percentWidth(.08f, table));
    table.add(resultLabel).bottom().padTop(10f);
    table.add(recipeImages).right().top().width(Value.percentWidth(.08f, table));
    table.row();
    table.add(resultTimer).top().padTop(10f).expand().colspan(3);
    table.row();
    table.add(money).bottom().width(Value.percentWidth(.3f, table))
        .height(Value.percentHeight(.06f, table));
    table.add(timer).bottom().expandX().width(Value.percentWidth(.2f, table))
        .height(Value.percentHeight(.06f, table));
    table.add(points).bottom().width(Value.percentWidth(.3f, table))
        .height(Value.percentHeight(.06f, table));
  }

  /**
   * Reset values and UI to be in their default state.
   */
  public void init() {
    timer.start();
    resultLabel.setVisible(false);
    resultTimer.setVisible(false);
    isGameFinished = false;
    updateChefUI(null);
  }

  /**
   * Increase money by chosen amount once order has been submitted.
   * If the money has been doubled, this is reset for later additions.
   */
  public void updateMoney() {
    if (moneyToAdd == 200) { 
      money.addMoney(moneyToAdd);
      resetMoneyToAdd();
    }
    else {
      money.addMoney(moneyToAdd);
    }
  }

  public Money getMoney() {
    return money;
  }

  /**
   * Double money due to the powerup.
   */
  public void doubleMoneyToAdd() {
    moneyToAdd = 200;
  }

  public void resetMoneyToAdd() {
    moneyToAdd = 100;
  }
  public int getReputation(){
    return points.getPoints();
  }
  public void addPoint() {
    points.addRepPoint();
  }

  public void subPoint() {
    points.subRepPoint();
    if (points.getPoints() <= 0) {
      finishGameUI("lose");
    }
  }

  public float getTime(){
    return timer.getTime();
  }

  public void setTime(float time){
    timer.setTime(time);
  }

  public void setCustomPoints(int p){
    points.setCustomPoints(p);
  }

  /**
   * Show the image of the currently selected chef as well as have the stack of ingredients
   * currently held by the chef.
   * @param chef The chef that is currently selected for which to show the UI.
   */
  public void updateChefUI(final Chef chef) {
    if (chef == null) {
      chefImage.setDrawable(null);
      ingredientImages.clearChildren();
      ingredientImagesBG.setVisible(false);
      return;
    }
    Texture texture = chef.getTexture();
    chefImage.setDrawable(new TextureRegionDrawable(texture));

    ingredientImages.clearChildren();
    ingredientImages.padTop(10f);
    for (Ingredient ingredient : chef.getStack()) {
      Image image = new Image(ingredient.getTexture());
      image.getDrawable().setMinHeight(chefDisplay.getHeight());
      image.getDrawable().setMinWidth(chefDisplay.getWidth());
      ingredientImages.addActor(image);
    }
    if (!chef.getStack().isEmpty()) {
      ImageButton btn = game.getButtonManager().createImageButton(removeBtnDrawable,
          ButtonColour.RED, -1.5f);
      btn.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
          chef.placeIngredient();
        }
      });
      ingredientImages.addActor(btn);
    }
    ingredientImagesBG.setVisible(!chef.getStack().isEmpty());

  }


  /**
   * End game and output the appropriate label stating the player's result.
   * Everything on the UI is cleared but the home button.
   * @param outcome The result of the game, either "win" or "lose".
   */
  public void finishGameUI(String outcome) {

    // Remove all elements but the home button
    table.removeActor(recipeImages);
    table.removeActor(chefDisplay);
    table.removeActor(powerupButton);
    table.removeActor(shopButton);
    table.removeActor(ingredientImages);
    table.removeActor(money);
    table.removeActor(timer);
    table.removeActor(points);

    if (outcome == "win") {
      resultLabel.setText("Congratulations!\nYour final time was:");
      resultTimer.setTime(timer.getTime());
      resultTimer.setVisible(true);
    } else {
      resultLabel.setText("Out of reputation points!\nGame is over.");
    }
    timer.stop();

    resultLabel.setVisible(true);
 
    isGameFinished = true;

  }

  /**
   * Show the current requested recipe that the player needs to make, the ingredients for that, and
   * the number of remaining recipes.
   * @param recipes      The recipes to display.
   * @param progressBars The order waiting times to display.
   */
  public void updateRecipeUI(ArrayList<Recipe> recipes, ArrayList<ProgressBar> progressBars) {
    
    recipeImages.clearChildren();
    recipeImages.add(orderLabel).align(Align.center).padTop(Value.percentHeight(.1f, recipeImages))
      .padBottom(Value.percentHeight(.05f, recipeImages));
    recipeImages.row();

    if (recipes.isEmpty()) {
      recipeImages.setVisible(false);
    } 
    else { 
      for (int i = 0; i < recipes.size(); i++) {
        ProgressBar progress = progressBars.get(i);
        recipeImages.add(progress).width(Value.percentWidth(.8f, recipeImages));
        recipeImages.row();

        Image recipeImage = new Image(recipes.get(i).getTexture());
        recipeImages.add(recipeImage).padBottom(Value.percentHeight(.1f, recipeImages));
        recipeImages.row();
      }
      recipeImages.setVisible(true);
    } 
  }

  public boolean isGameFinished() {
    return isGameFinished;
  }
}
