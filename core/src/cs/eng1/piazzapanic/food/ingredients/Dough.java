package cs.eng1.piazzapanic.food.ingredients;
import com.badlogic.gdx.graphics.Texture;
import cs.eng1.piazzapanic.food.FoodTextureManager;

public class Dough extends Ingredient{
    
    public Dough(FoodTextureManager textureManager) {
        super("dough", textureManager);
      }

    @Override
    public Texture getTexture() {
     return textureManager.getTexture(getType());
    }

    @Override
    public String getType() {
      if (isBaked) {
        return "pizza_base";
      }
      else if (isCooked) {
        return "bun";
      }
      else if (isBurnt){
          return "burnt";
      }
      else {
        return "dough";
      }
    }
}
