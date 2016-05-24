package ip4323.hommClass;

/**
 * Created by dima_ on 24.05.2016.
 */
public class CreateColosus implements CreateUnit {
    @Override
    public Unit createUnit(int count) {
        Unit unit = new Unit(3, count);
        unit.setDmg(3);
        unit.setDef(1);
        unit.setOvhp(7);
        unit.setSpeed(5);
        return unit;
    }
}
