package ip4323.hommClass;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dima_ on 19.05.2016.
 */
public class UnitInfo extends JFrame {

    private JFrame jf;

    Unit unit;

    public UnitInfo(Unit unit) throws HeadlessException {
        this.unit = unit;
        prepareGUI();
    }

    public void disappear() {
        jf.dispose();
    }

    public void prepareGUI() {
        jf = new JFrame("Unit info");
        jf.setSize(192, 64);
        jf.setUndecorated(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Dimension labelsize = new Dimension(64, 32);
        JLabel info1 = new JLabel("Info");
        info1.setVerticalAlignment(JLabel.TOP);
        info1.setHorizontalAlignment(JLabel.LEFT);
        info1.setPreferredSize(labelsize);
        JLabel info2 = new JLabel("Info");
        info2.setVerticalAlignment(JLabel.TOP);
        info2.setHorizontalAlignment(JLabel.LEFT);
        info2.setPreferredSize(labelsize);
        UnitPaint up = new UnitPaint(jf, 64, 64, unit);
        String text1 = "DMG " + unit.getDmg() + " DEF " + unit.getDef();
        String text2 = "HP " + unit.getHp() + "/" + unit.getOvhp() + " SPD " + unit.getSpeed();
        info1.setText(text1);
        info2.setText(text2);
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 64;
        c.ipady = 64;
        c.gridheight = 2;
        jf.add(up, c);
        c.gridheight = 1;
        c.ipady = 32;
        c.gridx = 1;
        c.gridy = 0;
        jf.add(info1, c);
        c.gridx = 1;
        c.gridy = 1;
        jf.add(info2, c);
        jf.setVisible(true);
    }
}
