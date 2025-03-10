package tron.gameplay;

import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {
    public boolean isUpP1, isDownP1, isLeftP1, isRightP1, isUpP2, isDownP2, isLeftP2, isRightP2;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            isUpP1 = true;
        }
        if (code == KeyEvent.VK_A) {
            isLeftP1 = true;
        }
        if (code == KeyEvent.VK_S) {
            isDownP1 = true;
        }
        if (code == KeyEvent.VK_D) {
            isRightP1 = true;
        }

        if (code == KeyEvent.VK_UP) {
            isUpP2 = true;
        }
        if (code == KeyEvent.VK_LEFT) {
            isLeftP2 = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            isDownP2 = true;
        }
        if (code == KeyEvent.VK_RIGHT) {
            isRightP2 = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            isUpP1 = false;
        }
        if (code == KeyEvent.VK_A) {
            isLeftP1 = false;
        }
        if (code == KeyEvent.VK_S) {
            isDownP1 = false;
        }
        if (code == KeyEvent.VK_D) {
            isRightP1 = false;
        }

        if (code == KeyEvent.VK_UP) {
            isUpP2 = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            isLeftP2 = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            isDownP2 = false;
        }
        if (code == KeyEvent.VK_RIGHT) {
            isRightP2 = false;
        }
    }
}
