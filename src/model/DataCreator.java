package model;

import model.enums.FoodTagEnum;
import model.enums.FoodColourEnum;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zbyszek
 */
public class DataCreator {
    
    private List<Food> foodList;
    private static DataCreator instance;
    
    private DataCreator() {
        populateList();
    }
    
    private void populateList() {
        foodList = new ArrayList<>();
        foodList.add(new Food(1, "kapusta", "kapusta pospolita", FoodColourEnum.GREEN));
        foodList.add(new Food(2, "hamburger", "hamburger jadalny", FoodColourEnum.YELLOW));
        foodList.add(new Food(3, "kebab", "kebab turecki", FoodColourEnum.RED));
        
        foodList.get(0).setTag(FoodTagEnum.VEGETABLES);
        foodList.get(1).setTag(FoodTagEnum.SOURCE_PORK);
        foodList.get(2).setTag(FoodTagEnum.SOURCE_BEEF);
        foodList.get(2).setTag(FoodTagEnum.SOURCE_PORK);
    }
    
    public List<Food> getFoodList() {
        return foodList;
    }
    
    public static DataCreator getInstance() {
        if(instance == null) {
            instance = new DataCreator();
        }
        return instance;
    }
}
