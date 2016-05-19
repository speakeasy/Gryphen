package org.speakeasy.gryphen;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Console extends Thread {

    private static int retries;
    private static int nthreads;
    private static ArrayList<HTTPRunnable> rQueue;
    private static ExecutorService threadPool;
    private static ArrayList<URLSite> sites = new ArrayList<URLSite>();

    public Console() {
    }

    @Override
    public void run() {
        threadPool = Executors.newWorkStealingPool();

        try {
            java.io.Console c = System.console();
            if (c == null) {
                System.err.println("No console.");
                System.exit(1);
            }

            retries = 3;
            String input = "";
            while (!setRetries(input)) {
                System.out.println("Please enter the max number of retries:");
                input = readEntry();
            }
            System.out.println("Using retry limit of " + retries + " times.");
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
                                addtoQueue(aurl, retries);
                            } else if (inputarr[1].toLowerCase().startsWith("https://")) {
                                System.out.println("Error: HTTPS not yet implemented!");
                            } else if (inputarr[1].toLowerCase().matches("[a-z0-9-]{3,}[.][a-z0-9-]{2,}.*")) {
                                aurl = new URL("http://" + inputarr[1]);
                                addtoQueue(aurl, retries);
                            }
                        } else {
                            System.out.println("Usage: >crawl http://example.com/");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
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
                    threadPool.shutdown();
                    System.out.println("Quit. \n");
                    System.exit(1);
                }
            } // loop

        } catch (InterruptedException ex) {
            System.out.println("The application will now exit. \n");
            threadPool.shutdownNow();
            System.exit(1);
        }
    }

    /*
     * @param URL to crawl
     * @param Integer max number of retries.
     * 
     * 
     */
    public void addtoQueue(URL url, int lRetries) {
        try {
            Runnable httpRunnable = new HTTPRunnable();
            threadPool.execute(httpRunnable);
            return;
        } catch (Exception ex) {
            System.out.println("Error: Unable to add to thread pool!");
            ex.printStackTrace();
            return;
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

    /*
     * @param String to make sure it is integer long.
     * 
     */
    private boolean checkLong(String num) {

        if (!"".equals(num)) {
            try {
                long i;
                i = Long.parseLong(num);
                return true;
            } catch (Exception ex) {
            }
        }
        return false;
    }

    private boolean setRetries(String lRetries) {
        if (checkLong(lRetries)) {
            try {
                this.retries = (int) Long.parseLong(lRetries);
                if (15 < this.retries) {
                    this.retries = 15;
                }
                return true;
            } catch (NumberFormatException nfex) {
                ;
            }
        }
        return false;
    }

    private boolean setThreads(String lThreads) {
        if (checkLong(lThreads)) {
            try {
                nthreads = (int) Long.parseLong(lThreads);
                if (4 > nthreads) {
                    nthreads = 4;
                }
                return true;
            } catch (NumberFormatException nfex) {
                ;
            }
        }
        return false;
    }

}
