import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RecordsPanel extends LibraryPanel implements LibraryObserver {
    private BorrowRecordTableAdapter recordsAdapter = new BorrowRecordTableAdapter();
    private JTable borrowRecordsTable;

    public RecordsPanel() {
        super(new BorderLayout());

        borrowRecordsTable = new JTable(recordsAdapter);

        JScrollPane scrollPane = new JScrollPane(borrowRecordsTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton showAllButton = new JButton("Show All Records");
        showAllButton.addActionListener(e -> showAllRecords());

        JButton showActiveButton = new JButton("Show Active Records");
        showActiveButton.addActionListener(e -> showActiveRecords());

        JButton showOverdueButton = new JButton("Show Overdue Records");
        showOverdueButton.addActionListener(e -> showOverdueRecords());

        buttonPanel.add(showAllButton);
        buttonPanel.add(showActiveButton);
        buttonPanel.add(showOverdueButton);

        add(buttonPanel, BorderLayout.SOUTH);

        libraryManager.addObserver(this);
        onLibraryChanged();
    }

    @Override
    public void onLibraryChanged() {
        showAllRecords();
    }

    private void showAllRecords() {
        updateBorrowRecordsTable(libraryManager.getAllBorrowRecords());
    }

    private void showActiveRecords() {
        updateBorrowRecordsTable(libraryManager.getActiveBorrowRecords());
    }

    private void showOverdueRecords() {
        updateBorrowRecordsTable(libraryManager.getOverdueRecords());
    }

    private void updateBorrowRecordsTable(List<BorrowRecord> records) {
        recordsAdapter.setRecords(records);
    }
}
