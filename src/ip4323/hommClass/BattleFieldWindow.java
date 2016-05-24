package ip4323.hommClass;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by dima_ on 11.05.2016.
 */
public class BattleFieldWindow extends Frame {
    public BattleFieldWindow(String s, Hero hero1, Hero hero2) {

        super(s);

        ScrollPane pane = new ScrollPane();

        pane.setSize(1280, 640);
        setResizable(false);

        add(pane, BorderLayout.CENTER);

        BattleField bf = new BattleField(this, 1280, 640, hero1, hero2);

        pane.add(bf);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e){

                dispose();

            }

        });

        pack();

        setVisible(true);
    }
}

