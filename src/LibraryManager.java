import java.util.ArrayList;
import java.util.List;

public class LibraryManager {
    // Singleton: the one shared instance
    private static LibraryManager instance;

    private List<Book> books;
    private List<BorrowRecord> borrowRecords;

    private LibraryManager() {
        books = new ArrayList<>();
        borrowRecords = new ArrayList<>();
        initializeSampleData();
    }

    public static synchronized LibraryManager getInstance() {
        if (instance == null) {
            instance = new LibraryManager();
        }
        return instance;
    }

    private void initializeSampleData() {
        books.add(new Book("978-0134685991", "Effective Java", "Joshua Bloch", "Programming", 3));
        books.add(new Book("978-0135166307", "Java: The Complete Reference", "Herbert Schildt", "Programming", 2));
        books.add(new Book("978-0132350884", "Clean Code", "Robert Martin", "Programming", 4));
        books.add(new Book("978-0596007126", "Head First Design Patterns", "Eric Freeman", "Programming", 2));
        books.add(new Book("978-1617294945", "Spring in Action", "Craig Walls", "Programming", 1));
    }

    public boolean addBook(Book book) {
        for (Book b : books) {
            if (b.getIsbn().equals(book.getIsbn())) {
                return false;
            }
        }
        books.add(book);
        return true;
    }

    public boolean removeBook(String isbn) {
        return books.removeIf(book -> book.getIsbn().equals(isbn));
    }

    public Book findBookByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(lowerQuery) ||
                book.getAuthor().toLowerCase().contains(lowerQuery) ||
                book.getIsbn().toLowerCase().contains(lowerQuery) ||
                book.getCategory().toLowerCase().contains(lowerQuery)) {
                results.add(book);
            }
        }
        return results;
    }

    public boolean borrowBook(String isbn, String borrowerName, String borrowerEmail) {
        Book book = findBookByIsbn(isbn);
        if (book != null && book.borrowBook()) {
            borrowRecords.add(new BorrowRecord(isbn, book.getTitle(), borrowerName, borrowerEmail));
            return true;
        }
        return false;
    }

    public boolean returnBook(String isbn, String borrowerName) {
        for (BorrowRecord record : borrowRecords) {
            if (record.getIsbn().equals(isbn) && 
                record.getBorrowerName().equals(borrowerName) && 
                !record.isReturned()) {
                record.markReturned();
                Book book = findBookByIsbn(isbn);
                if (book != null) {
                    book.returnBook();
                }
                return true;
            }
        }
        return false;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public List<BorrowRecord> getAllBorrowRecords() {
        return new ArrayList<>(borrowRecords);
    }

    public List<BorrowRecord> getActiveBorrowRecords() {
        List<BorrowRecord> active = new ArrayList<>();
        for (BorrowRecord record : borrowRecords) {
            if (!record.isReturned()) {
                active.add(record);
            }
        }
        return active;
    }

    public List<BorrowRecord> getOverdueRecords() {
        List<BorrowRecord> overdue = new ArrayList<>();
        for (BorrowRecord record : borrowRecords) {
            if (record.isOverdue()) {
                overdue.add(record);
            }
        }
        return overdue;
    }
}
