package com.acedened.autoblocker;

import org.apache.commons.cli.*;

public class ConsoleInterface {

    Option domain;
    //Option groupId;
    Option momentaryBlock;
    Option postsToScan;
    Option altSite;

    //long group;
    int posts;
    boolean momentary = false;
    String domainString;
    String site;

    public ConsoleInterface(String[] args) {
        getCommandLineArgs(args);
        ConsoleOutput output = new ConsoleOutput();
        Process process = new Process(domainString, posts, momentary, site, output);
        //process.start();

    }

    //от id для групп предлагаю отказаться - много мароки, т.к. есть паблики, клубы и т.п. Тем более вряд ли кому в голову придёт указывать их вместо ссылки
    //в качестве домена лучше просить полную ссылку - народу удобней будет копировать
    private void getCommandLineArgs(String[] args) {
        domain = new Option("d", "domain", true, "адрес группы (полный (без http), например vk.com/ihateapple)");
        //groupId = new Option("g", "group-id", true, "ID группы (не адрес, для этого есть аргумент -d)");
        momentaryBlock =
                new Option("m", "momentary", false,
                        "пытаться блокировать Apple ID сразу после его нахождения (по умолчанию все Apple ID блокируются после сканирования)");
        postsToScan = new Option("c", "count", true, "количество постов для сканирования");
        altSite = new Option("s", "site", true, "отправлять запросы на блокирование другому сайту (на случай, если главный упал)");
        Options options = new Options();
        options.addOption(domain);
        //options.addOption(groupId);
        options.addOption(momentaryBlock);
        options.addOption(postsToScan);
        options.addOption(altSite);
        try {
            CommandLine cLine = new GnuParser().parse(options, args);
            HelpFormatter formatter = new HelpFormatter();
            formatter.setSyntaxPrefix("Справка по ");
            if (!cLine.hasOption('d') /*&& !cLine.hasOption('g')*/) {
                formatter.printHelp("Apple ID AutoBlocker", options);
                System.exit(-1);
            }
            if (cLine.hasOption('m'))
                momentary = true;
            site = cLine.getOptionValue('s', "untabe.ru/iD/iha.php");
            //group = Long.parseLong(cLine.getOptionValue('g', null));
            domainString = cLine.getOptionValue('d', null);
            posts = Integer.parseInt(cLine.getOptionValue('c', "0"));
        } catch (ParseException e) {
            System.out.println("Ошибка аргументов: " + e.getLocalizedMessage());
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        new ConsoleInterface(args);
    }

    class ConsoleOutput extends Output {
        //TODO: вывод в отдельном треде

        @Override
        void connectedToGroup() {
            System.out.println("Коннектнулся");
        }

        @Override
        void error(Exception e) {
            System.out.println("Ошибка: " + e.getLocalizedMessage());
            e.printStackTrace();
        }

        @Override
        void foundAppleId(String id) {
            System.out.println("Нашёл appleId: " + id);
        }

        @Override
        void startedBlockingAppleId(String id) {
            System.out.println("Начинаю блокировать: " + id);
        }

        @Override
        void attemptBlockingAppleId(String id, String password) {
            System.out.println("Симмулирую попытку входа в " + id + " с паролем " + password);
        }

        @Override
        void endedBlockingAppleId(String id) {
            System.out.println("Закончил блокировать " + id);
        }

        @Override
        void ended(String[] listOfIds) {
            //TODO: ввыводить обработанные акки если надо
            System.out.println("Закончил");
        }

        @Override
        void siteOutput(String output) {
            System.out.println(output);
        }
    }
}