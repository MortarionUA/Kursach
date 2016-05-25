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
    private HeroToTownField htw;
    private Player player;

    public void setTw(TownField tw) {this.tw = tw;
    }
    public void setHtw(HeroToTownField htw) {this.htw = htw; }
    public void setPlayer(Player player) {this.player = player; }

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
                if(tw != null) {
                    if (tw.getTown().getBuildFlag() && (player.getMoney() > pos * 1000)) {
                        tw.getTown().getBuildings()[pos]++;
                        tw.getTown().setBuildFlag(false);
                        player.delmoney(pos * 1000);
                        if ((pos > 0) && (pos < 6)) {
                            tw.getTown().setBuyFlag(true, pos - 1);
                        }
                    }
                    tw.repaint();
                } else {
                    if (htw.getTown().getBuildFlag() && (player.getMoney() > pos * 1000)) {
                        htw.getTown().getBuildings()[pos]++;
                        htw.getTown().setBuildFlag(false);
                        player.delmoney(pos * 1000);
                        if ((pos > 0) && (pos < 6)) {
                            htw.getTown().setBuyFlag(true, pos - 1);
                        }
                    }
                    htw.repaint();
                }
                mainFrame.dispose();
            }
        });

        buttonBuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tw != null) {
                    if (tw.getTown().getBuyFlag(pos - 1)) {
                        boolean flag = false;
                        for (int i = 0; i < 5; i++) {
                            if ((tw.getTown().getArmy()[i] != null) && (tw.getTown().getArmy()[i].getType() == pos)) {
                                tw.getTown().getArmy()[i].setCount(tw.getTown().getArmy()[i].getCount() + (6 - pos) * (tw.getTown().getBuildings()[6] + 1));
                                flag = true;
                                tw.getTown().setBuyFlag(false, pos - 1);
                                break;
                            }
                        }
                        if (!flag) {
                            for (int i = 0; i < 5; i++) {
                                if ((tw.getTown().getArmy()[i] == null) && (player.getMoney() > (pos * (6 - pos) * (tw.getTown().getBuildings()[6] + 1)))){
                                    UnitFactory unitFactory = new UnitFactory();
                                    CreateUnit createUnit = unitFactory.getCreateUnit(pos);
                                    tw.getTown().getArmy()[i] = createUnit.createUnit((6 - pos) * (tw.getTown().getBuildings()[6] + 1));
                                    tw.getTown().setBuyFlag(false, pos - 1);
                                    player.delmoney(pos * (6 - pos) * (tw.getTown().getBuildings()[6] + 1));
                                    break;
                                }
                            }
                        }
                    }
                    tw.repaint();
                } else {
                    if (htw.getTown().getBuyFlag(pos - 1)) {
                        boolean flag = false;
                        for (int i = 0; i < 5; i++) {
                            if ((htw.getTown().getArmy()[i] != null) && (htw.getTown().getArmy()[i].getType() == pos)) {
                                htw.getTown().getArmy()[i].setCount(htw.getTown().getArmy()[i].getCount() + (6 - pos) * (htw.getTown().getBuildings()[6] + 1));
                                flag = true;
                                htw.getTown().setBuyFlag(false, pos - 1);
                                break;
                            }
                        }
                        if (!flag) {
                            for (int i = 0; i < 5; i++) {
                                if ((htw.getTown().getArmy()[i] == null) && (player.getMoney() > (pos * (6 - pos) * (htw.getTown().getBuildings()[6] + 1)))) {
                                    UnitFactory unitFactory = new UnitFactory();
                                    CreateUnit createUnit = unitFactory.getCreateUnit(pos);
                                    htw.getTown().getArmy()[i] = createUnit.createUnit((6 - pos) * (htw.getTown().getBuildings()[6] + 1));
                                    player.delmoney(pos * (6 - pos) * (htw.getTown().getBuildings()[6] + 1));
                                    htw.getTown().setBuyFlag(false, pos - 1);
                                    break;
                                }
                            }
                        }
                    }
                    htw.repaint();
                }
                mainFrame.dispose();
            }
        });

        if(tw != null) {
            if ((pos == 0) && (tw.getTown().getBuildings()[pos] < 3)) {
                controlPanel.add(buttonBuild);
                controlPanel.add(buttonExit);
            } else if ((pos == 0) && (tw.getTown().getBuildings()[pos] == 3)) {
                controlPanel.add(buttonExit);
            } else if ((pos == 6) && (tw.getTown().getBuildings()[pos] < 2)) {
                controlPanel.add(buttonBuild);
                controlPanel.add(buttonExit);
            } else if ((pos == 6) && (tw.getTown().getBuildings()[pos] == 2)) {
                controlPanel.add(buttonExit);
            } else if ((pos > 0) && (tw.getTown().getBuildings()[pos] == 0)) {
                controlPanel.add(buttonBuild);
                controlPanel.add(buttonExit);
            } else if ((pos > 0) && (tw.getTown().getBuildings()[pos] == 1)) {
                controlPanel.add(buttonBuy);
                controlPanel.add(buttonExit);
            }
        } else {
            if ((pos == 0) && (htw.getTown().getBuildings()[pos] < 3)) {
                controlPanel.add(buttonBuild);
                controlPanel.add(buttonExit);
            } else if ((pos == 0) && (htw.getTown().getBuildings()[pos] == 3)) {
                controlPanel.add(buttonExit);
            } else if ((pos == 6) && (htw.getTown().getBuildings()[pos] < 2)) {
                controlPanel.add(buttonBuild);
                controlPanel.add(buttonExit);
            } else if ((pos == 6) && (htw.getTown().getBuildings()[pos] == 2)) {
                controlPanel.add(buttonExit);
            } else if ((pos > 0) && (htw.getTown().getBuildings()[pos] == 0)) {
                controlPanel.add(buttonBuild);
                controlPanel.add(buttonExit);
            } else if ((pos > 0) && (htw.getTown().getBuildings()[pos] == 1)) {
                controlPanel.add(buttonBuy);
                controlPanel.add(buttonExit);
            }
        }

        mainFrame.setVisible(true);
    }
}
