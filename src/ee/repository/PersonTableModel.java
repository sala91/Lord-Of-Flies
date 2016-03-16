package ee.repository;

import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import ee.models.player;

public class PersonTableModel implements TableModel {

    List<player> players;

    public PersonTableModel(List<player> playerList) {
        players = playerList;
    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if(columnIndex==0){
            return "POS";
        }else if(columnIndex==1){
            return "NAME";
        }else if(columnIndex==2){
            return "SCORE";
        }
        return "";
    }

    @Override
    public int getRowCount() {
        return players.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        player citizen = players.get(rowIndex);

        if(columnIndex==0){
            return rowIndex+1;

        }else if(columnIndex==1){
            return citizen.getName();

        }else if(columnIndex==2){
            return citizen.getScore();
        }
        return null;

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

}
