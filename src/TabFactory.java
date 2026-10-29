import javax.swing.*;

// Creator of the Factory Method
public abstract class TabFactory {

    // Factory method: each subclass decides which panel to create
    protected abstract LibraryPanel createPanel(Runnable onDataChanged);

    protected abstract String getTitle();

    public LibraryPanel addTab(JTabbedPane tabbedPane, Runnable onDataChanged) {
        LibraryPanel panel = createPanel(onDataChanged);
        tabbedPane.addTab(getTitle(), panel);
        return panel;
    }
}
