package com.acedened.vkutils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class VKRequest {

    private static final String base = "https://api.vk.com/method/";

    public static String build(String methodName, VKArgument... methodArguments) {
        StringBuilder builder = new StringBuilder(base);
        builder.append(methodName).append('?');
        for (int i = 0; i < methodArguments.length; i++) {
            VKArgument arg = methodArguments[i];
            builder.append(arg.toString());
            if (i != methodArguments.length - 1) // if not last element
                builder.append("&");
        }
        return builder.toString();
    }

    public static String getRequestData(String fullURL) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line;
        URL url = new URL(fullURL);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        if (connection.getResponseCode() != 200)
            throw new ConnectException("Unable to connect: " + connection.getResponseCode());
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        return builder.toString();
    }

}
