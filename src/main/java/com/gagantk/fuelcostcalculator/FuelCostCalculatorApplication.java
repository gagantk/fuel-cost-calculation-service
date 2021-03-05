package com.gagantk.fuelcostcalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.gagantk.fuelcostcalculator.service.FuelCostService;

@SpringBootApplication
@EnableCaching
public class FuelCostCalculatorApplication implements CommandLineRunner {

    @Autowired
    private FuelCostService fuelCostService;

	public static void main(String[] args) {
		SpringApplication.run(FuelCostCalculatorApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
        fuelCostService.getFuelEvent();
	}

}
