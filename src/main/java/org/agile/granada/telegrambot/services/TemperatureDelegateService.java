package org.agile.granada.telegrambot.services;

/**
 * Created by pabloarroyo on 18/12/2016.
 */
public class TemperatureDelegateService implements IDelegateService {
    private WeatherClient weatherClient;

    @Override
    public String answer(String city) {
        String celsius = weatherClient.celsiusFor(city);
        if (celsius.equalsIgnoreCase("Unknown"))
            return "No puedo contestar la temperatura en " + city;
        else return "La temperatura en " + city + " es de " + celsius + " grados";
    }

    public void setWeatherClient(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }
}
