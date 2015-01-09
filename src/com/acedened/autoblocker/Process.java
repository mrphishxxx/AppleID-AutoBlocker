package com.acedened.autoblocker;

import com.acedened.vkutils.VKPost;
import com.acedened.vkutils.VKWall;
import com.acedened.vkutils.WallFilter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Process {
    //вся магия здесь
    //url полный

    private int postsToScan;
    private VKWall wall;
    private boolean momentaryBlock;
    Output out;
    private Pattern pattern;
    private ArrayList<String> foundEmails;
    private EmailBlocker emailBlocker;
    LinkedList<String> emails = new LinkedList<String>();


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
        emailBlocker = new EmailBlocker();
        emailBlocker.out = out;
        emailBlocker.emails = emails;
        emailBlocker.site = site;
    }

    public void start() {
        emailBlocker.start();
        int offset = 0;
        Matcher matcher;
        try {
            ArrayList<VKPost> posts = wall.getPosts(offset, postsToScan, WallFilter.ALL);
            if (postsToScan > wall.getPostsCount())
                postsToScan = wall.getPostsCount();
            out.connectedToGroup();
            for (VKPost post : posts) {
                matcher = pattern.matcher(post.getText());
                while (matcher.find()) {
                    String email = matcher.group(0);
                    foundEmails.add(email);
                    out.foundAppleId(email);
                    if (momentaryBlock)
                        emails.add(email);
                }
            }

        } catch (Exception e) {
            out.error(e);
        }
        emailBlocker.allEmailsGiven = true;


    }

}