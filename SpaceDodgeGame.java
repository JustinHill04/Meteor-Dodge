import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Random;

public class SpaceDodgeGame extends JFrame {

    public SpaceDodgeGame() {
        setTitle("Meteor Dodge");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Must use a Layout Manager with a JFrame
        setLayout(new BorderLayout());

        GamePanel panel = new GamePanel();
        add(panel, BorderLayout.CENTER);
        
        setVisible(true);
    }

    public static void main(String[] args) {
        new SpaceDodgeGame();
    }
}

// Main game logic 
class GamePanel extends JPanel implements Runnable, KeyListener {

    private Player player;
    // Must incorporate Linked List
    private LinkedList<Meteor> meteors; 
    
    private Thread gameThread;
    private boolean isRunning = false;
    private int score = 0;
    private Random rand;
    
    // GUI 
    private JButton startButton;
    private JLabel scoreLabel;
    
    public GamePanel() {
        this.setFocusable(true);
        this.addKeyListener(this);
        this.setLayout(null); 

        rand = new Random();
        meteors = new LinkedList<>();
        player = new Player(375, 500);

        setupGUI();
    }

    private void setupGUI() {
        // Must use different elements: JButton
        startButton = new JButton("Start Game");
        startButton.setBounds(350, 250, 100, 40);
        startButton.addActionListener(e -> startGame());
        
        // Must use different elements: JLabel
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(10, 10, 150, 30);
        
        add(startButton);
        add(scoreLabel);
    }

    private void startGame() {
        startButton.setVisible(false);
        meteors.clear();
        player = new Player(375, 500);
        score = 0;
        isRunning = true;
         
        // Must incorporate Multithreading
        gameThread = new Thread(this);
        gameThread.start();
        
        // Return focus to panel so keys work
        this.requestFocusInWindow();
    }

    @Override
    public void run() {
        // Must incorporate Animation
        while (isRunning) {
            updateGame();
            repaint();
            
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        score++;
        scoreLabel.setText("Score: " + score);

        player.move();

        // Spawn Meteors
        if (rand.nextInt(100) < 5) {
            if (rand.nextBoolean()) {
                meteors.add(new Meteor(rand.nextInt(750), -20));
            } else {
                meteors.add(new Meteor(rand.nextInt(750), -20, 5 + rand.nextInt(5)));
            }
        }

        // Update Meteors
        for (int i = 0; i < meteors.size(); i++) {
            Meteor m = meteors.get(i);
            m.move();
            
            if (m.getBounds().intersects(player.getBounds())) {
                gameOver();
            }

            if (m.isOffScreen(600)) {
                meteors.remove(i);
                i--;
            }
        }
    }

    private void gameOver() {
        isRunning = false;
        
        // Must use different elements: JOptionPane
        String name = JOptionPane.showInputDialog(this, "Game Over! Score: " + score + "\nEnter Name:");
        
        // Throw exception and catch it
        try {
            saveScore(name, score);
        } catch (InvalidNameException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            try { saveScore("Anonymous", score); } catch (Exception ignored) {}
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }

        startButton.setText("Play Again");
        startButton.setVisible(true);
    }

    // Method that throws Custom Exception
    private void saveScore(String name, int score) throws InvalidNameException, IOException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidNameException("Name cannot be empty!");
        }

        // Must read/write something to a File
        FileWriter fw = new FileWriter("highscores.txt", true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println(name + ": " + score);
        pw.close();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Player
        if (player != null) player.draw(g);

        // Meteors
        for (Meteor m : meteors) {
            m.draw(g);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) player.setVelX(-5);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) player.setVelX(5);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setVelX(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}