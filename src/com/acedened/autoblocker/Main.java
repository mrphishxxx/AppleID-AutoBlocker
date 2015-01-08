package com.acedened.autoblocker;

import com.acedened.vkutils.*;
import org.apache.commons.cli.*;

public class Main {

    Option domain;
    Option groupId;
    Option momentaryBlock;
    Option postsToScan;

    long group;
    int posts;
    boolean momentary;

    public Main(String[] args) {
        getCommandLineArgs(args);
    }

    private void getCommandLineArgs(String[] args) {
        domain = new Option("d", "domain", true, "адрес группы (без vk.com) (не ID, для этого есть аргумент -g)");
        groupId = new Option("g", "group-id", true, "ID группы (не адрес, для этого есть аргумент -d)");
        momentaryBlock =
                new Option("m", "momentary", false,
                        "пытаться блокировать Apple ID сразу после его нахождения (по умолчанию все Apple ID блокируются после сканирования)");
        postsToScan = new Option("c", "count", true, "количество постов для сканирования");
        Options options = new Options();
        options.addOption(domain);
        options.addOption(groupId);
        options.addOption(momentaryBlock);
        options.addOption(postsToScan);
        try {
            CommandLine cLine = new GnuParser().parse(options, args);
            HelpFormatter formatter = new HelpFormatter();
            formatter.setSyntaxPrefix("Справка по ");
            if (!cLine.hasOption('d') && !cLine.hasOption('g')) {
                formatter.printHelp("Apple ID AutoBlocker", options);
                System.exit(-1);
            }
            if (cLine.hasOption('m'))
                momentary = true;
        } catch (ParseException e) {
            System.out.println("Ошибка аргументов: " + e.getLocalizedMessage());
            System.exit(-1);
        }
    }

    private static final String EMAIL_REGEX =
            "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    public static void main(String[] args) {
        new Main(args);
    }
}
