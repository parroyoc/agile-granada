package org.agile.granada.telegrambot.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.agile.granada.telegrambot.util.RestHelper;
import org.agile.granada.telegrambot.util.RestRequest;
import org.agile.granada.telegrambot.util.RestResponse;
import org.json.JSONObject;

import static org.junit.Assert.assertEquals;

/**
 * Created by pabloarroyo on 18/12/2016.
 */
public class WeatherClient {
    private static final String BASE_URL = "http://weathers.co/api.php";

    public String celsiusFor(String city) {
        String response = performCall("?city=" + city.replace(" ", "+"));
        return extractFieldFromJson(response, "temperature");
    }

    private String extractFieldFromJson(String response, String field) {
        try {
            JSONObject json = new JSONObject(response);
            Object value = ((org.json.JSONObject)json.get("data")).get(field);
            return value.toString();
        } catch (Exception e) {
            return "Unknown";
        }
    }

    public static String performCall(String query) {
        RestRequest request = new RestRequest();
        request.setMethod(RestRequest.GET);
        request.setEndPoint(BASE_URL + query);
        RestResponse response = RestHelper.execute(request);
        assertEquals(200, response.getResponseCode());
        return response.getContent();
    }
}
