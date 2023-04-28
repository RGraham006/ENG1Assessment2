package cs.eng1.piazzapanic.powerups;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Powerup extends Actor {
    
    private final PowerupManager powerupManager;

    private String type;
    private boolean isActive = false;
    private float activeTime = 0f;

    public Powerup(String type, PowerupManager powerupManager) {
        this.type = type;
        this.powerupManager = powerupManager;
    }

    /**
     * Apply the powerup of a given type to the currently active chef.
     */
    public void applyPowerup() {
        switch (type) {
            case "chef_speed_up":
                if (powerupManager.getChefManager().getCurrentChef() != null) {
                    System.out.println("speed up chef");
                    powerupManager.getChefManager().getCurrentChef().doubleChefSpeed();
                    activeTime = 10f;
                    isActive = true;
                }
                break;
            case "prep_speed_up":   
                if (powerupManager.getChefManager().getCurrentChef() != null) {
                    if (!powerupManager.getChefManager().getCurrentChef().isPaused()) {
                        System.out.println("speed up prep");
                        powerupManager.getChefManager().getCurrentChef().doublePrepSpeed();
                        isActive = true;
                    }
                }
                break;
            case "add_rep_point":
                System.out.println("add rep point");
                powerupManager.getOverlay().addPoint();
                isActive = true;
                break;
            case "double_money":
                System.out.println("double money");
                powerupManager.getOverlay().doubleMoneyToAdd();
                isActive = true;
                break;
            case "reset_customer_wait": // Only resets first, so may edit so reset all
                System.out.println("reset wait time");
                powerupManager.getCustomerManager().resetCustomerWait();
                isActive = true;
                break;
        }
    }

    public void removePowerup() {
        // Only chef speed up is time based, all the rest are instantly deactivated
        switch (type) {
            case "chef_speed_up":
                System.out.println("reset chef speed");
                powerupManager.getChefManager().getCurrentChef().resetChefSpeed();
                activeTime = 0f;
                break;
            default:
                break;
        }

        System.out.println("reset powerup");
        isActive = false;
    }

    @Override
    public void act(float delta) {
        if (isActive) {
            if (activeTime <= 0f) {
                removePowerup();
                powerupManager.removePowerup();
            }

            activeTime -= delta;
        }

        super.act(delta);
    }
}
