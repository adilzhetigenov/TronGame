package tron.gameplay;

import tron.db.DBTable;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Gameplay extends JPanel implements Runnable {

    public final DBTable DBTable;
    private final Timer timer;
    private final JLabel timeLabel;
    private final long timeOfStart;
    public Player p1;
    public Player p2;
    public KeyListener KeyL = new KeyListener();
    public Thread gameThread;
    public int FPS = 60;
    public Color p1Color;
    public Color p2Color;
    private boolean isGameOn = true;

    private Window window;

    public Gameplay(String p1, Color p1Color, String p2, Color p2Color, Window window) {
        this.window = window;
        this.window.setSize(1920, 1080);
        this.p1 = new Player(KeyL, p1, 700, 350);
        this.p2 = new Player(KeyL, p2, 700, 700);
        DBTable = new DBTable();
        JLabel p1NameLabel = new JLabel("Player 1:");
        p1NameLabel.setForeground(Color.white);
        JLabel p2NameLabel = new JLabel("Player 2:");
        p2NameLabel.setForeground(Color.white);
        JLabel p1Name = new JLabel();
        p1Name.setText(p1);
        p1Name.setForeground(p1Color);
        JLabel p2Name = new JLabel();
        p2Name.setText(p2);
        p2Name.setForeground(p2Color);
        this.p1Color = p1Color;
        this.p2Color = p2Color;
        this.setPreferredSize(new Dimension(1920, 1080));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(KeyL);
        this.setFocusable(true);
        this.add(p1NameLabel);
        this.add(p1Name);
        this.add(p2NameLabel);
        this.add(p2Name);
        timeLabel = new JLabel(" ", SwingConstants.RIGHT);
        timeLabel.setForeground(Color.white);
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);
        timer = new Timer(10, e -> timeLabel.setText(elapsedTime() / 1000 + " secs"));
        timeOfStart = System.currentTimeMillis();
        timer.start();
        this.add(timeLabel);
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (isGameOn) {
            renew();
            window.repaint();

            try {
                double remain = nextDrawTime - System.nanoTime();
                remain /= 1000000.0;
                if (remain < 0) {
                    remain = 0;
                }
                Thread.sleep((long) remain);
                nextDrawTime += drawInterval;
            } catch (InterruptedException ex) {
                Logger.getLogger(Gameplay.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void renew() {
        boolean p1Collision = p1.update(true);
        boolean p2Collision = p2.update(false);
        boolean p1CollidesP2 = p1.meets(p2);
        boolean p2CollidesP1 = p2.meets(p1);

        boolean isGameOver = p1Collision || p2Collision || p1CollidesP2 || p2CollidesP1;

        if (isGameOver) {
            isGameOn = false;
            timer.stop();

            ArrayList<Player> PlayersCollided = new ArrayList<>();

            if (p1Collision || p1CollidesP2) {
                PlayersCollided.add(p1);
            }
            if (p2Collision || p2CollidesP1) {
                PlayersCollided.add(p2);
            }

            String msg;
            if (PlayersCollided.size() == 1) {
                Player lost = PlayersCollided.get(0);
                Player won = (lost == p1) ? p2 : p1;
                msg = "Player " + won.player + " wins!";
                DBTable DBTable = new DBTable();
                DBTable.update(won.player);
            } else if (PlayersCollided.size() > 1) {
                msg = "Tie!";
            } else {
                msg = "Game is Over!";
            }

            int chose = JOptionPane.showOptionDialog(
                    window,
                    msg,
                    "Game is Over",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"Restart", "Menu"},
                    "Restart"
            );

            if (chose == JOptionPane.YES_OPTION) {
                restartGame();
            } else {
                window.dispose();
            }
            isGameOn = false;
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2D = (Graphics2D) graphics;
        p1.draw(g2D, p1Color);
        p2.draw(g2D, p2Color);
    }

    public long elapsedTime() {
        return (System.currentTimeMillis() - timeOfStart);
    }

    public void restartGame() {
        this.window.restartGame();
    }
}
