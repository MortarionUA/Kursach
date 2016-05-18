package ip4323.hommClass;

/**
 * Created by dima_ on 10.04.2016.
 */
public class Player {
    private static int faction;
    private int money;
    private Hero[] heroes;
    public void addmoney(int income) {
        money += income;
    }
    public void delmoney(int decome) {
        money -= decome;
    }
    public void addHero(int posx, int posy) {
        heroes[heroes.length] = new Hero(posx, posy, faction);
    }
    public void delHero(int name) {
        heroes[name - 1] = null;
    }
}
