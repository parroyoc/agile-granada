package org.agile.granada.telegrambot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RestHelper {

    public static RestResponse execute(RestRequest request) {
        if (request.getMethod().equalsIgnoreCase(RestRequest.POST)) return doPostCall(request);
        else return doGetCall(request);
    }

    private static RestResponse doPostCall(RestRequest request) {
        RestResponse result = new RestResponse();

        try {
            URL url = new URL(request.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            try {
                conn.setDoOutput(true);
                conn.setRequestMethod(request.getMethod());
                addHeaders(conn, request.getHeaders());
                OutputStream os = conn.getOutputStream();
                os.write(request.getBody().getBytes());
                os.flush();
                os.close();
                if (conn.getResponseCode() != 200) {
                    System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
                }

                result.setResponseCode(conn.getResponseCode());
                result.setContent(readFullInputStream(conn));
            } catch (Exception e) {
                result.setResponseCode(conn.getResponseCode());
                System.out.println("unable to call rest endpoint" + e);
                e.printStackTrace();
            }

            conn.disconnect();
        } catch (Exception e) {
            result.setResponseCode(-1);
            System.out.println("unable to call rest endpoint" + e);
            e.printStackTrace();
        }

        return result;
    }

    private static RestResponse doGetCall(RestRequest request) {
        RestResponse result = new RestResponse();

        try {
            URL url = new URL(request.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            try {
                conn.setRequestMethod(request.getMethod());
                addHeaders(conn, request.getHeaders());
                if (conn.getResponseCode() != 200) {
                    System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
                }

                result.setResponseCode(conn.getResponseCode());
                result.setContent(readFullInputStream(conn));
            } catch (Exception e) {
                result.setResponseCode(conn.getResponseCode());
                System.out.println("unable to call rest endpoint" + e);
                e.printStackTrace();
            }

            conn.disconnect();
        } catch (Exception e) {
            result.setResponseCode(-1);
            System.out.println("unable to call rest endpoint" + e);
            e.printStackTrace();
        }

        return result;
    }

    private static void addHeaders(HttpURLConnection conn, ArrayList<RestHeader> headers) {
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        for (RestHeader head : headers) {
            conn.setRequestProperty(head.getHeader(), head.getValue());
        }
    }

    private static String readFullInputStream(HttpURLConnection conn) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String read;
        while ((read = br.readLine()) != null) {
            sb.append(read);
        }

        br.close();
        return sb.toString();
    }
}
