package ip4323.hommClass;

import java.io.Serializable;

/**
 * Created by dima_ on 05.04.2016.
 */
public class Unit implements Serializable {
    private int type;
    private int ovhp;
    private int hp;
    private int dmg;
    private int def;
    private int count;
    private int speed;
    private int posx;
    private int posy;
    private int playerHave;
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getPlayerHave() {
        return playerHave;
    }
    public void setPlayerHave(int playerHave) {
        this.playerHave = playerHave;
    }
    public int getOvhp() {
        return ovhp;
    }
    public void setOvhp(int ovhp) {
        this.ovhp = ovhp;
    }
    public int getHp() {
        return hp;
    }
    public int getDmg() {
        return dmg;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public void setDmg(int dmg) {
        this.dmg = dmg;
    }
    public void setDef(int def) {
        this.def = def;
    }
    public int getDef() {
        return def;
    }
    public void setPosx(int posx) {
        this.posx = posx;
    }
    public void setPosy(int posy) {
        this.posy = posy;
    }
    public int getPosx() {
        return posx;
    }
    public int getPosy() {
        return posy;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    Unit(int ntype, int ncount) {
        type = ntype;
        count = ncount;
    }
    public void move() {
        posx++;
        posy++;
        posx--;
        posy--;
    }
    public Unit attack(Unit attacked) {
        int tmpdmg;
        tmpdmg = (dmg - attacked.def)*count;
        if (tmpdmg < 1) {
            tmpdmg = (attacked.dmg - def)*attacked.count;
            do {
                count--;
                tmpdmg -= hp;
            }
            while (tmpdmg > hp);
            return attacked;
        }
        if (tmpdmg >= attacked.hp*attacked.count) {
            return null;
        }
        do {
            attacked.count--;
            attacked.hp = attacked.ovhp;
            tmpdmg -= attacked.hp;
        }
        while (tmpdmg >= attacked.hp);
        attacked.hp -= tmpdmg;
        tmpdmg = (attacked.dmg - def)*attacked.count;
        do {
            count--;
            tmpdmg -= hp;
        }
        while (tmpdmg >= hp);
        hp -= tmpdmg;
        return attacked;
    }
}
