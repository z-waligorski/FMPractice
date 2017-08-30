package model.enums;

/**
 *
 * @author Zbyszek
 */
public enum FoodTagEnum {
    SOURCE_BEEF("Beef"),
    SOURCE_PORK("Pork"),
    VEGETABLES("Vegetables"),
    MUSHROOMS("Mushrooms");
    
    private final String displayedName;

    private FoodTagEnum(String displayedName) {
        this.displayedName = displayedName;
    }
    
    public String getDisplayedName(){
        return displayedName;
    }   
}
