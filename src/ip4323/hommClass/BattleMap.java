package ip4323.hommClass;

/**
 * Created by dima_ on 05.04.2016.
 */
public class BattleMap {
    private int[][] fieldType = new int[20][10];
    private Unit[][] fieldUnit = new Unit[20][10];
    public Unit[][] getFieldUnit() {
        return fieldUnit;
    }
    public int[][] getFieldType() {
        return fieldType;
    }
    public void generateField() {
        for (int i = 0; i < 10; i++) {
            fieldType[0][i] = (int)(Math.random()*7);
            fieldType[1][i] = (int)(Math.random()*7);
            fieldType[18][i] = (int)(Math.random()*7);
            fieldType[19][i] = (int)(Math.random()*7);
        }
        for (int i = 2; i < 18; i++) {
            for (int j = 0; j < 10; j++) {
                int tmp;
                tmp = (int)(Math.random()*10);
                fieldType[i][j] = tmp;
            }
        }
    }
}
