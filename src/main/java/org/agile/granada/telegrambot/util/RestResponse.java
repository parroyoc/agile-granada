package org.agile.granada.telegrambot.util;

/**
 * Created by pabloarroyo on 11/10/2016.
 */
public class RestResponse {
    private String content;
    private int responseCode;

    public void setContent(String content) {
        this.content = content;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getContent() {
        return content;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
