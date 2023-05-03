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
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.stations.ChoppingStation;
import cs.eng1.piazzapanic.stations.StationAction;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.naming.InsufficientResourcesException;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ChoppingStationTests {

    public ChoppingStation initialiseChoppingStation(){
        Ingredient ingredient = new Ingredient("tomato", Mockito.mock(FoodTextureManager.class));
        Ingredient[] ingredients = {ingredient};
        return new ChoppingStation(0, Mockito.mock(TextureRegion.class), Mockito.mock(StationUIController.class), Mockito.mock(StationActionUI.class).getActionAlignment(), ingredients, "false", Mockito.mock(PiazzaPanicGame.class));
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
    public void testDoStationActionChop(){
       ChoppingStation choppingStation = initialiseChoppingStation();
       Chef chef = initialiseChef();
       choppingStation.update(chef);
       choppingStation.doStationAction(StationAction.ActionType.CHOP_ACTION);
       assertTrue(choppingStation.getInUse());
       assertTrue(chef.isPaused());
    }

    @Test
    public void testDoStationActionPlace(){
        ChoppingStation choppingStation = initialiseChoppingStation();
        Chef chef = initialiseChef();
        chef.setIngredientStack(new Ingredient("tomato", Mockito.mock(FoodTextureManager.class)));
        choppingStation.update(chef);
        choppingStation.doStationAction(StationAction.ActionType.PLACE_INGREDIENT);
        assertFalse(chef.hasIngredient());
    }

    @Test
    public void testDoStationActionGrab(){
        ChoppingStation choppingStation = initialiseChoppingStation();
        Chef chef = initialiseChef();
        chef.setIngredientStack(new Ingredient("tomato", Mockito.mock(FoodTextureManager.class)));
        choppingStation.update(chef);
        choppingStation.doStationAction(StationAction.ActionType.PLACE_INGREDIENT);
        choppingStation.update(chef);
        choppingStation.doStationAction(StationAction.ActionType.GRAB_INGREDIENT);
        assertFalse(choppingStation.getInUse());
        assertTrue(chef.hasIngredient());
    }

    @Test
    public void testReset(){
        ChoppingStation choppingStation = initialiseChoppingStation();
        Chef chef = initialiseChef();
        chef.setIngredientStack(new Ingredient("tomato", Mockito.mock(FoodTextureManager.class)));
        choppingStation.update(chef);
        choppingStation.doStationAction(StationAction.ActionType.PLACE_INGREDIENT);
        choppingStation.update(chef);
        choppingStation.doStationAction(StationAction.ActionType.CHOP_ACTION);
        choppingStation.reset();
        assertFalse(choppingStation.hasIngredient());
        assertEquals(choppingStation.getTimeChopped(), 0, 0);
    }

}
