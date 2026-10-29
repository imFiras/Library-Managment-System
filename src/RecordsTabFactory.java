public class RecordsTabFactory extends TabFactory {

    @Override
    protected LibraryPanel createPanel() {
        return new RecordsPanel();
    }

    @Override
    protected String getTitle() {
        return "Records";
    }
}
