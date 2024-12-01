package com.ujianrpl2.kasir.view;

import com.ujianrpl2.kasir.model.CartItem;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CheckoutSuccessForm extends JFrame {
    private JPanel mainPanel;
    private JTextArea orderSummary;
    private JButton okButton;
    private final List<CartItem> cartItems;
    private final double totalAmount;

    public CheckoutSuccessForm(List<CartItem> cartItems, double totalAmount) {
        this.cartItems = cartItems;
        this.totalAmount = totalAmount;
        initComponents();
        displayOrderSummary();
    }

    private void initComponents() {
        setTitle("Checkout Berhasil");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header
        JLabel headerLabel = new JLabel("Transaksi Berhasil!", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Order summary
        orderSummary = new JTextArea();
        orderSummary.setEditable(false);
        orderSummary.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(orderSummary);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // OK button
        okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void displayOrderSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("=====================================\n");
        summary.append("          RINGKASAN PESANAN         \n");
        summary.append("=====================================\n\n");

        // Add current date and time
        summary.append("Tanggal: ").append(java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        )).append("\n\n");

        // Column headers
        summary.append(String.format("%-20s %-8s %-12s\n", "Produk", "Qty", "Subtotal"));
        summary.append("-------------------------------------\n");

        // Items
        for (CartItem item : cartItems) {
            summary.append(String.format("%-20s %-8d IDR %-12.2f\n",
                    truncateString(item.getProduct().getName(), 20),
                    item.getQuantity(),
                    item.getSubtotal()
            ));
        }

        // Total
        summary.append("\n=====================================\n");
        summary.append(String.format("TOTAL: IDR %.2f\n", totalAmount));
        summary.append("=====================================\n\n");
        summary.append("          Terima kasih!             \n");
        summary.append("    Silahkan datang kembali!        \n");

        orderSummary.setText(summary.toString());
    }

    private String truncateString(String str, int length) {
        if (str.length() > length) {
            return str.substring(0, length - 3) + "...";
        }
        return str;
    }
}