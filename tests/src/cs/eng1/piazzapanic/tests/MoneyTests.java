package cs.eng1.piazzapanic.tests;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import cs.eng1.piazzapanic.ui.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class MoneyTests {

    public Money initialiseMoney(){
        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        return new Money(labelStyle);
    }

    @Test
    public void getMoneyTest(){
        Money money = initialiseMoney();
        assertTrue("Test that getMoney returns the Money count", money.getMoney() == 0);
    }

    @Test
    public void addMoneyTest(){
        Money money = initialiseMoney();
        Random randomNum = new Random();
        int oldMoney = money.getMoney();
        int number = randomNum.nextInt(100);
        money.addMoney(number);
        assertTrue("Test that addMoney increments the Money count", money.getMoney() == oldMoney+number);
    }

    @Test
    public void subtractMoneyTest(){
        Money money = initialiseMoney();
        Random randomNum = new Random();
        int oldMoney = money.getMoney();
        int number = randomNum.nextInt(100);
        money.subtractMoney(number);
        assertTrue("Test that subtractMoney increments the Money count", money.getMoney() == oldMoney-number);
    }

}
