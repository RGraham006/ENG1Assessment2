package cs.eng1.piazzapanic.tests;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import cs.eng1.piazzapanic.ui.Timer;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class TimerTests {

    public Timer initialiseTimer() {
        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        return new Timer(labelStyle);
    }

    @Test
    public void testTimerInit() {
        Timer timer = initialiseTimer();
        float initTime = timer.getTime();
        assertEquals(0f, initTime, 0f);
    }

    @Test
    public void testSetTimer() {
        Timer timer = initialiseTimer();
        float randomTime = (float) Math.random() * 100;
        timer.setTime(randomTime);
        assertEquals(randomTime, timer.getTime(), 0f);
    }

    @Test
    public void testTimerReset() {
        Timer timer = initialiseTimer();
        float randomTime = (float) Math.random() * 100;
        timer.setTime(randomTime);
        assertEquals(randomTime, timer.getTime(), 0f);
        timer.reset();
        assertEquals(0f, timer.getTime(), 0f);
    }

    @Test
    public void testTimerStarts() {
        Timer timer = initialiseTimer();
        assertFalse(timer.getIsRunning());
        timer.start();
        assertTrue(timer.getIsRunning());
    }

    @Test
    public void testTimerStops() {
        Timer timer = initialiseTimer();
        assertFalse(timer.getIsRunning());
        timer.start();
        assertTrue(timer.getIsRunning());
        timer.stop();
        assertFalse(timer.getIsRunning());
    }

}
