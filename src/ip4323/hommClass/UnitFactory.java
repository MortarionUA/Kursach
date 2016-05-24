package ip4323.hommClass;

/**
 * Created by dima_ on 24.05.2016.
 */
public class UnitFactory {
    public CreateUnit getCreateUnit(int unitType) {
        if(unitType == 1) {
            return new CreateSoldier();
        } else if(unitType == 2) {
            return new CreateArcher();
        } else if(unitType == 3) {
            return new CreateColosus();
        } else if(unitType == 4) {
            return new CreateCatapult();
        } else if(unitType == 5) {
            return new CreateDragon();
        }
        return null;
    }
}
