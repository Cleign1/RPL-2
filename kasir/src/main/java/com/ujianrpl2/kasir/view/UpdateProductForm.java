package com.ujianrpl2.kasir.view;

import com.ujianrpl2.kasir.controller.POSController;
import com.ujianrpl2.kasir.model.Product;

import javax.swing.*;
import java.awt.*;

public class UpdateProductForm extends JFrame {
    private JPanel mainPanel;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField stockField;
    private JButton updateButton;
    private JButton cancelButton;
    private final POSController controller;
    private final Long productId;
    private final JFrame parentFrame;

    public UpdateProductForm(POSController controller, Long productId, JFrame parentFrame) {
        this.controller = controller;
        this.productId = productId;
        this.parentFrame = parentFrame;
        initComponents();
        if (!loadProductData()) {
            // If product not found, both frames will be closed
            parentFrame.dispose();
            dispose();
        }
    }
    private void initComponents() {
        setTitle("Update Produk");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Product Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Nama Produk:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        mainPanel.add(nameField, gbc);

        // Price
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Harga:"), gbc);

        gbc.gridx = 1;
        priceField = new JTextField(20);
        mainPanel.add(priceField, gbc);

        // Stock
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Stok:"), gbc);

        gbc.gridx = 1;
        stockField = new JTextField(20);
        mainPanel.add(stockField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        updateButton = new JButton("Update");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);

        // Add main panel to frame
        add(mainPanel);

        // Add action listeners
        updateButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());

                controller.updateProduct(productId, name, price, stock);
                JOptionPane.showMessageDialog(this,
                        "Produk berhasil diperbarui!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Harap masukkan angka yang valid untuk harga dan stok.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }

    private boolean loadProductData() {
        Product product = controller.getProductById(productId);
        if (product != null) {
            nameField.setText(product.getName());
            priceField.setText(String.valueOf(product.getPrice()));
            stockField.setText(String.valueOf(product.getStock()));
            return true;
        } else {
            JOptionPane.showMessageDialog(this,
                    "Product tidak ditemukan!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
