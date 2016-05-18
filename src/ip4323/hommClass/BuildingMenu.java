package ip4323.hommClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by dima_ on 16.05.2016.
 */
public class BuildingMenu extends JFrame {
    private JFrame mainFrame;
    private JPanel controlPanel;
    private TownField tw;

    public void setTw(TownField tw) {
        this.tw = tw;
    }

    public BuildingMenu() {
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Меню");
        mainFrame.setSize(300, 400);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 1));

        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }

    protected void showButton(int pos) {
        JButton buttonBuild = new JButton("Build");
        JButton buttonBuy = new JButton("Buy");
        JButton buttonExit = new JButton("Exit");

        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
            }
        });

        buttonBuild.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tw.getTown().getBuildFlag()) {
                    tw.getTown().getBuildings()[pos]++;
                    tw.getTown().setBuildFlag(false);
                    if ((pos > 0) && (pos < 6)) {
                        tw.getTown().setBuyFlag(true, pos-1);
                    }
                }
                tw.repaint();
                mainFrame.dispose();
            }
        });

        buttonBuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tw.getTown().getBuyFlag(pos-1)) {
                    boolean flag = false;
                    for(int i=0; i<5; i++) {
                        if ((tw.getTown().getArmy()[i] != null) && (tw.getTown().getArmy()[i].getType() == pos)) {
                            tw.getTown().getArmy()[i].setCount(tw.getTown().getArmy()[i].getCount() + (6-pos)*(tw.getTown().getBuildings()[6]+1));
                            flag = true;
                            tw.getTown().setBuyFlag(false, pos-1);
                            break;
                        }
                    }
                    if (!flag){
                        for (int i = 0; i < 5; i++) {
                            if (tw.getTown().getArmy()[i] == null) {
                                tw.getTown().getArmy()[i] = new Unit(pos, (6-pos) * (tw.getTown().getBuildings()[6]+1));
                                tw.getTown().setBuyFlag(false, pos-1);
                                break;
                            }
                        }
                    }
                }
                tw.repaint();
                mainFrame.dispose();
            }
        });

        if ((pos == 0) && (tw.getTown().getBuildings()[pos] < 3)) {
            controlPanel.add(buttonBuild);
            controlPanel.add(buttonExit);
        } else if ((pos == 0) && (tw.getTown().getBuildings()[pos] == 3)) {
            controlPanel.add(buttonExit);
        } else if((pos == 6) && (tw.getTown().getBuildings()[pos] < 2)) {
            controlPanel.add(buttonBuild);
            controlPanel.add(buttonExit);
        } else if((pos == 6) && (tw.getTown().getBuildings()[pos] == 2)) {
                controlPanel.add(buttonExit);
        } else if((pos > 0) && (tw.getTown().getBuildings()[pos] == 0)) {
            controlPanel.add(buttonBuild);
            controlPanel.add(buttonExit);
        } else if((pos > 0) && (tw.getTown().getBuildings()[pos] == 1)) {
            controlPanel.add(buttonBuy);
            controlPanel.add(buttonExit);
        }

        mainFrame.setVisible(true);
    }
}
