package com.orange.flickrtask.utils;

public class NetworkState{

    public enum Status {
        RUNNING,
        SUCCESS,
        FAILED
    }

    public static final NetworkState LOADED,LOADING;
    private Status status;
    private String msg;

    public NetworkState(Status status,String msg) {
        this.status = status;
        this.msg = msg;
    }

    static {
        LOADED = new NetworkState(Status.SUCCESS, "Success");
        LOADING = new NetworkState(Status.RUNNING, "Running");
    }

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
