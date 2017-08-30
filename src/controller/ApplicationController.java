package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import model.DataModel;
import model.Food;
import model.enums.FoodColourEnum;
import model.enums.FoodTagEnum;
import view.models.FoodTableModel;
import view.models.FoodTagsTreeModel;
import view.InputDialog;
import view.MainFrame;
import view.TagFilter;

/**
 *
 * @author Zbyszek
 */
public class ApplicationController {
    
    private DataModel dataModel;
    private MainFrame mainFrame;
    //TODO selectedRow is ambiguous. Shadowing in methods. Field could be deleted 
    private int selectedRow;
    
    public ApplicationController(DataModel dataModel, MainFrame mainFrame) {
        this.dataModel = dataModel;
        this.mainFrame = mainFrame;
        addActionListeners();
    }
    
    public void addActionListeners() {
        mainFrame.addListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonText = e.getActionCommand();
                if(buttonText.equals("Add")){
                    startAdding();
                } else if(buttonText.equals("Edit")) {
                    startEditing();
                } else if(buttonText.equals("Delete")) {
                    deleteItem();
                } else if (buttonText.equals("Filter")) {
                    startFiltering();
                } else if(buttonText.equals("Clear")) {
                    clearFiltering();
                }                    
            }
        }, new MouseAdapter(){
            public void mouseClicked(MouseEvent e){			
		if (e.getClickCount() == 2) {
                    startEditing();
		} else if(e.getClickCount() == 1) {
                    mainFrame.enableDeleteButton();
                    mainFrame.enableEditButton();
		}
            }
        });
    }
    
    public void addActionListenersForInputDialog(InputDialog inputDialog) {
        inputDialog.addListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonText = e.getActionCommand();
                if(buttonText.equals("Save")){
                    saveInputDialogData(inputDialog);
                } else if(buttonText.equals("Cancel")) {
                    discardInputDialog(inputDialog);
                } else if(buttonText.equals("Remove >>")) {
                    removeTags(inputDialog);
                } else if (buttonText.equals("<< Add")) {
                    addTags(inputDialog);
                }
            }           
        });
    }  
    
    public void startAdding() {
        InputDialog dialog = new InputDialog(mainFrame, true);
        addActionListenersForInputDialog(dialog);
        dialog.setVisible(true);
    }
    
    public void startEditing() {
        JTable table = mainFrame.getTable();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            Food food = dataModel.getselectedFood(modelRow);
            InputDialog dialog = new InputDialog(mainFrame, true, food.getId(), food.getName(), food.getScientificName(), food.getTags(), food.getColour());
            dialog.isEditing();
            addActionListenersForInputDialog(dialog);
            dialog.setVisible(true);
        }
    }
    
    public void startFiltering() {
        String serchedValue = mainFrame.getSearchingValue().trim();
        JTable table = mainFrame.getTable();
        TableRowSorter<TableModel> rowSorter = (TableRowSorter<TableModel>) table.getRowSorter();
        //Create list of listeners depending on serched values        
        List<RowFilter<Object,Object>> filters = new ArrayList<>();
        if (!serchedValue.equals("")) {
            filters.add(RowFilter.regexFilter("(?i)" + serchedValue, 1,2));
        }
        String selectedTag = mainFrame.getComboBoxValue(); 
        switch(selectedTag){
            case "Beef":
                filters.add((RowFilter)new TagFilter(FoodTagEnum.SOURCE_BEEF));
                break;
            case "Pork":
                 filters.add((RowFilter)new TagFilter(FoodTagEnum.SOURCE_PORK));
                 break;
            case "Vegetables":
                filters.add((RowFilter)new TagFilter(FoodTagEnum.VEGETABLES));
                break;
            case "Mushrooms":
                filters.add((RowFilter)new TagFilter(FoodTagEnum.MUSHROOMS));
                break;
        }
        
        RowFilter<Object, Object> rowFilter = RowFilter.andFilter(filters);
        rowSorter.setRowFilter(rowFilter);
    }
    
    public void deleteItem() {
        JTable table = mainFrame.getTable();
        int selectedRow = table.getSelectedRow();
        if(selectedRow != -1) {
            int modelIndex = table.convertRowIndexToModel(selectedRow);
            FoodTableModel model = (FoodTableModel)table.getModel();
            model.removeData(modelIndex);
        }       
    }
    
    public void saveInputDialogData(InputDialog inputDialog) {
        try {
            FoodColourEnum colour = null;
            String colourString = inputDialog.getSelectedColour();
            switch(colourString) {
                case "Red":
                    colour = FoodColourEnum.RED;
                    break;
                    case "Green":
                    colour = FoodColourEnum.GREEN;
                    break;
                    case "Yellow":
                    colour = FoodColourEnum.YELLOW;
                    break;
            }
            
            Food newFood = new Food(inputDialog.getId(), inputDialog.getName(), inputDialog.getScientificName(), colour);
            
            FoodTagsTreeModel treeModel = inputDialog.getSelectedTagsTreeModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
            int numberOfNodes = root.getChildCount();
            
            for (int i = 0; i < numberOfNodes; i++){
                DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) root.getChildAt(i);
                String nodeText = (String) childNode.getUserObject();
                switch(nodeText){
                    case "Beef":
                        newFood.setTag(FoodTagEnum.SOURCE_BEEF);
                        break;
                    case "Pork":
                        newFood.setTag(FoodTagEnum.SOURCE_PORK);
                        break;
                    case "Vegetables":
                        newFood.setTag(FoodTagEnum.VEGETABLES);
                        break;

                    case "Mushrooms":
                        newFood.setTag(FoodTagEnum.MUSHROOMS);
                        break;
                }
            }
            if (inputDialog.isEditing() == true) {
                JTable table = mainFrame.getTable();
                int selectedRow = table.getSelectedRow();
                dataModel.updateRow(selectedRow, newFood);
                ((AbstractTableModel) mainFrame.getTableModel()).setValueAt(inputDialog.getId(), selectedRow, 0);
                ((AbstractTableModel) mainFrame.getTableModel()).setValueAt(inputDialog.getName(), selectedRow, 1);
                ((AbstractTableModel) mainFrame.getTableModel()).setValueAt(inputDialog.getScientificName(), selectedRow, 2);
            } else {
                Food food = new Food(inputDialog.getId(), inputDialog.getName(), inputDialog.getScientificName(), colour);
                ((FoodTableModel) mainFrame.getTableModel()).addData(food); 
            }
            inputDialog.dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Id must be an integer.");
        }
    }
    
    //TODO Part of the code is repeated in addTags(), repeated code should be extracted as a method
    public void removeTags(InputDialog inputDialog) {
        FoodTagsTreeModel selectedTagsTreeModel = inputDialog.getSelectedTagsTreeModel();
        FoodTagsTreeModel availableTagsTreeModel = inputDialog.getAvailableTagsTreeModel();
        DefaultMutableTreeNode availableTagsRoot = (DefaultMutableTreeNode) availableTagsTreeModel.getRoot();
        TreePath[] paths = inputDialog.getSelectedTags();
        if(paths == null){
            JOptionPane.showMessageDialog(mainFrame, "Select tags for removing\nCtrl+left click for selecting multiple tags.");
            return;
        }
        
        DefaultMutableTreeNode node;
        for(int i = 0 ; i < paths.length ; i++) {
            node = (DefaultMutableTreeNode) paths[i].getLastPathComponent();
            selectedTagsTreeModel.removeNodeFromParent(node);
            availableTagsTreeModel.insertNodeInto(node, availableTagsRoot, 0);
            inputDialog.getAvailabletagsTree().expandPath(new TreePath(availableTagsRoot.getPath()));
        }
    }
    
    public void addTags(InputDialog inputDialog) {
        FoodTagsTreeModel selectedTagsTreeModel = inputDialog.getSelectedTagsTreeModel();
        FoodTagsTreeModel availableTagsTreeModel = inputDialog.getAvailableTagsTreeModel();
        DefaultMutableTreeNode tagsRoot = (DefaultMutableTreeNode) selectedTagsTreeModel.getRoot();
        TreePath[] paths = inputDialog.getSelectedAvailableTags(); 
        if(paths == null){
            JOptionPane.showMessageDialog(mainFrame, "Select tags for adding\nCtrl+left click for selecting multiple tags.");
            return;
        }
        DefaultMutableTreeNode node;
        for(int i = 0 ; i < paths.length ; i++) {
            node = (DefaultMutableTreeNode) paths[i].getLastPathComponent();
            availableTagsTreeModel.removeNodeFromParent(node);
            selectedTagsTreeModel.insertNodeInto(node, tagsRoot, 0);
            inputDialog.getSelectedTagsTree().expandPath(new TreePath(tagsRoot.getPath()));           
        }
    }
    
    public void discardInputDialog(InputDialog inputDialog) {
        inputDialog.dispose();
    }
    
    public void clearFiltering() {
        JTable table = mainFrame.getTable();
        TableRowSorter<TableModel> rowSorter = (TableRowSorter<TableModel>) table.getRowSorter();
        rowSorter.setRowFilter(null);
        mainFrame.clearAfterSorting();
    }
}
    
