package tron.gameplay;

import tron.db.Score;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreBoard extends JDialog {

    public ScoreBoard(ArrayList<Score> scores, JFrame parent, String p1Name, String p2Name) {
        super(parent, "Score Board", true);

        setLayout(new BorderLayout());

        JPanel tablePanel = new JPanel(new BorderLayout());
        JTable table = new JTable(new TableScore(scores));
        table.setFillsViewportHeight(true);

        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablePanel.add(new JScrollPane(table));
        add(tablePanel, BorderLayout.CENTER);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        table.setRowSorter(sorter);

        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        table.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 13));
        table.setFont(new Font("Roboto", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(170, 215, 230));

        getContentPane().setBackground(new Color(240, 240, 240));

        setVisible(true);
    }
}
