package org.speakeasy.gryphen;

import java.net.URL;
import java.util.ArrayList;

class URLSite {
    public URL baseURL;
    public ArrayList<URL> tocrawl = new ArrayList<URL>();
    public ArrayList<URL> crawled = new ArrayList<URL>();
    public URLSite() {
        
    }
    public URLSite(URL thebaseURL) {
        baseURL = thebaseURL;
    }
}
