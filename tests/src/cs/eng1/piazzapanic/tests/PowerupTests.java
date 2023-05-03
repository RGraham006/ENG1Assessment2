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
import org.mockito.Mock;
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

    public Powerup initialisePowerup(String type, PowerupManager powerupManager) {
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

    public PowerupManager initialisePowerupManager(ChefManager chefManager, CustomerManager customerManager){
        return new PowerupManager(chefManager, customerManager, Mockito.mock(UIOverlay.class));
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
        Chef chef = initialiseChef();
        ChefManager chefManager = initialiseChefManager();
        chefManager.setCurrentChef(chef);
        CustomerManager customerManager = initialiseCustomerManager();
        PowerupManager powerupManager = initialisePowerupManager(chefManager, customerManager);
        Powerup powerup = initialisePowerup("chef_speed_up", powerupManager);

        float speed_before = chef.getChefSpeed();

        powerup.applyPowerup();

        float speed_after = chef.getChefSpeed();

        assertEquals(speed_after, speed_before * 2, 0.01f);
    }

    @Test
    public void testPrepSpeedupPowerup() {
        Chef chef = initialiseChef();
        ChefManager chefManager = initialiseChefManager();
        chefManager.setCurrentChef(chef);
        CustomerManager customerManager = initialiseCustomerManager();
        PowerupManager powerupManager = initialisePowerupManager(chefManager, customerManager);
        Powerup powerup = initialisePowerup("prep_speed_up", powerupManager);

        float speed_before = chef.getPrepSpeed();
        System.out.println(speed_before);

        powerup.applyPowerup();

        float speed_after = chef.getPrepSpeed();
        System.out.println(speed_after);

        speed_after *= 2;

        assertEquals(speed_after, speed_before, 0.01f);
    }

    @Test
    public void testResetCustomerWait() {
        ChefManager chefManager = initialiseChefManager();
        CustomerManager customerManager = initialiseCustomerManager();
        PowerupManager powerupManager = initialisePowerupManager(chefManager, customerManager);
        Powerup powerup = initialisePowerup("chef_speed_up", powerupManager);


        customerManager.updateCustomerOrders(0f);
        ProgressBar progressBar_before = customerManager.getFirstProgressBar();

        customerManager.updateCustomerOrders(0f);

        powerup.applyPowerup();

        ProgressBar progressBar_after = customerManager.getFirstProgressBar();

        assertEquals(progressBar_before.getValue(), progressBar_after.getValue(), 0.01f);
    }
}
