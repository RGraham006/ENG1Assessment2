package cs.eng1.piazzapanic.tests;

import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.food.FoodTextureManager;
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
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.IntIntMap;

import org.junit.Test;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class PowerupTests {

    public Powerup initialisePowerup(String type) {

        Chef chef = initialiseChef();
        ChefManager chefManager = initialiseChefManager();
        chefManager.setCurrentChef(chef);

        PowerupManager powerupManager = new PowerupManager(chefManager, initialiseCustomerManager(),
                Mockito.mock(UIOverlay.class));

        return new Powerup(type, powerupManager);
    }

    public ChefManager initialiseChefManager() {
        TiledMap map = new TmxMapLoader().load("main-game-map.tmx");
        float tileUnitSize = 1 / (float) map.getProperties().get("tilewidth", Integer.class);
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("Foreground");
        ChefManager chefManager = new ChefManager(tileUnitSize * 2.5f, collisionLayer, Mockito.mock(UIOverlay.class));
        return chefManager;
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

    public UIOverlay initialiseUIOverlay() {
        PiazzaPanicGame game = new PiazzaPanicGame();
        Stage stage = Mockito.mock(Stage.class);
        return new UIOverlay(stage, game);
    }

    public CustomerManager initialiseCustomerManager() {
        return new CustomerManager(Mockito.mock(UIOverlay.class), Mockito.mock(FoodTextureManager.class), 0, 1, 2);
    }

    @Test
    public void testChefSpeedupPowerup() {
        Powerup powerup = initialisePowerup("chef_speed_up");

        float speed_before = powerup.getPowerupManager().getChefManager().getCurrentChef().getChefSpeed();

        powerup.applyPowerup();

        float speed_after = powerup.getPowerupManager().getChefManager().getCurrentChef().getChefSpeed();

        speed_before *= 2;

        assertEquals(speed_after, speed_before, 0.01f);
    }

    @Test
    public void testPrepSpeedupPowerup() {
        Powerup powerup = initialisePowerup("prep_speed_up");

        float speed_before = powerup.getPowerupManager().getChefManager().getCurrentChef().getPrepSpeed();

        powerup.applyPowerup();

        float speed_after = powerup.getPowerupManager().getChefManager().getCurrentChef().getPrepSpeed();

        speed_after *= 2;

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
        powerup.getPowerupManager().getCustomerManager().updateCustomerOrders(0f);
        ProgressBar progressBar_before = powerup.getPowerupManager().getCustomerManager().getFirstProgressBar();

        powerup.getPowerupManager().getCustomerManager().updateCustomerOrders(0f);

        powerup.applyPowerup();

        ProgressBar progressBar_after = powerup.getPowerupManager().getCustomerManager().getFirstProgressBar();

        assertEquals(progressBar_before.getValue(), progressBar_after.getValue(), 0.01f);
    }
}
