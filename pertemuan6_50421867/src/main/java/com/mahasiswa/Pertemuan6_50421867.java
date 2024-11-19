/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mahasiswa;

import com.mahasiswa.controller.mahasiswaController;
import com.mahasiswa.service.mahasiswaService;
import com.mahasiswa.view.mahasiswaView;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
/**
 *
 * @author ibnuk
 */
@SpringBootApplication
public class Pertemuan6_50421867 {

    @Autowired
    private mahasiswaService mahasiswaService;
    
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        
        ApplicationContext context = SpringApplication.run(Pertemuan6_50421867.class, args);
        
        mahasiswaController controller = context.getBean(mahasiswaController.class);
        mahasiswaView mahasiswaView = new mahasiswaView(controller);
        mahasiswaView.setVisible(true);
    }
}
