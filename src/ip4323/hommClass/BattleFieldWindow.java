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
        newUnit1.setSpeed(11);

        Unit army1[] = new Unit[]{null, newUnit1, null, null, null};

        hero1.setAllArmy(army1);

        Hero hero2 = new Hero(1, 1, 1);

        Unit newUnit2 = new Unit(2, 333);
        newUnit2.setSpeed(6);

        Unit army2[] = new Unit[]{null, null, null, null, newUnit2};

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

