package cs.eng1.piazzapanic.tests;

import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.powerups.PowerupManager;
import cs.eng1.piazzapanic.ui.UIOverlay;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(GdxTestRunner.class)
public class PowerupManagerTests {

    public PowerupManager initialisePowerupManager(){
        return new PowerupManager(Mockito.mock(ChefManager.class), Mockito.mock(CustomerManager.class), Mockito.mock(UIOverlay.class));
    }

}
