public class BooksTabFactory extends TabFactory {

    @Override
    protected LibraryPanel createPanel(Runnable onDataChanged) {
        return new BooksPanel(onDataChanged);
    }

    @Override
    protected String getTitle() {
        return "Books";
    }
}
