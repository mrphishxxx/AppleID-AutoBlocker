package com.acedened.autoblocker;

import com.acedened.vkutils.VKPost;
import com.acedened.vkutils.VKWall;
import com.acedened.vkutils.WallFilter;

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

    private static final String EMAIL_REGEX =
            "[\\w\\.\\-]+@[\\w\\d]{1,10}(\\.[\\w\\d]{2,4})+";

    Process(String url, int postsToScan, boolean momentaryBlock, Output output) {
        String[] splitURL = url.split("/");
        wall = new VKWall(splitURL[splitURL.length -1], 0L);
        out = output;
        this.momentaryBlock = momentaryBlock;
        this.postsToScan = postsToScan;
        pattern = Pattern.compile(EMAIL_REGEX);
        foundEmails = new ArrayList<String>();
    }

    public void start() {
        int offset = 0;
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
                        if (momentaryBlock)
                            blockEmail(email);
                        else
                            foundEmails.add(email);
                    }
                }
            } catch (Exception e) {
                out.error(e);
                return;
            }
        } while (postsToScan >= 0);
        blockEmails(foundEmails);
    }

    private void blockEmails(Collection<String> emails) {
        //TODO: realise
    }

    public void blockEmail(String email) {
        //TODO: realize
    }


}