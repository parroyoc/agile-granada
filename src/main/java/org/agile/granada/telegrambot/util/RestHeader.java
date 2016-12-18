package org.agile.granada.telegrambot.util;

/**
 * Created by pabloarroyo on 11/10/2016.
 */
public class RestHeader {
    private final String header;
    private final String value;

    public RestHeader(final String header, final String value) {
        this.header = header;
        this.value = value;
    }

    public String getHeader() {
        return header;
    }

    public String getValue() {
        return value;
    }
}
