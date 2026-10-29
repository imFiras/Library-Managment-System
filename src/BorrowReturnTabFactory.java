public class BorrowReturnTabFactory extends TabFactory {

    @Override
    protected LibraryPanel createPanel() {
        return new BorrowReturnPanel();
    }

    @Override
    protected String getTitle() {
        return "Borrow/Return";
    }
}
