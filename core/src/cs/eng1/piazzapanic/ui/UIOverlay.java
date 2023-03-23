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
import cs.eng1.piazzapanic.ui.Money;

public class UIOverlay {

  private final Stack chefDisplay;
  private final Image chefImage;
  private final Image ingredientImagesBG;
  private final VerticalGroup ingredientImages;
  private final TextureRegionDrawable removeBtnDrawable;
  private final Image recipeImagesBG;
  private final VerticalGroup recipeImages;
  private final Timer timer;
  private final Label orderLabel;
  private final Label resultLabel;
  private final Timer resultTimer;
  private final PiazzaPanicGame game;
  private final Money money;

  private ReputationPoint points;


  public UIOverlay(Stage uiStage, final PiazzaPanicGame game) {
    this.game = game;

    // Initialize table
    Table table = new Table();
    table.setFillParent(true);
    table.center().top().pad(15f);
    uiStage.addActor(table);

    // Initialize UI for showing current chef
    chefDisplay = new Stack();
    chefDisplay.add(new Image(new Texture(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_button_square_gradient_down.png")));
    chefImage = new Image();
    chefImage.setScaling(Scaling.fit);
    chefDisplay.add(chefImage);

    // Initialize UI for showing current chef's ingredient stack
    Stack ingredientStackDisplay = new Stack();
    ingredientImagesBG = new Image(new Texture(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_button_square_gradient_down.png"));
    ingredientImagesBG.setVisible(false);
    ingredientStackDisplay.add(ingredientImagesBG);
    ingredientImages = new VerticalGroup();
    ingredientImages.padBottom(10f);
    ingredientStackDisplay.add(ingredientImages);

    // Initialize the timer
    LabelStyle timerStyle = new Label.LabelStyle(game.getFontManager().getHeaderFont(), null);
    timerStyle.background = new TextureRegionDrawable(new Texture(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/green_button_gradient_down.png"));
    timer = new Timer(timerStyle);
    timer.setAlignment(Align.center);

    // Initialize the Reputation Points
    LabelStyle repPointsStyle = new Label.LabelStyle(game.getFontManager().getHeaderFont(), null);
    repPointsStyle.background = new TextureRegionDrawable(new Texture(
      "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/green_button_gradient_down.png"));
    points = new ReputationPoint(repPointsStyle);
    points.setAlignment(Align.center);

    //  Initialize money
    LabelStyle moneyStyle = new Label.LabelStyle(game.getFontManager().getHeaderFont(), null);
    moneyStyle.background = new TextureRegionDrawable(new Texture(
      "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/green_button_gradient_down.png"));
    money = new Money(moneyStyle);
    money.setAlignment(Align.center);
    


    // Initialize the home button
    ImageButton homeButton = game.getButtonManager().createImageButton(new TextureRegionDrawable(
            new Texture(
                Gdx.files.internal("Kenney-Game-Assets-1/2D assets/Game Icons/PNG/White/1x/home.png"))),
        ButtonManager.ButtonColour.BLUE, -1.5f);

    homeButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.loadHomeScreen();
      }
    });
    removeBtnDrawable = new TextureRegionDrawable(
        new Texture("Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_crossWhite.png"));

    // Initialize the UI to display the currently requested recipe
    Stack recipeDisplay = new Stack();
    recipeImagesBG = new Image(new Texture(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_button_square_gradient_down.png"));
    recipeImagesBG.setVisible(false);
    recipeDisplay.add(recipeImagesBG);
    recipeImages = new VerticalGroup();
    recipeImages.padTop(20f);
    recipeImages.padBottom(20f);
    recipeImages.setWidth(80f);

    recipeDisplay.add(recipeImages);

    // Initialize counter for showing remaining recipes
    LabelStyle orderStyle = new LabelStyle(game.getFontManager().getSubHeaderFont(), Color.BLACK);
    orderLabel = new Label("Orders", orderStyle);

    // Initialize winning label
    LabelStyle labelStyle = new Label.LabelStyle(game.getFontManager().getTitleFont(), null);
    resultLabel = new Label("Congratulations! Your final time was:", labelStyle);
    resultLabel.setVisible(false);
    resultTimer = new Timer(labelStyle);
    resultTimer.setVisible(false);

    // Add everything

    table.add(chefDisplay).left().width(40f).height(40f);
    table.add().expandX();
    table.add(homeButton).right().width(80f).height(40f);
    table.row().padTop(10f).expand();
    table.add(ingredientStackDisplay).left().top().width(40f);
    table.add().expandX().width(250f);
    table.add(recipeDisplay).right().top().width(80f);
    table.row();
    table.add(resultLabel).colspan(3);
    table.row();
    table.add(resultTimer).colspan(3);
    table.row();
    table.add(money).bottom().width(300f).height(30f);
    table.add(timer).bottom().expandX().width(200f).height(30f);
    table.add(points).bottom().width(300f).height(30f);
    table.setDebug(true);
  }

  /**
   * Reset values and UI to be in their default state.
   */
  public void init() {
    timer.reset();
    timer.start();
    resultLabel.setVisible(false);
    resultTimer.setVisible(false);
    updateChefUI(null);
  }

  public void updateMoney() {
    money.addMoney(100);
    money.setText("Money: " + money.getMoney());
  }

  /**
   * Show the image of the currently selected chef as well as have the stack of ingredients
   * currently held by the chef.
   *
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
   * Show the label displaying that the game has finished along with the time it took to complete.
   */
  public void finishGameUI() {
    resultLabel.setVisible(true);
    resultTimer.setTime(timer.getTime());
    resultTimer.setVisible(true);
    timer.stop();
  }

  /**
   * Show the current requested recipe that the player needs to make, the ingredients for that, and
   * the number of remaining recipes.
   *
   * @param recipes The recipes to display.
   * @param progressBars The order waiting times to display.
   */
  public void updateRecipeUI(ArrayList<Recipe> recipes, ArrayList<ProgressBar> progressBars) {
    
    recipeImages.clearChildren();
    recipeImages.addActor(orderLabel);
    if (recipes.isEmpty()) {
      recipeImagesBG.setVisible(false);
    } 
    else { 
      for (int i = 0; i < recipes.size(); i++) {
        ProgressBar progress = progressBars.get(i);
        recipeImages.addActor(progress);

        Image recipeImage = new Image(recipes.get(i).getTexture());
        recipeImages.addActor(recipeImage);
      }
      recipeImagesBG.setVisible(true);
    } 
  }
}
