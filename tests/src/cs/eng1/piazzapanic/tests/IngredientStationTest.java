package cs.eng1.piazzapanic.tests;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.stations.IngredientStation;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

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

}
