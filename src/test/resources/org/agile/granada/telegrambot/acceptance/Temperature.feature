@Bot
@Temperature
Feature: Temperature in a city
  AS a customer
  I WANT to know the temperature in a city
  SO THAT I can decide what to wear

  Scenario: Temperature in a city is presented for known cities
    Given I am Pablo Arroyo
    And I want to know the temperature in a city which is among the cities for which the temperature is known
    When I ask the bot for the temperature in that city
    Then The bot replies with a message indicating the temperature in the city

