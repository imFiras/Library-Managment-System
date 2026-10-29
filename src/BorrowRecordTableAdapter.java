import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Adapter: lets a JTable (expects TableModel) display a List<BorrowRecord> directly
public class BorrowRecordTableAdapter extends AbstractTableModel {
    private static final String[] COLUMNS = {"ISBN", "Title", "Borrower", "Email", "Borrow Date", "Due Date", "Return Date", "Status"};
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private List<BorrowRecord> records = new ArrayList<>();

    public void setRecords(List<BorrowRecord> records) {
        this.records = records;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return records.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        BorrowRecord record = records.get(row);
        switch (column) {
            case 0: return record.getIsbn();
            case 1: return record.getTitle();
            case 2: return record.getBorrowerName();
            case 3: return record.getBorrowerEmail();
            case 4: return record.getBorrowDate().format(FORMATTER);
            case 5: return record.getDueDate().format(FORMATTER);
            case 6: return record.getReturnDate() != null ? record.getReturnDate().format(FORMATTER) : "";
            case 7: return record.isReturned() ? "Returned" : (record.isOverdue() ? "Overdue" : "Active");
            default: return null;
        }
    }
}
