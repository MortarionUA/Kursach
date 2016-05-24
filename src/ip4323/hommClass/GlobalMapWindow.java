package ip4323.hommClass;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Created by dima_ on 22.05.2016.
 */
public class GlobalMapWindow extends Frame {
    public GlobalMapWindow(String s) throws IOException, ClassNotFoundException {

        super(s);

        ScrollPane pane = new ScrollPane();

        pane.setSize(1280, 960);
        setResizable(false);

        add(pane, BorderLayout.CENTER);

        GlobalMap me = new GlobalMap(this, 1280, 960);

        pane.add(me);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e){

                System.exit(0);

            }

        });

        pack();

        setVisible(true);
    }
}
