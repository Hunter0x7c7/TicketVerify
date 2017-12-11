package com.ileiju.ticketverify.interfaces;

public interface StatusCallback2<T, E> {
    void success(T result);

    void result(int status, E result);
}
