package view;

import view.models.FoodTableModel;
import java.util.List;
import javax.swing.RowFilter;
import model.Food;
import model.enums.FoodTagEnum;

/**
 *
 * @author Zbyszek
 */
public class TagFilter extends RowFilter<FoodTableModel, Integer>{

    private FoodTagEnum tag;
    
    public TagFilter(FoodTagEnum tag) {
        this.tag = tag;
    }
    
    @Override
    public boolean include(RowFilter.Entry<? extends FoodTableModel, ? extends Integer> entry) {
        FoodTableModel model = entry.getModel();
        Food food = model.getValueAt(entry.getIdentifier());
        List<FoodTagEnum> tagList = food.getTags();
        if (tagList.contains(tag)) {
            return true;
        }
        return false;
    } 
}
