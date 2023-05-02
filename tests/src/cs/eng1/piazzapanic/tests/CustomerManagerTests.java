package cs.eng1.piazzapanic.tests;

import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.ui.UIOverlay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(GdxTestRunner.class)
public class CustomerManagerTests {

    public CustomerManager initialiseCustomerManager(){
        return new CustomerManager(Mockito.mock(UIOverlay.class), Mockito.mock(FoodTextureManager.class), 2, 5, 2);
    }

}
