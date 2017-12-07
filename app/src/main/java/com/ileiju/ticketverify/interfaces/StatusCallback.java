package com.ileiju.ticketverify.interfaces;

public interface StatusCallback<T> {
    void success(T info);

    void result(int status, T info);
}
