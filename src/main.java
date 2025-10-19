import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import Frames.LoginFrame;

public class main {
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Failed to initialize FlatLaf");
        }

        //MainFrame testobj = new MainFrame();   
        LoginFrame loginobj = new LoginFrame();
    }
}