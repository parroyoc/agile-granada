@Bot
Feature: General chat
  AS a customer
  I WANT to ask questions to the Agile Granada bot
  SO THAT I can get my answers in telegram

  Scenario: Unknown questions are answered with an unknown message
    Given I am Pablo Arroyo
    When I ask the bot for an unknown message
    Then The bot replies saying "Hola Pablo Arroyo"
    And The bot replies saying that he does not know how to reply to my question



