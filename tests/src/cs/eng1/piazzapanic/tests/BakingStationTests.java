package cs.eng1.piazzapanic.tests;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.stations.BakingStation;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.stations.StationAction;


import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class BakingStationTests {

    public BakingStation initialiseBakingStation(){
        Ingredient testIngredient = Mockito.mock(Ingredient.class);
        Ingredient[] ingredients = {testIngredient};
        return new BakingStation(0, Mockito.mock(TextureRegion.class), Mockito.mock(StationUIController.class), Mockito.mock(StationActionUI.class).getActionAlignment(),ingredients, "false", Mockito.mock(PiazzaPanicGame.class));
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
        BakingStation bakingStation = initialiseBakingStation();
        assertTrue(true);
    }

    @Test
    public void testDoStationActionBake(){
        BakingStation bakingStation = initialiseBakingStation();
        Chef chef = initialiseChef();
        bakingStation.update(chef);
        bakingStation.doStationAction(StationAction.ActionType.BAKE_ACTION);
        assertTrue(bakingStation.getInUse());
        assertTrue(chef.isPaused());
        
    }

    @Test
    public void testDoStationActionPlace(){
        BakingStation bakingStation = initialiseBakingStation();
        Chef chef = initialiseChef();
        chef.setIngredientStack(new Ingredient("potato", Mockito.mock(FoodTextureManager.class)));
        bakingStation.update(chef);
        bakingStation.doStationAction(StationAction.ActionType.PLACE_INGREDIENT);
        assertTrue(chef.hasIngredient());
    }

    @Test
    public void testDoStationActionGrab(){
        BakingStation bakingStation = initialiseBakingStation();
        Chef chef = initialiseChef();
        chef.setIngredientStack(new Ingredient("potato", Mockito.mock(FoodTextureManager.class)));
        bakingStation.update(chef);
        bakingStation.doStationAction(StationAction.ActionType.PLACE_INGREDIENT);
        bakingStation.update(chef);
        bakingStation.doStationAction(StationAction.ActionType.GRAB_INGREDIENT);
        assertFalse(bakingStation.getInUse());
        assertTrue(chef.hasIngredient());
    }

    @Test 
    public void testDoStationActionClear(){
        BakingStation bakingStation = initialiseBakingStation();
        Chef chef = initialiseChef();
        chef.setIngredientStack(new Ingredient("potato", Mockito.mock(FoodTextureManager.class)));
        bakingStation.update(chef);
        bakingStation.doStationAction(StationAction.ActionType.PLACE_INGREDIENT);
        bakingStation.update(chef);
        bakingStation.doStationAction(StationAction.ActionType.CLEAR_STATION);
        assertFalse(bakingStation.getInUse());
        assertTrue(chef.hasIngredient());
    }

    @Test 
    public void testRest(){
        BakingStation bakingStation = initialiseBakingStation();
        Chef chef = initialiseChef();
        chef.setIngredientStack(new Ingredient("potato", Mockito.mock(FoodTextureManager.class)));
        bakingStation.update(chef);
        bakingStation.doStationAction(StationAction.ActionType.PLACE_INGREDIENT);
        bakingStation.update(chef);
        bakingStation.doStationAction(StationAction.ActionType.BAKE_ACTION);
        bakingStation.reset();
        assertFalse(bakingStation.hasIngredient());
        assertEquals(bakingStation.getTimeBaked(), 0, 0);

    }
}
