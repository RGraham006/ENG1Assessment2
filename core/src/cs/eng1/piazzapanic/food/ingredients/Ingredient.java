package cs.eng1.piazzapanic.food.ingredients;

import com.badlogic.gdx.graphics.Texture;

import cs.eng1.piazzapanic.food.FoodTextureManager;

public class Ingredient {

  private final String type;
  protected final FoodTextureManager textureManager;
  protected boolean isCooked = false;
  protected  boolean isChopped = false;
  protected boolean isBaked = false;

  protected boolean isBurnt = false;
  protected boolean halfCooked = false;

  public Ingredient(String type, FoodTextureManager textureManager) {
    this.type = type;
    this.textureManager = textureManager;
  }

  @Override
  public String toString() {
    String output = getType() + "_";
    if (isChopped) output += "chopped";
    else if (isCooked) output += "cooked";
    else if (isBurnt) output = "burnt";
    else output += "raw";
    return output;
  }

  /**
   * Initialise an Ingredient based on a string name.
   * @param ingredientName The name of the ingredient which can be defined from Tiled.
   * @return               The Ingredient of the type defined by the input.
   */
  public static Ingredient fromString(String ingredientName,
      FoodTextureManager textureManager) {
    switch (ingredientName) {
      case "patty":
        return new Patty(textureManager);
      case "tomato":
        return new Tomato(textureManager);
      case "lettuce":
        return new Lettuce(textureManager);
      case "dough":
        return new Dough(textureManager);
      case "potato":
        return new Potato(textureManager);
      case "cheese":
        return new Cheese(textureManager);
      default:
        return null;
    }
  }

  /**
   * Initialize an array of ingredients based on the input string.
   * @param csvIngredientNames A string containing a list of ingredient names seperated by commas
   *                           with no whitespace as defined in Tiled.
   * @return                   An array of Ingredient based on the input string.
   */
  public static Ingredient[] arrayFromString(String csvIngredientNames,
      FoodTextureManager textureManager) {
    String[] ingredientNames = csvIngredientNames.split(",");
    Ingredient[] ingredients = new Ingredient[ingredientNames.length];
    for (int i = 0; i < ingredientNames.length; i++) {
      ingredients[i] = fromString(ingredientNames[i], textureManager);
    }
    return ingredients;
  }

  public String getType() {
    return type;
  }

  public Texture getTexture() {
    return textureManager.getTexture(getType());
  }

  public void setIsCooked(boolean value) {
    isCooked = value;
  }

  public boolean getIsCooked() {
    return isCooked;
  }

  public void setIsChopped(boolean value) {
    isChopped = value;
  }

  public boolean getIsChopped() {
    return isChopped;
  }

  public FoodTextureManager getTextureManager() {
    return textureManager;
  }

  public void setHalfCooked() {
    halfCooked = true;
  }

  public boolean getIsHalfCooked() {
    return halfCooked;
  }

  public boolean getBaked(){
    return isBaked;
  }

  public void setBaked(boolean value){
    isBaked = value;
  }

  public boolean getIsBurnt() {
    return isBurnt;
  }

  public void setIsBurnt(boolean value){
    isBurnt = value;
  }
}
