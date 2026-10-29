import javax.swing.*;

public class LibraryManagementSystem extends JFrame {
    private JTabbedPane tabbedPane;

    public LibraryManagementSystem() {
        initializeGUI();
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
            factory.addTab(tabbedPane);
        }

        add(tabbedPane);
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
