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
}
