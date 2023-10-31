package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test extends JFrame implements ActionListener {
    JButton button;
    Test(){
        JComboBox<String> j = new JComboBox<>(new String[]{"a", "b"});
        j.setBounds(100, 100, 100, 100);
        j.setLocation(200, 100);
        j.setSize(100, 20);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setVisible(true);
        this.setLayout(null);
        this.add(j);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
