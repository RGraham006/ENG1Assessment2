package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Money extends Label {

    private int money = 0;

    public Money(Label.LabelStyle labelStyle) {
        super("Money: 0", labelStyle);
    }

    public int getMoney(){
        return money;
    }

    public void addMoney(int i){
        money += i;
    }

    public void subtractMoney(int i){
        money -= i;
    }
}
