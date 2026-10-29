import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Adapter: lets a JTable (expects TableModel) display a List<Book> directly
public class BookTableAdapter extends AbstractTableModel {
    private static final String[] COLUMNS = {"ISBN", "Title", "Author", "Category", "Total Copies", "Available", "Date Added"};
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private List<Book> books = new ArrayList<>();

    public void setBooks(List<Book> books) {
        this.books = books;
        fireTableDataChanged();
    }

    public Book getBookAt(int row) {
        return books.get(row);
    }

    @Override
    public int getRowCount() {
        return books.size();
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
        Book book = books.get(row);
        switch (column) {
            case 0: return book.getIsbn();
            case 1: return book.getTitle();
            case 2: return book.getAuthor();
            case 3: return book.getCategory();
            case 4: return book.getTotalCopies();
            case 5: return book.getAvailableCopies();
            case 6: return book.getDateAdded().format(FORMATTER);
            default: return null;
        }
    }
}
