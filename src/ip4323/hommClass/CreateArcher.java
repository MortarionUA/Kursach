package ip4323.hommClass;

/**
 * Created by dima_ on 24.05.2016.
 */
public class CreateArcher implements CreateUnit {
    @Override
    public Unit createUnit(int count) {
        Unit unit = new Unit(2, count);
        unit.setDmg(3);
        unit.setDef(0);
        unit.setOvhp(4);
        unit.setSpeed(7);
        return unit;
    }
}
