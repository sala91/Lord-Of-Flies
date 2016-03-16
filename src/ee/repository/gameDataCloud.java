package ee.repository;

import ee.Main;

import java.io.*;
import java.sql.*;
import java.util.*;
import ee.models.player;

import javax.swing.*;

public class gameDataCloud {
    Connection connection;

    public gameDataCloud() throws SQLException {
        connection = DriverManager.getConnection(Main.DBConnectionString);
    }

    public void addResult(player citizen) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO lordoftheflies (name, score) VALUES (?, ?)");
        statement.setString(1, citizen.getName());
        statement.setInt(2, citizen.getScore());
        statement.execute();
    }

    public List<player> getResults() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM lordoftheflies ORDER BY score DESC LIMIT 100");
        List<player> andmed = new ArrayList<>();
        while (result.next()) {
            andmed.add(new player(result.getInt("id"), result.getString("name"), result.getInt("score")));
        }
        return andmed;
    }

    public JTable getResultsFormated() throws SQLException{
        return new JTable(new PersonTableModel(getResults()));
    }
}
