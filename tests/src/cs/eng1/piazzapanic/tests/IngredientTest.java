package cs.eng1.piazzapanic.tests;


import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class IngredientTest {

    public Ingredient initialiseIngredient(){
        Ingredient ingredient = new Ingredient(Mockito.mock(String.class), Mockito.mock(FoodTextureManager.class));;
        return ingredient;
    }

    @Test
    public void testCanBeBurnt(){
        Ingredient ingredient = initialiseIngredient();
        ingredient.setIsBurnt(true);
        assertTrue(ingredient.getIsBurnt());
    }

}
