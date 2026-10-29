import javax.swing.*;
import java.awt.*;

// Product of the Factory Method: every tab in the main window is a LibraryPanel
public abstract class LibraryPanel extends JPanel {
    protected final LibraryManager libraryManager = LibraryManager.getInstance();

    protected LibraryPanel(LayoutManager layout) {
        super(layout);
    }
}
