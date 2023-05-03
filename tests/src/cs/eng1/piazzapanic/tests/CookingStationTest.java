package cs.eng1.piazzapanic.tests;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.stations.CookingStation;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.stations.StationAction;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class CookingStationTest {

    public CookingStation initialiseCookingStation(){

        Ingredient ingredient = Mockito.mock(Ingredient.class);
        Ingredient[] ingredients = {ingredient};
        return new CookingStation(0, Mockito.mock(TextureRegion.class), Mockito.mock(StationUIController.class), Mockito.mock(StationActionUI.class).getActionAlignment(), ingredients, "false", Mockito.mock(PiazzaPanicGame.class));

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
    public void test(){
        CookingStation cookingStation = initialiseCookingStation();
        assertTrue(true);
    }

    @Test
    public void testDoStationActionCook(){
        CookingStation cookingStation = initialiseCookingStation();
        Chef chef = initialiseChef();
        cookingStation.update(chef);
        cookingStation.doStationAction(StationAction.ActionType.COOK_ACTION);
        assertTrue(cookingStation.getInUse());
        assertTrue(chef.isPaused());
    }

    @Test
    public void testDoStationActionFlip() {
        CookingStation cookingStation = initialiseCookingStation();
        Chef chef = initialiseChef();
        chef.setIngredientStack(new Ingredient("patty", Mockito.mock(FoodTextureManager.class)));
        cookingStation.update(chef);
        cookingStation.doStationAction(StationAction.ActionType.FLIP_ACTION);
        assertFalse(cookingStation.getInUse());
        assertTrue(chef.isPaused());
    }

    @Test 
    public void testDoStationActionPlace() {
        CookingStation cookingStation = initialiseCookingStation();
        Chef chef = initialiseChef();
        chef.setIngredientStack(new Ingredient("patty", Mockito.mock(FoodTextureManager.class)));
        cookingStation.update(chef);
        cookingStation.doStationAction(StationAction.ActionType.PLACE_INGREDIENT);
        assertTrue(chef.hasIngredient());
    }


    @Test
    public void testDoStationActionGrab(){
        CookingStation cookingStation = initialiseCookingStation();
        Chef chef = initialiseChef();
        chef.setIngredientStack(new Ingredient("patty", Mockito.mock(FoodTextureManager.class)));
        cookingStation.update(chef);
        cookingStation.doStationAction(StationAction.ActionType.PLACE_INGREDIENT);
        cookingStation.update(chef);
        cookingStation.doStationAction(StationAction.ActionType.GRAB_INGREDIENT);
        assertFalse(cookingStation.getInUse());
        assertTrue(chef.hasIngredient());
    }

    @Test
    public void testDoStationActionClear(){
        CookingStation cookingStation = initialiseCookingStation();
        Chef chef = initialiseChef();
        chef.setIngredientStack(new Ingredient("patty", Mockito.mock(FoodTextureManager.class)));
        cookingStation.update(chef);
        cookingStation.doStationAction(StationAction.ActionType.PLACE_INGREDIENT);
        cookingStation.update(chef);
        cookingStation.doStationAction(StationAction.ActionType.CLEAR_STATION);
        assertFalse(cookingStation.getInUse());
        assertTrue(chef.hasIngredient());
    }

    @Test 
    public void testRest(){
        CookingStation cookingStation = initialiseCookingStation();
        Chef chef = initialiseChef();
        chef.setIngredientStack(new Ingredient("patty", Mockito.mock(FoodTextureManager.class)));
        cookingStation.update(chef);
        cookingStation.doStationAction(StationAction.ActionType.PLACE_INGREDIENT);
        cookingStation.update(chef);
        cookingStation.doStationAction(StationAction.ActionType.BAKE_ACTION);
        cookingStation.reset();
        assertFalse(cookingStation.hasIngredient());
        assertEquals(cookingStation.getTimeCooked(), 0, 0);

    }


}
