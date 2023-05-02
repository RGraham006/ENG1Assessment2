package cs.eng1.piazzapanic.tests;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.ui.UIOverlay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class ChefManagerTests {

    public ChefManager initialiseChefManager(){
        TiledMap map = new TmxMapLoader().load("main-game-map.tmx");
        float tileUnitSize = 1 / (float) map.getProperties().get("tilewidth", Integer.class);
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("Foreground");
        ChefManager chefManager = new ChefManager(tileUnitSize * 2.5f, collisionLayer, Mockito.mock(UIOverlay.class));
        return chefManager;
    }

    @Test
    public void testChefManagerWorks(){
        ChefManager chefManager = initialiseChefManager();
        chefManager.addChefsToStage(Mockito.mock(Stage.class));
        assertTrue(true);
    }

}
