package cs.eng1.piazzapanic.tests;

import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.powerups.Powerup;
import cs.eng1.piazzapanic.powerups.PowerupManager;
import cs.eng1.piazzapanic.ui.UIOverlay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.badlogic.gdx.scenes.scene2d.Stage;

@RunWith(GdxTestRunner.class)
public class PowerupManagerTests {

    public PowerupManager initialisePowerupManager() {
        return new PowerupManager(Mockito.mock(ChefManager.class), Mockito.mock(CustomerManager.class),
                Mockito.mock(UIOverlay.class));
    }

    @Test
    public void testGetowerups() {
        PowerupManager powerupManager = initialisePowerupManager();
        ArrayList<Powerup> powerups = powerupManager.getPowerups();
        ArrayList<Powerup> powerupslist = new ArrayList<>(4);
        powerupslist.add(new Powerup("chef_speed_up", powerupManager));
        powerupslist.add(new Powerup("prep_speed_up", powerupManager));
        powerupslist.add(new Powerup("add_rep_point", powerupManager));
        powerupslist.add(new Powerup("double_money", powerupManager));
        powerupslist.add(new Powerup("reset_customer_wait", powerupManager));
        for (int i = 0; i < 5; i++) {
            assertEquals(powerups.get(i).getType(), powerupslist.get(i).getType());
        }
    }

    @Test
    public void testGeneratePowerups() {
        PowerupManager powerupManager = initialisePowerupManager();
        powerupManager.generatePowerup(0);
        assertEquals(powerupManager.getAppliedPowerup().getType(), "chef_speed_up");
        powerupManager.generatePowerup(1);
        assertEquals(powerupManager.getAppliedPowerup().getType(), "prep_speed_up");
        powerupManager.generatePowerup(2);
        assertEquals(powerupManager.getAppliedPowerup().getType(), "add_rep_point");
        powerupManager.generatePowerup(3);
        assertEquals(powerupManager.getAppliedPowerup().getType(), "double_money");
        powerupManager.generatePowerup(4);
        assertEquals(powerupManager.getAppliedPowerup().getType(), "reset_customer_wait");
    }

    @Test
    public void testRemovePowerup() {
        PowerupManager powerupManager = initialisePowerupManager();
        powerupManager.generatePowerup(0);
        powerupManager.removePowerup();
        assertEquals(powerupManager.getAppliedPowerup(), null);
    }
}
