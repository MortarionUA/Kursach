package ip4323.hommClass;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dima_ on 10.04.2016.
 */
public class Map implements Serializable {
    private int[][] mapTerr = new int[20][20];
    private ArrayList<Town> mapTown = new ArrayList<>();
    private ArrayList<Hero> mapHero = new ArrayList<>();
    private Player admin;

    public int getTownCount() {
        return mapTown.size();
    }

    public ArrayList<Town> getMapTown() {
        return mapTown;
    }

    public ArrayList<Hero> getMapHero() {
        return mapHero;
    }

    public void setMapTerr(int tempX, int tempY, int typeTerr) {
        mapTerr[tempX][tempY] = typeTerr;
    }

    public int[][] getMapTerr() {
        return mapTerr;
    }

    public void newMap() {
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                mapTerr[i][j] = 0;
            }
        }
    }

    public void addTown(int posX, int posY, int faction) {
        if ((findTown(posX, posY) == Integer.MAX_VALUE) && ((findTown(posX, posY+1) == Integer.MAX_VALUE)) && ((findTown(posX, posY-1) == Integer.MAX_VALUE))) {
            mapTown.add(new Town(posX, posY, faction));
        } else {
            admin.addmoney(1000000);
            TownFieldWindow tf = new TownFieldWindow("Town", mapTown.get(findTown(posX, posY)), admin);
        }
    }

    public void delTown(int posX, int posY) {
        if (findTown(posX, posY) == Integer.MAX_VALUE) {
        }
        else mapTown.remove(findTown(posX, posY));
    }

    public int findTown(int posX, int posY) {
        for(int i = 0; i < mapTown.size(); i++) {
            if ((mapTown.get(i).getPosX() == posX) && (mapTown.get(i).getPosY() == posY)) {
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }

    public void addHero(int posX, int posY, int faction) {
        if ((findHero(posX, posY) == Integer.MAX_VALUE) && ((findHero(posX, posY+1) == Integer.MAX_VALUE)) && ((findHero(posX, posY-1) == Integer.MAX_VALUE))) {
            mapHero.add(new Hero(posX, posY, faction));
            UnitFactory unitFactory = new UnitFactory();
            CreateUnit createUnit = unitFactory.getCreateUnit(1);
            mapHero.get(findHero(posX, posY)).getArmy()[0] = createUnit.createUnit(1);
        } else {
            HeroFieldWindow tf = new HeroFieldWindow("Hero", mapHero.get(findHero(posX, posY)));
        }
    }

    public void delHero(int posX, int posY) {
        if (findHero(posX, posY) != Integer.MAX_VALUE) mapHero.remove(findHero(posX, posY));
    }

    public int findHero(int posX, int posY) {
        for(int i = 0; i < mapHero.size(); i++) {
            if ((mapHero.get(i).getPosX() == posX) && (mapHero.get(i).getPosY() == posY)) {
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }
}
