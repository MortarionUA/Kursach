package ip4323.hommClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by dima_ on 19.05.2016.
 */
public class HeroToTownFieldWindow extends JFrame {
    public HeroToTownFieldWindow (String s) {

        super(s);

        ScrollPane pane = new ScrollPane();

        pane.setSize(640, 512);
        setResizable(false);

        add(pane, BorderLayout.CENTER);

        Hero hero1 = new Hero(0, 0, 0);

        Unit newUnit = new Unit(1, 999);

        Unit army1[] = new Unit[]{null, newUnit, null, null, null};

        hero1.setAllArmy(army1);

        Town town = new Town(0, 0, 0);

        Unit army[] = new Unit[]{null, newUnit, null, null, null};

        town.setAllArmy(army);

        int[] buildings = new int[]{0, 0, 0, 0, 0, 0, 0};

        town.setBuildings(buildings);

        HeroToTownField hf = new HeroToTownField(this, 640, 512, hero1, town);

        pane.add(hf);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e){

                System.exit(0);

            }

        });

        pack();

        setVisible(true);
    }
}
