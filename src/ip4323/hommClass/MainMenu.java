package ip4323.hommClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Created by dima_ on 11.05.2016.
 */
public class MainMenu extends JFrame {
    private JFrame mainFrame;
    private JPanel controlPanel;

    public MainMenu() {
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Меню");
        mainFrame.setSize(400, 600);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(9, 1));

        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }

    private void showButton() {
        JButton buttonBattle = new JButton("Battle");
        JButton buttonMapEditor = new JButton("Map Editor");
        JButton buttonExit = new JButton("Exit");
        JButton buttonTown = new JButton("Town");
        JButton buttonHeroToHero = new JButton("HeroToHero");
        JButton buttonHeroToTown = new JButton("HeroToTown");
        JButton buttonUnit = new JButton("Unit");
        JButton buttonGame = new JButton("Game");

        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
            }
        });

        buttonGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new GlobalMapWindow("Game");
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                mainFrame.dispose();
            }
        });

        buttonBattle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BattleFieldWindow("BattleField");
                mainFrame.dispose();
            }
        });

        buttonTown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //new TownFieldWindow("Town");
                mainFrame.dispose();
            }
        });

        buttonHeroToHero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HeroToHeroFieldWindow("HeroToHero");
                mainFrame.dispose();
            }
        });

        buttonMapEditor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MapEditorWindow("Map Editor");
                mainFrame.dispose();
            }
        });

        buttonHeroToTown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HeroToTownFieldWindow("HeroToTown");
                mainFrame.dispose();
            }
        });

        buttonUnit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Unit newUnit = new Unit(1, 333);
                new UnitInfo(newUnit);
                mainFrame.dispose();
            }
        });

        controlPanel.add(buttonGame);
        controlPanel.add(buttonBattle);
        controlPanel.add(buttonMapEditor);
        controlPanel.add(buttonTown);
        controlPanel.add(buttonHeroToHero);
        controlPanel.add(buttonHeroToTown);
        controlPanel.add(buttonUnit);
        controlPanel.add(buttonExit);

        mainFrame.setVisible(true);
    }

    public static void main (String args[]) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.showButton();
    }
}
