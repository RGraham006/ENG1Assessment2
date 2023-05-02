package cs.eng1.piazzapanic.tests;

import cs.eng1.piazzapanic.powerups.Powerup;
import cs.eng1.piazzapanic.powerups.PowerupManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class PowerupTests {

    public Powerup initialisePowerup() {
        return new Powerup("test", Mockito.mock(PowerupManager.class));
    }

}
