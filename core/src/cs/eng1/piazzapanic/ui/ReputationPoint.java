package cs.eng1.piazzapanic.ui;

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
    public void setCustomPoints(int points_set){
        this.points = points_set;
    }
    public void addRepPoint(){
        if (this.points < 5){   // For testing, this is now at max 5 points
            this.points += 1;
            updateRepPoints();
        }
    }

    public void subRepPoint(){
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

