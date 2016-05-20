package org.speakeasy.gryphen;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class URLSite extends Thread {

    private boolean running;
    public URL baseURL;
    public ArrayList<URL> tocrawl = new ArrayList<URL>();
    public ArrayList<URL> crawled = new ArrayList<URL>();
    public int retries = 3;
    public int nthreads = 8;
    public ArrayList<HTTPRunnable> rQueue = new ArrayList<HTTPRunnable>();
    private ExecutorService threadPool = Executors.newWorkStealingPool();

    public URLSite() {

    }

    public URLSite(URL thebaseURL) {
        baseURL = thebaseURL;
    }

    @Override
    public void start() {
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

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public void addToQueue(URL url) {
        for (int i = 0; i < tocrawl.size(); i++) {
            if (tocrawl.get(i).toExternalForm().equals(url.toExternalForm())) {
                return;
            }
        }
        tocrawl.add(url);
    }

    public void moveToCrawled(URL url) {
        for (int i = 0; i < tocrawl.size(); i++) {
            if (tocrawl.get(i).toExternalForm().equals(url.toExternalForm())) {
                crawled.add(tocrawl.remove(i));
                return;
            }
        }
    }

    public void shutdownThreadPool() {
        running = false;
    }

    private void onExit() {
        threadPool.shutdown();
    }

    public void shutdownThreadPoolNow() {
        threadPool.shutdownNow();
    }

}
