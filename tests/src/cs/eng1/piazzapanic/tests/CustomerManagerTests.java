package cs.eng1.piazzapanic.tests;

import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.recipes.Burger;
import cs.eng1.piazzapanic.food.recipes.JacketPotato;
import cs.eng1.piazzapanic.food.recipes.Pizza;
import cs.eng1.piazzapanic.food.recipes.Recipe;
import cs.eng1.piazzapanic.food.recipes.Salad;
import cs.eng1.piazzapanic.ui.UIOverlay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

@RunWith(GdxTestRunner.class)
public class CustomerManagerTests {

    public CustomerManager initialiseCustomerManager(){
        return new CustomerManager(Mockito.mock(UIOverlay.class), Mockito.mock(FoodTextureManager.class), 0, 5, 2);
    }

    @Test
    public void testGenerateOrder() {
        CustomerManager customerManager = initialiseCustomerManager();
        ArrayList<Recipe> customerOrders = customerManager.getCustomerOrders();
        int initList = customerOrders.size();
        customerManager.updateCustomerOrders(0f);
        assertEquals(initList + 1, customerOrders.size());
    }

    @Test
    public void testSubmitOrder() {
        CustomerManager customerManager = initialiseCustomerManager();
        ArrayList<Recipe> customerOrders = customerManager.getCustomerOrders();
        customerManager.updateCustomerOrders(0f);
        int initList = customerOrders.size();
        customerManager.removeCustomerOrder(0);
        assertEquals(initList - 1, customerOrders.size());
    }

    @Test
    public void testCheckRecipe() {
        CustomerManager customerManager = initialiseCustomerManager();
        ArrayList<Recipe> customerOrders = customerManager.getCustomerOrders();
        customerManager.updateCustomerOrders(0f);
        Recipe initRecipe = customerOrders.get(0);
        FoodTextureManager textureManager = Mockito.mock(FoodTextureManager.class);
        Recipe[] possibleRecipes = new Recipe[] {new Burger(textureManager), new Salad(textureManager), 
            new Pizza(textureManager), new JacketPotato(textureManager)};
        
        for (Recipe recipe : possibleRecipes) {
            if (recipe.getType() == initRecipe.getType()) {
                assertTrue(customerManager.checkRecipe(recipe));
            }
            else {
                assertFalse(customerManager.checkRecipe(recipe));
            }
        }

    }

    @Test
    public void testUpdateRemainingCustomers() {
        CustomerManager customerManager = initialiseCustomerManager();
        int initCustomerNum = customerManager.getRemainingCustomers();
        customerManager.setRemainingCustomers();
        assertEquals(initCustomerNum - 1, customerManager.getRemainingCustomers());
    }

    @Test
    public void testRemoveCustomerOrder(){
        CustomerManager customerManager = initialiseCustomerManager();
        customerManager.updateCustomerOrders(0f);
        ArrayList<Recipe> initOrders = customerManager.getCustomerOrders();
        Recipe recipe = initOrders.get(0);
        customerManager.removeCustomerOrder(recipe);
        assertEquals(initOrders.size(), customerManager.getCustomerOrders().size(), 0);
    }

}