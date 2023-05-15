package com.SWE.project.Classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.SWE.project.Exceptions.InvalidCredentialsException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Login {

    public static void main(String[] args) throws Exception {
        try {
            System.out.println(logIn("6412", "7562"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static URI buildUri(String url, String key, String value) {
        try {
            if (url.charAt(url.length() - 1) != '?') {
                return new URI(url + "&" + key + "=" + value);
            }
            return new URI(url + key + "=" + value);
        } catch (Exception e) {
            return null;
        }

    }

    public static Map<String, String> logIn(String user, String password) throws InvalidCredentialsException {
        String urlString = "https://us-central1-swe206-221.cloudfunctions.net/app/UserSignIn?";
        URI temp = buildUri(urlString, "username", user);
        temp = buildUri(temp.toString(), "password", password);
        ObjectMapper mapper;
        Map<String, String> returnMap = new HashMap<>();

        URL url;
        try {
            url = temp.toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            int status = con.getResponseCode();
            if (status != 200)
                throw new InvalidCredentialsException("invalid Credentials");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            mapper = new ObjectMapper();

            returnMap = mapper.readValue(content.toString(), Map.class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnMap;
    }
}
