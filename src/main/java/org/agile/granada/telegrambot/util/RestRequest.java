package org.agile.granada.telegrambot.util;

import java.util.ArrayList;

/**
 * Created by pabloarroyo on 11/10/2016.
 */
public class RestRequest {
    public static final String POST = "POST";
    public static final String GET = "GET";

    private String endPoint;
    private String method;
    private String body;
    private final ArrayList<RestHeader> headers;

    public RestRequest() {
        this.headers = new ArrayList<RestHeader>();
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void addHeader(String header, String value) {
        this.headers.add(new RestHeader(header, value));
    }

    public ArrayList<RestHeader> getHeaders() {
        return headers;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }
}
