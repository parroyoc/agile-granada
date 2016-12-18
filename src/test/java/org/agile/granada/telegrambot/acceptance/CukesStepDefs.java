package org.agile.granada.telegrambot.acceptance;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.User;

import static org.junit.Assert.assertTrue;

public class CukesStepdefs {

    private static final String MENSAJE_DESCONOCIDO = "mensaje desconocido";
    private String city;
    private String response;
    private User user;

    @Given("^I am Pablo Arroyo$")
    public void I_am_Pablo_Arroyo() throws Throwable {
        user = new User(new JSONObject("{\"id\":\"12345\",\"first_name\":\"Pablo\",\"last_name\":\"Arroyo\",\"username\":\"Waterflow\"}"));
    }

    @Given("^I want to know the temperature in a city which is among the cities for which the temperature is known$")
    public void I_want_to_know_the_temperature_in_a_city_which_is_among_the_cities_for_which_the_temperature_is_known() throws Throwable {
        city = "New York";
    }

    @When("^I ask the bot for the temperature in that city$")
    public void I_ask_the_bot_for_the_temperature_in_that_city() throws Throwable {
        response = ChatCommandBotClient.chat(user, "temperatura " + city);
    }

    @Then("^The bot replies with a message indicating the temperature in the city$")
    public void The_bot_replies_with_a_message_indicating_the_temperature_in_the_city() throws Throwable {
        assertTrue(response.contains("La temperatura en " + city + " es de"));
        assertTrue(response.contains("grados"));
    }

    @When("^I ask the bot for an unknown message$")
    public void I_ask_the_bot_for_an_unknown_message() throws Throwable {
        response = ChatCommandBotClient.chat(user, MENSAJE_DESCONOCIDO);
    }

    @Then("^The bot replies saying \"([^\"]*)\"$")
    public void The_bot_replies_saying(String partialResponse) throws Throwable {
        assertTrue(response.contains(partialResponse));
    }

    @And("^The bot replies saying that he does not know how to reply to my question$")
    public void The_bot_replies_saying_that_he_does_not_know_how_to_reply_to_my_question() throws Throwable {
        assertTrue(response.contains("No tengo claro que contestar a:"));
        assertTrue(response.contains(MENSAJE_DESCONOCIDO));
    }
}