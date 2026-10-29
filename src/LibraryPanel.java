import javax.swing.*;
import java.awt.*;

// Product of the Factory Method: every tab in the main window is a LibraryPanel
public abstract class LibraryPanel extends JPanel {
    protected final LibraryManager libraryManager = LibraryManager.getInstance();
    protected final Runnable onDataChanged;

    protected LibraryPanel(LayoutManager layout, Runnable onDataChanged) {
        super(layout);
        this.onDataChanged = onDataChanged;
    }

    // Reloads the panel's data from the library
    public abstract void refresh();
}
