package cs.eng1.piazzapanic.food.ingredients;

import com.badlogic.gdx.graphics.Texture;

import cs.eng1.piazzapanic.food.FoodTextureManager;

public class Potato extends Ingredient {

    public Potato(FoodTextureManager textureManager){
        super("potato", textureManager);
    }

    /**
     * Get texture based on whether potato has been baked.
     * @return The texture to display.
     */
    @Override
    public Texture getTexture() {
        String name = getType() + "_";
        if (isBaked && !isBurnt) {
            name += "baked";
        }else if (isBurnt){
            name = "burnt";
        }
        else {
            name += "raw";
        }
        return textureManager.getTexture(name);
    }

}
