public class ReportsTabFactory extends TabFactory {

    @Override
    protected LibraryPanel createPanel(Runnable onDataChanged) {
        return new ReportsPanel(onDataChanged);
    }

    @Override
    protected String getTitle() {
        return "Reports";
    }
}
