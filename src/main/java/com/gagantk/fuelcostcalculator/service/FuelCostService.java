package com.gagantk.fuelcostcalculator.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gagantk.fuelcostcalculator.config.MqttConfig;
import com.gagantk.fuelcostcalculator.model.FuelEvent;
import com.gagantk.fuelcostcalculator.model.FuelPrice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FuelCostService {

	private Map<String, Date> carFuelStatus = new HashMap<String, Date>();

	public void getFuelEvent() throws MqttException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
		CountDownLatch countDownLatch = new CountDownLatch(10);
		MqttConfig.getMqttClient().subscribeWithResponse("fuel", (s, mqttMessage) -> {
            FuelEvent fuelEvent = mapper.readValue(mqttMessage.getPayload(), FuelEvent.class);
			log.debug("{}: {}", fuelEvent.getCity(), fuelEvent.getFuelLid());
			calculateFuelAmount(fuelEvent);
			countDownLatch.countDown();
		});

		countDownLatch.await(5, TimeUnit.SECONDS);
	}

	public void calculateFuelAmount(FuelEvent fuelEvent) throws MqttException, InterruptedException {
		String city = fuelEvent.getCity();
		if (carFuelStatus.containsKey(city)) {
			if (!fuelEvent.getFuelLid()) {
                float timeDiff = (new Date().getTime() - carFuelStatus.get(city).getTime()) / 1000;
				float fuelQuantity = (timeDiff / 30);
				log.debug("timeDiff: {} seconds, fuelQuantity: {} L", new DecimalFormat("#").format(timeDiff), String.format("%.2f", fuelQuantity));
                float totalAmount = fuelQuantity * getFuelPrice(city);
				log.info("A car in {} has filled {} L of fuel in {} seconds and total fuel cost is ₹{}", city, String.format("%.2f", fuelQuantity),
						new DecimalFormat("#").format(timeDiff), String.format("%.2f", totalAmount));
                carFuelStatus.remove(city);
			}
		} else if (fuelEvent.getFuelLid()) {
            Date currentDateTime = new Date();
			carFuelStatus.put(city, currentDateTime);
		}
	}

	@Cacheable(value="fuelPrices", key="#city")
	private float getFuelPrice(String city) {
		String fuelPriceURL = "http://fuel-price-api.herokuapp.com/api/price/{city}";
		ResponseEntity<FuelPrice> responseEntity = new RestTemplate().getForEntity(fuelPriceURL, FuelPrice.class, city);
		FuelPrice fuelPrice = responseEntity.getBody();
		log.info(fuelPrice.getCity(), fuelPrice.getPrice());
		return fuelPrice.getPrice();
	}

}
