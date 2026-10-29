// Strategy: decides which borrow records are shown
public interface RecordFilterStrategy {
    String getName();

    boolean matches(BorrowRecord record);
}
