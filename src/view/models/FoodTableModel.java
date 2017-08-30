package view.models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Food;

/**
 *
 * @author Zbyszek
 */
public class FoodTableModel extends AbstractTableModel {
    List<Food> tableData = new ArrayList<>();
    private Object[] columnNames;
    
    public FoodTableModel(List<Food> tableData, Object[] columnNames) {
        this.tableData = tableData;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
           return tableData.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return (String)columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Food food = tableData.get(rowIndex);
        Object value = null;
        switch (columnIndex) {
            case 0:
                value = food.getId();
                break;
            case 1:
                value = food.getName();
                break;
            case 2:
                value = food.getScientificName();
                break;
            case 3:
                value = food.getTags();
                break;
            default:
                return null;
        }  
        return value;
    }
    
    public Food getValueAt(int rowIndex){
        return tableData.get(rowIndex);
    }
    
    public void addData(Food food){
        tableData.add(food);
        fireTableRowsInserted(tableData.size() - 1, tableData.size() - 1);
    }
    
    public void removeData(int row) {
        tableData.remove(row);
        fireTableRowsDeleted(tableData.size() - 1, tableData.size() - 1);
    }
    
    @Override 
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex == 0){
            return Integer.class;
        } else {
            return String.class;
        }
    }   
}
