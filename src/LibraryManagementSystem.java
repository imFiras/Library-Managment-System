import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LibraryManagementSystem extends JFrame {
    private LibraryManager libraryManager;
    private JTabbedPane tabbedPane;
    private DefaultTableModel booksTableModel;
    private DefaultTableModel borrowRecordsTableModel;
    private JTable booksTable;
    private JTable borrowRecordsTable;

    public LibraryManagementSystem() {
        libraryManager = LibraryManager.getInstance();
        initializeGUI();
        refreshTables();
    }

    private void initializeGUI() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Books", createBooksPanel());
        tabbedPane.addTab("Borrow/Return", createBorrowReturnPanel());
        tabbedPane.addTab("Records", createRecordsPanel());
        tabbedPane.addTab("Reports", createReportsPanel());

        add(tabbedPane);
    }

    private JPanel createBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"ISBN", "Title", "Author", "Category", "Total Copies", "Available", "Date Added"};
        booksTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        booksTable = new JTable(booksTableModel);
        booksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(booksTable);
        panel.add(scrollPane, BorderLayout.CENTER);

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

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBorrowReturnPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel borrowPanel = new JPanel(new GridBagLayout());
        borrowPanel.setBorder(BorderFactory.createTitledBorder("Borrow Book"));
        
        gbc.gridx = 0; gbc.gridy = 0;
        borrowPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        JTextField borrowIsbnField = new JTextField(15);
        borrowPanel.add(borrowIsbnField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        borrowPanel.add(new JLabel("Borrower Name:"), gbc);
        gbc.gridx = 1;
        JTextField borrowerNameField = new JTextField(15);
        borrowPanel.add(borrowerNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        borrowPanel.add(new JLabel("Borrower Email:"), gbc);
        gbc.gridx = 1;
        JTextField borrowerEmailField = new JTextField(15);
        borrowPanel.add(borrowerEmailField, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        JButton borrowButton = new JButton("Borrow Book");
        borrowButton.addActionListener(e -> {
            String isbn = borrowIsbnField.getText().trim();
            String name = borrowerNameField.getText().trim();
            String email = borrowerEmailField.getText().trim();
            
            if (isbn.isEmpty() || name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (libraryManager.borrowBook(isbn, name, email)) {
                JOptionPane.showMessageDialog(this, "Book borrowed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                borrowIsbnField.setText("");
                borrowerNameField.setText("");
                borrowerEmailField.setText("");
                refreshTables();
            } else {
                JOptionPane.showMessageDialog(this, "Book not available or not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        borrowPanel.add(borrowButton, gbc);

        JPanel returnPanel = new JPanel(new GridBagLayout());
        returnPanel.setBorder(BorderFactory.createTitledBorder("Return Book"));
        
        gbc.gridx = 0; gbc.gridy = 0;
        returnPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        JTextField returnIsbnField = new JTextField(15);
        returnPanel.add(returnIsbnField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        returnPanel.add(new JLabel("Borrower Name:"), gbc);
        gbc.gridx = 1;
        JTextField returnNameField = new JTextField(15);
        returnPanel.add(returnNameField, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        JButton returnButton = new JButton("Return Book");
        returnButton.addActionListener(e -> {
            String isbn = returnIsbnField.getText().trim();
            String name = returnNameField.getText().trim();
            
            if (isbn.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (libraryManager.returnBook(isbn, name)) {
                JOptionPane.showMessageDialog(this, "Book returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                returnIsbnField.setText("");
                returnNameField.setText("");
                refreshTables();
            } else {
                JOptionPane.showMessageDialog(this, "No matching borrow record found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        returnPanel.add(returnButton, gbc);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.NORTH;
        panel.add(borrowPanel, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(returnPanel, gbc);

        return panel;
    }

    private JPanel createRecordsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"ISBN", "Title", "Borrower", "Email", "Borrow Date", "Due Date", "Return Date", "Status"};
        borrowRecordsTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        borrowRecordsTable = new JTable(borrowRecordsTableModel);

        JScrollPane scrollPane = new JScrollPane(borrowRecordsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton showAllButton = new JButton("Show All Records");
        showAllButton.addActionListener(e -> showAllRecords());
        
        JButton showActiveButton = new JButton("Show Active Records");
        showActiveButton.addActionListener(e -> showActiveRecords());
        
        JButton showOverdueButton = new JButton("Show Overdue Records");
        showOverdueButton.addActionListener(e -> showOverdueRecords());

        buttonPanel.add(showAllButton);
        buttonPanel.add(showActiveButton);
        buttonPanel.add(showOverdueButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextArea reportArea = new JTextArea(20, 50);
        reportArea.setEditable(false);
        reportArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(reportArea);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4;
        panel.add(scrollPane, gbc);

        JButton inventoryButton = new JButton("Inventory Report");
        inventoryButton.addActionListener(e -> {
            StringBuilder report = new StringBuilder();
            report.append("LIBRARY INVENTORY REPORT\n");
            report.append("========================\n\n");
            
            List<Book> books = libraryManager.getAllBooks();
            report.append(String.format("Total Books: %d\n\n", books.size()));
            
            for (Book book : books) {
                report.append(String.format("ISBN: %s\n", book.getIsbn()));
                report.append(String.format("Title: %s\n", book.getTitle()));
                report.append(String.format("Author: %s\n", book.getAuthor()));
                report.append(String.format("Category: %s\n", book.getCategory()));
                report.append(String.format("Total Copies: %d\n", book.getTotalCopies()));
                report.append(String.format("Available: %d\n", book.getAvailableCopies()));
                report.append("----------------------------------------\n");
            }
            
            reportArea.setText(report.toString());
        });

        JButton borrowingButton = new JButton("Borrowing Report");
        borrowingButton.addActionListener(e -> {
            StringBuilder report = new StringBuilder();
            report.append("BORROWING ACTIVITY REPORT\n");
            report.append("=========================\n\n");
            
            List<BorrowRecord> records = libraryManager.getAllBorrowRecords();
            List<BorrowRecord> active = libraryManager.getActiveBorrowRecords();
            List<BorrowRecord> overdue = libraryManager.getOverdueRecords();
            
            report.append(String.format("Total Borrowing Records: %d\n", records.size()));
            report.append(String.format("Active Borrows: %d\n", active.size()));
            report.append(String.format("Overdue Books: %d\n\n", overdue.size()));
            
            if (!overdue.isEmpty()) {
                report.append("OVERDUE BOOKS:\n");
                report.append("==============\n");
                for (BorrowRecord record : overdue) {
                    report.append(String.format("Title: %s\n", record.getTitle()));
                    report.append(String.format("Borrower: %s\n", record.getBorrowerName()));
                    report.append(String.format("Due Date: %s\n", record.getDueDate()));
                    report.append("----------------------------------------\n");
                }
            }
            
            reportArea.setText(report.toString());
        });

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(inventoryButton, gbc);
        gbc.gridx = 1;
        panel.add(borrowingButton, gbc);

        return panel;
    }

    private void showAddBookDialog() {
        JDialog dialog = new JDialog(this, "Add New Book", true);
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
                refreshTables();
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

        String isbn = (String) booksTableModel.getValueAt(selectedRow, 0);
        Book book = libraryManager.findBookByIsbn(isbn);
        if (book == null) return;

        JDialog dialog = new JDialog(this, "Edit Book", true);
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
            refreshTables();
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

        String isbn = (String) booksTableModel.getValueAt(selectedRow, 0);
        String title = (String) booksTableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete '" + title + "'?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (libraryManager.removeBook(isbn)) {
                JOptionPane.showMessageDialog(this, "Book deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTables();
            }
        }
    }

    private void searchBooks(String query) {
        if (query.trim().isEmpty()) {
            refreshBooksTable();
            return;
        }

        List<Book> results = libraryManager.searchBooks(query);
        booksTableModel.setRowCount(0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Book book : results) {
            Object[] row = {
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                book.getTotalCopies(),
                book.getAvailableCopies(),
                book.getDateAdded().format(formatter)
            };
            booksTableModel.addRow(row);
        }
    }

    private void showAllRecords() {
        List<BorrowRecord> records = libraryManager.getAllBorrowRecords();
        updateBorrowRecordsTable(records);
    }

    private void showActiveRecords() {
        List<BorrowRecord> records = libraryManager.getActiveBorrowRecords();
        updateBorrowRecordsTable(records);
    }

    private void showOverdueRecords() {
        List<BorrowRecord> records = libraryManager.getOverdueRecords();
        updateBorrowRecordsTable(records);
    }

    private void updateBorrowRecordsTable(List<BorrowRecord> records) {
        borrowRecordsTableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (BorrowRecord record : records) {
            Object[] row = {
                record.getIsbn(),
                record.getTitle(),
                record.getBorrowerName(),
                record.getBorrowerEmail(),
                record.getBorrowDate().format(formatter),
                record.getDueDate().format(formatter),
                record.getReturnDate() != null ? record.getReturnDate().format(formatter) : "",
                record.isReturned() ? "Returned" : (record.isOverdue() ? "Overdue" : "Active")
            };
            borrowRecordsTableModel.addRow(row);
        }
    }

    private void refreshTables() {
        refreshBooksTable();
        showAllRecords();
    }

    private void refreshBooksTable() {
        booksTableModel.setRowCount(0);
        List<Book> books = libraryManager.getAllBooks();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (Book book : books) {
            Object[] row = {
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                book.getTotalCopies(),
                book.getAvailableCopies(),
                book.getDateAdded().format(formatter)
            };
            booksTableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.getSystemLookAndFeelClassName();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new LibraryManagementSystem().setVisible(true);
        });
    }
}
