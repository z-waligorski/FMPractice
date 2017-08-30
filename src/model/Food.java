package model;

import model.enums.FoodTagEnum;
import model.enums.FoodColourEnum;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zbyszek
 */
public class Food {

    private int id;
    private String name;
    private String scientificName;
    private List<FoodTagEnum> tags;
    private FoodColourEnum colour;
    
    public Food(int id, String name, String scientificName, FoodColourEnum colour) {
        tags = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.scientificName = scientificName;
        this.colour = colour;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public List <FoodTagEnum> getTags() {
        return tags;
    }

    public void setTag(FoodTagEnum tag) {
        this.tags.add(tag) ;
    }  

    public FoodColourEnum getColour() {
        return colour;
    }

    public void setColour(FoodColourEnum colour) {
        this.colour = colour;
    }   
}
