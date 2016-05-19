package ip4323.hommClass;

import java.io.Serializable;

/**
 * Created by dima_ on 08.04.2016.
 */
public class Town implements Serializable{
    private int faction;
    private boolean buyFlag[] = new boolean[] {false, false, false, false, false};
    private boolean buildFlag = true;
    private int posX, posY;
    private int[] buildings;
    private Unit[] army;
    public void build(int type) {
        if (type == 0) buildings[type]++;
        else if (type == 1) buildings[type]++;
        else buildings[type] = 1;
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

    public boolean getBuyFlag(int pos) {
        return buyFlag[pos];
    }

    public void setBuyFlag(boolean buyFlag, int pos) {
        this.buyFlag[pos] = buyFlag;
    }

    public boolean getBuildFlag() {
        return buildFlag;
    }

    public void setBuildFlag(boolean buildFlag) {
        this.buildFlag = buildFlag;
    }

    public int getFaction() {
        return faction;
    }

    public void setFaction(int faction) {
        this.faction = faction;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int[] getBuildings() {
        return buildings;
    }

    public void setBuildings(int[] buildings) {
        this.buildings = buildings;
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

    public Town(int posX, int posY, int faction) {
        this.posX = posX;
        this.posY = posY;
        this.faction = faction;
        buildings = new int[]{0, 0, 0, 0, 0, 0, 0};
        army = new Unit[]{null, null, null, null, null};
    }
}
