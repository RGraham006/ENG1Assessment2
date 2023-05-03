package cs.eng1.piazzapanic.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.stations.RecipeStation;
import cs.eng1.piazzapanic.stations.Station;
import cs.eng1.piazzapanic.stations.StationAction;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;
import cs.eng1.piazzapanic.ui.UIOverlay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class RecipeStationTests {

    public RecipeStation initialiseRecipeStation(){
        return new RecipeStation(0, Mockito.mock(TextureRegion.class), Mockito.mock(StationUIController.class), Mockito.mock(StationActionUI.class).getActionAlignment(), Mockito.mock(FoodTextureManager.class), Mockito.mock(CustomerManager.class), Mockito.mock(UIOverlay.class), 0, Mockito.mock(PiazzaPanicGame.class));
    }
    public Chef initialiseChef(){
        TiledMap map = new TmxMapLoader().load("main-game-map.tmx");
        float tileUnitSize = 1 / (float) map.getProperties().get("tilewidth", Integer.class);
        ChefManager chefManager = Mockito.mock(ChefManager.class);
        Texture chefTexture = new Texture(Gdx.files.internal("Kenney-Game-Assets-2/2D assets/Topdown Shooter (620 assets)/PNG/Man Brown/manBrown_hold.png"));
        Chef chef = new Chef(chefTexture,
                new Vector2(chefTexture.getWidth() * tileUnitSize * 2.5f, chefTexture.getHeight() * tileUnitSize * 2.5f), chefManager);
        return chef;
    }

    @Test
    public void resetTest(){
        RecipeStation recipeStation = initialiseRecipeStation();
        assertTrue("if bunCount = 0", 0 == recipeStation.returnCount("bun"));
        assertTrue("if pattyCount = 0", 0 == recipeStation.returnCount("patty"));
        assertTrue("if lettuceCount = 0", 0 == recipeStation.returnCount("lettuce"));
        assertTrue("if tomatoCount = 0", 0 == recipeStation.returnCount("tomato"));
        assertTrue("if pizzaCount = 0", 0 == recipeStation.returnCount("pizza"));
        assertTrue("if potatoCount = 0", 0 == recipeStation.returnCount("potato"));
        assertTrue("if cheeseCount = 0", 0 == recipeStation.returnCount("cheese"));

    }

    @Test
    public void getActionTypesTest(){
        RecipeStation recipeStation = initialiseRecipeStation();
        Chef chef = initialiseChef();
        chef.setIngredientStack(new Ingredient("cheese", Mockito.mock(FoodTextureManager.class)));
        recipeStation.update(chef);
        assertFalse("Action List is non-empty",recipeStation.getActionTypes().isEmpty());
    }

    @Test
    public void doStationActionPlaceTest(){
        RecipeStation recipeStation = initialiseRecipeStation();
        Chef chef = initialiseChef();
        chef.setIngredientStack(new Ingredient("tomato", Mockito.mock(FoodTextureManager.class)));
        recipeStation.update(chef);
        recipeStation.doStationAction(StationAction.ActionType.PLACE_INGREDIENT);
        assertFalse(chef.hasIngredient());
    }

    @Test
    public void doStationActionMakeBurgerTest(){
        RecipeStation recipeStation = initialiseRecipeStation();
        Chef chef = initialiseChef();
        recipeStation.update(chef);
        recipeStation.doStationAction(StationAction.ActionType.MAKE_BURGER);
        assertTrue("if pattyCount = -1", -1 == recipeStation.returnCount("patty"));
        assertTrue("if bunCount = -1", -1 == recipeStation.returnCount("bun"));
    }

    @Test
    public void doStationActionMakeSaladTest(){
        RecipeStation recipeStation = initialiseRecipeStation();
        Chef chef = initialiseChef();
        recipeStation.update(chef);
        recipeStation.doStationAction(StationAction.ActionType.MAKE_SALAD);
        assertTrue("if tomatoCount = -1", -1 == recipeStation.returnCount("tomato"));
        assertTrue("if lettuceCount = -1", -1 == recipeStation.returnCount("lettuce"));
    }

    @Test
    public void doStationActionMakePizzaTest(){
        RecipeStation recipeStation = initialiseRecipeStation();
        Chef chef = initialiseChef();
        recipeStation.update(chef);
        recipeStation.doStationAction(StationAction.ActionType.MAKE_PIZZA);
        assertTrue("if tomatoCount = -1", -1 == recipeStation.returnCount("tomato"));
        assertTrue("if pizzaBaseCount = -1", -1 == recipeStation.returnCount("pizza"));
        assertTrue("if cheeseCount = -1", -1 == recipeStation.returnCount("cheese"));
    }

    @Test
    public void doStationActionMakeJacketPotatoTest(){
        RecipeStation recipeStation = initialiseRecipeStation();
        Chef chef = initialiseChef();
        recipeStation.update(chef);
        recipeStation.doStationAction(StationAction.ActionType.MAKE_JACKET_POTATO);
        assertTrue("if potatoCount = -1", -1 == recipeStation.returnCount("potato"));
        assertTrue("if cheeseCount = -1", -1 == recipeStation.returnCount("cheese"));
    }

    @Test
    public void doStationActionSubmitOrderTest(){
        RecipeStation recipeStation = initialiseRecipeStation();
        Chef chef = initialiseChef();
        recipeStation.update(chef);
        recipeStation.doStationAction(StationAction.ActionType.SUBMIT_ORDER);
        assertNull("if completedRecipe = null", recipeStation.returnCRecipe());

    }



    @Test
    public void test(){
        RecipeStation recipeStation = initialiseRecipeStation();
        assertTrue(true);
    }

}
