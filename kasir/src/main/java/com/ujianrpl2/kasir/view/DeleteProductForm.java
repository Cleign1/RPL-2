package com.ujianrpl2.kasir.view;

import com.ujianrpl2.kasir.controller.POSController;
import com.ujianrpl2.kasir.model.Product;
import javax.swing.*;
import java.awt.*;

public class DeleteProductForm extends JFrame {
    private JPanel mainPanel;
    private JTextField idField;
    private JButton deleteButton;
    private JButton cancelButton;
    private JLabel idLabel;
    private final POSController controller;

    public DeleteProductForm(POSController controller) {
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        setTitle("Hapus Produk");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);  // Center on screen

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // ID Label and Field
        idLabel = new JLabel("Product ID:");
        idField = new JTextField(15);

        // Buttons
        deleteButton = new JButton("Hapus");
        cancelButton = new JButton("Batalkan");

        // Layout components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(idLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(idField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        // Add main panel to frame
        add(mainPanel);

        // Add action listeners
        deleteButton.addActionListener(e -> {
            try {
                Long id = Long.parseLong(idField.getText());
                Product product = controller.getProductById(id);

                if (product != null) {
                    int confirm = JOptionPane.showConfirmDialog(
                            this,
                            "Apakah Anda yakin ingin menghapus produk ini?\n" +
                                    "Nama: " + product.getName() + "\n" +
                                    "Harga: " + product.getPrice() + "\n" +
                                    "Stok: " + product.getStock(),
                            "Konfirmasi Penghapusan",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        controller.deleteProduct(id);
                        JOptionPane.showMessageDialog(
                                this,
                                "Produk Berhasil Dihapus!",
                                "Berhasil",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Produk tidak ditemukan!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Tolong masukkan ID yang benar",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }
}