package cs.eng1.piazzapanic.tests;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.stations.RecipeStation;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;
import cs.eng1.piazzapanic.ui.UIOverlay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class RecipeStationTests {

    public RecipeStation initialiseRecipeStation(){
        return new RecipeStation(0, Mockito.mock(TextureRegion.class), Mockito.mock(StationUIController.class), Mockito.mock(StationActionUI.class).getActionAlignment(), Mockito.mock(FoodTextureManager.class), Mockito.mock(CustomerManager.class), Mockito.mock(UIOverlay.class), 0, Mockito.mock(PiazzaPanicGame.class));
    }

    @Test
    public void test(){
        RecipeStation recipeStation = initialiseRecipeStation();
        assertTrue(true);
    }

}
