import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReportsPanel extends LibraryPanel {

    public ReportsPanel() {
        super(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextArea reportArea = new JTextArea(20, 50);
        reportArea.setEditable(false);
        reportArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(reportArea);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4;
        add(scrollPane, gbc);

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

            List<BorrowRecord> records = libraryManager.getBorrowRecords(new AllRecordsFilter());
            List<BorrowRecord> active = libraryManager.getBorrowRecords(new ActiveRecordsFilter());
            List<BorrowRecord> overdue = libraryManager.getBorrowRecords(new OverdueRecordsFilter());

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
        add(inventoryButton, gbc);
        gbc.gridx = 1;
        add(borrowingButton, gbc);
    }
}
