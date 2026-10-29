public class AllRecordsFilter implements RecordFilterStrategy {

    @Override
    public String getName() {
        return "All";
    }

    @Override
    public boolean matches(BorrowRecord record) {
        return true;
    }
}
