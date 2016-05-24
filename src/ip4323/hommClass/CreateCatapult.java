package ip4323.hommClass;

/**
 * Created by dima_ on 24.05.2016.
 */
public class CreateCatapult implements CreateUnit {
    @Override
    public Unit createUnit(int count) {
        Unit unit = new Unit(4, count);
        unit.setDmg(4);
        unit.setDef(0);
        unit.setOvhp(5);
        unit.setSpeed(6);
        return unit;
    }
}
