package cs.eng1.piazzapanic.tests;

import com.badlogic.gdx.scenes.scene2d.Stage;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.ui.UIOverlay;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class UIOverlayTests {

    public UIOverlay initialiseUIOverlay(){
        return new UIOverlay(Mockito.mock(Stage.class), Mockito.mock(PiazzaPanicGame.class));
    }

    @Test
    public void test(){
        UIOverlay uiOverlay = initialiseUIOverlay();
        assertTrue(true);
    }

}
