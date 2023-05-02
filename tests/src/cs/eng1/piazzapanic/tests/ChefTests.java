package cs.eng1.piazzapanic.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.chef.FixedStack;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ChefTests {

    public Chef initialiseChef(){
        TiledMap map = new TmxMapLoader().load("main-game-map.tmx");
        float tileUnitSize = 1 / (float) map.getProperties().get("tilewidth", Integer.class);
        ChefManager chefManager = Mockito.mock(ChefManager.class);
        Texture chefTexture = new Texture(Gdx.files.internal("Kenney-Game-Assets-2/2D assets/Topdown Shooter (620 assets)/PNG/Man Brown/manBrown_hold.png"));
        Chef chef = new Chef(chefTexture,
                new Vector2(chefTexture.getWidth() * tileUnitSize * 2.5f, chefTexture.getHeight() * tileUnitSize * 2.5f), chefManager);
        return chef;
    }

    @Test
    public void testGrabIngredient(){

        Chef chef = initialiseChef();
        FixedStack<Ingredient> ingredients = chef.getStack();
        int initStack = ingredients.size();
        chef.grabIngredient(Mockito.mock(Ingredient.class));
        assertEquals(initStack + 1, ingredients.size());

    }

    @Test
    public void testPlaceIngredient(){

        Chef chef = initialiseChef();
        FixedStack<Ingredient> ingredients = chef.getStack();
        chef.grabIngredient(Mockito.mock(Ingredient.class));
        int someStack = ingredients.size();
        chef.placeIngredient();
        assertEquals(ingredients.size(), someStack - 1);

    }

    @Test
    public void testHasIngredient(){
        Chef chef = initialiseChef();
        FixedStack<Ingredient> ingredients = chef.getStack();
        chef.grabIngredient(Mockito.mock(Ingredient.class));
        assertTrue(chef.hasIngredient());
    }

    @Test
    public void testCantOverfillStack(){
        Chef chef = initialiseChef();
        FixedStack<Ingredient> ingredients = chef.getStack();
        for (int i = 0; i < ingredients.capacity(); i++) {
            chef.grabIngredient(Mockito.mock(Ingredient.class));
        }
        assertFalse(chef.canGrabIngredient());
    }

    @Test
    public void testCanBePaused(){
        Chef chef = initialiseChef();
        chef.setPaused(true);
        assertTrue(chef.isPaused());
    }

    @Test
    public void testCanBeUnpaused(){
        Chef chef = initialiseChef();
        chef.setPaused(true);
        assertTrue(chef.isPaused());
        chef.setPaused(false);
        assertFalse(chef.isPaused());
    }

    @Test
    public void testSpeedupSpeed(){
        Chef chef = initialiseChef();
        chef.doubleChefSpeed();
        assertEquals(chef.getChefSpeed(), 6f, 0f);
    }

    @Test
    public void testSpeedDownSpeed() {
        Chef chef = initialiseChef();
        chef.doubleChefSpeed();
        assertEquals(chef.getChefSpeed(), 6f, 0f);
        chef.resetChefSpeed();
        assertEquals(chef.getChefSpeed(), 3f, 0f);
    }

    @Test
    public void testSpeedupPrepping(){
        Chef chef = initialiseChef();
        chef.doublePrepSpeed();
        assertEquals(chef.getPrepSpeed(), 2.5f, 0f);
    }

    @Test
    public void testSpeedDownPrepping(){
        Chef chef = initialiseChef();
        chef.doublePrepSpeed();
        assertEquals(chef.getPrepSpeed(), 2.5f, 0f);
        chef.resetPrepSpeed();
        assertEquals(chef.getPrepSpeed(), 5f, 0f);
    }

    @Test
    public void testInputEnabled(){
        Chef chef = initialiseChef();
        chef.setInputEnabled(true);
        assertTrue(chef.isInputEnabled());
    }

    @Test
    public void testInputDisabled(){
        Chef chef = initialiseChef();
        chef.setInputEnabled(true);
        assertTrue(chef.isInputEnabled());
        chef.setInputEnabled(false);
        assertFalse(chef.isInputEnabled());
    }

    @Test
    public void test(){

    }

}
