package com.abenezermulugeta.inventoryservice;

import com.abenezermulugeta.inventoryservice.model.Inventory;
import com.abenezermulugeta.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
@EnableEurekaClient
public class InventoryServiceApplication implements CommandLineRunner {
	private final InventoryRepository inventoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Inventory inventory = new Inventory();
		inventory.setSkuCode("iphone_13");
		inventory.setQuantity(100);
		inventoryRepository.save(inventory);

		Inventory inventory1 = new Inventory();
		inventory1.setSkuCode("iphone_13_red");
		inventory1.setQuantity(0);

		inventoryRepository.save(inventory1);
	}
}
