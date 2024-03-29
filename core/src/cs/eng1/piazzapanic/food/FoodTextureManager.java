package cs.eng1.piazzapanic.food;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import java.util.HashMap;

public class FoodTextureManager implements Disposable {

  private final HashMap<String, Texture> foodTextures;
  private final Texture notFoundImage;

  public FoodTextureManager() {
    this.foodTextures = new HashMap<>();
    this.foodTextures.put("patty_cooked",
        new Texture(Gdx.files.internal("food/original/cooked_patty.png")));
    this.foodTextures.put("patty_raw",
        new Texture(Gdx.files.internal("food/original/uncooked_patty.png")));
    this.foodTextures.put("lettuce_raw",
        new Texture(Gdx.files.internal("food/glitch/vegetable/lettuce.png")));
    this.foodTextures.put("lettuce_chopped",
        new Texture(Gdx.files.internal("food/original/lettuce_chopped.png")));
    this.foodTextures.put("tomato_raw",
        new Texture(Gdx.files.internal("food/glitch/fruit/tomato.png")));
    this.foodTextures.put("tomato_chopped",
        new Texture(Gdx.files.internal("food/original/tomato_chopped.png")));
    this.foodTextures.put("bun",
        new Texture(Gdx.files.internal("food/glitch/misc/bun.png")));
    this.foodTextures.put("burger",
        new Texture(Gdx.files.internal("food/glitch/misc/sandwich_burger_04.png")));
    this.foodTextures.put("salad",
        new Texture(Gdx.files.internal("food/glitch/misc/salad.png")));
    this.foodTextures.put("dough",
            new Texture(Gdx.files.internal("food/glitch/misc/corn_fritter.png")));
    this.foodTextures.put("dough_cooked",
            new Texture(Gdx.files.internal("food/glitch/misc/cornbread.png")));
    this.foodTextures.put("pizza_base",
        new Texture(Gdx.files.internal("food/glitch/dessert/pie.png")));
    this.foodTextures.put("pizza",
        new Texture(Gdx.files.internal("food/glitch/misc/pizza_01.png")));
    this.foodTextures.put("potato_raw",
        new Texture(Gdx.files.internal("food/glitch/vegetable/potato.png")));
    this.foodTextures.put("potato_baked",
        new Texture(Gdx.files.internal("food/glitch/misc/potato_patty.png"))); // REPLACE WITH BAKED POTATO
    this.foodTextures.put("jacket_potato",
        new Texture(Gdx.files.internal("food/glitch/misc/fry_up.png")));  // REPLACE WITH JACKET POTATO
    this.foodTextures.put("cheese",
        new Texture(Gdx.files.internal("food/glitch/dairy/cheese_01.png")));
    this.foodTextures.put("burnt",
        new Texture(Gdx.files.internal("food/glitch/spice/black_pepper.png")));
    notFoundImage = new Texture(Gdx.files.internal("badlogic.jpg"));
  }

  /**
   * @param foodType The food string to get the corresponding texture from the hashmap.
   * @return         The texture for the specified food or an image to signify that the texture does not
   * exist.
   */
  public Texture getTexture(String foodType) {
    Texture texture = foodTextures.get(foodType);
    if (texture != null) {
      return texture;
    } else {
      return notFoundImage;
    }
  }

  @Override
  public void dispose() {
    for (Texture texture : foodTextures.values()) {
      texture.dispose();
    }
    notFoundImage.dispose();
  }
}
