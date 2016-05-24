package ip4323.hommClass;

/**
 * Created by dima_ on 24.05.2016.
 */
public class CreateDragon implements CreateUnit {
    @Override
    /**
     *
     */
    public Unit createUnit(int count) {
        Unit unit = new Unit(5, count);
        unit.setDmg(5);
        unit.setDef(1);
        unit.setOvhp(10);
        unit.setSpeed(10);
        return unit;
    }
}
