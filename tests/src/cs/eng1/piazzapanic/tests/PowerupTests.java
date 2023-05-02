package cs.eng1.piazzapanic.tests;

import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.powerups.Powerup;
import cs.eng1.piazzapanic.powerups.PowerupManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.matchers.GreaterOrEqual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class PowerupTests {

    public Powerup initialisePowerup(String type) {
        return new Powerup(type, Mockito.mock(PowerupManager.class));
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
        return chef;
    }

    @Test
    public void testApplyPowerup() {
        Powerup powerup = initialisePowerup("chef_speed_up");
        Chef chef = initialiseChef();
        float speed = chef.getChefSpeed();
        powerup.applyPowerup();
    }
}
