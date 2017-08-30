package foodmanager;

import controller.ApplicationController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.DataModel;
import view.MainFrame;

/**
 *
 * @author Zbyszek
 */
public class FoodManagerPractice {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FoodManagerPractice.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(FoodManagerPractice.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FoodManagerPractice.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(FoodManagerPractice.class.getName()).log(Level.SEVERE, null, ex);
        }
        DataModel dataModel = new DataModel();
        MainFrame mainFrame = new MainFrame(dataModel.getDataForMainFrameTable(), dataModel.getColumnsNames());
        ApplicationController applicationController = new ApplicationController(dataModel, mainFrame);
        mainFrame.setVisible(true);
    }   
}
