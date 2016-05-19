package ip4323.hommClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        controlPanel.setLayout(new GridLayout(7, 1));

        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }

    private void showButton() {
        JButton buttonBattle = new JButton("Battle");
        JButton buttonMapEditor = new JButton("Map Editor");
        JButton buttonExit = new JButton("Exit");
        JButton buttonTown = new JButton("Town");
        JButton buttonHero = new JButton("Hero");
        JButton buttonHeroToHero = new JButton("HeroToHero");
        JButton buttonHeroToTown = new JButton("HeroToTown");

        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        buttonHero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HeroFieldWindow("Hero");
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

        controlPanel.add(buttonBattle);
        controlPanel.add(buttonMapEditor);
        controlPanel.add(buttonTown);
        controlPanel.add(buttonHero);
        controlPanel.add(buttonHeroToHero);
        controlPanel.add(buttonHeroToTown);
        controlPanel.add(buttonExit);

        mainFrame.setVisible(true);
    }

    public static void main (String args[]) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.showButton();
    }
}
