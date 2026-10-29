import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryManagementSystem extends JFrame {
    private JTabbedPane tabbedPane;
    private List<LibraryPanel> panels = new ArrayList<>();

    public LibraryManagementSystem() {
        initializeGUI();
        refreshTables();
    }

    private void initializeGUI() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        TabFactory[] factories = {
            new BooksTabFactory(),
            new BorrowReturnTabFactory(),
            new RecordsTabFactory(),
            new ReportsTabFactory()
        };
        for (TabFactory factory : factories) {
            panels.add(factory.addTab(tabbedPane, this::refreshTables));
        }

        add(tabbedPane);
    }

    private void refreshTables() {
        for (LibraryPanel panel : panels) {
            panel.refresh();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.getSystemLookAndFeelClassName();
            } catch (Exception e) {
                e.printStackTrace();
            }

            new LibraryManagementSystem().setVisible(true);
        });
    }
}
