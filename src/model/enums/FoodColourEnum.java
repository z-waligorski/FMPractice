package model.enums;

/**
 *
 * @author Zbyszek
 */
public enum FoodColourEnum {
    RED("Red"),
    GREEN("Green"),
    YELLOW("Yellow");
    
    private final String displayedColourName;

    private FoodColourEnum(String displayedColourName) {
        this.displayedColourName = displayedColourName;
    }

    public String getDisplayedColourName() {
        return displayedColourName;
    }
}
