package com.acedened.autoblocker;

public abstract class Output {

    abstract void connectedToGroup();

    abstract void error(Exception e);

    abstract void foundAppleId(String id);

    abstract void startedBlockingAppleId(String id);

    abstract void attemptBlockingAppleId(String id, String password);

    abstract void endedBlockingAppleId(String id);

    abstract void ended(String listOfIds[]);

    abstract void siteOutput(String output, String id);

}