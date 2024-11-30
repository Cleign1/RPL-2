package com.ujianrpl2.kasir.controller;

import com.ujianrpl2.kasir.model.Product;
import com.ujianrpl2.kasir.model.CartItem;
import com.ujianrpl2.kasir.dao.ProductDAO;
import com.ujianrpl2.kasir.view.MainView;
import java.util.ArrayList;
import java.util.List;

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
        // Update stock for each item
        for (CartItem item : cart) {
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            productDAO.updateProduct(product);
        }

        // Clear cart and refresh view
        cart.clear();
        mainView.updateCart(cart);
        refreshProductList();
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
}
