public class BorrowReturnTabFactory extends TabFactory {

    @Override
    protected LibraryPanel createPanel(Runnable onDataChanged) {
        return new BorrowReturnPanel(onDataChanged);
    }

    @Override
    protected String getTitle() {
        return "Borrow/Return";
    }
}
