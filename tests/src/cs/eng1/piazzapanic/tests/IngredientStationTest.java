package cs.eng1.piazzapanic.tests;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.stations.IngredientStation;
import cs.eng1.piazzapanic.stations.Station;
import cs.eng1.piazzapanic.stations.StationAction;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;
import cs.eng1.piazzapanic.chef.Chef;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class IngredientStationTest {

    public IngredientStation initialiseIngredientStation(){
        return new IngredientStation(0, Mockito.mock(TextureRegion.class), Mockito.mock(StationUIController.class), Mockito.mock(StationActionUI.class).getActionAlignment(), new Ingredient("test", Mockito.mock(FoodTextureManager.class)));
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
    public void testDoStationActionGrab(){
        IngredientStation ingredientStation = initialiseIngredientStation();
        Chef chef = initialiseChef();
        ingredientStation.update(chef);
        ingredientStation.doStationAction(StationAction.ActionType.GRAB_INGREDIENT);
        assertTrue(chef.hasIngredient());
    }

    @Test 
    public void testGetActionTypes(){
        IngredientStation ingredientStation = initialiseIngredientStation();
        // ingredientStation.doStationAction(null);
     
    }

    @Test 
    public void testDoStationAction(){

    }

    @Test
    public void testDraw(){
        Batch batch = Mockito.mock(Batch.class);
        Station station = Mockito.mock(Station.class);
        station.draw(batch, 1.0f);

    }
}
