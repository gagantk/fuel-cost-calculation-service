package com.gagantk.fuelcostcalculator.config;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttConfig {
	
	private static final String MQTT_PUBLISHER_ID = "fuelevent-subscriber";
	private static final String MQTT_SERVER_URI = "tcp://127.0.0.1:1883";
	private static IMqttClient mqttClient;
	
	public static IMqttClient getMqttClient() {
		try {
			if (mqttClient == null) {
				mqttClient = new MqttClient(MQTT_SERVER_URI, MQTT_PUBLISHER_ID);
			}
			
			MqttConnectOptions options = new MqttConnectOptions();
			options.setAutomaticReconnect(true);
			options.setCleanSession(true);
			options.setConnectionTimeout(10);
			
			if (!mqttClient.isConnected()) {
				mqttClient.connect(options);
			}
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
		return mqttClient;
	}

}
