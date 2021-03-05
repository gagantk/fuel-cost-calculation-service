package com.gagantk.fuelcostcalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FuelCostCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuelCostCalculatorApplication.class, args);
	}

}
