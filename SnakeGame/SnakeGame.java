package SnakeGame;

import javax.swing.*;
import java.awt.*;

public class SnakeGame extends JFrame{
    static JButton startButton;
    GamePanel panel;
    static JButton playAgainButton;
    public void run(){
        panel = new GamePanel();
        setImage();
        panel.setStartButton(getStartButton());
        panel.setDifficultyButton(getComboCox());
        panel.image(setImage());
        this.setBackground(new Color(192, 178, 178));
        this.add(panel);
        this.setTitle("Snake");
        this.setResizable(false);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public ImageIcon setImage(){
        ImageIcon image = new ImageIcon("img_1.png");
        JLabel displayImage = new JLabel(image);
        displayImage.setBounds(150, 175, 300, 300);
        displayImage.setVisible(true);
        this.add(displayImage);
        return image;
    }

    public JButton getStartButton(){
        startButton = new JButton();
        startButton.setBounds(150, 500, 300, 60);
        startButton.setText("Start Game");
        startButton.addActionListener(this.panel);
        startButton.setFont(new Font("Ink Free", Font.BOLD, 35));
        this.add(startButton);
        return startButton;
    }
    public JComboBox<String> getComboCox(){
        JComboBox<String> box = new JComboBox<>(new String[]{"Easy", "Normal", "Hard"});
        box.setBounds(150, 50, 300, 100);
        box.setFont(new Font("Ink Free", Font.BOLD, 40));
        this.add(box);
        return box;
    }
}
