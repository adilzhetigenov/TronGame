package tron.gameplay;

import tron.db.Score;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


public class TableScore extends AbstractTableModel {

    private final String[] columnName = new String[]{"Name", "Score"};
    private final ArrayList<Score> scores;

    public TableScore(ArrayList<Score> scores) {
        this.scores = scores;
    }

    @Override
    public int getRowCount() {
        return scores.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }


    @Override
    public String getColumnName(int i) {
        return columnName[i];
    }
    @Override
    public Object getValueAt(int r, int c) {
        Score s = scores.get(r);
        if (c == 0) return s.getName();
        return s.getScore();
    }

}
