package cs.eng1.piazzapanic.ui;


public class Money  {


    private int gold;


    public Money() {
        gold = 0;

    }


    public int showBalance(){
        return gold;
    }

    public void addGold(int i){
        gold += i;
    }

    public void subtract(int i){
        gold -= i;
    }
}
