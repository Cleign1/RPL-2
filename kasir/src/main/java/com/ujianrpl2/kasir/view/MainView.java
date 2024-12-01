package com.ujianrpl2.kasir.view;

import com.ujianrpl2.kasir.controller.POSController;
import com.ujianrpl2.kasir.model.Product;
import com.ujianrpl2.kasir.model.CartItem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainView extends JFrame {
    private final POSController controller;
    private final JTable productTable;
    private final JTable cartTable;
    private final JTextField nameField;
    private final JTextField priceField;
    private final JTextField stockField;
    private final JLabel totalLabel;

    public MainView(POSController controller) {
        this.controller = controller;

        // Initialize components first
        productTable = new JTable();
        cartTable = new JTable();
        nameField = new JTextField(20);
        priceField = new JTextField(10);
        stockField = new JTextField(10);
        totalLabel = new JLabel("Total: IDR0");

        // Set up the frame
        setTitle("Aplikasi POS");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Point of Sale", createPOSPanel());
        tabbedPane.addTab("Inventori", createInventoryPanel());
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createPOSPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Product list
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setBorder(BorderFactory.createTitledBorder("Produk"));
        productTable.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {"ID", "Nama", "Harga", "Stok"}
        ));

        // Add refresh button for product table
        JButton refreshProductBtn = new JButton("Refresh Produk");
        refreshProductBtn.addActionListener(e -> controller.refreshProductList());

        // Create a panel for the product table and its refresh button
        JPanel productTopPanel = new JPanel(new BorderLayout());
        productTopPanel.add(refreshProductBtn, BorderLayout.NORTH);
        productTopPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);
        productPanel.add(productTopPanel, BorderLayout.CENTER);

        // Cart
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Keranjang Belanja"));
        cartTable.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {"Produk", "Kuantitas", "Subtotal"}
        ));

        // Add refresh button for cart
        JButton refreshCartBtn = new JButton("Refresh Keranjang");
        refreshCartBtn.addActionListener(e -> controller.refreshCart());

        // Create a panel for the cart table and its refresh button
        JPanel cartTopPanel = new JPanel(new BorderLayout());
        cartTopPanel.add(refreshCartBtn, BorderLayout.NORTH);
        cartTopPanel.add(new JScrollPane(cartTable), BorderLayout.CENTER);
        cartPanel.add(cartTopPanel, BorderLayout.CENTER);

        // Add to cart button
        JButton addToCartBtn = new JButton("Tambah Ke Keranjang");
        addToCartBtn.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                String quantityStr = JOptionPane.showInputDialog("Masukkan kuantitas:");
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    Product product = getSelectedProduct();
                    if (product != null) {
                        controller.addToCart(product, quantity);
                    }
                } catch (NumberFormatException ex) {
                    showError("Kuantitas Belum Benar!");
                }
            }
        });

        // Checkout button
        JButton checkoutBtn = new JButton("Checkout");
        checkoutBtn.addActionListener(e -> controller.processTransaction());

        // Layout
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addToCartBtn);
        buttonsPanel.add(checkoutBtn);
        buttonsPanel.add(totalLabel);

        panel.add(productPanel, BorderLayout.CENTER);
        panel.add(cartPanel, BorderLayout.EAST);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Form for adding/editing products
        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        formPanel.add(new JLabel("Nama:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Harga:"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Stok:"));
        formPanel.add(stockField);

        JButton addBtn = new JButton("Tambah Produk");
        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                controller.addProduct(name, price, stock);
                clearFields();
            } catch (NumberFormatException ex) {
                showError("Input Salah!");
            }
        });

        JButton updateBtn = new JButton("Update Produk");
        updateBtn.addActionListener(e -> {
            new UpdateProductIDForm(controller).setVisible(true);
        });

        JButton deleteBtn = new JButton("Hapus Produk");
        deleteBtn.addActionListener(e -> {
            new DeleteProductForm(controller).setVisible(true);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }

    public void updateProductTable(List<Product> products) {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0);
        for (Product product : products) {
            model.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock()
            });
        }
    }

    public void updateCart(List<CartItem> cart) {
        DefaultTableModel model = (DefaultTableModel) cartTable.getModel();
        model.setRowCount(0);
        double total = 0;

        for (CartItem item : cart) {
            model.addRow(new Object[]{
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getSubtotal()
            });
            total += item.getSubtotal();
        }

        totalLabel.setText(String.format("Total: IDR%.2f", total));
    }

    private Product getSelectedProduct() {
        int row = productTable.getSelectedRow();
        if (row >= 0) {
            Long id = (Long) productTable.getValueAt(row, 0);
            String name = (String) productTable.getValueAt(row, 1);
            double price = (double) productTable.getValueAt(row, 2);
            int stock = (int) productTable.getValueAt(row, 3);

            Product product = new Product(name, price, stock);
            product.setId(id);
            return product;
        }
        return null;
    }

    private void clearFields() {
        nameField.setText("");
        priceField.setText("");
        stockField.setText("");
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}