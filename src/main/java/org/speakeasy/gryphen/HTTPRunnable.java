package org.speakeasy.gryphen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HTTPRunnable implements Runnable {

    private URL url = null;
    private int retries = 0;
    private File file;
    private HTTPThreadState state = HTTPThreadState.STOPPED;

    public HTTPRunnable() {

    }

    public HTTPRunnable(URL theurl, int theretries) {
        url = theurl;
        retries = theretries;
        state = HTTPThreadState.INIT;
    }

    @Override
    public void run() {
        doFetch();
    }

    private void doFetch() {
        try {
            // Send data
            URLConnection urlConn = url.openConnection();
            urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36");
            urlConn.setRequestProperty("Referrer", url.toExternalForm());
            urlConn.setRequestProperty("Accept-Charset", "UTF-8");
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setChunkedStreamingMode(1496);
            urlConn.setDoOutput(true);
            httpConn.connect();
            state = HTTPThreadState.CONNECTING;
            DataOutputStream wr = new DataOutputStream(httpConn.getOutputStream());
            //System.out.print(urlConn.getHeaderFields().values() + "");
            //System.out.print(urlConn + "");

            wr.writeBytes("");
            wr.flush();

            // Get the response
            DataInputStream rd = new DataInputStream(urlConn.getInputStream());

            DataOutputStream fout = new DataOutputStream(new FileOutputStream(file));

            state = HTTPThreadState.DOWNLOADING;
            while (rd.available() > 0) {
                if(rd.available() > 8) {
                    fout.writeLong(rd.readLong());
                }
                fout.writeByte(rd.readByte());
            }

            wr.close();
            rd.close();
            state = HTTPThreadState.CLOSED;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setURL(URL theURL) {
        url = theURL;
    }

    public void setRetries(int nretries) {
        retries = nretries;
    }

}
