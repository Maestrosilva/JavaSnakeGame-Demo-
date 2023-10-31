package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_HEIGHT = 600;
    static final int SCREEN_WIDTH = 600;
    static final int UNITS_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_HEIGHT * SCREEN_WIDTH / UNITS_SIZE);
    static final int DELAY_ON_EASY = 150;
    static final int DELAY_ON_NORMAL = 115;
    static final int DELAY_ON_HARD = 80;
    final int[] x = new int[SCREEN_WIDTH];
    final int[] y = new int[SCREEN_HEIGHT];
    static final int INITIAL_SNAKE_PARTS = 5;
    static final char INITIAL_DIRECTION = 'R';
    int delay;
    int snakeParts;
    int applesEaten;
    int appleX;
    int appleY;
    char direction;
    static Random random = new Random();
    Timer timer;
    boolean running = false;

    boolean gameStarted = false;
    JComboBox<String> comboBox;
    JButton startButton;
    JButton playAgainButton;
    ImageIcon image;

    int finished;

    public GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(new Adapter());
        this.snakeParts = INITIAL_SNAKE_PARTS;
        this.direction = INITIAL_DIRECTION;
    }
    public void startGame(){
        newApple();
        if(comboBox.getSelectedItem().equals("Easy")){
            delay = DELAY_ON_EASY;
        }
        else if(comboBox.getSelectedItem().equals("Normal")){
            delay = DELAY_ON_NORMAL;
        }
        else if(comboBox.getSelectedItem().equals("Hard")){
            delay = DELAY_ON_HARD;
        }
        timer = new Timer(delay, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNITS_SIZE; i++) {
                g.drawLine(i * UNITS_SIZE, 0, i * UNITS_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNITS_SIZE, SCREEN_WIDTH, i * UNITS_SIZE);
            }
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNITS_SIZE, UNITS_SIZE);
            for (int i = 0; i < snakeParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(38, 183, 40));
                    g.fillRect(x[i], y[i], UNITS_SIZE, UNITS_SIZE);
                } else {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNITS_SIZE, UNITS_SIZE);
                }
            }
            String score = "Score: ";
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString(score + applesEaten, (SCREEN_WIDTH - metrics.stringWidth(score + applesEaten))/ 2, metrics.getFont().getSize());
        }
        else if(gameStarted){
            finished = 1;
            endOfTheGame(g);
        }
    }
    public void setStartButton(JButton b){
        startButton = b;
    }
    public void setPlayAgainButton(JButton b){
        playAgainButton = b;
    }
    public void move(){
        for (int i = snakeParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U' -> y[0] = y[0] - UNITS_SIZE;
            case 'D' -> y[0] = y[0] + UNITS_SIZE;
            case 'L' -> x[0] = x[0] - UNITS_SIZE;
            case 'R' -> x[0] = x[0] + UNITS_SIZE;
        }
    }
    public void checkForApple(){
        if(x[0] == appleX && y[0] == appleY){
            applesEaten++;
            snakeParts++;
            newApple();
        }
    }
    public void newApple(){
        appleX = random.nextInt(SCREEN_WIDTH / UNITS_SIZE) * UNITS_SIZE;
        appleY = random.nextInt(SCREEN_WIDTH / UNITS_SIZE) * UNITS_SIZE;
    }
    public void checkForCollisions(){
        checkForSnakeCollisions();
        checkForBorderCollisions();
    }
    public void endOfTheGame(Graphics g){
        if(finished  == 1) {
            String message = "Game Over!";
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 80));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString(message, (SCREEN_WIDTH - metrics.stringWidth(message)) / 2, SCREEN_HEIGHT / 2 - 50);
            String scoreMessage = "Final score:";
            String score = String.valueOf(applesEaten);
            g.setFont(new Font("Ink Free", Font.BOLD, 50));
            g.drawString(scoreMessage, (SCREEN_WIDTH - metrics.stringWidth(scoreMessage) - 30), SCREEN_HEIGHT / 2 + 50);
            g.drawString(score, (SCREEN_WIDTH - metrics.stringWidth(score)) / 2, SCREEN_HEIGHT / 2 + 130);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == playAgainButton){
            playAgainButton.setVisible(false);
            new GamePanel();
        }
        if(!gameStarted){
            if (e.getSource() == startButton) {
                this.setBackground(Color.BLACK);
                startButton.setVisible(false);
                startButton.setEnabled(false);
                comboBox.setVisible(false);
                comboBox.setEnabled(false);
                gameStarted = true;
                running = true;
                startGame();
                startButton.setVisible(false);
            }
        }
        if(running){
            move();
            checkForApple();
            checkForCollisions();
        }
        repaint();
    }
    public void setDifficultyButton(JComboBox<String> c) {
        comboBox = c;
    }

    public void image(ImageIcon image) {
        this.image = image;
    }

    public class Adapter extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT, KeyEvent.VK_D:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_S, KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_A, KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_W, KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
            }
        }
    }
    private void checkForSnakeCollisions() {
        for (int i = snakeParts; i > 0; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
    }
    private void checkForBorderCollisions() {
        if(x[0] < 0){
            running = false;
        }
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        if(y[0] < 0){
            running = false;
        }
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }
}
