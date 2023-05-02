package cs.eng1.piazzapanic.tests;

import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.powerups.Powerup;
import cs.eng1.piazzapanic.powerups.PowerupManager;
import cs.eng1.piazzapanic.ui.Money;
import cs.eng1.piazzapanic.ui.ReputationPoint;
import cs.eng1.piazzapanic.ui.UIOverlay;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.IntIntMap;

import org.junit.Test;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class PowerupTests {

    public Powerup initialisePowerup(String type) {

        Chef chef = initialiseChef();
        ChefManager chefManager = Mockito.mock(ChefManager.class);
        chefManager.setCurrentChef(chef);

        PowerupManager powerupManager = new PowerupManager(chefManager, Mockito.mock(CustomerManager.class),
                Mockito.mock(UIOverlay.class));

        return new Powerup(type, powerupManager);
    }

    public Chef initialiseChef() {
        TiledMap map = new TmxMapLoader().load("main-game-map.tmx");
        float tileUnitSize = 1 / (float) map.getProperties().get("tilewidth", Integer.class);
        ChefManager chefManager = Mockito.mock(ChefManager.class);
        Texture chefTexture = new Texture(Gdx.files.internal(
                "Kenney-Game-Assets-2/2D assets/Topdown Shooter (620 assets)/PNG/Man Brown/manBrown_hold.png"));
        Chef chef = new Chef(chefTexture,
                new Vector2(chefTexture.getWidth() * tileUnitSize * 2.5f,
                        chefTexture.getHeight() * tileUnitSize * 2.5f),
                chefManager);
        chefManager.setCurrentChef(chef);
        return chef;
    }

    @Test
    public void testChefSpeedupPowerup() {
        Powerup powerup = initialisePowerup("chef_speed_up");

        Chef chef_before = powerup.getPowerupManager().getChefManager().getCurrentChef();
        float speed_before = chef_before.getChefSpeed();

        powerup.applyPowerup();

        Chef cehf_after = powerup.getPowerupManager().getChefManager().getCurrentChef();
        float speed_after = cehf_after.getChefSpeed();

        speed_before *= 2;

        assertEquals(speed_after, speed_before, 0.01f);
    }

    @Test
    public void testPrepSpeedupPowerup() {
        Powerup powerup = initialisePowerup("prep_speed_up");

        Chef chef_before = powerup.getPowerupManager().getChefManager().getCurrentChef();
        float speed_before = chef_before.getPrepSpeed();

        powerup.applyPowerup();

        Chef chef_after = powerup.getPowerupManager().getChefManager().getCurrentChef();
        float speed_after = chef_after.getPrepSpeed();

        speed_before *= 2;

        assertEquals(speed_after, speed_before, 0.01f);
    }

    @Test
    public void testAddRepPoint() {
        Powerup powerup = initialisePowerup("add_rep_point");

        int points_before = powerup.getPowerupManager().getOverlay().getReputation();

        powerup.applyPowerup();

        int points_after = powerup.getPowerupManager().getOverlay().getReputation();

        points_before += 1;

        assertEquals(points_after, points_before, 0.01f);
    }

    @Test
    public void testDoubleMoney() {
        Powerup powerup = initialisePowerup("double_money");

        int money_before = powerup.getPowerupManager().getOverlay().getMoney().getMoney();

        powerup.applyPowerup();
        powerup.getPowerupManager().getOverlay().updateMoney();

        int money_after = powerup.getPowerupManager().getOverlay().getMoney().getMoney();

        assertEquals(money_after, money_before, 0.01f);
    }

    @Test
    public void testResetCustomerWait() {
        Powerup powerup = initialisePowerup("reset_customer_wait");

    }
}
