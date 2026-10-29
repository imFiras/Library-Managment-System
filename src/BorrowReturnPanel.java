import javax.swing.*;
import java.awt.*;

public class BorrowReturnPanel extends LibraryPanel {
    private final CirculationFacade circulation = new CirculationFacade(this);

    public BorrowReturnPanel() {
        super(new GridBagLayout());
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
            if (circulation.borrowBook(borrowIsbnField.getText(), borrowerNameField.getText(), borrowerEmailField.getText())) {
                borrowIsbnField.setText("");
                borrowerNameField.setText("");
                borrowerEmailField.setText("");
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
            if (circulation.returnBook(returnIsbnField.getText(), returnNameField.getText())) {
                returnIsbnField.setText("");
                returnNameField.setText("");
            }
        });
        returnPanel.add(returnButton, gbc);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.NORTH;
        add(borrowPanel, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        add(returnPanel, gbc);
    }
}
