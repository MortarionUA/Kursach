package ip4323.hommClass;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by dima_ on 18.05.2016.
 */
public class HeroToHeroFieldWindow extends Frame {
    public HeroToHeroFieldWindow(String s, Hero hero1, Hero hero2) {

        super(s);

        ScrollPane pane = new ScrollPane();

        pane.setSize(640, 512);
        setResizable(false);

        add(pane, BorderLayout.CENTER);

        HeroToHeroField hf = new HeroToHeroField(this, 640, 512, hero1, hero2);

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
