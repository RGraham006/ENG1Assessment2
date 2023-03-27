package cs.eng1.piazzapanic.food;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Queue;
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

  private int remainingCustomers = 5;
  private final float waitTime = 100f;
  private ArrayList<Recipe> customerOrders;
  private ArrayList<Float> customerWaitTimes;
  private ArrayList<ProgressBar> customerWaitProgressBars;
  private final ProgressBarStyle progressBarStyle;
  
  private Recipe currentOrder;
  private final List<RecipeStation> recipeStations;
  private final UIOverlay overlay;
  private final Recipe[] possibleRecipes;
  private float nextOrder = 0f;

  public CustomerManager(UIOverlay overlay, FoodTextureManager textureManager) {
    this.overlay = overlay;
    this.recipeStations = new LinkedList<>();
    
    possibleRecipes = new Recipe[] {new Burger(textureManager), new Salad(textureManager), new Pizza(textureManager), new JacketPotato(textureManager)};

    customerOrders = new ArrayList<>();
    customerWaitTimes = new ArrayList<>();
    customerWaitProgressBars = new ArrayList<>();

    // Progress bar styling
    progressBarStyle = new ProgressBarStyle(new TextureRegionDrawable(new Texture(
        Gdx.files.internal(
            "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/blue_button_outline_up.png"))), null);
    progressBarStyle.knobBefore = new TextureRegionDrawable(new Texture(Gdx.files.internal(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/blue_button_gradient_up.png")));

    progressBarStyle.background.setMinHeight(10f);
    // progressBarStyle.background.setMinWidth(10f);

    progressBarStyle.knobBefore.setMinHeight(10f);
    // progressBarStyle.knobBefore.setMinWidth(10f);
  }

  /**
   * Updates info relating to customer orders. Generates a new one if enough time has passed and their is space.
   * Sets progress bars to new values according to remaining wait time.
   * @param delta The time passed since last call
   */
  public void updateCustomerOrders(float delta) {
    if (remainingCustomers == 0) {
      overlay.finishGameUI();
    }
    if (customerOrders.size() < remainingCustomers) {
      if (nextOrder <= 0f) {
        Recipe nextRecipe = possibleRecipes[new Random().nextInt(possibleRecipes.length)];
        
        // Update order arrays
        customerOrders.add(nextRecipe);
        customerWaitTimes.add(waitTime);
        ProgressBar progress = new ProgressBar(0, waitTime, 0.1f, false, progressBarStyle);
        
        progress.setDebug(true);
        progress.setValue(0);
        progress.setVisible(true);
        customerWaitProgressBars.add(progress);

        nextOrder = new Random().nextFloat(20f);
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
      }
      else {
        customerWaitTimes.set(i, wait);
      }
    }

    overlay.updateRecipeUI(customerOrders, customerWaitProgressBars);
  }

  public void removeCustomerOrder(int index) {
    customerOrders.remove(index);
    customerWaitTimes.remove(index);
    customerWaitProgressBars.remove(index);
  }

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
   * @return a boolean signifying if the recipe is correct.
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

  /**
   * Complete the current order nad move on to the next one. Then update the UI. If all the recipes
   * are completed, then show the winning UI.
   */
  public void nextRecipe() {
    if (customerOrders.isEmpty()) {
      currentOrder = null;
    } else {
      currentOrder = customerOrders.get(customerOrders.size() - 1);
    }
    notifyRecipeStations();
    overlay.updateRecipeUI(customerOrders, customerWaitProgressBars);
    if (currentOrder == null) {
      overlay.finishGameUI();
    }
  }

  public void setRemainingCustomers() {
    remainingCustomers--;
  }

  /**
   * If one recipe station has been updated, let all the other ones know that there is a new recipe
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
}
