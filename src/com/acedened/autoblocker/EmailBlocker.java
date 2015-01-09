package com.acedened.autoblocker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

public class EmailBlocker extends Thread {

    boolean allEmailsGiven = false;
    Output out;
    String site;
    LinkedList<String> emails;

    @Override
    public void run() {
        while (true) {
            if (allEmailsGiven && emails.isEmpty()) {
                out.ended(null); //todo: return blocked ids
                this.stop();
            }
            String email;
            try {
                email = emails.get(0);
                emails.remove(0);
                try {
                    out.startedBlockingAppleId(email);
                    URL url = new URL("http://" + site + "?txtFullName=" + email);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    if (connection.getResponseCode() != 200) //200 is OK
                        out.error(new Exception("Unable to connect. Error code " + connection.getResponseCode()));
                    else {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String siteOutput;
                        out.startedBlockingAppleId(email);
                        while ((siteOutput = reader.readLine()) != null) {
                            out.siteOutput(siteOutput, email);
                        }
                    }
                } catch (Exception e) {
                    out.error(e);
                }
                out.endedBlockingAppleId(email);
            } catch (Exception e) {
            }


        }

    }
}
