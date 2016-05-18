package ip4323.hommClass;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by dima_ on 18.05.2016.
 */
public class HeroFieldWindow extends Frame {
    public HeroFieldWindow(String s) {

        super(s);

        ScrollPane pane = new ScrollPane();

        pane.setSize(640, 192);
        setResizable(false);

        add(pane, BorderLayout.CENTER);

        Hero hero1 = new Hero(0, 0, 0);

        Unit newUnit = new Unit(1, 999);

        Unit army1[] = new Unit[]{null, newUnit, null, null, null};

        hero1.setAllArmy(army1);

        HeroField hf = new HeroField(this, 640, 512, hero1);

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