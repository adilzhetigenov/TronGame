package tron.gameplay;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import tron.db.DBTable;
import tron.db.Score;

public class MenuFrame {
    private final JTextField p1Name;
    private final JTextField p2Name;
    private final JComboBox<String> p1Color;
    private final JComboBox<String> p2Color;
    private final String[] colors = {"orange", "green", "white", "gray", "red", "blue", "yellow"};

    public MenuFrame() {
        JFrame menu = new JFrame();
        menu.setTitle("Game Tron");
        menu.setSize(500, 500);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            String imagePath = "src/tron/background.png";
            ImagePanel backgroundPanel = new ImagePanel(ImageIO.read(new File(imagePath)));
            menu.setContentPane(backgroundPanel);
        } catch (IOException ex) {
            Logger.getLogger(MenuFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.setOpaque(false);

        JLabel player1Label = new JLabel("Player 1: ");
        JLabel player2Label = new JLabel("Player 2: ");
        player1Label.setForeground(Color.LIGHT_GRAY);
        player2Label.setForeground(Color.LIGHT_GRAY);

        p1Name = new JTextField(15);
        p2Name = new JTextField(15);
        p1Name.setMaximumSize(new Dimension(200, 30));
        p2Name.setMaximumSize(new Dimension(200, 30));

        namePanel.add(player1Label);
        namePanel.add(p1Name);
        namePanel.add(Box.createHorizontalStrut(20)); // Add spacing
        namePanel.add(player2Label);
        namePanel.add(p2Name);

        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.X_AXIS));
        colorPanel.setOpaque(false);

        JLabel color1Label = new JLabel("Color 1: ");
        JLabel color2Label = new JLabel("Color 2: ");
        color1Label.setForeground(Color.LIGHT_GRAY);
        color2Label.setForeground(Color.LIGHT_GRAY);

        p1Color = new JComboBox<>(colors);
        p2Color = new JComboBox<>(colors);
        p1Color.setMaximumSize(new Dimension(100, 30));
        p2Color.setMaximumSize(new Dimension(100, 30));

        p1Color.setSelectedItem("blue");
        p2Color.setSelectedItem("orange");

        updateColor(p1Color, p2Color);

        p1Color.addActionListener(e -> updateColor(p1Color, p2Color));
        p2Color.addActionListener(e -> updateColor(p2Color, p1Color));

        colorPanel.add(color1Label);
        colorPanel.add(p1Color);
        colorPanel.add(Box.createHorizontalStrut(20)); // Add spacing
        colorPanel.add(color2Label);
        colorPanel.add(p2Color);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton startButton = new JButton("Start Game");
        JButton showScoreButton = new JButton("Show Scoreboard");

        startButton.addActionListener(getActionListener());
        showScoreButton.addActionListener(e -> showScoreboard());

        buttonPanel.add(startButton);
        buttonPanel.add(showScoreButton);

        contentPanel.add(Box.createVerticalStrut(50)); // Add spacing
        contentPanel.add(namePanel);
        contentPanel.add(Box.createVerticalStrut(20)); // Add spacing
        contentPanel.add(colorPanel);
        contentPanel.add(Box.createVerticalStrut(20)); // Add spacing
        contentPanel.add(buttonPanel);

        menu.add(contentPanel);
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }

    private ActionListener getActionListener() {
        return e -> {
            if (isEmpty(p1Name.getText()) || isEmpty(p2Name.getText())) {
                makeWarning("Enter player names!");
            } else {
                String color1 = (String) p1Color.getSelectedItem();
                String color2 = (String) p2Color.getSelectedItem();
                new Window(p1Name.getText(), color1, p2Name.getText(), color2);
            }
        };
    }

    private void showScoreboard() {
        ArrayList<Score> scores = (new DBTable()).getScores();
        new ScoreBoard(scores, (JFrame) SwingUtilities.getWindowAncestor(p1Name),
                p1Name.getText(),
                p2Name.getText());
    }

    public static Color getColor(String colorName) {
        switch (colorName) {
            case "green":
                return Color.GREEN;
            case "orange":
                return Color.ORANGE;
            case "white":
                return Color.WHITE;
            case "gray":
                return Color.GRAY;
            case "red":
                return Color.RED;
            case "yellow":
                return Color.YELLOW;
            case "blue":
                return Color.BLUE;
            default:
                return Color.BLACK;
        }
    }

    private boolean isEmpty(String name) {
        return name.trim().isEmpty();
    }

    private void makeWarning(String message) {
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void updateColor(JComboBox<String> activeCombo, JComboBox<String> otherCombo) {
        String chosenColor = (String) activeCombo.getSelectedItem();
        String curChoice = (String) otherCombo.getSelectedItem();
        otherCombo.removeAllItems();
        for (String color : colors) {
            if (!color.equals(chosenColor)) {
                otherCombo.addItem(color);
            }
        }
        otherCombo.setSelectedItem(curChoice);
        if (otherCombo.getSelectedItem() == null && otherCombo.getItemCount() > 0) {
            otherCombo.setSelectedIndex(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuFrame::new);
    }

    private static class ImagePanel extends JPanel {
        private final Image backgroundImage;

        public ImagePanel(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
