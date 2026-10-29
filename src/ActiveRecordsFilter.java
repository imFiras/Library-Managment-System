public class ActiveRecordsFilter implements RecordFilterStrategy {

    @Override
    public String getName() {
        return "Active";
    }

    @Override
    public boolean matches(BorrowRecord record) {
        return !record.isReturned();
    }
}
