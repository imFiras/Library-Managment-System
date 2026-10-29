public class RecordsTabFactory extends TabFactory {

    @Override
    protected LibraryPanel createPanel(Runnable onDataChanged) {
        return new RecordsPanel(onDataChanged);
    }

    @Override
    protected String getTitle() {
        return "Records";
    }
}
