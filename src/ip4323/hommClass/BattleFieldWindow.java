package ip4323.hommClass;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by dima_ on 11.05.2016.
 */
public class BattleFieldWindow extends Frame {
    public BattleFieldWindow(String s) {

        super(s);

        ScrollPane pane = new ScrollPane();

        pane.setSize(1280, 640);
        setResizable(false);

        add(pane, BorderLayout.CENTER);

        Hero hero1 = new Hero(0, 0, 0);

        Unit newUnit1 = new Unit(1, 999);
        newUnit1.setSpeed(7);
        newUnit1.setDmg(1);
        newUnit1.setOvhp(1);

        Unit newUnit3 = new Unit(3, 222);
        newUnit3.setSpeed(5);
        newUnit3.setDmg(3);
        newUnit3.setDef(1);
        newUnit3.setOvhp(5);

        Unit newUnit4 = new Unit(4, 111);
        newUnit4.setSpeed(4);
        newUnit4.setDmg(7);
        newUnit4.setOvhp(2);

        Unit army1[] = new Unit[]{null, newUnit1, newUnit3, null, newUnit4};

        hero1.setAllArmy(army1);

        Hero hero2 = new Hero(1, 1, 1);

        Unit newUnit2 = new Unit(2, 333);
        newUnit2.setSpeed(6);
        newUnit2.setDmg(3);
        newUnit2.setOvhp(2);

        Unit newUnit5 = new Unit(5, 55);
        newUnit5.setSpeed(12);
        newUnit5.setDmg(12);
        newUnit5.setDef(1);
        newUnit5.setOvhp(12);

        Unit newUnit6 = new Unit(2, 555);
        newUnit6.setSpeed(6);
        newUnit6.setDmg(3);
        newUnit6.setOvhp(2);

        Unit army2[] = new Unit[]{newUnit6, null, newUnit5, null, newUnit2};

        hero2.setAllArmy(army2);

        BattleField bf = new BattleField(this, 1280, 640, hero1, hero2);

        pane.add(bf);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e){

                System.exit(0);

            }

        });

        pack();

        setVisible(true);
    }
}

