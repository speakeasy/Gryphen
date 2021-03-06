/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.speakeasy.gryphen;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author root
 */
public class URLSiteManager extends Thread {

    private boolean running;
    private static int retries = 3;
    private static ArrayList<URLSite> sites = new ArrayList<URLSite>();

    URLSiteManager() {
    }

    @Override
    public void run() {
        
        running = true;
        while (running) {
            try {
                Thread.sleep(7);
            } catch (InterruptedException iex) {
                ;
            }
            //
        }
        onExit();
    }

    /*
     * @param URL to crawl
     * @param Integer max number of retries.
     * @param Integer number of threads.
     * 
     */
    public void addtoQueue(URL url, int lRetries, int nthreads) {
        try {
            Runnable httpRunnable = new HTTPRunnable();
            //exeSvc.execute(httpRunnable);
            return;
        } catch (Exception ex) {
            System.out.println("Error: Unable to add to thread pool!");
            ex.printStackTrace();
            return;
        }
    }

    /*
     * @param String to make sure it is integer long.
     * 
     */
    public boolean checkLong(String num) {

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

    public boolean setRetries(String lRetries) {
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

    public void setRetries(int nretries) {
        retries = nretries;
    }

    public int getRetries() {
        return retries;
    }

    public void showInfo() {
        System.out.println("");
    }

    public void shutdownThreadPool() {
        running = false;
    }

    private void onExit() {
        //exeSvc.shutdown();
    }

    public void shutdownThreadPoolNow() {
        //exeSvc.shutdownNow();
    }
}
