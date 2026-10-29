import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BooksPanel extends LibraryPanel {
    private BookTableAdapter booksAdapter = new BookTableAdapter();
    private JTable booksTable;

    public BooksPanel(Runnable onDataChanged) {
        super(new BorderLayout(), onDataChanged);

        booksTable = new JTable(booksAdapter);
        booksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(booksTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(e -> showAddBookDialog());

        JButton editButton = new JButton("Edit Book");
        editButton.addActionListener(e -> showEditBookDialog());

        JButton deleteButton = new JButton("Delete Book");
        deleteButton.addActionListener(e -> deleteSelectedBook());

        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchBooks(searchField.getText()));

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(new JLabel("Search:"));
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void refresh() {
        fillTable(libraryManager.getAllBooks());
    }

    private JFrame owner() {
        return (JFrame) SwingUtilities.getWindowAncestor(this);
    }

    private void showAddBookDialog() {
        JDialog dialog = new JDialog(owner(), "Add New Book", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField isbnField = new JTextField(20);
        JTextField titleField = new JTextField(20);
        JTextField authorField = new JTextField(20);
        JTextField categoryField = new JTextField(20);
        JSpinner copiesSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        panel.add(isbnField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        panel.add(authorField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        panel.add(categoryField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Copies:"), gbc);
        gbc.gridx = 1;
        panel.add(copiesSpinner, gbc);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            String isbn = isbnField.getText().trim();
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String category = categoryField.getText().trim();
            int copies = (Integer) copiesSpinner.getValue();

            if (isbn.isEmpty() || title.isEmpty() || author.isEmpty() || category.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Book book = new Book(isbn, title, author, category, copies);
            if (libraryManager.addBook(book)) {
                JOptionPane.showMessageDialog(dialog, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                onDataChanged.run();
            } else {
                JOptionPane.showMessageDialog(dialog, "Book with this ISBN already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showEditBookDialog() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book book = booksAdapter.getBookAt(selectedRow);

        JDialog dialog = new JDialog(owner(), "Edit Book", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField isbnField = new JTextField(book.getIsbn(), 20);
        isbnField.setEditable(false);
        JTextField titleField = new JTextField(book.getTitle(), 20);
        JTextField authorField = new JTextField(book.getAuthor(), 20);
        JTextField categoryField = new JTextField(book.getCategory(), 20);
        JSpinner copiesSpinner = new JSpinner(new SpinnerNumberModel(book.getTotalCopies(), 1, 100, 1));

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        panel.add(isbnField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        panel.add(authorField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        panel.add(categoryField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Total Copies:"), gbc);
        gbc.gridx = 1;
        panel.add(copiesSpinner, gbc);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String category = categoryField.getText().trim();
            int copies = (Integer) copiesSpinner.getValue();

            if (title.isEmpty() || author.isEmpty() || category.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            book.setTitle(title);
            book.setAuthor(author);
            book.setCategory(category);
            book.setTotalCopies(copies);

            JOptionPane.showMessageDialog(dialog, "Book updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
            onDataChanged.run();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void deleteSelectedBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book book = booksAdapter.getBookAt(selectedRow);
        String isbn = book.getIsbn();
        String title = book.getTitle();

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete '" + title + "'?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (libraryManager.removeBook(isbn)) {
                JOptionPane.showMessageDialog(this, "Book deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                onDataChanged.run();
            }
        }
    }

    private void searchBooks(String query) {
        if (query.trim().isEmpty()) {
            refresh();
            return;
        }
        fillTable(libraryManager.searchBooks(query));
    }

    private void fillTable(List<Book> books) {
        booksAdapter.setBooks(books);
    }
}
