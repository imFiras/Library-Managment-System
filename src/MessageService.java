import javax.swing.*;
import java.awt.*;

// Subsystem class used by CirculationFacade
public class MessageService {
    private final Component parent;

    public MessageService(Component parent) {
        this.parent = parent;
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(parent, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
