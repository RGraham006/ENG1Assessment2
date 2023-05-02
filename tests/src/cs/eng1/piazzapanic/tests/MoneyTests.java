package cs.eng1.piazzapanic.tests;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import cs.eng1.piazzapanic.ui.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class MoneyTests {

    public Money initialiseMoney(){
        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        return new Money(labelStyle);
    }

    @Test
    public void test(){
        Money money = initialiseMoney();
        assertTrue(true);
    }

}
