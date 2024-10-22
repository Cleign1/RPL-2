/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ibnuk - Muhamad Ibnu Khaidar Hafiz - 50421867 - 4IA15
 */
public class Seller extends User{
    private String product;
    
    public void addProduct(String productName) {
        this.product = productName;
        System.out.println("Seller Menambahkan Product "+productName);
    }
    
    public String sellProduct() {
        return this.product;
    }
}
