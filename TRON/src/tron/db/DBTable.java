package tron.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class DBTable {
    String name = "scores";
    private Connection connection;

    public DBTable() {
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "password");
        properties.put("serverTimezone", "UTC");      try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TronDB", properties);
            createTable();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Can't connect to database!");
        }
    }

    public ArrayList<Score> getScores() {
        ArrayList<Score> scores = new ArrayList<>();
        try {
            var stmt = connection.createStatement();
            var resultSet = stmt.executeQuery(String.format("SELECT * FROM %s ORDER BY score DESC LIMIT 10", name));
            while (resultSet.next()) {
                var playerName = resultSet.getString("name");
                var playerScore = resultSet.getInt("score");
                scores.add(new Score(playerName, playerScore));
            }
        } catch (SQLException e) {
            System.out.println("Failed to get scores from the table!");
        }
        return scores;
    }

    private void createTable() {
        try {
            var stmt = connection.createStatement();
            stmt.executeUpdate(String.format("CREATE TABLE IF NOT EXISTS %s (name VARCHAR(255) PRIMARY KEY, score INT DEFAULT 0)", name));
        } catch (SQLException e) {
            System.out.println("Can't create the table!");
        }
    }

    public void update(String player) {
        try {
            var stmt = connection.createStatement();
            stmt.executeUpdate(String.format(
                "INSERT INTO %s (name, score) VALUES ('%s', 1) " +
                "ON DUPLICATE KEY UPDATE score = score + 1", name, player));
        } catch (Exception e) {
            System.out.println("Can't update score " + e.getMessage());
        }
    }
}