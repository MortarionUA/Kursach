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
        mainFrame.setSize(300, 400);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 1));

        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }

    private void showButton() {
        JButton buttonNewGame = new JButton("New Game");
        JButton buttonMapEditor = new JButton("Map Editor");
        JButton buttonExit = new JButton("Exit");

        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
            }
        });

        buttonNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //new TownFieldWindow("Battle");
                new HeroFieldWindow("Hero");
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

        controlPanel.add(buttonNewGame);
        controlPanel.add(buttonMapEditor);
        controlPanel.add(buttonExit);

        mainFrame.setVisible(true);
    }

    public static void main (String args[]) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.showButton();
    }
}
