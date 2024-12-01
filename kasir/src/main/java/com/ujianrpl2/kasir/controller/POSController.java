package com.ujianrpl2.kasir.controller;

import com.ujianrpl2.kasir.dao.ProductDAO;
import com.ujianrpl2.kasir.model.Product;
import com.ujianrpl2.kasir.model.CartItem;
import com.ujianrpl2.kasir.view.MainView;
import com.ujianrpl2.kasir.view.CheckoutSuccessForm;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;


public class POSController {
    private final ProductDAO productDAO;
    private final MainView mainView;
    private final List<CartItem> cart;

    public POSController(ProductDAO productDAO) {
        this.productDAO = productDAO;
        this.cart = new ArrayList<>();
        this.mainView = new MainView(this);
        refreshProductList();
    }

    public void showMainView() {
        mainView.setVisible(true);
    }

    public void refreshProductList() {
        List<Product> products = productDAO.getAllProducts();
        mainView.updateProductTable(products);
    }

    public void addToCart(Product product, int quantity) {
        if (product.getStock() >= quantity) {
            cart.add(new CartItem(product, quantity));
            mainView.updateCart(cart);
        } else {
            mainView.showError("Stok Kurang!");
        }
    }

    public void processTransaction() {
        // Create a final copy of the cart items for the lambda
        final List<CartItem> cartItems = new ArrayList<>(cart);
        double total = 0;

        // Calculate total
        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }

        // Store total in final variable for lambda
        final double finalTotal = total;

        // Update stock for each item
        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            productDAO.updateProduct(product);
        }

        // Show success popup
        SwingUtilities.invokeLater(() -> {
            new CheckoutSuccessForm(cartItems, finalTotal).setVisible(true);
        });

        // Clear cart and refresh view
        cart.clear();
        mainView.updateCart(cart);
        mainView.updateProductTable(productDAO.getAllProducts());
    }

    public void addProduct(String name, double price, int stock) {
        Product product = new Product(name, price, stock);
        productDAO.addProduct(product);
        refreshProductList();
    }

    public void updateProduct(Long id, String name, double price, int stock) {
        Product product = productDAO.findById(id);
        if (product != null) {
            product.setName(name);
            product.setPrice(price);
            product.setStock(stock);
            productDAO.updateProduct(product);
            refreshProductList();
        }
    }

    public void deleteProduct(Long id) {
        productDAO.deleteProduct(id);
        refreshProductList();
    }

    public Product getProductById(Long id) {
        return productDAO.findById(id);
    }

    public void refreshCart() {
        mainView.updateCart(cart);
    }
}
