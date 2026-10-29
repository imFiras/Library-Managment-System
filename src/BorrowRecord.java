import java.time.LocalDate;

public class BorrowRecord {
    private String isbn;
    private String title;
    private String borrowerName;
    private String borrowerEmail;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean isReturned;

    public BorrowRecord(String isbn, String title, String borrowerName, String borrowerEmail) {
        this.isbn = isbn;
        this.title = title;
        this.borrowerName = borrowerName;
        this.borrowerEmail = borrowerEmail;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(14);
        this.isReturned = false;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getBorrowerName() { return borrowerName; }
    public String getBorrowerEmail() { return borrowerEmail; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean isReturned() { return isReturned; }

    public void markReturned() {
        this.isReturned = true;
        this.returnDate = LocalDate.now();
    }

    public boolean isOverdue() {
        return !isReturned && LocalDate.now().isAfter(dueDate);
    }
}
