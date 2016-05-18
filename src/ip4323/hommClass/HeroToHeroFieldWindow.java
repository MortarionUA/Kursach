package ip4323.hommClass;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by dima_ on 18.05.2016.
 */
public class HeroToHeroFieldWindow extends Frame {
    public HeroToHeroFieldWindow(String s) {

        super(s);

        ScrollPane pane = new ScrollPane();

        pane.setSize(640, 512);
        setResizable(false);

        add(pane, BorderLayout.CENTER);

        Hero hero1 = new Hero(0, 0, 0);

        Unit newUnit = new Unit(1, 999);

        Unit army1[] = new Unit[]{null, newUnit, null, null, null};

        hero1.setAllArmy(army1);

        Hero hero2 = new Hero(0, 0, 0);

        Unit army2[] = new Unit[]{null, newUnit, null, null, null};

        hero2.setAllArmy(army2);

        HeroToHeroField hf = new HeroToHeroField(this, 640, 512, hero1, hero2);

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
