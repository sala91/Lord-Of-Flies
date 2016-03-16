package ee;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;

import ee.repository.*;

public class HighScores extends JApplet  {

    JPanel panel = new JPanel();

    public void init(){
        setMyLayout();
        getData();
        setVisible(true);
    }

    private void getData() {
        gameDataCloud godData = null;
        JTable table = null;

        try {
            godData = new gameDataCloud();
            table = godData.getResultsFormated();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
    }

    private void setMyLayout() {
        setSize(600, 600);
        setLayout(new BorderLayout());
        add(BorderLayout.CENTER, panel);
    }



}
