import javax.swing.*;

// Creator of the Factory Method
public abstract class TabFactory {

    // Factory method: each subclass decides which panel to create
    protected abstract LibraryPanel createPanel();

    protected abstract String getTitle();

    public LibraryPanel addTab(JTabbedPane tabbedPane) {
        LibraryPanel panel = createPanel();
        tabbedPane.addTab(getTitle(), panel);
        return panel;
    }
}
