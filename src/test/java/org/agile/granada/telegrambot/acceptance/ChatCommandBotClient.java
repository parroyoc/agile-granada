package org.agile.granada.telegrambot.acceptance;

import org.agile.granada.telegrambot.services.ResponseGeneratorService;
import org.telegram.telegrambots.api.objects.User;

public class ChatCommandBotClient {

    /* Esto tendria que ser E2E en lo posible. En este caso es casi una prueba unitaria adicional
     * dado que es muy complejo simular un cliente de Telegram con un chatId valido */
    public static String chat(User user, String message) {
        ResponseGeneratorService responseGenerator = new ResponseGeneratorService();
        return responseGenerator.answer(user, message);
    }
}
