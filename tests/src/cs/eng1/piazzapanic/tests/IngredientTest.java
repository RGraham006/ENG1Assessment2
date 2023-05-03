package cs.eng1.piazzapanic.tests;


import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class IngredientTest {

    public Ingredient initialiseIngredient(){
        Ingredient ingredient = new Ingredient("patty", Mockito.mock(FoodTextureManager.class));;
        return ingredient;
    }

    @Test
    public void testGetTexture() {
        Ingredient ingredient = initialiseIngredient();
        assertNull(ingredient.getTexture());
    }
    @Test
    public void testGetType() {
        Ingredient ingredient = initialiseIngredient();
      assertEquals("patty", ingredient.getType());
    }
    
    @Test
    public void testCanBeBurnt(){
        
        Ingredient ingredient = initialiseIngredient();
        ingredient.setIsBurnt(true);
        assertTrue(ingredient.getIsBurnt());
    }

    @Test
    public void testCanBeBaked(){
        Ingredient ingredient = initialiseIngredient();
        ingredient.setBaked(true);
        assertTrue(ingredient.getBaked());
    }

    @Test
    public void testCanBeCooked(){
        Ingredient ingredient = initialiseIngredient();
        ingredient.setIsCooked(true);
        assertTrue(ingredient.getIsCooked());
    }

    @Test
    
    public void testCanBeHalfCooked(){
        Ingredient ingredient = initialiseIngredient();
        ingredient.setHalfCooked();
        assertTrue(ingredient.getIsHalfCooked());
    }


    @Test
    public void testCanBeChopped(){
        Ingredient ingredient = initialiseIngredient();
        ingredient.setIsChopped(true);
        assertTrue(ingredient.getIsChopped());
        
    }

    @Test
    public  void testToString(){
        Ingredient ingredient = initialiseIngredient();
        ingredient.setIsChopped(true);
        assertEquals("patty_chopped", ingredient.toString());
        ingredient.setIsChopped(false);
        ingredient.setIsBurnt(true);
        assertEquals("burnt", ingredient.toString());
        ingredient.setIsBurnt(false);
        ingredient.setIsCooked(true);
        assertEquals("patty_cooked", ingredient.toString());
        ingredient.setIsCooked(false);
        assertEquals("patty_raw", ingredient.toString());
    }
    

}
