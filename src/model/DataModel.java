package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zbyszek
 */
public class DataModel {
    
    private static List<Food> foodList;
    
    public DataModel() {
        populateList();
    }
        
    private void populateList() {
        foodList = new ArrayList<>();
        DataCreator creator = DataCreator.getInstance();       
        foodList = creator.getFoodList();
    }
    
    public List<Food> getDataForMainFrameTable() {
        return foodList;
    }
    
    public Food getselectedFood(int index) {
        return foodList.get(index);
    }

    public Object[] getColumnsNames() {
        String[]columnsNames = {"Id", "Name", "ScientificName"};
        return columnsNames;
    }
    
    public void deleteRow(int rowNumber) {
        foodList.remove(rowNumber);        
    }
    
    public void updateRow(int rowNumber, Food food) {
        foodList.set(rowNumber, food);       
    }
    
    public void insertRow(Food food) {
        foodList.add(food);
    }    
}
