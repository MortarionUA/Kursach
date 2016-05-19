package ip4323.hommClass;

/**
 * Created by dima_ on 15.05.2016.
 */
import java.awt.*;
import java.awt.event.*;

public class TownFieldWindow extends Frame {

    private Town town;

    public TownFieldWindow(String s, Town town) {

        super(s);

        ScrollPane pane = new ScrollPane();

        pane.setSize(576, 256);
        setResizable(false);

        add(pane, BorderLayout.CENTER);
//
//        Town town = new Town(0, 0, 0);
//
//        Unit newUnit = new Unit(1, 333);
//
//        Unit army[] = new Unit[]{null, newUnit, null, null, null};
//
//        town.setAllArmy(army);
//
//        int[] buildings = new int[]{0, 0, 0, 0, 0, 0, 0};
//
//        town.setBuildings(buildings);

        this.town = town;

        TownField tf = new TownField(this, 576, 256, town);

        pane.add(tf);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e){

                System.exit(0);

            }

        });

        pack();

        setVisible(true);
    }
}
