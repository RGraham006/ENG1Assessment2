package cs.eng1.piazzapanic.tests;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class AssetTests {
    
    @Test
    public void testChefAssetsExist() {
        assertTrue("Test that chef 1 asset exists.", Gdx.files.internal(
            "Kenney-Game-Assets-2/2D assets/Topdown Shooter (620 assets)/PNG/Man Brown/manBrown_hold.png").exists());
        assertTrue("Test that chef 2 asset exists", Gdx.files.internal(
            "Kenney-Game-Assets-2/2D assets/Topdown Shooter (620 assets)/PNG/Woman Green/womanGreen_hold.png").exists());
        assertTrue("Test that chef 3 asset exists", Gdx.files.internal(
            "Kenney-Game-Assets-2/2D assets/Topdown Shooter (620 assets)/PNG/Man Red/manRed_hold.png").exists());
    }

    @Test
    public void testFoodAssetsExist() {
        assertTrue("Test that cooked patty asset exists.", Gdx.files.internal(
            "food/original/cooked_patty.png").exists());
            assertTrue("Test that uncooked patty asset exists.", Gdx.files.internal(
            "food/original/uncooked_patty.png").exists());
        assertTrue("Test that lettuce asset exists.", Gdx.files.internal(
            "food/glitch/vegetable/lettuce.png").exists());
        assertTrue("Test that chopped lettuce asset exists.", Gdx.files.internal(
            "food/original/lettuce_chopped.png").exists());
        assertTrue("Test that tomato asset exists.", Gdx.files.internal(
            "food/glitch/fruit/tomato.png").exists());
        assertTrue("Test that chopped tomato asset exists.", Gdx.files.internal(
            "food/original/tomato_chopped.png").exists());
        assertTrue("Test that bun asset exists.", Gdx.files.internal(
            "food/glitch/misc/bun.png").exists());
        assertTrue("Test that burger asset exists.", Gdx.files.internal(
            "food/glitch/misc/sandwich_burger_04.png").exists());
        assertTrue("Test that salad asset exists.", Gdx.files.internal(
            "food/glitch/misc/salad.png").exists());
        assertTrue("Test that dough asset exists.", Gdx.files.internal(
            "food/glitch/misc/corn_fritter.png").exists());
        assertTrue("Test that cooked dough asset exists.", Gdx.files.internal(
            "food/glitch/misc/cornbread.png").exists());
        assertTrue("Test that pizza base asset exists.", Gdx.files.internal(
            "food/glitch/dessert/pie.png").exists());
        assertTrue("Test that pizza asset exists.", Gdx.files.internal(
            "food/glitch/misc/pizza_01.png").exists());
        assertTrue("Test that potato asset exists.", Gdx.files.internal(
            "food/glitch/vegetable/potato.png").exists());
        assertTrue("Test that baked potato asset exists.", Gdx.files.internal(
            "food/glitch/misc/potato_patty.png").exists());
        assertTrue("Test that jacket potato asset exists.", Gdx.files.internal(
            "food/glitch/misc/fry_up.png").exists());
        assertTrue("Test that cheese asset exists.", Gdx.files.internal(
            "food/glitch/dairy/cheese_01.png").exists());
        assertTrue("Test that burnt food asset exists.", Gdx.files.internal(
            "food/glitch/spice/black_pepper.png").exists());
    }

    @Test
    public void testMapExists() {
        assertTrue("Test that map exists.", Gdx.files.internal(
            "main-game-map.tmx").exists());
    }

    @Test
    public void testUIAssetsExist() {
        // Button Assets
        assertTrue("Test that grey button exists.", Gdx.files.internal(
            "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_button_flat_up.png").exists());
        assertTrue("Test that blue button exists.", Gdx.files.internal(
            "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/blue_button_flat_up.png").exists());
        assertTrue("Test that blue button down exists.", Gdx.files.internal(
            "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/blue_button_flat_down.png").exists());
        assertTrue("Test that blue button disabled exists.", Gdx.files.internal(
            "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/blue_button_flat_disabled.png").exists());
        assertTrue("Test that blue button outline exists.", Gdx.files.internal(
            "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/blue_button_outline_up.png").exists());
        assertTrue("Test that blue button (gradient) exists.", Gdx.files.internal(
            "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/blue_button_gradient_up.png").exists());
        assertTrue("Test that grey square exists.", Gdx.files.internal(
            "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_button_square_gradient_down.png").exists());
        assertTrue("Test that green rectangle exists.", Gdx.files.internal(
            "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/green_button_gradient_up.png").exists());
        assertTrue("Test that checkmark box exists.", Gdx.files.internal(
            "fonts/MontserratMedium.ttf").exists());
        // Symbol Assets
        assertTrue("Test that shop asset exists.", Gdx.files.internal(
            "Kenney-Game-Assets-1/2D assets/Game Icons/PNG/White/2x/shoppingCart.png").exists());
        assertTrue("Test that powerup asset exists.", Gdx.files.internal(
            "Kenney-Game-Assets-1/2D assets/Game Icons/PNG/White/2x/star.png").exists());
        assertTrue("Test that home asset exists.", Gdx.files.internal(
            "Kenney-Game-Assets-1/2D assets/Game Icons/PNG/White/2x/home.png").exists());
        assertTrue("Test that delete asset exists.", Gdx.files.internal(
            "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_crossWhite.png").exists());
    }

    @Test
    public void testFontAssetsExist() {
        assertTrue("Test that font exists", Gdx.files.internal(
            "fonts/MontserratMedium.ttf").exists());
        assertTrue("Test that italic font exists", Gdx.files.internal(
            "fonts/MontserratMedium.ttf").exists());
    }
}
