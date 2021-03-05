package com.gagantk.fuelcostcalculator.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.gagantk.fuelcostcalculator.service.FuelCostService;

@Component
@Profile("!test")
public class MessageQueueLoader implements CommandLineRunner {

    @Autowired
    private FuelCostService fuelCostService;
    
    @Override
	public void run(String... args) throws Exception {
        fuelCostService.getFuelEvent();
	}

}
 
