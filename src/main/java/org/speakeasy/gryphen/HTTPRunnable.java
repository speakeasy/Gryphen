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

    private boolean doFetch() {
        try {
            // Send data
            URLConnection urlConn = url.openConnection();
            urlConn.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; " + "Windows NT 5.1; en-US; rv:1.8.0.11) ");
            urlConn.setRequestProperty("Referrer", url.toExternalForm());
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setChunkedStreamingMode(1496);
            urlConn.setDoOutput(true);
            httpConn.connect();
            DataOutputStream wr = new DataOutputStream(httpConn.getOutputStream());
            //System.out.print(urlConn.getHeaderFields().values() + "");
            //System.out.print(urlConn + "");

            wr.writeBytes("");
            wr.flush();

            // Get the response
            DataInputStream rd = new DataInputStream(urlConn.getInputStream());

            DataOutputStream fout = new DataOutputStream(new FileOutputStream(file));

            while (rd.available() > 0) {
                fout.writeByte(rd.readByte());
            }

            wr.close();
            rd.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
