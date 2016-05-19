package ip4323.hommClass;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dima_ on 10.04.2016.
 */
public class Map implements Serializable {
    private int[][] mapTerr = new int[20][20];
    private Hero[][] mapHero = new Hero[20][20];
    private ArrayList<Town> mapTown = new ArrayList<>();

    public int getTownCount() {
        return mapTown.size();
    }

    public ArrayList<Town> getMapTown() {
        return mapTown;
    }

    public Hero[][] getMapHero() {
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

    public void addTown(int posX, int posY) {
        if ((findTown(posX, posY) == Integer.MAX_VALUE) && ((findTown(posX, posY+1) == Integer.MAX_VALUE)) && ((findTown(posX, posY-1) == Integer.MAX_VALUE))) {
            mapTown.add(new Town(posX, posY, 0));
        } else {
            TownFieldWindow tf = new TownFieldWindow("Town", mapTown.get(findTown(posX, posY)));
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
}
