/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ibnuk - Muhamad Ibnu Khaidar Hafiz - 50421867 - 4IA15
 */
public class main {
    public static void main(String[] args) {
        User user = new User();
        user.login();
        user.logout();
        
        Buyer buyer = new Buyer();
        buyer.login();
        buyer.logout();
        
        Seller seller = new Seller();
        seller.addProduct("Mie Instan Goreng");
        
        System.out.println("Seller Menjual "+seller.sellProduct());
    }
}
