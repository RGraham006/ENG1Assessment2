package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ReputationPoint extends Label {
    
    private int points = 3;
    private boolean gameover = false;
    

    public ReputationPoint(Label.LabelStyle labelStyle){
        super("Reputation Points: 3", labelStyle);
    }

    public int getPoints(){
        return this.points;
    }

    public void addRepPoint(){
        if (this.points < 3){
            this.points += 1;
            updateRepPoints();
        }
    }

    public void removeRepPoint(){
        if (this.points > 0){
            this.points -= 1;
            updateRepPoints();
        }
        if (this.points == 0){
            this.gameover = true;
        }
    }

    public boolean isGameOver(){
        return this.gameover;
    }

    public void updateRepPoints(){
        this.setText("Reputation Points: " + this.points);
    }
}

