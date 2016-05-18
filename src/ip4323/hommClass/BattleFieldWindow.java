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

        pane.setSize(1280, 1280);
        setResizable(false);

        add(pane, BorderLayout.CENTER);

        BattleField bf = new BattleField(this, 1280, 640);

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

