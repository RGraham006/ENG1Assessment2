package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.food.recipes.Burger;
import cs.eng1.piazzapanic.food.recipes.JacketPotato;
import cs.eng1.piazzapanic.food.recipes.Pizza;
import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.recipes.Recipe;
import cs.eng1.piazzapanic.food.recipes.Salad;
import cs.eng1.piazzapanic.stations.StationAction.ActionType;
import cs.eng1.piazzapanic.ui.StationActionUI.ActionAlignment;
import cs.eng1.piazzapanic.ui.StationUIController;
import cs.eng1.piazzapanic.ui.UIOverlay;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * The RecipeStation class is a station representing the place in the kitchen where you combine ingredients 
 * to create food. The food can then be served to the customer via the station.
 */
public class RecipeStation extends Station {
  private final FoodTextureManager textureManager;
  private final CustomerManager customerManager;

  protected int bunCount = 0;
  protected int pattyCount = 0;
  protected int lettuceCount = 0;
  protected int tomatoCount = 0;
  protected int pizzaBaseCount = 0;
  protected int potatoCount = 0;
  protected int cheeseCount = 0;

  private final int mode;
  private Recipe completedRecipe = null;

  private final UIOverlay uiOverlay;
  private final PiazzaPanicGame game;

  /**
   * The constructor method for the class.
   * @param id                    The unique identifier of the station.
   * @param textureRegion         The rectangular area of the texture.
   * @param stationUIController   The controller from which we can get show and hide the action
   *                              buttons belonging to the station.
   * @param alignment             Dictates where the action buttons are shown.
   * @param textureManager        The controller from which we can get information on what texture
   *                              each ingredient should have.
   * @param customerManager       The controller from which we can get information on what food
   *                              needs to be served.
   */
  public RecipeStation(int id, TextureRegion textureRegion, StationUIController stationUIController,
      ActionAlignment alignment, FoodTextureManager textureManager,
      CustomerManager customerManager, UIOverlay uiOverlay, int mode, PiazzaPanicGame game) {

    super(id, textureRegion, stationUIController, alignment);
    this.textureManager = textureManager;
    this.customerManager = customerManager;
    this.uiOverlay = uiOverlay;

    this.game = game;
    this.mode = mode;



  }

  @Override
  public void reset() {
    bunCount = 0;
    pattyCount = 0;
    lettuceCount = 0;
    tomatoCount = 0;
    pizzaBaseCount = 0;
    potatoCount = 0;
    cheeseCount = 0;
    completedRecipe = null;
    super.reset();
  }

  /**
   * Obtains the actions that can be currently performed depending on the states of the station
   * itself and the selected chef.
   * @return actionTypes - the list of actions the station can currently perform.
   */
  @Override
  public List<ActionType> getActionTypes() {
    LinkedList<ActionType> actionTypes = new LinkedList<>();
    if (nearbyChef != null) {
      if (!nearbyChef.getStack().isEmpty()) {
        Ingredient checkItem = nearbyChef.getStack().peek();
        if (checkItem.getIsChopped() || checkItem.getIsCooked() || checkItem.getBaked() || Objects.equals(
            checkItem.getType(), "cheese")) {
          // If a chef is nearby and is carrying at least one ingredient
          // and the top ingredient is cooked, chopped or a bun then display the action
          actionTypes.add(ActionType.PLACE_INGREDIENT);
        }
      }
      System.out.println(completedRecipe);
      if (completedRecipe == null) {
        if (pattyCount >= 1 && bunCount >= 1 && nearbyChef.getStack().hasSpace()) {
          actionTypes.add(ActionType.MAKE_BURGER);
        }
        if (tomatoCount >= 1 && lettuceCount >= 1 && nearbyChef.getStack().hasSpace()) {
          actionTypes.add(ActionType.MAKE_SALAD);
        }
        if (tomatoCount >= 1 && pizzaBaseCount >= 1 && cheeseCount >= 1 && nearbyChef.getStack().hasSpace()) {
          actionTypes.add(ActionType.MAKE_PIZZA);
        }
        if (potatoCount >= 1 && cheeseCount >=1 && nearbyChef.getStack().hasSpace()) {
          actionTypes.add(ActionType.MAKE_JACKET_POTATO);
        }
      } else if (customerManager.checkRecipe(completedRecipe)) {
        actionTypes.add(ActionType.SUBMIT_ORDER);
      }
    }
    return actionTypes;
  }

  /**
   * Given an action, the station should attempt to do that action based on the chef that is nearby
   * or what ingredient(s) are currently on the station.
   * @param action The action that needs to be done by this station if it can.
   */
  @Override
  public void doStationAction(ActionType action) {
    switch (action) {
      case PLACE_INGREDIENT:
        Ingredient topItem = nearbyChef.getStack().peek();
        switch (topItem.getType()) {
          case "patty":
            nearbyChef.placeIngredient();
            pattyCount += 1;
            break;
          case "tomato":
            nearbyChef.placeIngredient();
            tomatoCount += 1;
            break;
          case "lettuce":
            nearbyChef.placeIngredient();
            lettuceCount += 1;
            break;
          case "bun":
            nearbyChef.placeIngredient();
            bunCount += 1;
            break;
          case "pizza_base":
            nearbyChef.placeIngredient();
            pizzaBaseCount += 1;
            break;
          case "potato":
            nearbyChef.placeIngredient();
            potatoCount += 1;
            break;
          case "cheese":
            nearbyChef.placeIngredient();
            cheeseCount += 1;
            break;
        }

        break;
      case MAKE_BURGER:
        completedRecipe = new Burger(textureManager);
        pattyCount -= 1;
        bunCount -= 1;
        break;

      case MAKE_SALAD:
        completedRecipe = new Salad(textureManager);
        tomatoCount -= 1;
        lettuceCount -= 1;
        break;

      case MAKE_PIZZA:
        completedRecipe = new Pizza(textureManager);
        tomatoCount -= 1;
        pizzaBaseCount -= 1;
        cheeseCount -= 1;
        break;

      case MAKE_JACKET_POTATO:
        completedRecipe = new JacketPotato(textureManager);
        potatoCount -= 1;
        cheeseCount -= 1;
        break;

      case SUBMIT_ORDER:
        if (completedRecipe != null) {
          if (customerManager.checkRecipe(completedRecipe)) {
            customerManager.removeCustomerOrder(completedRecipe);
            if (mode == 0){
              customerManager.setRemainingCustomers();
            }
            game.money.addMoney(100);
            uiOverlay.updateMoney();

            completedRecipe = null;
          }
        }
        break;
    }
    uiController.showActions(this, getActionTypes());
  }

  /**
   * Displays ingredients that have been placed on the station.
   * @param batch       Used to display a 2D texture.
   * @param parentAlpha The parent alpha, to be multiplied with this actor's alpha, allowing the parent's 
   *                    alpha to affect all children.
   */
  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    if (bunCount > 0) {
      drawFoodTexture(batch, textureManager.getTexture("bun"));
    }
    if (pattyCount > 0) {
      drawFoodTexture(batch, textureManager.getTexture("patty_cooked"));
    }
    if (lettuceCount > 0) {
      drawFoodTexture(batch, textureManager.getTexture("lettuce_chopped"));
    }
    if (tomatoCount > 0) {
      drawFoodTexture(batch, textureManager.getTexture("tomato_chopped"));
    }
    if (pizzaBaseCount > 0) {
      drawFoodTexture(batch, textureManager.getTexture("pizza_base"));
    }
    if (potatoCount > 0) {
      drawFoodTexture(batch, textureManager.getTexture("potato_baked"));
    }
    if (cheeseCount > 0) {
      drawFoodTexture(batch, textureManager.getTexture("cheese"));
    }
    if (completedRecipe != null) {
      drawFoodTexture(batch, completedRecipe.getTexture());
    }
  }

  public Recipe returnCRecipe(){
    return completedRecipe;
  }

  public int returnCount(String name){
    if(name == "bun"){
      return bunCount;
    }
    if(name == "patty"){
      return pattyCount;
    }
    if(name == "lettuce"){
      return lettuceCount;
    }
    if(name == "tomato"){
      return tomatoCount;
    }
    if(name == "pizza"){
      return pizzaBaseCount;
    }
    if(name == "potato"){
      return potatoCount;
    }
    if(name == "cheese"){
      return cheeseCount;
    }
    else{
      return 0;
    }
  }

  /**
   * Updates the current available actions based on the new customer's order.
   */
  public void updateOrderActions() {
    uiController.showActions(this, getActionTypes());
  }
}
