package ip4323.hommClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by dima_ on 19.05.2016.
 */
public class HeroToTownFieldWindow extends JFrame {
    public HeroToTownFieldWindow (String s, Hero hero, Town town, Player player) {

        super(s);

        ScrollPane pane = new ScrollPane();

        pane.setSize(640, 512);
        setResizable(false);

        add(pane, BorderLayout.CENTER);

        HeroToTownField hf = new HeroToTownField(this, 640, 512, hero, town, player);

        pane.add(hf);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e){

                dispose();

            }

        });

        pack();

        setVisible(true);
    }
}
