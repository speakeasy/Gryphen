package org.speakeasy.gryphen;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Console extends Thread {

    URLSiteManager urlmgr = new URLSiteManager();

    public Console() {
    }

    @Override
    public void run() {

        try {
            java.io.Console c = System.console();
            if (c == null) {
                System.err.println("No console.");
                System.exit(1);
            } else {

                urlmgr.setRetries(3);
                String input = "";
                while (!urlmgr.setRetries(input)) {
                    System.out.println("Please enter the max number of retries:");
                    input = readEntry();
                }
                System.out.println("Using retry limit of " + urlmgr.getRetries() + " times.");
                System.out.println("For help type \"help\"");

                String[] inputarr;
                URL aurl;
                while (true) {
                    System.out.println("Please enter a command:");
                    input = readEntry();
                    inputarr = input.split(" ");

                    if ("CRAWL".equalsIgnoreCase(inputarr[0])) {
                        try {
                            if (inputarr.length > 1 && inputarr.length < 3) {
                                if (inputarr[1].toLowerCase().startsWith("http://")) {
                                    aurl = new URL(inputarr[1]);
                                    urlmgr.addtoQueue(aurl, urlmgr.getRetries());
                                } else if (inputarr[1].toLowerCase().startsWith("https://")) {
                                    System.out.println("Error: HTTPS not yet implemented!");
                                } else if (inputarr[1].toLowerCase().matches("[a-z0-9-]{3,}[.][a-z0-9-]{2,}.*")) {
                                    aurl = new URL("http://" + inputarr[1]);
                                    urlmgr.addtoQueue(aurl, urlmgr.getRetries());
                                }
                            } else {
                                System.out.println("Usage: >crawl http://example.com/");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else if ("INFO".equalsIgnoreCase(inputarr[0])) {
                        urlmgr.showInfo();
                    } else if ("HELP".equalsIgnoreCase(inputarr[0])) {
                        System.out.println("The following commands are available:");
                        System.out.println("\"help\" prints this message.");
                        System.out.println("crawl <url to crawl>");
                        System.out.println("\"quit\" stop crawling and exit the program gracefully.");
                        System.out.println("\"exit\" interrupt crawling and exit the program immediately.");
                    } else if ("EXIT".equalsIgnoreCase(inputarr[0])) {
                        throw new InterruptedException();
                    } else if ("QUIT".equalsIgnoreCase(inputarr[0])) {
                        System.out.println("Shutting down threads.");
                        urlmgr.shutdownThreadPool();
                        System.out.println("Quit. \n");
                        System.exit(1);
                    }
                } // loop
            }

        } catch (InterruptedException ex) {
            System.out.println("The application will now exit. \n");
            urlmgr.shutdownThreadPoolNow();
            System.exit(1);
        }
    }

    /*
     * @return string of console input.
     * 
     */
    private static String readEntry() {
        try {
            StringBuilder buffer = new StringBuilder();
            System.out.flush();
            int c = System.in.read();
            while (c != '\n' && c != -1) {
                buffer.append((char) c);
                c = System.in.read();
            }
            return buffer.toString().trim();
        } catch (java.io.IOException e) {
            return "";
        }
    }

}
