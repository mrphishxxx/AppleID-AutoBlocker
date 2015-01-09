package com.acedened.autoblocker;

import com.acedened.vkutils.VKPost;
import com.acedened.vkutils.VKWall;
import com.acedened.vkutils.WallFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Process {
    //вся магия здесь
    //url полный

    private int postsToScan;
    private VKWall wall;
    private boolean momentaryBlock;
    private Output out;
    private Pattern pattern;
    private ArrayList<String> foundEmails;
    private String site;

    private static final String EMAIL_REGEX =
            "[\\w\\.\\-]+@[\\w\\d]{1,10}(\\.[\\w\\d]{2,4})+";

    Process(String url, int postsToScan, boolean momentaryBlock, String site, Output output) {
        String[] splitURL = url.split("/");
        wall = new VKWall(splitURL[splitURL.length -1], 0L);
        out = output;
        this.momentaryBlock = momentaryBlock;
        this.postsToScan = postsToScan;
        pattern = Pattern.compile(EMAIL_REGEX);
        foundEmails = new ArrayList<String>();
        this.site = site;
    }

    public void start() throws IOException {
        int offset = 0;
        if (postsToScan == 0)
            postsToScan = Integer.MAX_VALUE;
        Matcher matcher;
        do {
            try {
                ArrayList<VKPost> posts = wall.getPosts(offset, 100, WallFilter.ALL);
                if (postsToScan > wall.getPostsCount())
                    postsToScan = wall.getPostsCount();
                for (VKPost post : posts) {
                    matcher = pattern.matcher(post.getText());
                    if (matcher.find()) {
                        String email = matcher.group(0);
                        out.foundAppleId(email);
                        if (momentaryBlock) {
                            blockEmail(email);
                        } else {
                            foundEmails.add(email);
                        }
                    }
                }
            } catch (Exception e) {
                out.error(e);
                return;
            }
            postsToScan -= 100;
            offset += 100;
        } while (postsToScan >= 0);
        blockEmails(foundEmails);
    }

    private void blockEmails(Collection<String> emails) throws IOException {
        for (String email : emails) {
            blockEmail(email);
        }
    }

    public void blockEmail(String email) throws IOException {
        URL url = new URL( "http://" + site + "?txtFullName=" + email);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (connection.getResponseCode() != 200) //200 is OK
            throw new ConnectException("Unable to connect: " + connection.getResponseCode());
        else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String siteOutput;
            out.startedBlockingAppleId(email);
            while ((siteOutput = reader.readLine()) != null) {
                out.siteOutput(siteOutput);
            }
        }

    }


}