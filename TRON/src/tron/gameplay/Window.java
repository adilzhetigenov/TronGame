package tron.gameplay;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private final String p1Color;
    private final String p2Color;
    private final String p1Name;
    private final String p2Name;

    public Window(String p1Name, String p1Color, String p2Name, String p2Color) {
        this.p1Color = p1Color;
        this.p2Color = p2Color;
        this.p1Name = p1Name;
        this.p2Name = p2Name;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Game Tron");
        setSize(1920, 1080);
        getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLUE));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Gameplay gameplay = new Gameplay(p1Name, MenuFrame.getColor(p1Color), p2Name, MenuFrame.getColor(p2Color), this);
        add(gameplay);

        gameplay.startGame();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void restartGame() {
        dispose();
        new Window(p1Name, p1Color, p2Name, p2Color);
    }
}
