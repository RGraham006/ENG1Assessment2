package cs.eng1.piazzapanic.food.ingredients;

import com.badlogic.gdx.graphics.Texture;
import cs.eng1.piazzapanic.food.FoodTextureManager;

public class Patty extends Ingredient {

  public Patty(FoodTextureManager textureManager) {
    super("patty", textureManager);
  }

  /**
   * Get the texture based on whether the patty has been cooked.
   *
   * @return the texture to display.
   */
  @Override
  public Texture getTexture() {
    String name = getType() + "_";
    if (isCooked && !isBurnt) {
      return textureManager.getTexture(name += "cooked");
    } else if (isBurnt){
      return textureManager.getTexture("burnt");
    } else {
      return textureManager.getTexture(name += "raw");
    }
  }
}
