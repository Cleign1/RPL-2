package com.ujianrpl2.kasir.view;

import com.ujianrpl2.kasir.controller.POSController;

import javax.swing.*;
import java.awt.*;

public class UpdateProductIDForm extends JFrame {
    private JPanel mainPanel;
    private JTextField idField;
    private JButton nextButton;
    private JButton cancelButton;
    private JLabel idLabel;
    private final POSController controller;

    public UpdateProductIDForm(POSController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Perbarui Produk - Masukkan ID");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // ID Label and Field
        idLabel = new JLabel("Product ID:");
        idField = new JTextField(15);

        // Buttons
        nextButton = new JButton("Next");
        cancelButton = new JButton("Cancel");

        // Layout components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(idLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(idField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nextButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        // Add main panel to frame
        add(mainPanel);

        // Add action listeners
        nextButton.addActionListener(e -> {
            try {
                Long id = Long.parseLong(idField.getText());
                new UpdateProductForm(controller, id, this).setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Tolong Masukkan ID Yang Valid",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }
}
