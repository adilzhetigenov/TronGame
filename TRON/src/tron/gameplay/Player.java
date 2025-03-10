package tron.gameplay;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Player {

    String player;
    ArrayList<Integer> tailLine = new ArrayList<>();
    KeyListener keyListener;
    Point2D.Float position = new Point2D.Float();
    Point2D.Float speed = new Point2D.Float(3, 0);
    private static final int SPRITE_SIZE = 18;
    private static final int LINE_SIZE = 8;

    public Player(KeyListener keyListener, String player, int x, int y) {
        this.keyListener = keyListener;
        this.player = player;
        setInitPosition(x, y);
    }

    private void setInitPosition(int x, int y) {
        position.x = x;
        position.y = y;
    }

    public void draw(Graphics2D g2, Color c) {
        g2.setColor(c);
        for (int i = 0; i < tailLine.size() - 4; i += 4) {
            int x1 = tailLine.get(i) + 3;
            int y1 = tailLine.get(i + 1) + 3;
            g2.fillRect(x1, y1, 25, 25);
        }
        try {
            BufferedImage headIcon = ImageIO.read(new File("src/tron/Sprite.png"));
            BufferedImage resizedIcon = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
            Graphics2D iconGraphics = resizedIcon.createGraphics();
            iconGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            iconGraphics.drawImage(headIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH), 0, 0, null);
            iconGraphics.dispose();
            g2.drawImage(resizedIcon, (int) Math.floor(position.x), (int) Math.floor(position.y), null);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean update(boolean l) {
        if (l) {
            if (keyListener.isUpP1) {
                speed.y = -1;
                speed.x = 0;
            } else if (keyListener.isRightP1) {
                speed.x = 1;
                speed.y = 0;
            } else if (keyListener.isDownP1) {
                speed.y = 1;
                speed.x = 0;
            } else if (keyListener.isLeftP1) {
                speed.x = -1;
                speed.y = 0;
            }
        } else {
            if (keyListener.isUpP2) {
                speed.y = -1;
                speed.x = 0;
            } else if (keyListener.isRightP2) {
                speed.x = 1;
                speed.y = 0;
            } else if (keyListener.isDownP2) {
                speed.y = 1;
                speed.x = 0;
            } else if (keyListener.isLeftP2) {
                speed.x = -1;
                speed.y = 0;
            }
        }

        position.y += speed.y;
        position.x += speed.x;

        tailLine.add((int) position.x);
        tailLine.add((int) position.y);

        if (position.x + 7 >= 1510 || position.y + 7 >= 816 || position.x <= 1 || position.y <= 1) {
            System.out.println("collision detected!");
            return true;
        }

        return false;
    }

    public boolean meets(Player other) {
        Rectangle thisSprite = new Rectangle((int) position.x, (int) position.y, SPRITE_SIZE, SPRITE_SIZE);
        Rectangle otherSprite = new Rectangle((int) other.position.x, (int) other.position.y, SPRITE_SIZE, SPRITE_SIZE);

        if (thisSprite.intersects(otherSprite)) {
            System.out.println(player + " collided head-on with " + other.player);
            return true;
        }

        int trailBuffer = 3;

        for (int i = 0; i < other.tailLine.size(); i += 2) {
            Rectangle trailRect = new Rectangle(other.tailLine.get(i) + trailBuffer, other.tailLine.get(i + 1) + trailBuffer, LINE_SIZE, LINE_SIZE);
            if (thisSprite.intersects(trailRect)) {
                System.out.println(player + " collided with " + other.player + "'s line.");
                return true;
            }
        }

        return false;
    }

}
