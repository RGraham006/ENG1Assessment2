package cs.eng1.piazzapanic.ui;

public class Money {
    private int money;

    public void Money(){
        money = 0;
    }

    public int getMoney(){
        return money;
    }

    public void setMoney(int value){
        money = money + value;
    }

    public String toString(){
        String moneyString = String.valueOf(money);
        return moneyString;
    }
}
