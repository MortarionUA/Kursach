package ip4323.hommClass;

import javax.lang.model.type.NullType;

/**
 * Created by dima_ on 07.04.2016.
 */
public class Hero {
    private int faction;
    private int dmgBonus;
    private int defBonus;
    private int hpBonus;
    private int exp;
    private int posx;
    private int posy;
    private Unit[] army = new Unit[5];
    public Hero(int nposx, int nposy, int nfaction) {
        dmgBonus = 0;
        defBonus = 0;
        hpBonus = 0;
        exp = 0;
        posx = nposx;
        posy = nposy;
        faction = nfaction;
    }
    public int getFaction() {
        return faction;
    }
    public int getDmgBonus() {
        return dmgBonus;
    }
    public int getDefBonus() {
        return defBonus;
    }
    public int getHpBonus() {
        return hpBonus;
    }

    public Unit[] getArmy() {
        return army;
    }

    public void setArmy(Unit army, int pos) {
        this.army[pos] = army;
    }

    public void setAllArmy(Unit[] army) {
        this.army = army;
    }
    public void expadd(int nexp) {
        exp += nexp;
    }
    public void expdel(int nexp) {
        exp -= nexp;
    }
    public void levelup(int chose) {
        if (chose == 1) dmgBonus++;
        else if (chose == 2) defBonus++;
        else hpBonus++;
        expdel((dmgBonus + defBonus + hpBonus) * 1000);
    }
    public void putArmy(int type, int count, int pos) {
        if (army[pos].getType() == type) army[pos].setCount(army[pos].getType() + count);
        else {
            army[pos] = new Unit(type, count);
        }
    }
    public void delarmy(int pos) {
        army[pos] = null;
    }
    public void move() {
        posx--;
        posx++;
        posy--;
        posy++;
    }
}
