package cs.eng1.piazzapanic.tests;

import cs.eng1.piazzapanic.powerups.Powerup;
import cs.eng1.piazzapanic.powerups.PowerupManager;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(GdxTestRunner.class)
public class PowerupTests {

    public Powerup initialisePowerup(){
        return new Powerup(Mockito.mock(String.class), Mockito.mock(PowerupManager.class));
    }

}
