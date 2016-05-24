package ip4323.hommClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MapEditorWindow extends Frame {
    public MapEditorWindow(String s) {

        super(s);

        ScrollPane pane = new ScrollPane();

        pane.setSize(1280, 1280);
        //setResizable(false);

        add(pane, BorderLayout.CENTER);

        MapEditor me = new MapEditor(this, 1280, 1280);

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
