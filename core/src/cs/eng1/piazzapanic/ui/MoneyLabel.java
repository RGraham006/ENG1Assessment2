package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import cs.eng1.piazzapanic.PiazzaPanicGame;

public class MoneyLabel extends Label {

    private final PiazzaPanicGame game;

    private String labelText;

    public MoneyLabel(Label.LabelStyle labelStyle, PiazzaPanicGame game) {
        super("Money : 0", labelStyle);
        labelText = "Money : 0";
        this.game = game;
    }

    public void act(){
        labelText = game.money.toString();
        this.setText("Money : " + labelText);
    }

}
