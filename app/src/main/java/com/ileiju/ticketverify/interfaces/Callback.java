package com.ileiju.ticketverify.interfaces;

public interface Callback<T, E> {
    void success(T result);

    void result(int status, E e);
}
