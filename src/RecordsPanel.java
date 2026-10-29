import javax.swing.*;
import java.awt.*;

public class RecordsPanel extends LibraryPanel implements LibraryObserver {
    private BorrowRecordTableAdapter recordsAdapter = new BorrowRecordTableAdapter();
    private JTable borrowRecordsTable;
    private RecordFilterStrategy currentFilter = new AllRecordsFilter();
    private JLabel filterLabel = new JLabel();

    public RecordsPanel() {
        super(new BorderLayout());

        borrowRecordsTable = new JTable(recordsAdapter);

        JScrollPane scrollPane = new JScrollPane(borrowRecordsTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton showAllButton = new JButton("Show All Records");
        showAllButton.addActionListener(e -> setFilter(new AllRecordsFilter()));

        JButton showActiveButton = new JButton("Show Active Records");
        showActiveButton.addActionListener(e -> setFilter(new ActiveRecordsFilter()));

        JButton showOverdueButton = new JButton("Show Overdue Records");
        showOverdueButton.addActionListener(e -> setFilter(new OverdueRecordsFilter()));

        buttonPanel.add(showAllButton);
        buttonPanel.add(showActiveButton);
        buttonPanel.add(showOverdueButton);
        buttonPanel.add(filterLabel);

        add(buttonPanel, BorderLayout.SOUTH);

        libraryManager.addObserver(this);
        onLibraryChanged();
    }

    @Override
    public void onLibraryChanged() {
        recordsAdapter.setRecords(libraryManager.getBorrowRecords(currentFilter));
        filterLabel.setText("Showing: " + currentFilter.getName());
    }

    private void setFilter(RecordFilterStrategy filter) {
        currentFilter = filter;
        onLibraryChanged();
    }
}
