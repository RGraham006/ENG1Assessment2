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

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class BakingStationTests {

    public BakingStation initialiseBakingStation(){
        Ingredient testIngredient = Mockito.mock(Ingredient.class);
        Ingredient[] ingredients = {testIngredient};
        return new BakingStation(0, Mockito.mock(TextureRegion.class), Mockito.mock(StationUIController.class), Mockito.mock(StationActionUI.class).getActionAlignment(),ingredients, "false", Mockito.mock(PiazzaPanicGame.class));
    }


    @Test
    public void test(){
        BakingStation bakingStation = initialiseBakingStation();
        assertTrue(true);
    }

    @Test
    public void testCheckIfBurnt(){
        BakingStation bakingStation = initialiseBakingStation();
        
        assertTrue(bakingStation.checkIfBurnt(2));
        
    }
}
