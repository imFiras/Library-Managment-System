import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecordsPanel extends LibraryPanel {
    private DefaultTableModel borrowRecordsTableModel;
    private JTable borrowRecordsTable;

    public RecordsPanel(Runnable onDataChanged) {
        super(new BorderLayout(), onDataChanged);

        String[] columnNames = {"ISBN", "Title", "Borrower", "Email", "Borrow Date", "Due Date", "Return Date", "Status"};
        borrowRecordsTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        borrowRecordsTable = new JTable(borrowRecordsTableModel);

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
    }

    @Override
    public void refresh() {
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
        borrowRecordsTableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (BorrowRecord record : records) {
            Object[] row = {
                record.getIsbn(),
                record.getTitle(),
                record.getBorrowerName(),
                record.getBorrowerEmail(),
                record.getBorrowDate().format(formatter),
                record.getDueDate().format(formatter),
                record.getReturnDate() != null ? record.getReturnDate().format(formatter) : "",
                record.isReturned() ? "Returned" : (record.isOverdue() ? "Overdue" : "Active")
            };
            borrowRecordsTableModel.addRow(row);
        }
    }
}
