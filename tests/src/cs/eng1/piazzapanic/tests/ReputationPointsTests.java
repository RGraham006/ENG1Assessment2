package cs.eng1.piazzapanic.tests;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import cs.eng1.piazzapanic.ui.ReputationPoint;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class ReputationPointsTests {

    public ReputationPoint initialiseReputationPoint(){
        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        return new ReputationPoint(labelStyle);
    }

    @Test
    public void test(){
        ReputationPoint reputationPoint = initialiseReputationPoint();
        assertTrue(true);
    }

}
