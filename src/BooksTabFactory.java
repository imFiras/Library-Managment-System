public class BooksTabFactory extends TabFactory {

    @Override
    protected LibraryPanel createPanel() {
        return new BooksPanel();
    }

    @Override
    protected String getTitle() {
        return "Books";
    }
}
