package org.speakeasy.gryphen;

import java.net.URL;
import java.util.ArrayList;

class URLSite {
    public URL baseURL;
    public ArrayList<URL> tocrawl = new ArrayList<URL>();
    public ArrayList<URL> crawled = new ArrayList<URL>();
    public int retries = 3;
    public URLSite() {
        
    }
    public URLSite(URL thebaseURL) {
        baseURL = thebaseURL;
    }
    
    public void setRetries(int retries) {
        this.retries = retries;
    }
    
    public void addToQueue(URL url) {
        for(int i = 0; i < tocrawl.size(); i++) {
            if(tocrawl.get(i).toExternalForm().equals(url.toExternalForm())) {
                return;
            }
        }
        tocrawl.add(url);
    }
    
    public void moveToCrawled(URL url) {
        for(int i = 0; i < tocrawl.size(); i++) {
            if(tocrawl.get(i).toExternalForm().equals(url.toExternalForm())) {
                crawled.add(tocrawl.remove(i));
                return;
            }
        }
    }
    
}
