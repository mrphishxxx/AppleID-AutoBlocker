package com.acedened.vkutils;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;

public class VKWall {

    long ownerId;
    String domain;
    int postsCount;

    public VKWall(String domain, Long groupID) {
        if (groupID == null && domain == null) {
            throw new IllegalArgumentException();
        }

        this.domain = domain;
        if (groupID == null)
            groupID = 0L;
        this.ownerId = groupID;
    }

    public ArrayList<VKPost> getPosts
            (int offset, int count, WallFilter wallFilter) throws IOException, VKApiException {


        String filter = wallFilter.name().toLowerCase();
        String request = VKRequest.build("wall.get",
                new VKArgument("owner_id", ownerId),
                new VKArgument("domain", domain),
                new VKArgument("offset", offset),
                new VKArgument("count", count),
                new VKArgument("wall_filter", filter));

        String data = VKRequest.getRequestData(request);
        ArrayList response = (ArrayList) new Gson().fromJson(data, HashMap.class).get("response");
        if (response == null)
            throw new VKApiException((LinkedTreeMap) new Gson().fromJson(data, HashMap.class).get("error"));
        ArrayList<VKPost> posts = new ArrayList<VKPost>();
        postsCount = ((Double)response.get(0)).intValue();
        response.remove(0);
        for (Object postData : response) {
            LinkedTreeMap post = (LinkedTreeMap) postData;
            Double fromId = (Double)post.get("from_id");
            posts.add(new VKPost(post.get("text").toString(), fromId.longValue()));
        }

        return posts;
    }

    public int getPostsCount() {
        return postsCount;
    }

}
