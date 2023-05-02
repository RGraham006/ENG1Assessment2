package cs.eng1.piazzapanic.tests;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.stations.ChoppingStation;
import cs.eng1.piazzapanic.stations.CookingStation;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class CookingStationTest {

    public CookingStation initialiseCookingStation(){

        Ingredient ingredient = Mockito.mock(Ingredient.class);
        Ingredient[] ingredients = {ingredient};
        return new CookingStation(0, Mockito.mock(TextureRegion.class), Mockito.mock(StationUIController.class), Mockito.mock(StationActionUI.class).getActionAlignment(), ingredients, "false", Mockito.mock(PiazzaPanicGame.class));

    }

    @Test
    public void test(){
        CookingStation cookingStation = initialiseCookingStation();
        assertTrue(true);
    }

}
