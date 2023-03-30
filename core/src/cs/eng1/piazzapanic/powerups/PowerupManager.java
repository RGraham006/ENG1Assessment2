package cs.eng1.piazzapanic.powerups;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Stage;

import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.ui.UIOverlay;

public class PowerupManager {

    private final String[] powerupTypes = new String[] {
        "chef_speed_up",
        "prep_speed_up",
        "add_rep_point",
        "double_money",
        "reset_customer_wait"
    };
    private final ArrayList<Powerup> powerups;
    private final UIOverlay overlay;

    private Powerup appliedPowerup = null;

    private final ChefManager chefManager;
    private final CustomerManager customerManager;
    

    public PowerupManager(ChefManager chefManager, CustomerManager customerManager, UIOverlay overlay) {
        // Initialise powerups using given types
        powerups = new ArrayList<>(powerupTypes.length);
        for (String type : powerupTypes) {
            powerups.add(new Powerup(type, this));
        }

        // Links to appropriate classes
        this.chefManager = chefManager;
        this.overlay = overlay;
        this.customerManager = customerManager;
    }

    public void addPowerupToStage(final Stage stage) {
        for (Powerup powerup : powerups) {
            stage.addActor(powerup);
        }
    }

    public ArrayList<Powerup> getPowerups() {
        return powerups;
    }

    public void generatePowerup() {
        appliedPowerup = powerups.get(0);   // CHEF SPEED
        // appliedPowerup = powerups.get(1);   // PREP SPEED
        // appliedPowerup = powerups.get(2);   // ADD REP POINT
        // appliedPowerup = powerups.get(3);   // DOUBLE MONEY
        // appliedPowerup = powerups.get(4);   // RESET WAIT TIME FOR FIRST CUSTOMER
        appliedPowerup.applyPowerup();
    }

    public void removePowerup() {
        appliedPowerup = null;
    }

    public ChefManager getChefManager() {
        return chefManager;
    }

    public UIOverlay getOverlay() {
        return overlay;
    }

    public CustomerManager getCustomerManager() {
        return customerManager;
    }
    
}