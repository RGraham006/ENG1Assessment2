package cs.eng1.piazzapanic.food;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import cs.eng1.piazzapanic.food.recipes.Burger;
import cs.eng1.piazzapanic.food.recipes.JacketPotato;
import cs.eng1.piazzapanic.food.recipes.Pizza;
import cs.eng1.piazzapanic.food.recipes.Recipe;
import cs.eng1.piazzapanic.food.recipes.Salad;
import cs.eng1.piazzapanic.stations.RecipeStation;
import cs.eng1.piazzapanic.ui.UIOverlay;
import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CustomerManager {

  private int remainingCustomers;
  private float waitTime;
  private ArrayList<Recipe> customerOrders;
  private ArrayList<Float> customerWaitTimes;
  private ArrayList<ProgressBar> customerWaitProgressBars;
  private final ProgressBarStyle progressBarStyle;

  private Recipe currentOrder;
  private final List<RecipeStation> recipeStations;
  private final UIOverlay overlay;
  private final Recipe[] possibleRecipes;
  private float nextOrder = 0f;

  /**
   * Handles all orders from the customers, including recipes and wait times.
   * 
   * @param overlay        The overlay for UI elements.
   * @param textureManager The manager for any food textures.
   * @param mode           The game mode, either scenario or endless (0 or 1).
   * @param customerNum    The number of orders placed in scenario mode.
   * @param difficulty     The game difficulty.
   */
  public CustomerManager(UIOverlay overlay, FoodTextureManager textureManager, final int mode, final int customerNum,
      int difficulty) {
    this.overlay = overlay;
    this.recipeStations = new LinkedList<>();
    switch (difficulty) {
      case 3:
        waitTime = 75f;
        break;
      case 1:
        waitTime = 125f;
        break;
      default:
        waitTime = 100f;
    }

    if (mode == 0) {
      remainingCustomers = customerNum;
    }
    if (mode == 1) {
      remainingCustomers = 1;
    }

    possibleRecipes = new Recipe[] { new Burger(textureManager), new Salad(textureManager), new Pizza(textureManager),
        new JacketPotato(textureManager) };

    customerOrders = new ArrayList<>();
    customerWaitTimes = new ArrayList<>();
    customerWaitProgressBars = new ArrayList<>();

    // Progress bar styling
    progressBarStyle = new ProgressBarStyle(new TextureRegionDrawable(new Texture(
        Gdx.files.internal(
            "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/blue_button_outline_up.png"))),
        null);
    progressBarStyle.knobBefore = new TextureRegionDrawable(new Texture(Gdx.files.internal(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/blue_button_gradient_up.png")));

    progressBarStyle.background.setMinHeight(12f);
    progressBarStyle.knobBefore.setMinHeight(12f);
  }

  /**
   * Updates info relating to customer orders. Generates a new one if enough time
   * has passed and their is space.
   * Sets progress bars to new values according to remaining wait time.
   * 
   * @param delta The time passed since last call.
   */
  public void updateCustomerOrders(float delta) {
    if (remainingCustomers == 0) {
      overlay.finishGameUI(true);
    }
    if (customerOrders.size() < remainingCustomers) {
      if (nextOrder <= 0f) {
        Recipe nextRecipe = possibleRecipes[new Random().nextInt(possibleRecipes.length)];

        // Update order arrays
        customerOrders.add(nextRecipe);
        customerWaitTimes.add(waitTime);
        ProgressBar progress = new ProgressBar(0, waitTime, 0.1f, false, progressBarStyle);

        progress.setValue(0);
        progress.setVisible(true);
        customerWaitProgressBars.add(progress);

        nextOrder = new Random().nextFloat();
      }
      nextOrder -= delta;
    }

    for (int i = 0; i < customerWaitTimes.size(); i++) {
      float wait = customerWaitTimes.get(i);
      wait -= delta;
      ProgressBar progress = customerWaitProgressBars.get(i);

      progress.setValue(wait);
      if (wait <= 0) {
        removeCustomerOrder(i);
        overlay.subPoint();
      } else {
        customerWaitTimes.set(i, wait);
      }
    }

    overlay.updateRecipeUI(customerOrders, customerWaitProgressBars);
  }

  /**
   * Clears customer order arrays when a recipe is removed, including the order,
   * wait times and progress.
   * 
   * @param index The recipe location to be removed.
   */
  public void removeCustomerOrder(int index) {
    customerOrders.remove(index);
    customerWaitTimes.remove(index);
    customerWaitProgressBars.remove(index);
  }

  /**
   * Alternative method for removeCustomerOrder(int index) should the index not be
   * known.
   * Searches the orders array for the first instance of the given recipe.
   * 
   * @param recipe The recipe to be removed.
   */
  public void removeCustomerOrder(Recipe recipe) {
    for (int i = 0; i < customerOrders.size(); i++) {
      Recipe order = customerOrders.get(i);
      if (order.getType() == recipe.getType()) {
        removeCustomerOrder(i);
      }
    }
  }

  public void resetCustomerWait() {
    ProgressBar progress = customerWaitProgressBars.get(0);
    progress.setValue(0);
    customerWaitProgressBars.set(0, progress);
    customerWaitTimes.set(0, waitTime);
  }

  /**
   * Check to see if the recipe matches the currently requested order.
   * 
   * @param recipe The recipe to check against the current order.
   * @return A boolean signifying if the recipe is correct.
   */
  public boolean checkRecipe(Recipe recipe) {
    for (int i = 0; i < customerOrders.size(); i++) {
      Recipe order = customerOrders.get(i);
      if (order.getType() == recipe.getType()) {
        return true;
      }
    }
    return false;
  }

  public void setRemainingCustomers() {
    remainingCustomers--;
  }

  /**
   * If one recipe station has been updated, let all the other ones know that
   * there is a new recipe
   * to be built.
   */
  private void notifyRecipeStations() {
    for (RecipeStation recipeStation : recipeStations) {
      recipeStation.updateOrderActions();
    }
  }

  public void addRecipeStation(RecipeStation station) {
    recipeStations.add(station);
  }

  public int getRemainingCustomers() {
    return remainingCustomers;
  }

  public ArrayList<Recipe> getCustomerOrders() {
    return customerOrders;
  }

  public ProgressBar getFirstProgressBar() {
    return this.customerWaitProgressBars.get(0);
  }
}
