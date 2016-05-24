package ip4323.hommClass;

/**
 * Created by dima_ on 10.04.2016.
 */
public class Player {
    private int money;
    private boolean isHeroBuy;

    public Player(int money) {
        this.money = money;
        isHeroBuy = true;
    }

    public void addmoney(int income) {
        money += income;
    }
    public void delmoney(int decome) {
        money -= decome;
    }

    public boolean isHeroBuy() {
        return isHeroBuy;
    }

    public void setHeroBuy(boolean heroBuy) {
        isHeroBuy = heroBuy;
    }
}
