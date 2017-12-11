package com.ileiju.ticketverify.interfaces;

public interface StatusCallback1<T> {
    void success(T result);

    void result(int status, String result);
}
