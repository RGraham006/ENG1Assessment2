package cs.eng1.piazzapanic.tests;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import cs.eng1.piazzapanic.ui.ReputationPoint;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class ReputationPointsTests {

    public ReputationPoint initialiseReputationPoint(){
        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        return new ReputationPoint(labelStyle);
    }

    @Test
    public void testPointSub() {
        ReputationPoint reputationPoint = initialiseReputationPoint();
        int initPoints = reputationPoint.getPoints();
        reputationPoint.subRepPoint();
        assertEquals(initPoints - 1, reputationPoint.getPoints());
    }

    @Test
    public void testPointAdd() {
        ReputationPoint reputationPoint = initialiseReputationPoint();
        reputationPoint.subRepPoint();
        int initPoints = reputationPoint.getPoints();
        reputationPoint.addRepPoint();
        assertEquals(initPoints + 1, reputationPoint.getPoints());
    }

    @Test
    public void testCannotAddPoint() {
        ReputationPoint reputationPoint = initialiseReputationPoint();
        int initPoints = reputationPoint.getPoints();
        reputationPoint.addRepPoint();;
        assertEquals(initPoints, reputationPoint.getPoints());
    }

    @Test
    public void testCannotSubPoint() {
        ReputationPoint reputationPoint = initialiseReputationPoint();
        int initPoints = reputationPoint.getPoints();
        for (int i = 0; i < initPoints; i++) {
            reputationPoint.subRepPoint();
        }
        int minPoints = reputationPoint.getPoints();
        assertEquals(0, minPoints);
        reputationPoint.subRepPoint();
        assertEquals(minPoints, reputationPoint.getPoints());
    }

    @Test
    public void testGameOver() {
        ReputationPoint reputationPoint = initialiseReputationPoint();
        int initPoints = reputationPoint.getPoints();
        for (int i = 0; i < initPoints; i++) {
            reputationPoint.subRepPoint();
        }
        int minPoints = reputationPoint.getPoints();
        assertEquals(0, minPoints);
        assertTrue(reputationPoint.isGameOver());
    }

    @Test
    public void testInitPointsAtThree() {
        ReputationPoint reputationPoint = initialiseReputationPoint();
        assertEquals(3, reputationPoint.getPoints());
    }

    @Test
    public void testSetCustomPoints() {
        ReputationPoint reputationPoint = initialiseReputationPoint();
        int randomPoints = ((int) Math.random() * 2) + 1;
        reputationPoint.setCustomPoints(randomPoints);
        assertEquals(randomPoints, reputationPoint.getPoints());
    }

}
