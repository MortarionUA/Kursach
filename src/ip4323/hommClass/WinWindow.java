package ip4323.hommClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by dima_ on 26.05.2016.
 */
public class WinWindow extends JFrame {
    public WinWindow(int winner) {
        JFrame jf = new JFrame();
        jf.setResizable(false);
        jf.setUndecorated(true);
        jf.setSize(new Dimension(600, 400));
        jf.setLayout(new GridLayout(2,1));
        String winner1 = "Player1 won";
        String winner2 = "Player2 won";
        JLabel jl;
        if(winner == 1) {
            jl = new JLabel(winner1, SwingConstants.CENTER);
        }
        else {
            jl = new JLabel(winner2, SwingConstants.CENTER);
        }
        JButton buttonExit = new JButton("Exit");
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });

        jf.add(jl);
        jf.add(buttonExit);

        jf.setVisible(true);
    }
}
