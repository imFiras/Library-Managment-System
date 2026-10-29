public class ReportsTabFactory extends TabFactory {

    @Override
    protected LibraryPanel createPanel() {
        return new ReportsPanel();
    }

    @Override
    protected String getTitle() {
        return "Reports";
    }
}
