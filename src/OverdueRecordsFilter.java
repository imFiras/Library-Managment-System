public class OverdueRecordsFilter implements RecordFilterStrategy {

    @Override
    public String getName() {
        return "Overdue";
    }

    @Override
    public boolean matches(BorrowRecord record) {
        return record.isOverdue();
    }
}
