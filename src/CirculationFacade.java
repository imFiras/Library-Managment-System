import java.awt.*;

// Facade: one simple entry point for borrowing and returning books.
// Hides validation, the library manager and user messages from the UI.
public class CirculationFacade {
    private final LibraryManager libraryManager = LibraryManager.getInstance();
    private final InputValidator validator = new InputValidator();
    private final MessageService messages;

    public CirculationFacade(Component parent) {
        this.messages = new MessageService(parent);
    }

    public boolean borrowBook(String isbn, String name, String email) {
        if (validator.hasEmptyField(isbn, name, email)) {
            messages.showError("Please fill all fields.");
            return false;
        }
        if (!validator.isValidEmail(email.trim())) {
            messages.showError("Please enter a valid email address.");
            return false;
        }
        if (!libraryManager.borrowBook(isbn.trim(), name.trim(), email.trim())) {
            messages.showError("Book not available or not found.");
            return false;
        }
        messages.showSuccess("Book borrowed successfully!");
        return true;
    }

    public boolean returnBook(String isbn, String name) {
        if (validator.hasEmptyField(isbn, name)) {
            messages.showError("Please fill all fields.");
            return false;
        }
        if (!libraryManager.returnBook(isbn.trim(), name.trim())) {
            messages.showError("No matching borrow record found.");
            return false;
        }
        messages.showSuccess("Book returned successfully!");
        return true;
    }
}
