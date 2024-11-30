package com.ujianrpl2.kasir;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.ujianrpl2.kasir.controller.POSController;
import com.ujianrpl2.kasir.dao.ProductDAO;

@SpringBootApplication
public class KasirApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(KasirApplication.class)
				.headless(false)
				.run(args);

		java.awt.EventQueue.invokeLater(() -> {
			ProductDAO productDAO = context.getBean(ProductDAO.class);
			POSController controller = new POSController(productDAO);
			controller.showMainView();
		});
	}

}
