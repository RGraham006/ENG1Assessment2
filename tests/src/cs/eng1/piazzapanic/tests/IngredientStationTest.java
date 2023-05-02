package cs.eng1.piazzapanic.tests;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.stations.IngredientStation;
import cs.eng1.piazzapanic.stations.Station;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;
import cs.eng1.piazzapanic.chef.Chef;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.junit.Assert.*;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class IngredientStationTest {

    public IngredientStation initialiseIngredientStation(){
        return new IngredientStation(0, Mockito.mock(TextureRegion.class), Mockito.mock(StationUIController.class), Mockito.mock(StationActionUI.class).getActionAlignment(), Mockito.mock(Ingredient.class));
    }

    @Test
    public void test(){
        IngredientStation ingredientStation = initialiseIngredientStation();
        assertTrue(true);
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
