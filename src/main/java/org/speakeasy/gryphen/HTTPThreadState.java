package org.speakeasy.gryphen;

public enum HTTPThreadState {

    STOPPED,
    INIT,
    CONNECTING,
    WAITING,
    DOWNLOADING,
    RETRYING,
    DONE;

}
